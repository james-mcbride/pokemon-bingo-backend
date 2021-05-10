package com.example.pokemonbingoserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<BingoCard> getBingoCards() {
        return bingoCards;
    }

    public void setBingoCards(List<BingoCard> bingoCards) {
        this.bingoCards = bingoCards;
    }

    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @Column(nullable = false, unique = true, length = 250)
    private String name;

    @JsonIgnore
    @OneToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    @JsonBackReference
    private List<BingoCard> bingoCards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    @JsonBackReference
    private List<GroupMember> groupMembers;

    public Group(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Group(){};
}