package com.example.staff_management.example.lazy_eager;

import com.example.staff_management.example.lazy_eager.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class Loader {
    private final BookRepo bookRepo;

    public void load(){
        Book book = bookRepo.findById(1).get();
        System.out.println("book "+book.getId());
        System.out.println("author "+book.getAuthor().getName());
    }

    public void persist(){
        Author author = new Author();
        author.setName("Author " + new Random().nextInt());
        Book book = new Book();
        book.setAuthor(author);
        bookRepo.save(book);
    }

    public void delete(int id){
        bookRepo.deleteById(id);
    }


}
