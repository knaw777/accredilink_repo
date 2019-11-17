package com.accredilink.bgv.service;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.dto.RegistrationDTO;
import com.accredilink.bgv.util.Constants;
@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	String userMailFrom;
		
	@Transactional
	public boolean registrationNotification(RegistrationDTO registrationDTO) {
		
		try {
			StringBuilder mailBody = new StringBuilder();
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper(mimeMessage,true);
			msgHelper.setFrom(userMailFrom);
			msgHelper.setTo(registrationDTO.getEmailId());
			msgHelper.setSubject(Constants.ACCREDILINK_REGISTRATION_SUCCESSFUL);
			
			mailBody.append("Username : " + registrationDTO.getEmailId() + "\n");
			mailBody.append("Password : " + registrationDTO.getPassword());
			
			msgHelper.setText(mailBody.toString());
			mailSender.send(mimeMessage);
			
		} catch(Exception e) {
			System.out.println("Exception raised in notification : " + e);
			logger.error("Exceptin raised in notification : " + e);
			return false;
		}
		return true;
	}
}