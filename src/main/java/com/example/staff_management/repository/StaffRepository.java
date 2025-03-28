package com.example.staff_management.repository;

import com.example.staff_management.dto.StaffCheckinsDto;
import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.repository.projection.StaffCheckinProjection;
import com.example.staff_management.entities.Staff;
import com.example.staff_management.repository.projection.StaffProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        SELECT\s
               s.firstName AS firstName,\s
               s.lastName AS lastName,\s
               s.email AS email,\s
               c.id AS checkinId,\s
               c.checkinTime AS checkinTime,\s
               c.checkoutTime AS checkoutTime
        FROM Staff s
        LEFT JOIN Checkin c ON s.id = c.staff.id AND c.date = CURRENT_DATE
        WHERE c.id IS NULL OR c.checkinTime IS NULL OR c.checkoutTime IS NULL
   \s""")
    List<StaffCheckinProjection> findStaffWithoutCheckinOrCheckoutToday();


    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM staff WHERE email = :email")
    Staff findStaffByEmailNativeQuery(String email);

    @Query("SELECT s FROM Staff s WHERE s.email = :email")
    Staff findStaffByEmailJPQL(String email);

    @Query(value = "SELECT new com.example.staff_management.dto.StaffDto(" +
            "s.firstName, s.lastName, s.email, s.role) FROM Staff s WHERE s.email = :email")
    StaffDto findStaffDtoByEmailContructor(String email);

    @Query("SELECT s.firstName as firstName, s.lastName lastName, s.email as email, s.role as role FROM Staff s WHERE s.email = :email")
    StaffProjection findStaffProjectionByEmail(String email);






    @Query(value = "SELECT * FROM staff WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', :firstNameKeyword, '%')) " +
            "OR LOWER(last_name) LIKE LOWER(CONCAT('%', :lastNameKeyword, '%'))",
            countQuery = "SELECT count(*) FROM staff WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', :firstNameKeyword, '%')) " +
                    "OR LOWER(last_name) LIKE LOWER(CONCAT('%', :lastNameKeyword, '%'))",
            nativeQuery = true)
    Page<Staff> getByFirstNameContainingOrLastNameContainingNative(@Param("firstNameKeyword") String firstNameKeyword,
                                                             @Param("lastNameKeyword") String lastNameKeyword,
                                                             Pageable pageable);

    Page<Staff> getByFirstNameContainingOrLastNameContaining(String firstNameKeyword, String lastNameKeyword, Pageable pageable);

    @Query("SELECT s FROM Staff s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :firstNameKeyword, '%')) " +
            "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :lastNameKeyword, '%'))")
    Page<Staff> getByFirstNameContainingOrLastNameContainingJPQL(@Param("firstNameKeyword") String firstNameKeyword,
                                                             @Param("lastNameKeyword") String lastNameKeyword,
                                                             Pageable pageable);


    @Query("SELECT s FROM Staff s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Slice<Staff> findByFirstNameContainingOrLastNameContainingSlice(String keyword, Pageable pageable);

}
