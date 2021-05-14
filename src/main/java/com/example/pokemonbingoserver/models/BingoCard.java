package com.example.pokemonbingoserver.models;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bingo_cards")
public class BingoCard{

    public BingoCard(){}


    public BingoCard(List<CollectedCard> cards, Group group) {
        this.cards = cards;
        this.group = group;
    }
    public BingoCard( Group group) {
        this.group = group;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CollectedCard> getCards() {
        return cards;
    }

    public void setCards(List<CollectedCard> cards) {
        this.cards = cards;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bingoCard")
    private List<CollectedCard> cards;

    @ManyToOne
    @JoinColumn( name= "group_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Group group;
}
