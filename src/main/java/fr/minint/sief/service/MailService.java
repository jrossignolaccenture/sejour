package fr.minint.sief.service;

import static fr.minint.sief.domain.enumeration.ApplicationType.renouvellement;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.CharEncoding;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import fr.minint.sief.domain.Application;
import fr.minint.sief.domain.User;
import fr.minint.sief.repository.UserRepository;

/**
 * Service for sending e-mails.
 * <p/>
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private Environment env;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;
    
    @Inject
    private UserRepository userRepository;

    /**
     * System default email address that sends the e-mails.
     */
    private String from;

    @PostConstruct
    public void init() {
        this.from = env.getProperty("mail.from");
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(User user, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("activationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendPasswordResetMail(User user, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("passwordResetEmail", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendApplicationPaidEmail(Application application, String baseUrl) {
        log.debug("Sending application payment e-mail to '{}'", application.getEmail());
        Locale locale = Locale.forLanguageTag(userRepository.findOneByEmail(application.getEmail()).get().getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", application.getIdentity());
        context.setVariable("baseUrl", baseUrl);
        context.setVariable("paymentDate", DateTimeFormat.forPattern("dd/MM/yyyy").print(application.getPaymentDate()));
        context.setVariable("paymentHour", DateTimeFormat.forPattern("HH:mm").print(application.getPaymentDate()));
        String content = templateEngine.process("paymentEmail", context);
        String subject = messageSource.getMessage("email.payment.title", null, locale);
        sendEmail(application.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendApplicationReceivableEmail(Application application, String baseUrl) {
        log.debug("Sending application admissibility e-mail to '{}'", application.getEmail());
        Locale locale = Locale.forLanguageTag(userRepository.findOneByEmail(application.getEmail()).get().getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", application.getIdentity());
        context.setVariable("applicationId", application.getId());
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process(application.getType() == renouvellement ? "admissibilityRenewalEmail" : "admissibilityEmail", context);
        String subject = messageSource.getMessage("email.admissibility.title", null, locale);
        sendEmail(application.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendApplicationScheduledEmail(Application application, String baseUrl) {
        log.debug("Sending application scheduled e-mail to '{}'", application.getEmail());
        Locale locale = Locale.forLanguageTag(userRepository.findOneByEmail(application.getEmail()).get().getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", application.getIdentity());
        context.setVariable("baseUrl", baseUrl);
        context.setVariable("rdvDate", DateTimeFormat.forPattern("dd/MM/yyyy").print(application.getRdvDate()));
        context.setVariable("rdvHour", DateTimeFormat.forPattern("HH:mm").print(application.getRdvDate()));
        String content = templateEngine.process("rdvEmail", context);
        String subject = messageSource.getMessage("email.rdv.title", null, locale);
        sendEmail(application.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendApplicationValidatedEmail(Application application, String baseUrl) {
        log.debug("Sending application validated e-mail to '{}'", application.getEmail());
        Locale locale = Locale.forLanguageTag(userRepository.findOneByEmail(application.getEmail()).get().getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", application.getIdentity());
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process(application.getType() == renouvellement ? "decisionRenewalEmail" : "decisionEmail", context);
        String subject = messageSource.getMessage("email.decision.title", null, locale);
        sendEmail(application.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendPermitEmail(Application application, String baseUrl) {
        log.debug("Sending permit e-mail to '{}'", application.getEmail());
        Locale locale = Locale.forLanguageTag(userRepository.findOneByEmail(application.getEmail()).get().getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", application.getIdentity());
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process(application.getType() == renouvellement ? "permitRenewalEmail" : "permitEmail", context);
        String subject = messageSource.getMessage("email.permit.title", null, locale);
        sendEmail(application.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendArrivalEmail(Application application, String baseUrl) {
        log.debug("Sending arrival e-mail to '{}'", application.getEmail());
        Locale locale = Locale.forLanguageTag(userRepository.findOneByEmail(application.getEmail()).get().getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", application.getIdentity());
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("arrivalEmail", context);
        String subject = messageSource.getMessage("email.arrival.title", null, locale);
        sendEmail(application.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendRenewalEmail(User user, String baseUrl) {
        log.debug("Sending renewal e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user.getIdentity());
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("renewalEmail", context);
        String subject = messageSource.getMessage("email.renewal.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
}
