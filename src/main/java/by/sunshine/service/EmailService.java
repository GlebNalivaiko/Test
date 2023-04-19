package by.sunshine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String TITLE = "Sunshine Security";
    private static final String MESSAGE = "<h2 style='color:blue;'>bold-red email</h2>";
    private static final int LOWER_RANGE = 100_000;
    private static final int UPPER_BOUND = 999_999;
    private static final String ENCODING = "UTF-8";
    @Value("${email.sender.name}")
    private String emailSender;

    private final JavaMailSenderImpl javaMailSender;

    public int getAccessNumber(String emailReceiver) {
        int accessNumber = new Random().nextInt(LOWER_RANGE, UPPER_BOUND);
        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, ENCODING);
            message.setTo(emailReceiver);
            message.setFrom(emailSender);
            message.setSubject(TITLE);
            message.setText(MESSAGE + accessNumber, true);
        });
        return accessNumber;
    }
}
