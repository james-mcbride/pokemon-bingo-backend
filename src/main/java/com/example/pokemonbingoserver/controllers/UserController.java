package com.example.pokemonbingoserver.controllers;

import com.example.pokemonbingoserver.models.*;
import com.example.pokemonbingoserver.repositories.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private BingoCardRepository bingoCardRepository;

    @Autowired
    private CollectedCardRepository collectedCardRepository;

    @RequestMapping(value="/register", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    User postNewUser(@RequestParam(name="firstName", required = false) String firstName, @RequestParam(name="lastName", required = false) String lastName, @RequestParam(name="username", required = false) String username, @RequestParam(name="password", required = false) String password) {
        System.out.println("about to try to save a new user!");
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(username);
        System.out.println(password);
        //create new user
        User newUser = userRepository.save(new User(username, firstName, lastName,password));

        //create new solo group for user to use
        Group newGroup = groupRepository.save(new Group(newUser.getUsername()+"'s solo group", newUser));
        //create new group member for user for new group
        GroupMember groupMember=groupMemberRepository.save(new GroupMember(newGroup, newUser));
        return newUser;
    }

    @RequestMapping(value="/login", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    User loginUser(@RequestParam(name="username") String username, @RequestParam(name="password") String password) {
       User user=userRepository.findByUsername(username);
       if (user.getPassword().equals(password)) {
           return user;
       } else{
           return null;
       }
    }

    @RequestMapping(value="/profile/{id}/bingoCards", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<BingoCard> retrieveUsersBingoCards(@PathVariable long id){
         return bingoCardRepository.findBingoCardsByUser(userRepository.getOne(id));
    }

    @RequestMapping(value="/profile/{id}/groups", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<Group> retrieveUsersGroups(@PathVariable long id){
        return groupRepository.findGroupsByUser(userRepository.getOne(id));
    }

    @RequestMapping(value="/profile/{id}/bingoCard", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    HashMap<String, Object> retrieveUsersObjects(@PathVariable long id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("groups", groupRepository.findGroupsByUser(userRepository.findById(id).get()));
        map.put("bingoCards", bingoCardRepository.findBingoCardsByUser(userRepository.findById(id).get()));
        return map;
    }

    @RequestMapping(value="/users/search", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<User> searchUsers(@RequestParam(name="name") String name) {
        List<User> users=userRepository.findUsersByFirstNameContainingOrLastNameContainingOrUsernameContaining(name, name, name);
        System.out.println(users.size());
        return users;
    }

    @RequestMapping(value="/groups/create", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
     Group  createNewGroup(@RequestParam(name="name") String name, @RequestParam(name="groupMembersList") Integer[] groupMembers, @RequestParam(name="owner") String ownerId){
        System.out.println("saving a new group");
        Group group = groupRepository.save(new Group(name, userRepository.getOne(Long.parseLong(ownerId))));
        groupMemberRepository.save(new GroupMember(group, userRepository.getOne(Long.parseLong(ownerId))));
        for (int i=0; i<groupMembers.length; i++){
            groupMemberRepository.save(new GroupMember(group, userRepository.getOne((long) groupMembers[i])));
        }
        return group;
    }

}
