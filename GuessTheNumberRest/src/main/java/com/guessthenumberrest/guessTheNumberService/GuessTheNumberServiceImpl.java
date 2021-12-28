/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.guessTheNumberService;

import com.guessthenumberrest.dao.*;
import com.guessthenumberrest.model.Game;
import com.guessthenumberrest.model.GameRound;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ebisa
 */
@Component
public class GuessTheNumberServiceImpl implements GuessTheNumberService {

    @Autowired
    GameDao gameDao;

    @Autowired
    GameRoundDao roundDao;

    @Override
    public Game addGame() {
        Game games = new Game();
        games.setAnswer(generateAnswer());
        return gameDao.add(games);
    }

    @Override
    public GameRound addGameRound(GameRound round) throws GameRoundNotFoundException,
            GameNotFoundException, TheGameCompletedException, InvalidGuessException {
        GameRound rounds = calculate(round.getGuess(), round.getGameId());
        if (rounds == null) {
            throw new GameRoundNotFoundException("No Round for Game with this Id.");
        } else {
            return roundDao.add(rounds);
        }

    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList(gameDao.getAllGames());
    }

    @Override
    public Game getGame(int gameId) {
        return gameDao.findById(gameId);
    }

    @Override
    public Game getGameById(int gameId) throws GameNotFoundException {
        Game game = gameDao.findById(gameId);
        if (game == null) {
            throw new GameNotFoundException("No game with the given id is found.");
        }
        if (!game.isFinished()) {
            game.setAnswer("");
        }
        return game;
    }

    @Override
    public List<Game> getAllGamesHideInprogressAnswer() {
        List<Game> allGames = new ArrayList(gameDao.getAllGames());
        for (Game game : allGames) {
            if (!game.isFinished()) {
                game.setAnswer("");
            }
        }
        return allGames;
    }

    @Override
    public List<GameRound> getRounds(int gameId) throws GameRoundNotFoundException{
        List<GameRound> rounds = roundDao.getRounds(gameId);
        if(rounds.size() == 0){
            throw new GameRoundNotFoundException("There is no Round for the given game id");
        }
        return rounds;
    }

    private boolean validateTheGuess(String guess) {
        boolean isValid = true;
        String myChar;
        int counter = 0;
        for (int i = 0; i < guess.length(); i++) {
            myChar = guess.substring(i, i + 1);
            guess = guess.replaceFirst(myChar, "");
            if (guess.contains(myChar)) {
                counter++;
            }
            guess = myChar + guess;
        }
        if (counter > 0 || guess.length() != 4) {
            isValid = false;
        }
        return isValid;
    }

    private String generateAnswer() {
        Random random = new Random();
        String answer = "";
        Integer digit;
        do {
            digit = random.nextInt(10);
            if (!answer.contains(digit.toString())) {
                answer = answer += digit.toString();
            }
        } while (answer.length() < 4);
        return answer;
    }

    private GameRound calculate(String guess, int gameId) throws GameNotFoundException,
            InvalidGuessException, TheGameCompletedException {
        GameRound rounds = new GameRound();
        boolean isValid = validateTheGuess(guess);
        Game game = getGame(gameId);
        if (game == null) {
            throw new GameNotFoundException("No game with the given id is found.");
        }
        if (!isValid) {
            throw new InvalidGuessException("The Guess is Not Valid.");
        }
        if (game.isFinished()) {
            throw new TheGameCompletedException("The Game with the given id is completed.");
        }
        String result = "";
        Integer exactMatchCounter = 0;
        Integer partialMatchCounter = 0;
        if (isValid && !game.isFinished()) {
            for (int i = 0; i < guess.length(); i++) {
                if (game.getAnswer().charAt(i) == guess.charAt(i)) {
                    exactMatchCounter++;
                } else if (game.getAnswer().contains(guess.substring(i, i + 1))
                        && game.getAnswer().charAt(i) != guess.charAt(i)) {
                    partialMatchCounter++;
                }
            }
            result = result + "e:" + exactMatchCounter + ":p:" + partialMatchCounter;
            if (Integer.parseInt(result.substring(2, 3)) == 4) {
                game.setFinished(true);
                gameDao.updateGame(game);
            }
            rounds.setGameId(game.getGameId());
            rounds.setGuess(guess);
            rounds.setResult(result);
            rounds.setTime(LocalDateTime.now());
        }
        return rounds;
    }

    @Override
    public void deleteGame(int id) {
        gameDao.deleteGame(id);
    }

    @Override
    public void deleteGameRound(int id) {
        roundDao.deleteGameRound(id);
    }

    @Override
    public GameRound getGameRound(int roundId) {
        return roundDao.findById(roundId);
    }

    @Override
    public List<GameRound> getAllGameRounds() {
        return roundDao.getAllGameRounds();
    }

    @Override
    public boolean updateGame(Game game) {
        return gameDao.updateGame(game);
    }

}
