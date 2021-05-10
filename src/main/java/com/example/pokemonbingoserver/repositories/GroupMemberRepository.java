package com.example.pokemonbingoserver.repositories;

import com.example.pokemonbingoserver.models.Group;
import com.example.pokemonbingoserver.models.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

}
