package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender mailSender;
    private MailContentBuilder contentBuilder;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.subject}")
    private String subject;

    @Value("${spring.mail.activationUrl}")
    private String activationUrl;

    public MailService(JavaMailSender mailSender, MailContentBuilder contentBuilder) {
        this.mailSender = mailSender;
        this.contentBuilder = contentBuilder;
    }

    @Async
    public void sendActivationEmail(
        String emailTo,
        String firstName,
        String lastName,
        String activationCode
    ) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            String content = contentBuilder.buildAccountActivationMail(
                firstName,
                lastName,
                activationUrl,
                activationCode
            );
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(emailTo);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };

        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // TODO: somehow handle this exception
        }
    }
}
