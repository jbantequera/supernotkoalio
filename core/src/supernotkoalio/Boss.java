package supernotkoalio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Boss extends Image {
    TextureRegion stand;
    Animation walk;

    float time = 0;
    float xVelocity = 4.5f;
	float yVelocity = 3;
	
	boolean startMoving = false;
    boolean isFacingRight = true;
	boolean isMoving = false;
    TiledMapTileLayer layer;

    Vector2 playerPosition;
	
    public Boss(Vector2 position) {
        final float width = 16;
        final float height = 15;
        this.setSize(1, height/width);

        Texture playerTexture = new Texture("bat.png");
        TextureRegion[][] grid = TextureRegion.split(playerTexture, (int) width, (int) height);

        stand = grid[0][0];
        walk = new Animation(0.15f, grid[0][0], grid[0][1], grid[0][2]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		this.setPosition(position.x, position.y);
    }

	public void setPlayerPosition(Vector2 playerPosition){
		this.playerPosition = playerPosition;
	}
	
    public void act(float delta) {
		time = time + delta;
		
		float x = this.getX();
		float y = this.getY();
		float angle = (float)Math.atan2(playerPosition.y - y, playerPosition.x - x);

		x += xVelocity * Math.cos(angle) * delta;
		y += yVelocity * Math.sin(angle) * delta;

		if (xVelocity * Math.cos(angle) >= 0)
			isFacingRight = true;
		else
			isFacingRight = false;

		if (x >= 24)
			x = 23;
		
		this.setPosition(x, y);
	}

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
		frame = walk.getKeyFrame(time);

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }
	
	public void enable(){
		this.startMoving = true;
	}
}
