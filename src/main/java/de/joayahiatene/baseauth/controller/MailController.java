package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MailController {

    static final String EMAILSEND = "Email sent.";
    static final String EMAILFAILED = "Email sending failed.";

    private MailService mailService;

    @Autowired
    public MailController(final MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendEmail")
    public void sendEmail() {
        mailService.sendMail("Test","Test","noreplybaseauth@gmail.com");
    }
}
