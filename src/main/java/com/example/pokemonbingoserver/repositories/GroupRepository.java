package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.BingoCard;
import com.example.pokemonbingoserver.models.Group;
import com.example.pokemonbingoserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select g from Group g, GroupMember gm, User u where gm.group=g AND gm.member=u AND u=?1 order by g.id DESC")
    List<Group> findGroupsByUser(User user);
}