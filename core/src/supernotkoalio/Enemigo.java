package supernotkoalio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Enemigo extends Image {
    TextureRegion stand;
    Animation walk;

    float time = 0;
    float xVelocity = -2;
    float yVelocity = 0;
	
	boolean startMoving = false;
    boolean isFacingRight = true;
	boolean isMoving = false;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.8f;
	
    public Enemigo(Vector2 position) {
        final float width = 14;
        final float height = 14;
        this.setSize(1, height/width);

        Texture playerTexture = new Texture("ghost.png");
        TextureRegion[][] grid = TextureRegion.split(playerTexture, (int) width, (int) height);

        stand = grid[0][0];
        walk = new Animation(0.5f, grid[0][0], grid[0][1], grid[0][2]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		this.setPosition(position.x, position.y);
    }

    public void act(float delta) {
        time = time + delta;

		if (startMoving){
			yVelocity = yVelocity + GRAVITY;

			float x = this.getX();
			float y = this.getY();
			float xChange = xVelocity * delta;
			float yChange = yVelocity * delta;

			if (xVelocity >= 0)
				isFacingRight = true;
			else
				isFacingRight = false;

			if (canMoveTo(x + xChange, y, false) == false) {
				xVelocity *= -1;
				xChange = 0;
			}

			if (canMoveTo(x, y + yChange, false) == false) {
				yVelocity = yChange = 0;
			}

			this.setPosition(x + xChange, y + yChange);
		}
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (xVelocity != 0) {
            frame = walk.getKeyFrame(time);
        } else {
            frame = stand;
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layer.setCell(x, y, null);
                    }
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
	
	public void enable(){
		this.startMoving = true;
	}
}
