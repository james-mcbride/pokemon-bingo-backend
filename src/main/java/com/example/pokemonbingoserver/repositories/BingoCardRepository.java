package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BingoCardRepository extends JpaRepository<BingoCard, Long> {
    @Query("select b from BingoCard b, Group g, GroupMember gm, User u where b.group=g AND gm.group=g AND gm.member=u AND u=?1 order by b.id DESC")
    List<BingoCard> findBingoCardsByUser(User user);

    @Query("select bc from BingoCard bc, CollectedCard c, GroupMember gm, User u, Group g where bc.group=g AND gm.group=g AND gm.member=u AND c.bingoCard=bc AND bc.hasWinner=false AND u=?1 AND c.card=?2 order by g.id DESC")
    List<BingoCard> BingoCardCardsByUser(User user, Card card );

    Boolean existsBingoCardByGroupAndAndHasWinner(Group group, boolean bool);

    BingoCard findBingoCardByGroupAndHasWinner(Group group, boolean bool);

    List<BingoCard> findBingoCardsByGroup_Id(long groupId);
}
