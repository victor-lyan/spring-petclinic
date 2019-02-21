package org.springframework.samples.petclinic.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildAccountActivationMail(
        String firstName,
        String lastName,
        String activationUrl,
        String activationCode
    ) {
        String username = firstName + " " + lastName;
        String link = activationUrl + activationCode;

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("link", link);

        return templateEngine.process("mail/account-activation", context);
    }
}
