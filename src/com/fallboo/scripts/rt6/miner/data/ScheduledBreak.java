/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.miner.data;

/**
 *
 * @author Jake
 */
public class ScheduledBreak {

    private int workTime, breakTime;

    public ScheduledBreak(int workTime, int breakTime) {
        this.workTime = workTime;
        this.breakTime = breakTime;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public int getWorkTime() {
        return workTime;
    }

}
