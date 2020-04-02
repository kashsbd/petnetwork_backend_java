package com.martersolutions.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martersolutions.config.JwtTokenUtil;
import com.martersolutions.model.Media;
import com.martersolutions.model.Owner;
import com.martersolutions.model.OwnerCredential;
import com.martersolutions.repository.MediaRepository;
import com.martersolutions.repository.OwnerCredentialRepository;
import com.martersolutions.repository.OwnerRepository;
import com.martersolutions.utils.Constants;

@RestController
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	private OwnerCredentialRepository creRepo;

	@Autowired
	MediaRepository mediaRepo;

	@Autowired
	OwnerRepository ownerRepo;

	@Autowired
	private JwtTokenUtil JwtTokenUtil;

	private ObjectMapper mapper = new ObjectMapper();

	private Process process;

	@PostMapping("/credential")
	public ResponseEntity<String> signupFirstPage(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletResponse rep) throws JsonProcessingException {
		List<OwnerCredential> owner = creRepo.findByEmail(email);
		if (owner.size() == 1) {
			return new ResponseEntity<String>(mapper.writeValueAsString("EXIST"), HttpStatus.FOUND);
		} else {
			try {
				rep.setHeader("Token", JwtTokenUtil.generateToken(new OwnerCredential(email, password)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			OwnerCredential credential = new OwnerCredential();
			credential.setEmail(email);
			credential.setPassword(password);
			String creId = creRepo.save(credential).getId();
			System.out.println("Return Credential Id " + creId);
			return new ResponseEntity<String>(mapper.writeValueAsString(creId), HttpStatus.OK);
		}
	}

	@PostMapping("/saveOwnerInfo")
	public ResponseEntity<String> storeOwnerInfo(@RequestParam("ownerInfo") String ownerInfo,
			@RequestParam("ownerProPic") MultipartFile file, HttpServletResponse res)
			throws JsonProcessingException, IOException, InterruptedException {

		String ownerId;
		try {
			String newDate = LocalDateTime.now().toString().replaceAll(":", "-").replace(".", "-");
			String fullPath = Constants.OWNER_PROPIC_FOLDER + file.getOriginalFilename();

			Owner info = null;

			Media media = new Media();

			info = mapper.readValue(ownerInfo, Owner.class);

			byte[] bytes = file.getBytes();
			Path path = Paths.get(fullPath);
			Files.write(path, bytes);

			process = Runtime.getRuntime().exec(new String[] { "ffmpeg", "-i", fullPath, "-vf", "scale='-1:-1'",
					Constants.OWNER_PROPIC_FOLDER + info.getCredentialId() + newDate + file.getOriginalFilename() });

			while (process.isAlive()) {
				Thread.sleep(10000);
			}

			media.setMediaUrl(info.getCredentialId() + newDate + file.getOriginalFilename());
			media.setCreatedDate(LocalDateTime.now().toString());
			media.setType("OWNER_PROPIC");
			// 1. save proPic to mediaRepo
			String proPicId = mediaRepo.save(media).getId();

			// 2.saving proPic Id to ownerRepo
			info.setProfilePicId(proPicId);
			ownerId = ownerRepo.save(info).getId();

			OwnerCredential credential = creRepo.findById(info.getCredentialId()).get();

			// 3.save ownerId to creRepo since we haven't "owner Id" in credential Repo :)
			credential.setOwnerId(ownerId);
			creRepo.save(credential);

			Files.delete(path);

			try {
				String accessToken = JwtTokenUtil
						.generateToken(new OwnerCredential(credential.getEmail(), credential.getPassword()));
				String refreshToken = JwtTokenUtil
						.generateRefreshToken(new OwnerCredential(credential.getEmail(), credential.getPassword()));

				res.setHeader("accessToken", accessToken);
				res.setHeader("refreshToken", refreshToken);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return new ResponseEntity<String>(mapper.writeValueAsString(ownerId), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Sometheing woring in saving ownerinfo");
			e.printStackTrace();
			return new ResponseEntity<String>(mapper.writeValueAsString("CANNOT_SAVE_OWNER_INFO"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
