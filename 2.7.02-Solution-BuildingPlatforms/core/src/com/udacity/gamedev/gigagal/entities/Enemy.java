package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;
import com.udacity.gamedev.gigagal.util.Enums.Direction;
import com.udacity.gamedev.gigagal.util.Utils;


public class Enemy {

    final long startTime;
    private final Platform platform;

    // TODO: Note that we added a random phase
    private final float randomPhase;

    public Vector2 position;
    public int health;
    private Direction direction;

    public Enemy(Platform platform) {
        this.platform = platform;
        direction = Direction.RIGHT;
        position = new Vector2(platform.left, platform.top + Constants.ENEMY_CENTER.y);
        startTime = TimeUtils.nanoTime();
        health = Constants.ENEMY_HEALTH;

        // TODO: Initialized it
        randomPhase = MathUtils.random();
    }

    public void update(float delta) {
        switch (direction) {
            case LEFT:
                position.x -= Constants.ENEMY_MOVEMENT_SPEED * delta;
                break;
            case RIGHT:
                position.x += Constants.ENEMY_MOVEMENT_SPEED * delta;
        }

        if (position.x < platform.left) {
            position.x = platform.left;
            direction = Direction.RIGHT;
        } else if (position.x > platform.right) {
            position.x = platform.right;
            direction = Direction.LEFT;
        }

        final float elapsedTime = Utils.secondsSince(startTime);

        // TODO: And used it to make sure that the enemies don't all bob up and down at once
        final float bobMultiplier = 1 + MathUtils.sin(MathUtils.PI2 * ((elapsedTime / Constants.ENEMY_BOB_PERIOD) + randomPhase));

        position.y = platform.top + Constants.ENEMY_CENTER.y + Constants.ENEMY_BOB_AMPLITUDE * bobMultiplier;
    }

    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.enemyAssets.enemy;
        Utils.drawTextureRegion(batch, region, position, Constants.ENEMY_CENTER);
    }
}
