/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.framework;

/**
 * @author Jake
 */
public class ClientContext extends org.powerbot.script.rt6.ClientContext {
    public final Paint paint;
    public final AntiPattern antiPattern;

    public ClientContext(org.powerbot.script.rt6.ClientContext cc) {
        super(cc);
        paint = new Paint(this);
        antiPattern = new AntiPattern(this);
    }


}
