/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.controller;

import com.guessthenumberrest.dao.*;
import com.guessthenumberrest.guessTheNumberService.GuessTheNumberService;
import com.guessthenumberrest.model.*;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.guessthenumberrest.guessTheNumberService.*;

/**
 *
 * @author ebisa
 */
//@Component
@RestController
@RequestMapping("/api/guessthenumber")
public class GuessTheNumberController {

    @Autowired
    GuessTheNumberService service;

    @GetMapping("games")
    public List<Game> getAllGame() {
        return service.getAllGamesHideInprogressAnswer();
    }

    @PostMapping("begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int create() {
        Game game = service.addGame();
        return game.getGameId();
    }

    @PostMapping("guess")
    public ResponseEntity<GameRound> guess(@RequestBody GameRound round)
            throws GameRoundNotFoundException, GameNotFoundException, TheGameCompletedException,
            InvalidGuessException {
        GameRound round2 = service.addGameRound(round);
        if (round2 == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(round2);
    }

    @GetMapping("game/{id}")
    public ResponseEntity<Game> findById(@PathVariable int id) throws GameNotFoundException {
        Game result = service.getGameById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("rounds/{id}")
    public List<GameRound> getRounds(@PathVariable int id) throws GameRoundNotFoundException {
        return service.getRounds(id);
    }
}
