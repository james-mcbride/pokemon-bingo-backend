package com.example.pokemonbingoserver.controllers;
import com.example.pokemonbingoserver.models.BingoCard;
import com.example.pokemonbingoserver.models.Card;
import com.example.pokemonbingoserver.models.CollectedCard;
import com.example.pokemonbingoserver.models.Group;
import com.example.pokemonbingoserver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BingoCardRepository bingoCardRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CollectedCardRepository collectedCardRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/newCard", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    Card submitTripComment(@RequestParam(name="cardName") String cardName, @RequestParam(name="imageUrl") String imageUrl, @RequestParam(name="deckSet") String deckSet, @RequestParam(name="level") String level,@RequestParam(name="hp") String hp, @RequestParam(name="pokedexNumber") String pokedexNumber) {
        Card card = new Card(cardName, imageUrl, deckSet, Long.parseLong(level), Long.parseLong(pokedexNumber), Long.parseLong(hp));
        cardRepository.save(card);
        return card;
    }

    @RequestMapping(value="/cards", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<Card> retrieveAllCards(){
        return cardRepository.findAll();
    }

    @RequestMapping(value="/groups/{id}/bingo", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    BingoCard retrieveNewBingoCard(@PathVariable long id){
        //will save a new bingoCard for the group
        BingoCard bingoCard=new BingoCard(groupRepository.getOne(id));
        BingoCard savedBingo = bingoCardRepository.save(bingoCard);

        //will randomly grab cards that are stored, and add them to list for bingo
        List<CollectedCard> cards = new ArrayList<>();

        //will grab all cards from database
        ArrayList<Card> allCards= (ArrayList<Card>) cardRepository.findAll();
        for (int i=0; i<25; i++){
            long randomInt = Math.round(Math.random()*(allCards.size()-1));
            Card card = allCards.get((int) randomInt);
            CollectedCard collectedCard = collectedCardRepository.save(new CollectedCard(card, bingoCard));
            cards.add(collectedCard);
            allCards.remove(card);
        }

        savedBingo.setCards(cards);
        return savedBingo;
    }

    @RequestMapping(value="/profile/{id}/draw", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    HashMap<String, List<CollectedCard>> retrieveUsersCards(@PathVariable long id, @RequestParam(name="draw") String commitDraw ){
        if (commitDraw.equalsIgnoreCase("yes")) {
            List<Card> cards = cardRepository.findCardsByNameIsNotNullOrderByHp();
            CollectedCard cardDraw = new CollectedCard();
            cardDraw.setOwner(userRepository.getOne(id));

            long drawNum = Math.round(Math.random() * 100);
            if (drawNum < 55) {
                System.out.println(drawNum + ": weak pokemon draw");
                long drawNumTwo = Math.round(Math.random() * 50);
                cardDraw.setCard(cards.get((int) drawNumTwo));

            } else if (drawNum < 68) {
                System.out.println(drawNum + ": ehhh pokemon draw");
                long drawNumTwo = Math.round(Math.random() * 24 + 50);
                cardDraw.setCard(cards.get((int) drawNumTwo));
            } else if (drawNum < 80) {
                System.out.println(drawNum + ": not bad pokemon draw");
                long drawNumTwo = Math.round(Math.random() * 26 + 74);
                cardDraw.setCard(cards.get((int) drawNumTwo));
            } else if (drawNum < 97) {
                System.out.println(drawNum + ": good pokemon draw");

                long drawNumTwo = Math.round(Math.random() * 40 + 100);
                cardDraw.setCard(cards.get((int) drawNumTwo));
            } else {
                System.out.println(drawNum + ": best pokemon draw");

                long drawNumTwo = Math.round(Math.random() * 9 + 100);
                cardDraw.setCard(cards.get((int) drawNumTwo));
            }
            collectedCardRepository.save(cardDraw);
        }

        HashMap<String, List<CollectedCard>> map = new HashMap<>();
        map.put("cards", collectedCardRepository.findCollectedCardsByOwner_Id(id));
        return map;
    }



}
