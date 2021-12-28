/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest.guessTheNumberService;

/**
 *
 * @author ebisa
 */
public class TheGameCompletedException extends Exception {

    public TheGameCompletedException(String message) {
        super(message);
    }

    public TheGameCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
