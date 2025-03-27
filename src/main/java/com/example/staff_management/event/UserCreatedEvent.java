package com.example.staff_management.event;

import com.example.staff_management.entities.Staff;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private final Staff staff;

    public UserCreatedEvent(Object source, Staff staff) {
        super(source);
        this.staff = staff;
    }
}