package supernotkoalio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen {
	OrthographicCamera camera;
	Texture mainMenuImage;
	SpriteBatch batch;
	Game game;
	
	public MenuScreen(Game game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.camera = new OrthographicCamera(800, 480);
		
		this.mainMenuImage = new Texture(Gdx.files.internal("mainmenu.png"));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(mainMenuImage, 0 - (camera.viewportWidth / 2), 0 - (camera.viewportHeight / 2));
		batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if (touchPos.x >= -160 && touchPos.x <= 160){
				if (touchPos.y >= -224 && touchPos.y <= -154){
					game.setScreen(new MainScreen(game));
					dispose();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
