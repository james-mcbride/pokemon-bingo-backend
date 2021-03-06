package com.example.pokemonbingoserver.controllers;

import com.example.pokemonbingoserver.models.*;
import com.example.pokemonbingoserver.repositories.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
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


    @RequestMapping(value="/register", method=RequestMethod.POST, produces = "application/json")
    public @ResponseBody User postNewUser(@RequestBody HashMap<String, String> data, HttpServletRequest httpServletRequest){
        //create new user
        User newUser = userRepository.save(new User(data.get("username"), data.get("firstName"), data.get("lastName"),data.get("password"), data.get("profilePicture")));

        //create new solo group for user to use
        Group newGroup = groupRepository.save(new Group(newUser.getUsername()+"'s solo group", newUser));
        //create new group member for user for new group
        GroupMember groupMember=groupMemberRepository.save(new GroupMember(newGroup, newUser));
        return newUser;
    }


    @RequestMapping(value="/login", method=RequestMethod.POST, produces = "application/json")
    public @ResponseBody User login(@RequestBody HashMap<String, String> data, HttpServletRequest httpServletRequest){
        System.out.println(data.get("username"));
        System.out.println(data.get("password"));
        User user=userRepository.findByUsername(data.get("username"));
        if (user.getPassword().equals(data.get("password"))) {
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

        List<BingoCard> bingoCards=bingoCardRepository.findBingoCardsByUser(userRepository.findById(id).get());

        for (BingoCard bingoCard: bingoCards){
            List<GroupMember> groupMembers = bingoCard.getGroup().getGroupMembers();
            HashMap<Long, Object> allMemberMatches = new HashMap<>();
            for (GroupMember groupMember: groupMembers){
                List<Long> groupMemberMatches = collectedCardRepository.findUsersCollectedCardIds(groupMember.getMember(), bingoCard.getCreatedAt(), bingoCard.getFinishedAt());
                allMemberMatches.put(groupMember.getId(), groupMemberMatches);
            }
            bingoCard.setGroupMemberMatches(allMemberMatches);
        }

        map.put("bingoCards", bingoCards );
        return map;
    }

    @RequestMapping(value="/users/search", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<User> searchUsers(@RequestParam(name="name") String name) {
        List<User> users=userRepository.findUsersByFirstNameContainingOrLastNameContainingOrUsernameContaining(name, name, name);
        System.out.println(users.size());
        return users;
    }

//    @RequestMapping(value="/groups/create", method=RequestMethod.GET, produces="application/json")
//    public @ResponseBody
//     Group  createNewGroup(@RequestParam(name="name") String name, @RequestParam(name="groupMembersList") Integer[] groupMembers, @RequestParam(name="owner") String ownerId){
//        System.out.println("saving a new group");
//        System.out.println(groupMembers.length);
//        Group group = groupRepository.save(new Group(name, userRepository.getOne(Long.parseLong(ownerId))));
//        groupMemberRepository.save(new GroupMember(group, userRepository.getOne(Long.parseLong(ownerId))));
//
//        for (int i=0; i<groupMembers.length; i++){
//            System.out.println(groupMembers[i]);
//            groupMemberRepository.save(new GroupMember(group, userRepository.getOne((long) groupMembers[i])));
//        }
//        return group;
//    }
//
//}

    @RequestMapping(value="/groups/create", method=RequestMethod.POST, produces="application/json")
    public @ResponseBody Group createGroup(@RequestBody HashMap<String, Object> data, HttpServletRequest httpServletRequest){
        System.out.println("saving a new group");
        long ownerId= (int) data.get("owner");
        Group group = groupRepository.save(new Group((String) data.get("name"), userRepository.getOne(ownerId)));
        groupMemberRepository.save(new GroupMember(group, userRepository.getOne(ownerId)));

        ArrayList<Integer> groupMembers= (ArrayList<Integer>) data.get("groupMembersList");
        for (int i=0; i<groupMembers.size(); i++){
            long userId= groupMembers.get(i);
            groupMemberRepository.save(new GroupMember(group, userRepository.getOne(userId)));
        }
        return group;
    }

    @RequestMapping(value="/group/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    HashMap<String, Object> retrieveGroupInfo(@PathVariable long id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("group", groupRepository.findById(id).get());

        List<BingoCard> bingoCards=bingoCardRepository.findBingoCardsByGroup_Id(id);

        for (BingoCard bingoCard: bingoCards){
            List<GroupMember> groupMembers = bingoCard.getGroup().getGroupMembers();
            HashMap<Long, Object> allMemberMatches = new HashMap<>();
            for (GroupMember groupMember: groupMembers){
                List<Long> groupMemberMatches = collectedCardRepository.findUsersCollectedCardIds(groupMember.getMember(), bingoCard.getCreatedAt(), bingoCard.getFinishedAt());
                allMemberMatches.put(groupMember.getId(), groupMemberMatches);
            }
            bingoCard.setGroupMemberMatches(allMemberMatches);
        }
        map.put("bingoCards", bingoCards);

        return map;
    }

}
