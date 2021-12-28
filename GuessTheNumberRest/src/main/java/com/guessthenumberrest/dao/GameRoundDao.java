/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.dao;

import com.guessthenumberrest.model.*;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface GameRoundDao {

    GameRound add(GameRound round);

    List<GameRound> getAllGameRounds();

    GameRound findById(int roundId);

    void deleteGameRound(int roundId);

    boolean updateGameRound(GameRound round);

    List<GameRound> getRounds(int gameId);
}
