package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findCardsByNameIsNotNullOrderByHp();
}