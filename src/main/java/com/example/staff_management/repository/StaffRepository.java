package com.example.staff_management.repository;

import com.example.staff_management.dto.StaffCheckinsDto;
import com.example.staff_management.repository.projection.StaffCheckinProjection;
import com.example.staff_management.entities.Staff;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    Optional<Staff> findByEmail(String username);

    boolean existsByCheckinCode(String checkinCode);

    List<Staff> findByFirstNameContainingOrLastNameContaining(String firstNameKeyword, String lastNameKeyword, Pageable pageable);
    @Query("""
        SELECT s.id AS staffId, 
               s.firstName AS firstName, 
               s.lastName AS lastName, 
               s.email AS email, 
               c.id AS checkinId, 
               c.checkinTime AS checkinTime, 
               c.checkoutTime AS checkoutTime
        FROM Staff s
        LEFT JOIN Checkin c ON s.id = c.staff.id AND c.date = CURRENT_DATE
        WHERE c.id IS NULL OR c.checkinTime IS NULL OR c.checkoutTime IS NULL
    """)
    List<StaffCheckinProjection> findStaffWithoutCheckinOrCheckoutToday();


    boolean existsByEmail(String email);
}
