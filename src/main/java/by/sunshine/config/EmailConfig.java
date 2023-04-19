package by.sunshine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static by.sunshine.constant.Email.*;

@Configuration
public class EmailConfig {
    @Value("${email.sender.name}")
    private String emailSender;
    @Value("${email.sender.password}")
    private String password;
    @Value("${email.port}")
    private int port;
    @Value("${email.host}")
    private String host;


    @Bean
    public JavaMailSenderImpl sendEmailNumber() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(emailSender);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(getProperties());
        return javaMailSender;
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put(MAIL_TRANSPORT_PROTOCOL, SMTP);
        properties.put(MAIL_SMTP_AUTH, TRUE);
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, TRUE);
        properties.put(MAIL_DEBUG, TRUE);
        return properties;
    }
}
