/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.dao;

import com.guessthenumberrest.dao.GameDaoDB.GameMapper;
import com.guessthenumberrest.model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ebisa
 */
@Repository
public class GameRoundDaoDB implements GameRoundDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public GameRoundDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    @Transactional
    public GameRound add(GameRound round) {
        final String INSERT_GAMEROUND = "INSERT INTO GameRound(guess, result,time, gameId)"
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_GAMEROUND,
                round.getGuess(),
                round.getResult(),
                Timestamp.valueOf(round.getTime()),
                round.getGameId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newId);
        return round;
    }

    @Override
    public List<GameRound> getAllGameRounds() {
        final String GET_ALL_GAMEROUNDS = "SELECT * FROM GameRound";
        List<GameRound> rounds = jdbc.query(GET_ALL_GAMEROUNDS, new GameRoundMapper());
        //addGameToGameRound(rounds);
        return rounds;
    }

//    private void addGameToGameRound(List<GameRound> rounds) {
//        for (GameRound round : rounds) {
//            round.setGame(getGameForGameRound(round));
//        }
//    }

//    private Game getGameForGameRound(GameRound round) {
//        final String SELECT_GAME_FOR_GAMEROUND = "SELECT g.* FROM Game g "
//                + "JOIN GameRound r ON g.gameId = r.gameId WHERE r.roundId = ?";
//        return jdbc.queryForObject(SELECT_GAME_FOR_GAMEROUND, new GameMapper(),
//                round.getRoundId());
//    }

    @Override
    public GameRound findById(int roundId) {
        try {
            final String GET_GAMEROUND = "SELECT * FROM GameRound WHERE roundId = ?";
            GameRound round = jdbc.queryForObject(GET_GAMEROUND, new GameRoundMapper(), roundId);
            //round.setGame(getGameForGameRound(round));
            return round;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean updateGameRound(GameRound round) {
        final String UPDATE_GAMEROUND = "UPDATE GameRound "
                + "SET guess = ?, result = ?, time = ?, gameId = ? "
                + "WHERE roundId = ?";
        return jdbc.update(UPDATE_GAMEROUND,
                round.getGuess(),
                round.getResult(),
                round.getTime(),
                round.getGameId(),
                round.getRoundId())> 0;
    }

    @Override
    public void deleteGameRound(int roundId) {
        final String DELETE_FROM_GAMEROUND = "DELETE FROM GameRound WHERE roundId = ?";
        jdbc.update(DELETE_FROM_GAMEROUND, roundId);
        final String DELETE_FROM_GAME = "DELETE FROM Game WHERE gameId = ?";
        jdbc.update(DELETE_FROM_GAME, roundId);
    }

    @Override
    public List<GameRound> getRounds(int gameId) {
        final String GET_ROUND = "SELECT * FROM GameRound "
                + "WHERE gameId = ? "
                + "ORDER BY time";
        List<GameRound> rounds = jdbc.query(GET_ROUND, new GameRoundMapper(), gameId);
        //addGameToGameRound(rounds);
        return rounds;
    }

    public static final class GameRoundMapper implements RowMapper<GameRound> {

        @Override
        public GameRound mapRow(ResultSet rs, int index) throws SQLException {
            GameRound round = new GameRound();
            round.setRoundId(rs.getInt("roundId"));
            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("result"));
            round.setTime(rs.getTimestamp("time").toLocalDateTime());
            round.setGameId(rs.getInt("gameId"));
            return round;
        }
    }
}
