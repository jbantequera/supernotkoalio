package supernotkoalio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import java.util.ArrayList;

public class MainScreen implements Screen {
	Game game;
	
    Stage stage;
	StaticCamera hud;
    TiledMap map;
	int numMapa;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
	BitmapFont font = new BitmapFont();
	final float pixelsPerTile = 8;
	
	ArrayList<Enemigo> listaEnemigos;
	ArrayList<EnemigoSaltarin> listaEnemigosSaltarines;
	ArrayList<Moneda> listaMonedas;
	ArrayList<FrascoVida> listaFrascoVidas;
	ArrayList<FrascoMuerte> listaFrascoMuertes;
	Player skeleton;
	Boss boss;
	
	int vidas = 4;
	int score = 0;

	public MainScreen(Game game){
		this.game = game;
	}
	
    public void show() {
		this.hud = new StaticCamera();
		font.getData().setScale(0.2f);
        map = new TmxMapLoader().load("mapa.tmx");
		numMapa = 1;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
		
        stage = new Stage();
        stage.getViewport().setCamera(camera);
		
        skeleton = new Player();
        skeleton.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        skeleton.setPosition(10, 2);
        stage.addActor(skeleton);
		
		nuevaVida();
    }

	public void generarMonedas(){
		listaMonedas = new ArrayList();
		listaMonedas.add(new Moneda(new Vector2(10, 5)));
		listaMonedas.add(new Moneda(new Vector2(40, 10)));
		listaMonedas.add(new Moneda(new Vector2(50, 7)));
		listaMonedas.add(new Moneda(new Vector2(51, 7)));
		listaMonedas.add(new Moneda(new Vector2(52, 7)));
		listaMonedas.add(new Moneda(new Vector2(66, 4)));
		listaMonedas.add(new Moneda(new Vector2(73, 4)));
		listaMonedas.add(new Moneda(new Vector2(80, 4)));
		listaMonedas.add(new Moneda(new Vector2(109, 6)));
		listaMonedas.add(new Moneda(new Vector2(110, 6)));

		for (Moneda coin : listaMonedas){
			coin.layer = (TiledMapTileLayer) map.getLayers().get("walls");
			stage.addActor(coin);
		}
	}
	
