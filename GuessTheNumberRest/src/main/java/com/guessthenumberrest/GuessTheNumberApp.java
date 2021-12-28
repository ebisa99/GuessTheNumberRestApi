/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guessthenumberrest;

import com.guessthenumberrest.controller.GuessTheNumberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author ebisa
 */
@SpringBootApplication
public class GuessTheNumberApp /*implements CommandLineRunner*/ {

//    @Autowired
//    GuessTheNumberController controller;

    public static void main(String[] args) {
        SpringApplication.run(GuessTheNumberApp.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        //controller.run();
//    }
}
