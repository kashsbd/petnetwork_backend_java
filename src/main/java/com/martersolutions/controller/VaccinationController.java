package com.martersolutions.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martersolutions.model.Media;
import com.martersolutions.model.Pet;
import com.martersolutions.model.PetVaccination;
import com.martersolutions.model.RNVaccination;

import com.martersolutions.repository.MediaRepository;
import com.martersolutions.repository.OwnerRepository;
import com.martersolutions.repository.PetRepository;
import com.martersolutions.repository.PetVaccinationRepository;
import com.martersolutions.utils.Constants;

@RestController
@RequestMapping("/pets")
public class VaccinationController {
	@Autowired
	MediaRepository mediaRepo;

	@Autowired
	PetRepository petRepo;

	@Autowired
	OwnerRepository ownerRepo;

	@Autowired
	PetVaccinationRepository vaccinationRepo;

	private Process process;

	private ObjectMapper mapper = new ObjectMapper();

	// add Pet Vaccination
	@PostMapping("/addVaccination")
	public ResponseEntity<String> addVaccination(@RequestParam("vaccineImage") MultipartFile file,
			@RequestParam("vaccinationInfo") String vaccinationInfo)
			throws JsonProcessingException, IOException, InterruptedException {
		System.out.println(vaccinationInfo);
		try {
			String newDate = LocalDateTime.now().toString().replaceAll(":", "-").replace(".", "-");
			String fullPath = Constants.FEEDPIC_FOLDER + file.getOriginalFilename();

			Media media = new Media();

			PetVaccination petVaccination = new PetVaccination();

			petVaccination = mapper.readValue(vaccinationInfo, PetVaccination.class);
			byte[] bytes = file.getBytes();
			Path path = Paths.get(fullPath);
			Files.write(path, bytes);

			process = Runtime.getRuntime().exec(new String[] { "ffmpeg", "-i", fullPath, "-vf", "scale='-1:-1'",
					Constants.PET_VACCINE_FOLDER + petVaccination.getPetId() + newDate + file.getOriginalFilename() });

			while (process.isAlive()) {
				Thread.sleep(10000);
			}

			media.setMediaUrl(petVaccination.getPetId() + newDate + file.getOriginalFilename());
			media.setType("PET_VACCINE_PIC");
			media.setCreatedDate(newDate);

			String vaccinePicId = mediaRepo.save(media).getId();

			petVaccination.setVaccinePicId(vaccinePicId);

			Files.delete(path);

			String vaccinationId = vaccinationRepo.save(petVaccination).getId();

			Pet pet = petRepo.findById(petVaccination.getPetId()).get();
			pet.getVaccinationsId().add(vaccinationId);
			petRepo.save(pet);
			System.out.println("SAVED");
			return new ResponseEntity<String>(mapper.writeValueAsString("SAVE"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(mapper.writeValueAsString("NOT_SAVE"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// vaccination list By PetId
	@RequestMapping("/vaccinationlist/{Id}")
	public List<RNVaccination> vaccinationList(@PathVariable("Id") String Id) {
		List<RNVaccination> vacciList = new ArrayList<RNVaccination>();
		System.out.println("LIst Id" + Id);
		Pet pet = petRepo.findById(Id).get();

		ArrayList<String> vaccinationList = pet.getVaccinationsId();
		System.out.println("Vaccination Id List " + vaccinationList);
		Iterator<String> id = vaccinationList.iterator();

		while (id.hasNext()) {
			String vaccinationId = id.next();
			PetVaccination vacci = vaccinationRepo.findById(vaccinationId).get();
			RNVaccination RNVaccination = new RNVaccination(vacci.getVaccinePicId(), vacci.getVaccineName(),
					vacci.getInjectionDate(), vacci.getNextInjectionDate(), vacci.getVaccinationNote());
			System.out.println("Returning Vaccination List " + RNVaccination.toString());
			vacciList.add(RNVaccination);
		}

		return vacciList;

	}

	// //get all Vaccinenams
	// @GetMapping("/vaccineName")
	// public ArrayList<String> getVaccineNames() {
	// ArrayList<String> list = new ArrayList<String>(
	// Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
	// System.out.println(list);
	//
	// return list;
	//
	// }

	// vaccine pic
	@GetMapping("/showVaccinePic/{Id}")
	public ResponseEntity<Resource> showPetPic(@PathVariable("Id") String Id) {
		Resource file = null;
		System.out.println("Id to fetch VaccinePic " + Id);

		Media media = mediaRepo.findById(Id).get();

		file = loadAsResource(media.getMediaUrl());
		System.out.println(file);

		return ResponseEntity.ok().body(file);
	}

	public Resource loadAsResource(String filename) {
		String path = Constants.PET_VACCINE_FOLDER + filename;
		System.out.println("VaccinePicpath " + path);

		try {
			Path file = Paths.get(path);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				System.out.println("no file");
			}
		} catch (MalformedURLException e) {
			System.out.println(e);
		}
		return null;
	}

}
