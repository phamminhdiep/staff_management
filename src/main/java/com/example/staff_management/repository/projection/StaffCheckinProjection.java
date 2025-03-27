package com.example.staff_management.repository.projection;

import java.time.LocalTime;

public interface StaffCheckinProjection {
    Integer getStaffId();
    String getFirstName();
    String getLastName();
    String getEmail();
    Integer getCheckinId();
    LocalTime getCheckinTime();
    LocalTime getCheckoutTime();
}
