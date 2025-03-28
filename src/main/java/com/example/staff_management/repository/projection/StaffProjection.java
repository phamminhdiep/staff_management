package com.example.staff_management.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface StaffProjection {

    String getFirstName();
    String getLastName();
    String getEmail();
    String getRole();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullname();

}
