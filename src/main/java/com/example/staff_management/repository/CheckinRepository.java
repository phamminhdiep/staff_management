package com.example.staff_management.repository;

import com.example.staff_management.entities.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {
    @Query(value = """
      select c from Checkin c
      where c.staff.id = :staffId
      """)
    List<Checkin> findAllByStaffId(Integer staffId);

    @Query(value = """
      select c from Checkin c
      where c.staff.id = :staffId and c.date = :date
      """)
    Optional<Checkin> findByStaffIdAndDate(Integer staffId, LocalDate date);

    @Query(value = """
      select c from Checkin c
      where c.staff.id = :staffId and c.date between :startDate and :endDate
      """)
    List<Checkin> findByStaffIdAndDateBetween(Integer staffId, LocalDate startDate, LocalDate endDate);
}
