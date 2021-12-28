/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.dao;

import com.guessthenumberrest.TestApplicationConfiguration;
import com.guessthenumberrest.model.Game;
import com.guessthenumberrest.model.GameRound;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ebisa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameRoundDaoDBTest {

    @Autowired
    GameDao gameDao;
    @Autowired
    GameRoundDao roundDao;

    public GameRoundDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGame(game.getGameId());
        }
        List<GameRound> rounds = roundDao.getAllGameRounds();
        for (GameRound round : rounds) {
            roundDao.deleteGameRound(round.getRoundId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class GameRoundDaoDB.
     */
    @Test
    public void testAddGetGameRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);

        GameRound round = new GameRound();
        round.setGuess("2345");
        round.setResult("e:0:p:3");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setGameId(game.getGameId());
        round = roundDao.add(round);

        GameRound fromDao = roundDao.findById(round.getRoundId());

        assertEquals(round, fromDao);
    }

    /**
     * Test of getAllGameRounds method, of class GameRoundDaoDB.
     */
    @Test
    public void testGetAllGameRounds() {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);

        GameRound round = new GameRound();
        round.setGuess("2345");
        round.setResult("e:0:p:3");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setGameId(game.getGameId());
        round = roundDao.add(round);

        GameRound round2 = new GameRound();
        round2.setGuess("6789");
        round2.setResult("e:0:p:0");
        round2.setTime(LocalDateTime.now().withNano(0));
        round2.setGameId(game.getGameId());
        round2 = roundDao.add(round2);

        List<GameRound> rounds = roundDao.getAllGameRounds();

        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
    }

    /**
     * Test of updateGameRound method, of class GameRoundDaoDB.
     */
    @Test
    public void testUpdateGameRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);

        GameRound round = new GameRound();
        round.setGuess("2345");
        round.setResult("e:0:p:0");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setGameId(game.getGameId());
        round = roundDao.add(round);

        GameRound fromDao = roundDao.findById(round.getRoundId());

        assertEquals(round, fromDao);

        round.setGuess("0987");

        roundDao.updateGameRound(round);

        assertNotEquals(round, fromDao);

        fromDao = roundDao.findById(round.getRoundId());

        assertEquals(round, fromDao);
    }

    /**
     * Test of deleteGameRound method, of class GameRoundDaoDB.
     */
    @Test
    public void testDeleteGameRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);

        GameRound round = new GameRound();
        round.setGuess("2345");
        round.setResult("e:0:p:0");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setGameId(game.getGameId());
        round = roundDao.add(round);
        roundDao.deleteGameRound(round.getRoundId());

        GameRound fromDao = roundDao.findById(round.getRoundId());

        assertNull(fromDao);
    }
    @Test
    public void testGetRounds(){
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);

        GameRound round = new GameRound();
        round.setGuess("2345");
        round.setResult("e:0:p:0");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setGameId(game.getGameId());
        round = roundDao.add(round);
        
        GameRound rounds = new GameRound();
        rounds.setGuess("4567");
        rounds.setResult("e:0:p:1");
        rounds.setTime(LocalDateTime.now().withNano(0));
        rounds.setGameId(game.getGameId());
        rounds = roundDao.add(rounds);
        List<GameRound> gameRound = roundDao.getRounds(game.getGameId());
        assertEquals(2, gameRound.size());
        assertTrue(gameRound.contains(round));
        assertTrue(gameRound.contains(rounds));
        assertEquals(gameRound.get(0).getGameId(), gameRound.get(1)
        .getGameId());
    }
}
