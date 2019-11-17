package com.accredilink.bgv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accredilink.bgv.dto.RegistrationDTO;
import com.accredilink.bgv.service.RegistrationService;
import com.accredilink.bgv.util.ResponseObject;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	@PostMapping("/register")
	public ResponseObject registration(@RequestBody RegistrationDTO registrationDTO) throws Exception {
		String message = registrationService.registration(registrationDTO);
		ResponseObject responseObj = new ResponseObject();
		responseObj.setMessage(message);
		return responseObj;
	}

}