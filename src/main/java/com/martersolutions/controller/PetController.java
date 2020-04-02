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
import com.martersolutions.model.Owner;
import com.martersolutions.model.Pet;
import com.martersolutions.model.RNPet;
import com.martersolutions.repository.MediaRepository;
import com.martersolutions.repository.OwnerRepository;
import com.martersolutions.repository.PetRepository;
import com.martersolutions.repository.PetVaccinationRepository;
import com.martersolutions.utils.Constants;

@RestController
@RequestMapping("/owners")
public class PetController {

	@Autowired
	MediaRepository mediaRepo;

	@Autowired
	PetRepository petRepo;

	@Autowired
	OwnerRepository ownerRepo;

	@Autowired
	PetVaccinationRepository vaccinationRepo;

	private ObjectMapper mapper = new ObjectMapper();

	private Process process;

	@PostMapping("/addpets")
	public ResponseEntity<String> addPet(@RequestParam("petProPic") MultipartFile file,
			@RequestParam("petInfo") String petInfo) throws JsonProcessingException, IOException, InterruptedException {

		try {
			String newDate = LocalDateTime.now().toString().replaceAll(":", "-").replace(".", "-");
			String fullPath = Constants.PET_PROPIC_FOLDER + file.getOriginalFilename();

			Media media = new Media();

			Pet pet = new Pet();

			pet = mapper.readValue(petInfo, Pet.class);
			byte[] bytes = file.getBytes();
			Path path = Paths.get(fullPath);
			Files.write(path, bytes);

			process = Runtime.getRuntime().exec(new String[] { "ffmpeg", "-i", fullPath, "-vf", "scale='320:-1'",
					Constants.PET_PROPIC_FOLDER + pet.getOwnerId() + newDate + file.getOriginalFilename() });

			while (process.isAlive()) {
				Thread.sleep(1000);
			}

			media.setMediaUrl(pet.getOwnerId() + newDate + file.getOriginalFilename());
			media.setType("PET_PROPIC");
			media.setCreatedDate(newDate);

			String picId = mediaRepo.save(media).getId();

			pet.setProPicId(picId);

			Files.delete(path);

			String petId = petRepo.save(pet).getId();

			Owner owner = ownerRepo.findById(pet.getOwnerId()).get();
			owner.getPets().add(petId);
			ownerRepo.save(owner);

			return new ResponseEntity<String>(mapper.writeValueAsString("SAVE"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<String>(mapper.writeValueAsString("NOT_SAVE"), HttpStatus.BAD_REQUEST);
		}
	}

	// retrieve all pets by ownerId
	@GetMapping("/petlist/{Id}")
	public List<RNPet> petList(@PathVariable("Id") String Id) {
		System.out.println("Id " + Id);
		List<RNPet> rnPets = new ArrayList<RNPet>();

		Owner owner = ownerRepo.findById(Id).get();

		List<String> petList = owner.getPets();
		System.out.println("Pet List " + petList);
		Iterator<String> id = petList.iterator();

		while (id.hasNext()) {

			// retrive one of pets Id.

			String petId = id.next();
			System.out.println(petId);
			Pet pet = petRepo.findById(petId).get();
			System.out.println("petpropic " + pet.getProPicId());
			RNPet RNPet = new RNPet(pet.getId(), pet.getPetType(), pet.getPetName(), pet.getPetAge(),
					pet.getPetBirthDate(), pet.getPetGender(), pet.getPetWeight(), pet.getPetDescription(),
					pet.getProPicId());
			rnPets.add(RNPet);

			System.out.println(RNPet.toString());
		}

		System.out.println("Number of pets " + rnPets.size());
		return rnPets;
	}

	// retrieving only pet proPic by pet Id.
	@GetMapping("/showPetProPic/{Id}")
	public ResponseEntity<Resource> showPetPic(@PathVariable("Id") String Id) {
		Resource file = null;
		System.out.println("Id to fetch proPic " + Id);

		Media media = mediaRepo.findById(Id).get();

		file = loadAsResource(media.getMediaUrl());
		System.out.println(file);
		// Media media=mediaRepo.findById(id).get();
		// System.out.println(media.getMediaUrl());

		// file=loadAsResource(media.getMediaUrl());
		// System.out.println(file);

		return ResponseEntity.ok().body(file);
	}

	public Resource loadAsResource(String filename) {
		String path = Constants.PET_PROPIC_FOLDER + filename;
		System.out.println(path);

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
