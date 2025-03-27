package com.example.staff_management.repository;

import com.example.staff_management.entities.Checkin;
import com.example.staff_management.entities.Staff;
import com.example.staff_management.repository.StaffRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StaffRepositoryTest {

    @Mock
    private StaffRepository staffRepository;

    @Test
    public void testFindByEmail() {
        Staff staff = Staff.builder()
                .email("dieppm@gmail.com")
                .firstName("Diep")
                .lastName("Pham")
                .build();
        Mockito.when(staffRepository.findByEmail("dieppm@gmail.com")).thenReturn(Optional.of(staff));

        Optional<Staff> result = staffRepository.findByEmail("dieppm@gmail.com");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Diep", result.get().getFirstName());
        Assertions.assertEquals("dieppm@gmail.com", result.get().getEmail());
    }

    @Test
    public void testExistsByCheckinCode() {
        String checkinCode = "1234";
        Staff staff = Staff.builder()
                .checkinCode(checkinCode)
                .build();
        when(staffRepository.existsByCheckinCode("1234")).thenReturn(true);
        Assertions.assertTrue(staffRepository.existsByCheckinCode(checkinCode));
        Assertions.assertFalse(staffRepository.existsByCheckinCode("4321"));
    }


    @Test
    public void findByFirstNameContainingOrLastNameContaining(){
        Staff staff = Staff.builder()
                .firstName("Diep")
                .lastName("Pham")
                .build();
        when(staffRepository.findByFirstNameContainingOrLastNameContaining("Diep", "Pham", null)).thenReturn(List.of(staff));
        List<Staff> result = staffRepository.findByFirstNameContainingOrLastNameContaining("Diep", "Pham", null);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Diep", result.get(0).getFirstName());
        Assertions.assertEquals("Pham", result.get(0).getLastName());

    }

}