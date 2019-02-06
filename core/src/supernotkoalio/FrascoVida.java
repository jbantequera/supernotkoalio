package supernotkoalio;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class FrascoVida extends Image {
    Animation levitate;
    float time = 0;
    TiledMapTileLayer layer;
	
    public FrascoVida(Vector2 position) {
        final float width = 8;
        final float height = 12;
        this.setSize(1, height/width);

        Texture coinTexture = new Texture("upflask.png");
        TextureRegion[][] grid = TextureRegion.split(coinTexture, (int) width, (int) height);

        levitate = new Animation(0.15f, grid[0][0], grid[0][1], grid[0][2], grid[0][3]);
        levitate.setPlayMode(Animation.PlayMode.LOOP);
		
		this.setPosition(position.x, position.y);
    }

    public void act(float delta) {
        time = time + delta;
    }

    public void draw(Batch batch, float parentAlpha) {
		batch.draw(levitate.getKeyFrame(time), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
