/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernotkoalio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Javier
 */
public class WinScreen implements Screen{

	Game game;
	OrthographicCamera camera;
	Texture failScreenImage;
	SpriteBatch batch;
	BitmapFont font;
	int score;
	
	public WinScreen (Game game, int score){
		this.game = game;
		this.score = score;
		this.batch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.font = new BitmapFont();
		this.font.getData().setScale(3);
		
		camera.setToOrtho(false, 800, 480);
		failScreenImage = new Texture(Gdx.files.internal("winmenu.png"));
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(failScreenImage, 0, 0);
		font.draw(batch, String.valueOf(score), 403, 265);
		batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if (touchPos.x >= 238 && touchPos.x <= 561){
				if (touchPos.y >= 16 && touchPos.y <= 88){
					game.setScreen(new MainScreen(game));
					dispose();
				}
				
				if (touchPos.y >= 105 && touchPos.y <= 303){
					game.setScreen(new MenuScreen(game));
					dispose();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
}
