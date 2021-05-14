package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.CollectedCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectedCardRepository extends JpaRepository<CollectedCard, Long> {
    List<CollectedCard> findCollectedCardsByOwner_Id(long id);
}