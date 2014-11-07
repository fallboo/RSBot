package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.GraphScript.Action;
import org.powerbot.script.Random;
import com.fallboo.scripts.rt6.framework.ClientContext;

public class AntiPattern extends Action<ClientContext> {

    private long nextRun;

    public AntiPattern(ClientContext ctx) {
        super(ctx);
        nextRun = System.currentTimeMillis() + Random.nextInt(6000, 25000);
    }

    @Override
    public boolean valid() {
        return System.currentTimeMillis() > nextRun;
    }

    @Override
    public void run() {
        switch (Random.nextInt(0, 15)) {
            case 1:
                setPitch(20);
                setYaw(90);
                break;
            case 2:
                setYaw(150);
                setPitch(25);
                break;
            case 3:
                setPitch(40);
                setYaw(60);
                break;
            case 4:
                setPitch(60);
                setYaw(90);
                break;

        }
        nextRun = System.currentTimeMillis() + Random.nextInt(6000, 25000);
    }

    private void setYaw(int i) {
        int currYaw = ctx.camera.yaw(), ran = Random.nextInt(0, i), newYaw = (Random.nextInt(0, 1) == 1 ? currYaw + ran : currYaw - ran);
        if (newYaw > 360) {
            newYaw -= 360;
        } else if (0 > newYaw) {
            newYaw += 360;
        }
        ctx.camera.angle(newYaw);
    }

    private void setPitch(int i) {
        int currPitch = ctx.camera.pitch(), ran = Random.nextInt(0, i), newPitch = (Random.nextInt(0, 1) == 1 ? currPitch + ran : currPitch - ran);
        if (newPitch > 90) {
            newPitch = 90;
        } else if (50 > newPitch) {
            newPitch = 50;
        }
        ctx.camera.pitch(newPitch);
    }

}
