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

    @RequestMapping(value="/newCard", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody
    Card submitTripComment(@RequestParam(name="cardName") String cardName, @RequestParam(name="imageUrl") String imageUrl, @RequestParam(name="deckSet") String deckSet, @RequestParam(name="level") String level, @RequestParam(name="pokedexNumber") String pokedexNumber) {
        Card card = new Card(cardName, imageUrl, deckSet, Long.parseLong(level), Long.parseLong(pokedexNumber));
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
        bingoCardRepository.save(bingoCard);

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


        return bingoCard;
    }



}
