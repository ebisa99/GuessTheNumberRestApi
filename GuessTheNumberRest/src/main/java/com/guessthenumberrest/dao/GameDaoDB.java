/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.dao;

import com.guessthenumberrest.model.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ebisa
 */
@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    private final JdbcTemplate jdbc;

    
    public GameDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Game add(Game game) {
        final String INSERT_GAME = "INSERT INTO Game(gameAnswer, finished)"
                + "VALUES(?,?)";
        jdbc.update(INSERT_GAME,
                game.getAnswer(),
                game.isFinished()
        );
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        return game;

    }

    @Override
    public List<Game> getAllGames() {
        final String GET_ALL_GAMES = "SELECT*FROM Game";
        return jdbc.query(GET_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game findById(int gameId) {
        try {
            final String GET_GAME = "SELECT * FROM Game "
                    + "WHERE gameId = ?;";
            return jdbc.queryForObject(GET_GAME, new GameMapper(), gameId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE Game SET gameAnswer = ?, finished = ? "
                + "WHERE gameId = ?";
        return jdbc.update(UPDATE_GAME,
                game.getAnswer(),
                game.isFinished(),
                game.getGameId()) > 0;
    }

    @Override
    public boolean deleteGame(int gameId) {
        final String DELETE_GAMEROUND = "DELETE FROM GameRound WHERE gameId = ?";
        jdbc.update(DELETE_GAMEROUND, gameId);
        final String DELETE_GAME = "DELETE FROM Game WHERE gameId = ?";
        return jdbc.update(DELETE_GAME, gameId) > 0;
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("gameAnswer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }
}
