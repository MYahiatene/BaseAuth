package de.joayahiatene.baseauth.domain.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    private static final String MAILADRESS = "noreplybaseauth@gmail.com";
    private List<String> mailAdresses;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void setMailAdresses(final List<String> mailList) {
        this.mailAdresses = mailList;
    }

    @Override
    public List<String> getMailAdresses() {
        return mailAdresses;
    }

    @Override
    public void sendMail(final String mailSubject, final String mailMessage, final String emailAdress) {
        final SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(MAILADRESS);
        message.setTo(emailAdress);
        message.setSubject(mailSubject);
        message.setText(mailMessage);

        this.javaMailSender.send(message);
    }
}
