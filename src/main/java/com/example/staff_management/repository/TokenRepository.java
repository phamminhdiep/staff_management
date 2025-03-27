package com.example.staff_management.repository;

import com.example.staff_management.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join Staff s\s
      on t.staff.id = s.id\s
      where s.id = :staffId and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(int staffId);

    Optional<Token> findByToken(String token);


}