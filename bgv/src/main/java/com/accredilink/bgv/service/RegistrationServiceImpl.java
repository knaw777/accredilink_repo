package com.accredilink.bgv.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.dto.RegistrationDTO;
import com.accredilink.bgv.entity.Address;
import com.accredilink.bgv.entity.Company;
import com.accredilink.bgv.entity.User;
import com.accredilink.bgv.entity.UserType;
import com.accredilink.bgv.exception.CustomException;
import com.accredilink.bgv.repository.UserRepository;
import com.accredilink.bgv.util.Constants;
import com.accredilink.bgv.util.EmailValidator;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	@Autowired
	NotificationService notificationService;

	@Autowired
	UserRepository userRepository;

	/**
	 * @param registrationDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String registration(RegistrationDTO registrationDTO) throws Exception {

		/*
		 * Checking email id is valid or not, if it is invalid then throwing exception.
		 */
		boolean isValid = isEmailValid(registrationDTO.getEmailId());
		/*
		 * if (!isValid) { throw new CustomException(Constants.INVALID_EMAIL_ID); }
		 */

	

		try {

			User user = new User();
			User saveUser = null;

			user.setPassword(registrationDTO.getPassword());
			user.setEmailId(registrationDTO.getEmailId());
			user.setPhoneNumber(registrationDTO.getPhoneNo());
			user.setUserName(registrationDTO.getUserName());

			/*
			 * Checking the User, If the User is Employee then inserting records into only
			 * User table
			 */
			if (registrationDTO.getType().equalsIgnoreCase(Constants.INDIVIDUAL)) {

				user.setFirstName(registrationDTO.getFirstName());
				user.setLastName(registrationDTO.getLastName());
				user.setDateOfBirth(registrationDTO.getDateOfBirth());
				user.setSsnNumber(registrationDTO.getSsnNumber());
				user.setAddress(addressBinding(registrationDTO));
				user.setUserType(userTypeBinding(registrationDTO));
				saveUser = userRepository.save(user);
				/*
				 * Checking the User, If the User is Company then inserting records into User,
				 * Company, Address and UserType tables.
				 */
			} else if (registrationDTO.getType().equalsIgnoreCase(Constants.COMPANY)) {
				user.setEmailId(registrationDTO.getEmailId());
				user = companyRegistration(registrationDTO, user);
				saveUser = userRepository.save(user);
			}

		} catch (Exception e) {
			logger.error("Exception raised in registration ", e);
			throw new CustomException("Exception raised in registration");
		}

		boolean flag = notificationService.registrationNotification(registrationDTO);
		if (flag) {
			logger.info("Successfully sent email notification after registration.");
		} else {
			logger.error("ERROR : while sending notification at registration");
		}

		return "Registration is success";
	}

	private boolean isEmailValid(String emailId) {
		EmailValidator emailValidator = new EmailValidator();
		boolean status = false;
		if (emailId != null) {
			status = emailValidator.validate(emailId);
		}
		return status;
	}

	private User companyRegistration(RegistrationDTO registrationDTO, User user) {

		Company company = new Company();
		company.setCompany_name(registrationDTO.getCompanyName());
		company.setEin(registrationDTO.getEin());
		company.setCreatedDate(registrationDTO.getCreatedDate());
		company.setModifiedDate(registrationDTO.getModifiedDate());
		company.setCreatedBy(registrationDTO.getCreatedBy());
		company.setModifiedBy(registrationDTO.getModifiedBy());
		company.setActive(registrationDTO.isActive());

		user.setAddress(addressBinding(registrationDTO));
		user.setCompany(company);
		user.setUserType(userTypeBinding(registrationDTO));

		return user;

	}

	private Address addressBinding(RegistrationDTO registrationDTO) {
		Address address = new Address();
		address.setAddress_line1(registrationDTO.getAddressLine1());
		address.setAddressLine2(registrationDTO.getAddressLine2());
		address.setCity(registrationDTO.getCity());
		address.setState(registrationDTO.getState());
		address.setCountry(registrationDTO.getCountry());
		address.setZip(registrationDTO.getZip());
		address.setCreatedDate(registrationDTO.getCreatedDate());
		address.setModifiedDate(registrationDTO.getModifiedDate());
		address.setCreatedBy(registrationDTO.getCreatedBy());
		address.setModifiedBy(registrationDTO.getModifiedBy());
		address.setActive(registrationDTO.isActive());
		return address;
	}

	private UserType userTypeBinding(RegistrationDTO registrationDTO) {
		UserType userType = new UserType();
		userType.setUserTypeName(registrationDTO.getType());
		userType.setUserTypeDescription(registrationDTO.getUserTypeDescription());
		userType.setCreatedDate(registrationDTO.getCreatedDate());
		userType.setModifiedDate(registrationDTO.getModifiedDate());
		userType.setCreatedBy(registrationDTO.getCreatedBy());
		userType.setModifiedBy(registrationDTO.getModifiedBy());
		userType.setActive(registrationDTO.isActive());
		return userType;
	}

}