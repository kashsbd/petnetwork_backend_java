package com.martersolutions.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martersolutions.config.JwtTokenUtil;
import com.martersolutions.model.Owner;
import com.martersolutions.model.OwnerCredential;
import com.martersolutions.repository.OwnerCredentialRepository;
import com.martersolutions.repository.OwnerRepository;
import com.martersolutions.utils.Constants;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private JwtTokenUtil JwtTokenUtil;

	@Autowired
	private OwnerCredentialRepository creRepo;

	@Autowired
	private OwnerRepository ownerRepo;

	private ObjectMapper mapper = new ObjectMapper();
	private Map<String, String> returnData = new HashMap<String, String>();

	@PostMapping("")
	public ResponseEntity<String> Login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpServletResponse rep) throws JsonProcessingException {

		List<OwnerCredential> storeUser = creRepo.findByEmailAndPassword(email, password);
		if (storeUser.size() == 1) {
			try {
				String accessToken = JwtTokenUtil.generateToken(new OwnerCredential(email, password));
				String refreshToken = JwtTokenUtil.generateRefreshToken(new OwnerCredential(email, password));

				rep.setHeader("accessToken", accessToken);
				rep.setHeader("refreshToken", refreshToken);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			OwnerCredential loggedUser = storeUser.get(0);
			Owner owner = ownerRepo.findById(loggedUser.getOwnerId()).get();

			returnData.put("ownerId", owner.getId());
			returnData.put("ownerName", owner.getFirstName() + " " + owner.getLastName());

			return new ResponseEntity<String>(mapper.writeValueAsString(returnData), HttpStatus.OK);
		}

		return new ResponseEntity<String>(mapper.writeValueAsString("USER_NOT_FOUND"), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/refresh")
	public void refresh(HttpServletRequest req, HttpServletResponse res) {
		String header = req.getHeader(Constants.HEADER_STRING);

		if (header != null && header.startsWith(Constants.TOKEN_PREFIX)) {
			String authToken = header.replace(Constants.TOKEN_PREFIX, "");
			String email = JwtTokenUtil.getEmailFromToken(authToken);
			String newToken = JwtTokenUtil.generateToken(new OwnerCredential(email, "NO NEED"));
			res.setHeader("accessToken", newToken);
		}
	}

}
