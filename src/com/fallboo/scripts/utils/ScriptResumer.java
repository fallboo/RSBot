/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.utils;

import org.powerbot.script.ClientContext;
import org.powerbot.script.Script.Controller;

/**
 *
 * @author Jake
 */
public class ScriptResumer implements Runnable {

    private final Controller controller;

    public ScriptResumer(ClientContext ctx) {
        this.controller = ctx.controller;
    }

    @Override
    public void run() {
        System.out.println("Resuming script");
        controller.resume();
    }

}
