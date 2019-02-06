package supernotkoalio.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import supernotkoalio.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		config.title = "Super NotKoalio";
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new MyGdxGame(), config);
	}
}
