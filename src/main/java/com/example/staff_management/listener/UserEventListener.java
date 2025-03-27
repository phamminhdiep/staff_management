package com.example.staff_management.listener;

import com.example.staff_management.event.UserCreatedEvent;
import com.example.staff_management.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    private final EmailService emailService;

    public UserEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) throws MessagingException {
//        System.out.println("Email thread name " + Thread.currentThread().getName());
        emailService.sendEmailTemplate(event.getStaff());
    }
}