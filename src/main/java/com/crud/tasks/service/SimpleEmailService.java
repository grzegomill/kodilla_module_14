package com.crud.tasks.service;


import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation ...");

        try {

            javaMailSender.send(createMailMessage(mail));

            LOGGER.info("Email has been sent.");

        } catch (MailException e) {

            LOGGER.error("Failed  to process  email sending:" + mail.getMailTo(), e.getMessage(), e);

        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {

        final String tmpCC = mail.getToCc();

        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(mail.getFrom());

        mailMessage.setTo(mail.getMailTo());

        if (tmpCC != null && !tmpCC.isEmpty()) {
            mailMessage.setCc(tmpCC);
        }

        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        return mailMessage;
    }


}

