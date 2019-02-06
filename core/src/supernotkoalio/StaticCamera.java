/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernotkoalio;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Javier
 */
public class StaticCamera {
	SpriteBatch batch;
	OrthographicCamera camera;
	BitmapFont font;
	Texture background = new Texture("background.png");
	Texture middleground = new Texture("middleground.png");
	
	public StaticCamera(){
		this.batch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.font = new BitmapFont();
		this.font.getData().setScale(2);
		
		camera.setToOrtho(false, 800, 480);
	}
	
	public void renderHUD(int score, int lifes){
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		font.draw(batch, "SCORE: " + String.valueOf(score), 10, 460);
		font.draw(batch, "LIFES: " + String.valueOf(lifes), 10, 430);
		batch.end();
	}
	
	public void renderBackground(){
		batch.begin();
		batch.draw(background, 0, 0, 800, 480);
		batch.draw(middleground, 0, 0, 800, 480);
		batch.end();
	}
}
