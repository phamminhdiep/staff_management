package com.example.staff_management.example.lazy_eager.repo;

import com.example.staff_management.example.lazy_eager.Author;
import com.example.staff_management.example.lazy_eager.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

}
