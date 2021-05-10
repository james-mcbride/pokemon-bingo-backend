package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}