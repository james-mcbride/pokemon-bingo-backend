package com.example.pokemonbingoserver.models;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "bingo_cards")
public class BingoCard{

    public BingoCard(){}


    public BingoCard(List<CollectedCard> cards, Group group) {
        this.cards = cards;
        this.group = group;
        this.hasWinner=false;

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

    public HashMap<Long, Object> getGroupMemberMatches() {
        return groupMemberMatches;
    }

    public void setGroupMemberMatches(HashMap<Long, Object> groupMemberMatches) {

        this.groupMemberMatches = groupMemberMatches;
    }

    @Transient
    HashMap<Long, Object> groupMemberMatches;

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

    public boolean isHasWinner() {
        return hasWinner;
    }

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }

    @Column()
    private boolean hasWinner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Column()
    private String winner;

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Column()
    private Date finishedAt;


}
