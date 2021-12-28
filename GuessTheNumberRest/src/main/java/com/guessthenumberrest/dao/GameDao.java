/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.dao;

import com.guessthenumberrest.model.Game;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface GameDao {

    Game add(Game game);

    List<Game> getAllGames();

    Game findById(int gameId);

    boolean updateGame(Game game);

    boolean deleteGame(int gameId);
}
