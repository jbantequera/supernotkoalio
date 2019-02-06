package supernotkoalio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class EnemigoSaltarin extends Image {
    TextureRegion stand;
    Animation walk;

    float time = 0;
    float yVelocity = 0;
	
	boolean startMoving = false;
	boolean canJump = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -0.4f;
	final float MAX_VELOCITY = 5f;
	
    public EnemigoSaltarin(Vector2 position) {
        final float width = 12;
        final float height = 15;
        this.setSize(1, height/width);

        Texture playerTexture = new Texture("skull.png");
        TextureRegion[][] grid = TextureRegion.split(playerTexture, (int) width, (int) height);

        stand = grid[0][0];
        walk = new Animation(0.15f, grid[0][0], grid[0][1], grid[0][2], grid[0][3]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		this.setPosition(position.x, position.y);
    }

    public void act(float delta) {
        time = time + delta;
		if (startMoving){
			if (canJump) {
                yVelocity = yVelocity + MAX_VELOCITY * 4;
				canJump = false;
            }
			
			yVelocity = yVelocity + GRAVITY;

			float y = this.getY();
			float yChange = yVelocity * delta;
			
			if (time >= 3){
				canJump = true;
				time = 0;
			}
			
			if (y + yChange < -2){
				yChange = 0;
				yVelocity = 0;
				this.setPosition(this.getX(), -2);
			} else {
				this.setPosition(this.getX(), y + yChange);
			}
		}
    }

    public void draw(Batch batch, float parentAlpha) {
		batch.draw(walk.getKeyFrame(time), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
	
	public void enable(){
		this.startMoving = true;
	}
}
