/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.guessTheNumberService;

import com.guessthenumberrest.model.*;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface GuessTheNumberService {

    public Game addGame();

    public GameRound addGameRound(GameRound round) throws GameRoundNotFoundException,
            GameNotFoundException, TheGameCompletedException, InvalidGuessException;

    public List<Game> getAllGames();

    public List<Game> getAllGamesHideInprogressAnswer();

    public Game getGame(int gameId);

    public List<GameRound> getRounds(int gameId)throws GameRoundNotFoundException;

    public void deleteGame(int id);

    public void deleteGameRound(int id);

    public GameRound getGameRound(int roundId);

    public List<GameRound> getAllGameRounds();

    public boolean updateGame(Game game);

    public Game getGameById(int gameId) throws GameNotFoundException;
}
