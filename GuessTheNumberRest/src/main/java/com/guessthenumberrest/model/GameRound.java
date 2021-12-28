/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author ebisa
 */
public class GameRound {

    private int roundId;
    private String guess;
    private String result;
    private int gameId;
    private LocalDateTime time;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }


    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.roundId;
        hash = 23 * hash + Objects.hashCode(this.guess);
        hash = 23 * hash + Objects.hashCode(this.result);
        hash = 23 * hash + Objects.hashCode(this.gameId);
        hash = 23 * hash + Objects.hashCode(this.time);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameRound other = (GameRound) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.gameId, other.gameId)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameRound{" + "roundId=" + roundId + ", guess=" + guess + ", result=" + result + ", gameId=" + gameId + ", time=" + time + '}';
    }

}
