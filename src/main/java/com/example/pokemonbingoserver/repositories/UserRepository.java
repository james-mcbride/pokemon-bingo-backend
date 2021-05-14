package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findUsersByFirstNameContainingOrLastNameContainingOrUsernameContaining(String name, String name1, String name2);
}