	public void generarEnemigos(){
		
		listaEnemigos = new ArrayList();
		listaEnemigos.add(new Enemigo(new Vector2(5, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(18, 6)));
		listaEnemigos.add(new Enemigo(new Vector2(30, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(38, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(63, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(88, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(90, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(92, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(120, 2)));
		listaEnemigos.add(new Enemigo(new Vector2(136, 6)));
		listaEnemigos.add(new Enemigo(new Vector2(146, 6)));
		
		for (Enemigo ene : listaEnemigos){
			ene.layer = (TiledMapTileLayer) map.getLayers().get("walls");
			stage.addActor(ene);
		}
	}
	
	public void generarEnemigosSaltarines(){
		listaEnemigosSaltarines = new ArrayList();
		listaEnemigosSaltarines.add(new EnemigoSaltarin(new Vector2(66, -3)));
		listaEnemigosSaltarines.add(new EnemigoSaltarin(new Vector2(73, -3)));
		listaEnemigosSaltarines.add(new EnemigoSaltarin(new Vector2(80, -3)));
		listaEnemigosSaltarines.add(new EnemigoSaltarin(new Vector2(107, -3)));
		listaEnemigosSaltarines.add(new EnemigoSaltarin(new Vector2(113, -3)));
		
		for (EnemigoSaltarin ene : listaEnemigosSaltarines){
			ene.layer = (TiledMapTileLayer) map.getLayers().get("walls");
			stage.addActor(ene);
		}
	}
	
	public void generarFrascoVidas(){
		listaFrascoVidas = new ArrayList();
		listaFrascoVidas.add(new FrascoVida(new Vector2(28, 16)));

		for (FrascoVida frasco : listaFrascoVidas){
			frasco.layer = (TiledMapTileLayer) map.getLayers().get("walls");
			stage.addActor(frasco);
		}
	}
	
	public void generarFrascoMuertes(){
		if (listaFrascoMuertes == null){
			listaFrascoMuertes = new ArrayList();
			listaFrascoMuertes.add(new FrascoMuerte(new Vector2(28, 3)));

			for (FrascoMuerte frasco : listaFrascoMuertes){
				frasco.layer = (TiledMapTileLayer) map.getLayers().get("walls");
				stage.addActor(frasco);
			}
		}
	}
	
	public void generarBoss(){
		boss = new Boss(new Vector2(19, 4));
		boss.layer = (TiledMapTileLayer) map.getLayers().get("walls");
		stage.addActor(boss);
		boss.setPlayerPosition(new Vector2(skeleton.getX(), skeleton.getY()));
	}
	
	public void nuevaVida(){
		vidas--;
		score = 0;
		
		if (vidas > 0){
			if (numMapa == 1){
				skeleton.setPosition(10, 2);
				
				limpiarObjetosYEnemigos();
				generarMonedas();
				generarFrascoVidas();
				generarFrascoMuertes();
				generarEnemigos();
				generarEnemigosSaltarines();
			} else {
				skeleton.setPosition(1, 2);
				boss.setPosition(19, 4);
			}
		} else {
			game.setScreen(new FailScreen(game, score));
		}
	}
	
	public void limpiarObjetosYEnemigos(){
		if(listaEnemigos != null){
			for (Enemigo ene : listaEnemigos){
				ene.remove();
			}
		}
		
		if(listaEnemigosSaltarines != null){
			for (EnemigoSaltarin ene : listaEnemigosSaltarines){
				ene.remove();
			}
		}
		
		if(listaMonedas != null){
			for (Moneda coin : listaMonedas){
				coin.remove();
			}
		}
		
		if(listaFrascoVidas != null){
			for (FrascoVida frasco : listaFrascoVidas){
				frasco.remove();
			}
		}
		
		if(listaFrascoMuertes != null){
			for (FrascoMuerte frasco : listaFrascoMuertes){
				frasco.remove();
			}
		}
	}
	
	public void cambiarMapa(String mapa){
		this.map = new TmxMapLoader().load(mapa);
		this.renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
		this.numMapa = 2;
		
		skeleton.setPosition(1, 2);
		skeleton.layer = (TiledMapTileLayer) map.getLayers().get("walls");
		
		limpiarObjetosYEnemigos();
		generarBoss();
	}
	
    public void render(float delta) {
        Gdx.gl.glClearColor(0.129f, 0.149f, 0.247f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (numMapa == 1)
			hud.renderBackground();
		
		camera.position.x = skeleton.getX() + skeleton.getWidth() / 2;
		
		if (numMapa == 1){
			if (camera.position.x - (camera.viewportWidth / 2) <= 1)
				camera.position.x = 1 + (camera.viewportWidth / 2);
		}
		camera.update();
		
		renderer.setView(camera);
        renderer.render();
		
		if (numMapa == 1){
			calcularColisionEnemigos();
			calcularColisionEnemigosSaltarines();
			calcularColisionMonedas();
			calcularColisionFrascoVidas();
			calcularColisionFrascoMuertes();
		} else {
			boss.setPlayerPosition(new Vector2(skeleton.getX(), skeleton.getY()));
			calcularColisionBoss();
		}
		
		stage.act(delta);
		
		if (skeleton.getY() < 0)
			nuevaVida();
		
		if (numMapa == 1){
			if (skeleton.getX() >= 178 && skeleton.getX() <= 182){
				if (skeleton.getY() <= 6.1 && skeleton.getY() >= 6){
					cambiarMapa("mapa2.tmx");
				}
			}
		}
		
		
		if (numMapa == 2){
			if (skeleton.getX() >= 15 && skeleton.getX() <= 16)
				if (skeleton.getY() >= 7 && skeleton.getY() <= 8)
					for (int i = 2; i <= 9; i++)
						((TiledMapTileLayer) map.getLayers().get("walls")).setCell(24, i, null);
			
			if (skeleton.getX() >= 27 && skeleton.getX() <= 29)
				if (skeleton.getY() >= 2 && skeleton.getY() <= 3)
					game.setScreen(new WinScreen(game, score));
		}
		
        stage.draw();
		hud.renderHUD(score, vidas);
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }

    public void resume() {
    }

	private void calcularColisionEnemigos() {
		for (Enemigo ene : listaEnemigos){
			if (camera.position.x + camera.viewportWidth / 2 >= ene.getX())
				ene.enable();
			
			Rectangle hitBoxEne = new Rectangle(ene.getX(), ene.getY(), ene.getWidth(), ene.getHeight());
			Rectangle hitBoxPlay = new Rectangle(skeleton.getX(), skeleton.getY(), skeleton.getWidth(), skeleton.getHeight());
			
			if (hitBoxEne.overlaps(hitBoxPlay))
				nuevaVida();
		}
	}
	
	private void calcularColisionBoss() {
		Rectangle hitBoxEne = new Rectangle(boss.getX(), boss.getY(), boss.getWidth(), boss.getHeight());
		Rectangle hitBoxPlay = new Rectangle(skeleton.getX(), skeleton.getY(), skeleton.getWidth(), skeleton.getHeight());

		if (hitBoxEne.overlaps(hitBoxPlay))
			nuevaVida();
	}
	
	private void calcularColisionEnemigosSaltarines(){
		for (EnemigoSaltarin ene : listaEnemigosSaltarines){
			if (camera.position.x + camera.viewportWidth / 2 >= ene.getX())
				ene.enable();
			
			Rectangle hitBoxEne = new Rectangle(ene.getX(), ene.getY(), ene.getWidth(), ene.getHeight());
			Rectangle hitBoxPlay = new Rectangle(skeleton.getX(), skeleton.getY(), skeleton.getWidth(), skeleton.getHeight());
			
			if (hitBoxEne.overlaps(hitBoxPlay))
				nuevaVida();
		}
	}
	
	private void calcularColisionMonedas() {
		for (int i = 0; i < listaMonedas.size(); i++){
			Moneda coin = listaMonedas.get(i);
			
			Rectangle hitBoxCoin = new Rectangle(coin.getX(), coin.getY(), coin.getWidth(), coin.getHeight());
			Rectangle hitBoxPlay = new Rectangle(skeleton.getX(), skeleton.getY(), skeleton.getWidth(), skeleton.getHeight());
			
			if (hitBoxPlay.overlaps(hitBoxCoin)){
				listaMonedas.remove(coin);
				coin.remove();
				score++;
			}
		}
	}
	
	private void calcularColisionFrascoVidas() {
		for (int i = 0; i < listaFrascoVidas.size(); i++){
			FrascoVida frasco = listaFrascoVidas.get(i);
			
			Rectangle hitBoxFrasco = new Rectangle(frasco.getX(), frasco.getY(), frasco.getWidth(), frasco.getHeight());
			Rectangle hitBoxPlay = new Rectangle(skeleton.getX(), skeleton.getY(), skeleton.getWidth(), skeleton.getHeight());
			
			if (hitBoxPlay.overlaps(hitBoxFrasco)){
				listaFrascoVidas.remove(frasco);
				frasco.remove();
				vidas++;
			}
		}
	}
	
	private void calcularColisionFrascoMuertes() {
		for (int i = 0; i < listaFrascoMuertes.size(); i++){
			FrascoMuerte frasco = listaFrascoMuertes.get(i);
			
			Rectangle hitBoxFrasco = new Rectangle(frasco.getX(), frasco.getY(), frasco.getWidth(), frasco.getHeight());
			Rectangle hitBoxPlay = new Rectangle(skeleton.getX(), skeleton.getY(), skeleton.getWidth(), skeleton.getHeight());
			
			if (hitBoxPlay.overlaps(hitBoxFrasco)){
				listaFrascoMuertes.remove(frasco);
				frasco.remove();
				nuevaVida();
			}
		}
	}
}
