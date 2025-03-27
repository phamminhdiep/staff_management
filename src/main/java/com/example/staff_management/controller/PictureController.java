package com.example.staff_management.controller;

import com.example.staff_management.service.EmailService;
import com.example.staff_management.service.PictureService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pics")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;
    private final EmailService emailService;

    @GetMapping("/rest-template")
    public ResponseEntity<?> getPicture() {
        return ResponseEntity.ok().body(pictureService.getPictureInfoByRestTemplate());
    }

    @GetMapping("/web-client")
    public ResponseEntity<?> getPictureByWebClient() {
        return ResponseEntity.ok().body(pictureService.getPictureInfoByWebClient());
    }

//    @GetMapping("/send-email")
//    public ResponseEntity<?> sendEmail() throws MessagingException {
//
//        emailService.sendEmailTemplate("phamminhdiep24042002@gmail.com","Hello",
//                model);
//        return ResponseEntity.ok().body("Email sent successfully");
//    }
}
