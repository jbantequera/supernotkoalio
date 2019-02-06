package supernotkoalio;

import com.badlogic.gdx.Game;
import supernotkoalio.MainScreen;

public class MyGdxGame extends Game {
	public void create() {
		this.setScreen(new MenuScreen(this));
	}
}
