package com.example.pokemonbingoserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CollectedCard> getCards() {
        return cards;
    }

    public void setCards(List<CollectedCard> cards) {
        this.cards = cards;
    }

    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @Column(nullable = false, length = 20, unique = true)
    @javax.validation.constraints.NotBlank(message = "Username can't be empty")
    private String username;

    @Column(name = "first_name", nullable = false, length = 250)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 250)
    private String lastName;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Column(name = "profile_picture", nullable = false, length = 250)
    private String profilePicture;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @JsonIgnore
    private List<CollectedCard> cards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    @JsonIgnore
    private List<GroupMember> groupMembers;

    public User(@NotBlank(message = "Username can't be empty") String username, String firstName, String lastName, String password, String profilePicture) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.profilePicture=profilePicture;
    }

    public User(){

    }

}