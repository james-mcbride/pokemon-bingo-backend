package com.example.pokemonbingoserver.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="collected_cards")
public class CollectedCard {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BingoCard getBingoCard() {
        return bingoCard;
    }

    public void setBingoCard(BingoCard bingoCard) {
        this.bingoCard = bingoCard;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn( name= "card_id")
    @JsonManagedReference
    private Card card;

    @ManyToOne (optional = true)
    @JoinColumn( name= "user_id")
    @JsonIgnore
    private User owner;

    @ManyToOne (optional = true)
    @JoinColumn( name= "bingoCard_id")
    @JsonIgnore
    private BingoCard bingoCard;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private Date createdAt;

    public CollectedCard(Card card, User owner, BingoCard bingoCard) {
        this.card = card;
        this.owner = owner;
        this.bingoCard = bingoCard;
    }

    public CollectedCard(Card card, User owner) {
        this.card = card;
        this.owner = owner;
    }

    public CollectedCard(Card card, BingoCard bingoCard) {
        this.card = card;
        this.bingoCard = bingoCard;
    }

    public CollectedCard(){};

}
