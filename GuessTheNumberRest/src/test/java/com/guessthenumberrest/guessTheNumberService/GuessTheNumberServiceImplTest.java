/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.guessTheNumberService;

import com.guessthenumberrest.TestApplicationConfiguration;
import com.guessthenumberrest.model.*;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ebisa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GuessTheNumberServiceImplTest {

    @Autowired
    GuessTheNumberService service;

    public GuessTheNumberServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Game> games = service.getAllGames();
        for (Game game : games) {
            service.deleteGame(game.getGameId());
        }
        List<GameRound> rounds = service.getAllGameRounds();
        for (GameRound round : rounds) {
            service.deleteGameRound(round.getRoundId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addGame method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testAddGetGame() {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();
        Game fromDao = service.getGame(game.getGameId());
        assertEquals(game, fromDao);
    }

    /**
     * Test of addGameRound method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testAddGetGameRound() throws GameRoundNotFoundException,
            GameNotFoundException, TheGameCompletedException, InvalidGuessException {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();

        GameRound round = new GameRound();
        round.setGuess("2345");
        //round.setResult("e:0:p:0");
        //round.setTime(LocalDateTime.now());
        round.setGameId(game.getGameId());
        round = service.addGameRound(round);
        GameRound fromDao = service.getGameRound(round.getRoundId());
        fromDao.setTime(round.getTime());
        assertEquals(round, fromDao);
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetAllGames() {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();

//        Game game2 = new Game();
//        game2.setAnswer("1236");
        Game game2 = service.addGame();

        List<Game> games = service.getAllGames();
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of getRounds method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetRounds() throws GameRoundNotFoundException,
            GameNotFoundException, TheGameCompletedException, InvalidGuessException {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();

        GameRound round = new GameRound();
        round.setGuess("2345");
//        round.setResult("e:0:p:0");
//        round.setTime(LocalDateTime.now());
        round.setGameId(game.getGameId());
        round = service.addGameRound(round);

        GameRound rounds = new GameRound();
        rounds.setGuess("4567");
//        rounds.setResult("e:0:p:1");
//        rounds.setTime(LocalDateTime.now());
        rounds.setGameId(game.getGameId());
        rounds = service.addGameRound(rounds);
        List<GameRound> gameRound = service.getRounds(game.getGameId());
        gameRound.get(0).setTime(round.getTime());
        gameRound.get(1).setTime(rounds.getTime());
        assertEquals(2, gameRound.size());
        assertTrue(gameRound.contains(round));
        assertTrue(gameRound.contains(rounds));
        assertEquals(gameRound.get(0).getGameId(), gameRound.get(1)
                .getGameId());
    }

    /**
     * Test of deleteGame method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testDeleteGame() throws GameRoundNotFoundException,
            GameNotFoundException, TheGameCompletedException, InvalidGuessException {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();

        GameRound round = new GameRound();
        round.setGuess("1234");
//        round.setResult("e:0:p:0");
//        round.setTime(LocalDateTime.now());
        round.setGameId(game.getGameId());
        round = service.addGameRound(round);
        GameRound fromRoundDao = service.getGameRound(round.getRoundId());
        fromRoundDao.setTime(round.getTime());
        assertEquals(round, fromRoundDao);

        service.deleteGame(game.getGameId());

        Game fromDao = service.getGame(game.getGameId());
        assertNull(fromDao);
        fromRoundDao = service.getGameRound(round.getRoundId());
        assertNull(fromRoundDao);
    }

    /**
     * Test of deleteGameRound method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testDeleteGameRound() throws GameRoundNotFoundException,
            GameNotFoundException, TheGameCompletedException, InvalidGuessException {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();

        GameRound round = new GameRound();
        round.setGuess("1234");
//        round.setResult("e:0:p:0");
//        round.setTime(LocalDateTime.now());
        round.setGameId(game.getGameId());
        round = service.addGameRound(round);
        GameRound fromRoundDao = service.getGameRound(round.getRoundId());
        fromRoundDao.setTime(round.getTime());
        assertEquals(round, fromRoundDao);
        service.deleteGameRound(round.getRoundId());
        fromRoundDao = service.getGameRound(round.getRoundId());
        assertNull(fromRoundDao);
    }

    /**
     * Test of getAllGameRounds method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetAllGameRounds() throws GameRoundNotFoundException,
            GameNotFoundException, TheGameCompletedException, InvalidGuessException {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();

//        Game game2 = new Game();
//        game2.setAnswer("1236");
        Game game2 = service.addGame();

        GameRound round = new GameRound();
        round.setGuess("1234");
        //round.setResult("e:4:p:0");
        //round.setTime(LocalDateTime.now());
        round.setGameId(game.getGameId());
        round = service.addGameRound(round);

        GameRound round2 = new GameRound();
        round2.setGuess("1234");
        //round2.setResult("e:0:p:0");
        //round2.setTime(LocalDateTime.now());
        round2.setGameId(game2.getGameId());
        round2 = service.addGameRound(round2);
        List<GameRound> fromDao = service.getAllGameRounds();
        fromDao.get(0).setTime(round.getTime());
        fromDao.get(1).setTime(round2.getTime());
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(round));
        assertTrue(fromDao.contains(round2));

    }

    /**
     * Test of updateGame method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testUpdateGame() {
//        Game game = new Game();
//        game.setAnswer("1234");
        Game game = service.addGame();
        Game fromDao = service.getGame(game.getGameId());
        assertEquals(game, fromDao);
        game.setAnswer("3421");
        service.updateGame(game);
        assertNotEquals(game, fromDao);
    }

}
