/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.dao;

import com.guessthenumberrest.TestApplicationConfiguration;
import com.guessthenumberrest.model.*;
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
public class GameDaoDBTest {

    @Autowired
    GameDaoDB gameDao;
    
    @Autowired
    GameRoundDaoDB roundDao;

    public GameDaoDBTest() {
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
     * Test of add method, of class GameDaoDB.
     */
    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);
        Game fromDao = gameDao.findById(game.getGameId());
        assertEquals(game, fromDao);
    }

    /**
     * Test of getAllGames method, of class GameDaoDB.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("1234");
        gameDao.add(game);

        Game game2 = new Game();
        game2.setAnswer("6789");
        gameDao.add(game2);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of updateGame method, of class GameDaoDB.
     */
    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);
        Game fromDao = gameDao.findById(game.getGameId());

        assertEquals(game, fromDao);

        game.setAnswer("3456");

        gameDao.updateGame(game);

        assertNotEquals(game, fromDao);

        fromDao = gameDao.findById(game.getGameId());

        assertEquals(game, fromDao);
    }

    /**
     * Test of deleteGame method, of class GameDaoDB.
     */
    @Test
    public void testDeleteGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.add(game);

        GameRound round = new GameRound();
        round.setGuess("1234");
        round.setResult("e:0:p:0");
        round.setTime(LocalDateTime.now());
        round.setGameId(game.getGameId());
        roundDao.add(round);

        gameDao.deleteGame(game.getGameId());

        Game fromDao = gameDao.findById(game.getGameId());
        assertNull(fromDao);
    }

}
