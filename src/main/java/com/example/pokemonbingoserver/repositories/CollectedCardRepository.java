package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.Card;
import com.example.pokemonbingoserver.models.CollectedCard;
import com.example.pokemonbingoserver.models.Group;
import com.example.pokemonbingoserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectedCardRepository extends JpaRepository<CollectedCard, Long> {
    List<CollectedCard> findCollectedCardsByOwner_Id(long id);


    List<CollectedCard> findCollectedCardsByOwnerAndCard(User user, Card card);

    @Query("select c.card.pokedexNumber from CollectedCard c, User u where c.owner=u AND u=?1")
    List<Long> findUsersCollectedCardIds(User user);
}