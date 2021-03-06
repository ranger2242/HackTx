package com.quadx.wgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import shapes1_5_5.physics.Body;
import shapes1_5_5.physics.Physics;
import shapes1_5_5.shapes.ShapeRendererExt;
import shapes1_5_5.timers.Delta;

import static com.quadx.wgame.state.GameState.sun;
import static com.quadx.wgame.state.GameState.world;
import static shapes1_5_5.timers.Time.SECOND;

/**
 * Created by Chris Cavazos on 10/20/2018.
 */
public class Monster {
    public boolean dead = false;
    Body body = new Body();
    float a = 90;
    float h = 0;
    Delta dPlant = new Delta(1 * SECOND);
    Plant target=null;

    public Monster() {
        body.setBoundingBox(new Vector2(0, 0), new Vector2(32, 32));
        body.setPos(Physics.getRadialVector(world.r, 180));
        dPlant.finish();
        target=  world.getRandPlant(this);

    }

    public Monster(float ang) {
        this();
        a=ang;
    }

    public void update(float dt) {
        if(target !=null && !target.dead && target.shaded) {
            float angd = (a - target.ang + 180 + 360) % 360 - 180;
            if(angd>=0){
                move(-1);
            } else {
                move (1);
            }

            if(target.dead){
                target=null;
            }
        }else{
            move((sun.ang % 360) - (a % 360) < 1 ? -1 : 1);

            target=world.getRandPlant(this);
        }
    }

    public void move(float ang) {
        a += ang;
        body.setPos(Physics.getRadialVector(world.r, a).add(world.body.pos()));
    }

    public void render(ShapeRendererExt sr) {
        sr.setColor(dead?Color.PURPLE: Color.RED);
        sr.rect(body.getBoundingBox(), a);
    }

}
