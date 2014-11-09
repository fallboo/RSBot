package com.fallboo.scripts.rt6.framework;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Interactive;

public class AntiPattern {

    private long nextRun;
    private final ClientContext ctx;

    //TODO Add misclicking for components, objects(?), Mouse off screen, defocusing/focusing
    public enum State {
        IDLE, MOVING
    }

    public AntiPattern(ClientContext ctx) {
        this.ctx = ctx;
        nextRun = ctx.controller.script().getRuntime() + Random.nextInt(6000, 25000);
    }


    private boolean valid() {
        return ctx.controller.script().getRuntime() > nextRun;
    }

    public void run(State state) {
        if (!valid())
            return;
        switch (Random.nextInt(0, 25)) {
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
            case 5:
                if (state == State.IDLE) {
                    GameObject go = ctx.objects.select(Interactive.areInViewport()).shuffle().poll();
                    if (go.valid())
                        go.interact("Examine");
                }
                break;

        }
        nextRun = ctx.controller.script().getRuntime() + Random.nextInt(6000, 25000);
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
