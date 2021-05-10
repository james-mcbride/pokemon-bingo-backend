package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.BingoCard;
import com.example.pokemonbingoserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BingoCardRepository extends JpaRepository<BingoCard, Long> {
    @Query("select b from BingoCard b, Group g, GroupMember gm, User u where b.group=g AND gm.group=g AND gm.member=u AND u=?1 order by b.id DESC")
    List<BingoCard> findBingoCardsByUser(User user);
}
