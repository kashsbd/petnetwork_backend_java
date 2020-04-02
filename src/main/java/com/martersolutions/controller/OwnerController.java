package com.martersolutions.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martersolutions.model.Media;
import com.martersolutions.model.Owner;
import com.martersolutions.repository.MediaRepository;
import com.martersolutions.repository.OwnerRepository;
import com.martersolutions.utils.Constants;

@RestController
@RequestMapping("/owners")
public class OwnerController {

	@Autowired
	private OwnerRepository ownerRepo;

	@Autowired
	private MediaRepository mediaRepo;

	private ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/showProfile/{id}")
	public ResponseEntity<Object> showProfile(@PathVariable("id") String Id) throws JsonProcessingException {
		Owner owner;
		try {
			owner = ownerRepo.findById(Id).get();
		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<Object>(mapper.writeValueAsString(owner), HttpStatus.OK);
	}
	
	@GetMapping("/getOwnerPic/{Id}")
	public ResponseEntity<Resource> getOwnerPic(@PathVariable("Id") String Id) {

		Resource file = null;

		Owner owner = ownerRepo.findById(Id).get();

		Media media = mediaRepo.findById(owner.getProfilePicId()).get();

		file = loadAsResource(media.getMediaUrl());
		System.out.println(file);

		return ResponseEntity.ok().body(file);

	}

	public Resource loadAsResource(String filename) {
		String path = Constants.OWNER_PROPIC_FOLDER + filename;
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

	// retrieve ownerInfo by ownerId
	@GetMapping("/aboutOwner/{id}")
	public ResponseEntity<Object> showOwnerInfo(@PathVariable("id") String Id) {
		Map<String, String> map = new HashMap<String, String>();

		try {
			Owner owner = ownerRepo.findById(Id).get();
			map.put("location", owner.getCountry() + ", " + owner.getCity());
			map.put("phNo", owner.getPhNo());
			map.put("DOB", owner.getDob());
			map.put("Description", owner.getDescription());

			return new ResponseEntity<Object>(map, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>("NOT_OK", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// follow pet owner
	@PostMapping("/followOwner")
	public ResponseEntity<String> followOwner(@RequestParam("followerId") String followerId,
			@RequestParam("followedId") String followedId) throws JsonProcessingException {
		try {
			// owner who follow other owner
			Owner follower = ownerRepo.findById(followerId).get();
			follower.getFollowingLists().add(followedId);
			ownerRepo.save(follower);
			// owner who has been followed by other owner
			Owner followed = ownerRepo.findById(followedId).get();
			followed.getFollowerLists().add(followerId);
			ownerRepo.save(followed);
			System.out.println("Successfully followed");
			return new ResponseEntity<String>(mapper.writeValueAsString("Success"), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Unable to follow");
			return new ResponseEntity<String>(mapper.writeValueAsString("Unable to follow"), HttpStatus.NOT_FOUND);
		}
	}

	// unfollow pet owner
	@PostMapping("/unfollowOwner")
	public ResponseEntity<String> unfollowOwner(@RequestParam("followerId") String unFollowerId,
			@RequestParam("followedId") String unFollowedId) throws JsonProcessingException {

		try {
			// owner who unfollow other owner
			Owner unFollower = ownerRepo.findById(unFollowerId).get();
			unFollower.getFollowingLists().remove(unFollowedId);
			ownerRepo.save(unFollower);

			// owner who has been unfollowed by other owner
			Owner unFollowed = ownerRepo.findById(unFollowedId).get();
			unFollowed.getFollowerLists().remove(unFollowerId);
			ownerRepo.save(unFollowed);
			System.out.println("Successfully unfollow");
			return new ResponseEntity<String>(mapper.writeValueAsString("Success"), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to Unfollow");
			return new ResponseEntity<String>(mapper.writeValueAsString("Unable to unfollow"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// get all owner
	@GetMapping("")
	public Iterable<Owner> getAllOwner() {
		return ownerRepo.findAll();

	}

}