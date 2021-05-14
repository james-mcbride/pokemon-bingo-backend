package com.example.pokemonbingoserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="cards")
public class Card {

    public Card(){};

    public Card(String name, String imageURL, String deckSet, long level, long pokedexNumber, long hp) {
        this.name = name;
        this.imageURL = imageURL;
        this.deckSet = deckSet;
        this.level = level;
        this.pokedexNumber = pokedexNumber;
        this.hp=hp;
    }

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDeckSet() {
        return deckSet;
    }

    public void setDeckSet(String deckSet) {
        this.deckSet = deckSet;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getPokedexNumber() {
        return pokedexNumber;
    }

    public void setPokedexNumber(long pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    public List<CollectedCard> getCollectedCards() {
        return collectedCards;
    }

    public void setCollectedCards(List<CollectedCard> collectedCards) {
        this.collectedCards = collectedCards;
    }

    @Column (nullable = false, unique = true, length = 250)
    private String name;

    @Column
    private String imageURL;

    @Column
    private String deckSet;

    @Column
    private long level;

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    @Column
    private long hp;

    @Column
    private long pokedexNumber;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card")
    @JsonBackReference
    private List<CollectedCard> collectedCards;

}
