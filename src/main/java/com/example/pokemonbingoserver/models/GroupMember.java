package com.example.pokemonbingoserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name="groupMembers")
public class GroupMember {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (optional = true)
    @JoinColumn( name= "group_id")
    @JsonIgnore
    private Group group;

    @ManyToOne
    @JoinColumn (name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User member;

    public GroupMember(Group group, User member) {
        this.group = group;
        this.member = member;
    }

    public GroupMember(){};
}
