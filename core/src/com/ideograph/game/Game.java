// PixelCat was once here :ohayo: !

package com.ideograph.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sun.java.accessibility.util.internal.CheckboxTranslator;


import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.utils.Null;
//import com.badlogic.gdx.utils.ScreenUtils;
//import org.graalvm.compiler.core.phases.EconomyHighTier;
//import java.util.ArrayList;
//import com.badlogic.gdx.maps.MapObjects;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;


public class Game extends ApplicationAdapter {

	SpriteBatch batch_character; // ideograph_advantage
	Texture img_character;   // img of ideograph_advantage
	static float character_x = 490;
	static float character_y = 490;
	static float character_delta_x = 0;
	static float character_delta_y = 0;
	static float health_cur;
	static float stamina_cur;
	public boolean health_initialized = false;
	public boolean isDead = false;
	public boolean stamina_initialized = false;
	static TiledMap map0;
	public int death_screen_opacity = 0;
	public boolean inInventory = false;
	public int selected_item = 0;
	SpriteBatch batch_vignette;
	Texture img_vignette;
	SpriteBatch health_bar;
	SpriteBatch stamina_bar;
	static TiledMapTileLayer tiledLayer0;
	public int[] inventory_item = new int[25];
	Texture health_bar_bg;

	//level_tutorial level = new level_tutorial();
	Texture health_bar_color;
	Texture health_bar_outline;
	Texture stamina_bar_bg;
	Texture stamina_bar_color;
	Texture stamina_bar_outline;
	//Regions
	TextureRegion health_region;
	TextureRegion stamina_region;
	//death
	SpriteBatch death_screen;
	Texture you_died;   // img of ideograph_advantage
	//inventory
	SpriteBatch inventory;
	Texture inventory_icon;
	Texture inventory_UI;
	Texture inventory_selected;
	Texture dot;
	float map_delta_x = 0;
	float map_delta_y = 0;
	float health = 4900;
	float stamina = 4900;
	boolean jump = true;
	int wall_jump = 0;
	int click_x = 0;
	int click_y = 0;
	// foods :yum:
	Food sushi;
	Food cake;
	Food cookie;
	Food fries;
	Food juice;
	Food meat;
	Food pineapple;
	Food pizza;
	Food ramen;
	Food sunnyside_up_egg;
	Food spaghetti;
	Food taco;
	//tiled map stuff
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	Circle circle = new Circle();
	int count = 0;
	boolean inventory_initialized = false;
	Weapon owo = new Weapon();
	Weapon uwu = new Weapon();

	@Override
	public void create() {
		batch_character = new SpriteBatch();
		img_character = new Texture("u6307(64x).png");
		batch_vignette = new SpriteBatch();
		img_vignette = new Texture("vignette.png");

		//health bar = V5
		health_bar = new SpriteBatch();
		health_bar_bg = new Texture("BarV5_Bar.png");
		health_bar_color = new Texture("BarV5_ProgressBar.png");
		health_bar_outline = new Texture("BarV4_ProgressBarBorder.png");

		//stamina bar = V4
		stamina_bar = new SpriteBatch();
		stamina_bar_bg = new Texture("BarV4_Bar.png");
		stamina_bar_color = new Texture("BarV4_ProgressBar.png");
		stamina_bar_outline = new Texture("BarV4_ProgressBarBorder.png");



		//Regions
		health_region = new TextureRegion(health_bar_color, 1.0f, character_y - 470, (float) 271, 21.0f);
		stamina_region = new TextureRegion(stamina_bar_color, 1.0f, character_y - 490, (float) 271, 21.0f);

		// you died
		death_screen = new SpriteBatch();
		you_died = new Texture("you_died.png");

		// inventory
		inventory = new SpriteBatch();
		inventory_icon = new Texture("inventory_icon.png");
		inventory_UI = new Texture("inventory.png");
		inventory_selected = new Texture("inventory_selected.png");

		//maploader(?
		maploader.loadmap(level);

		//Load Map
		map = new TmxMapLoader().load("level/0.tmx");
		tiledLayer = (TiledMapTileLayer) maploader.map_current.getLayers().get(0);

		// camera stuff
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(maploader.map_current);


		/*
		item_id / item_name / description
		1  / sushi   		  / 好吃的壽司
		2  / cake    		  / 看起來很好吃的蛋糕 ||實際上已經放了兩個月||
		3  / cookie  		  /  一片手工餅乾
		4  / fries            /  麥X勞的薯條
		5  / juice   		  / 一杯果汁 ||內含酒精||
		6  / meat    		  / 一塊肉
		7  / pineapple 	      / 從鳳梨披薩拔下來的鳳梨
		8  / pizza            / 拔掉鳳梨的披薩
		9  / ramen 		  	  / 抽槳用拉麵
		10 / sunnyside_up_egg / 一顆蛋 似乎跟端午節立的但是同一顆
		11 / spaghetti 		  /
		12 / taco             / taco
		 */

		this.sushi = new Food("sushi", "好吃的壽司");
		this.cake = new Food("cake", "看起來很好吃的蛋糕 ||實際上已經放了兩個月||");
		this.cookie = new Food("cookie", "一片手工餅乾");
		this.fries = new Food("fries","麥X勞的薯條");
		this.juice = new Food("juice","一杯果汁 ||內含酒精||");
		this.meat = new Food("meat","一塊肉");
		this.pineapple = new Food("pineapple","從鳳梨披薩拔下來的鳳梨");
		this.pizza = new Food("pizza","拔掉鳳梨的披薩");
		this.ramen = new Food("ramen","抽槳用拉麵");
		this.sunnyside_up_egg = new Food("sunnyside_egg","一顆蛋 似乎跟端午節立的但是同一顆");
		this.spaghetti = new Food("spaghetti","原本在冷凍包裝中的義大利麵");
		this.taco = new Food("taco","taco");
	}

	public void renderBackground() {
		Gdx.gl.glClearColor(39 / 255f, 40 / 255f, 34 / 255f, 0.9f);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(character_x + 490, character_y, 0);
		camera.update();


		if (isDead) {
			camera.position.set((int) character_x + 490, character_y, 1);
		}

		tiledMapRenderer.render();
		tiledMapRenderer.setView(camera);
	}

	public void new_movement() {
		float xs = 0, ys = 0;
		float delta_xs = (1000000.0f / 60 / (character_delta_x + 0.00000004949494949f));
		float delta_ys = (1000000.0f / 60 / (character_delta_y + 0.00000004949494949f));
		int x_positive;
		int y_positive;
		if (delta_xs < 0) {
			delta_xs *= -1;
		}
		if (delta_ys < 0) {
			delta_ys *= -1;
		}
		while (xs < 16666 && ys < 16666) {
			if (character_delta_x < 0) {
				x_positive = -1;
			} else {
				x_positive = 1;
			}
			if (character_delta_y < 0) {
				y_positive = -1;
			} else {
				y_positive = 1;
			}

			if (xs + delta_xs < ys + delta_ys) {
				character_x += x_positive;
				xs += delta_xs;
			} else {
				character_y += y_positive;
				ys += delta_ys;
			}


			new_collision();
		}
	}

//	public void math_collision() {
//		int collision_x = (int) (character_x) / 72;
//		int collision_y = (int) (character_y) / 72;
//		boolean checked = false;
//		Rectangle collision_rect;
//		for (int i = -2; i < 3; i++) {
//			for (int j = -2; j < 3; j++) {
//				if (tiledLayer0.getCell(collision_x + i, collision_y + j) != null && !checked) {
//					count += 1;
//					circle.x = character_x + character_delta_x;
//					circle.y = character_y + character_delta_y;
//					circle.radius = 32;
//					collision_rect = new Rectangle((collision_x + i) * 72 - 32, (collision_y + j) * 72 - 32, 72, 72);
//					if (Intersector.overlaps(circle, collision_rect)) {
////						int up_left =
//					}
//				}
//			}
//		}
//	}

	public void new_collision() {
		int collision_x = (int) (character_x) / 72;
		int collision_y = (int) (character_y) / 72;
		int x_collision = 0;
		int y_collision = 0;
		boolean checked = false;
		Rectangle collision_rect;
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				if (tiledLayer0.getCell(collision_x + i, collision_y + j) != null && !checked) {
					count += 1;
					circle.x = character_x + character_delta_x;
					circle.y = character_y + character_delta_y;
					circle.radius = 32;
					collision_rect = new Rectangle((collision_x + i) * 72 - 32, (collision_y + j) * 72 - 32, 72, 72);
					if (Intersector.overlaps(circle, collision_rect)) {
						// collision on x direction
						circle.x = character_x + 32;
						circle.y = character_y;
						circle.radius = 1;
						if (!checked) {
							if (Intersector.overlaps(circle, collision_rect)) {
								x_collision = 1;
								checked = true;
								if (wall_jump != 1) {
									jump = true;
								}
								wall_jump = 1;
							}
							circle.x = character_x - 32;
							if (Intersector.overlaps(circle, collision_rect)) {
								x_collision = 1;
								checked = true;
								if (wall_jump != 2) {
									jump = true;
								}
								wall_jump = 2;
							}

							//collision on y direction
							circle.x = character_x;
							circle.y = character_y + 32;
							circle.radius = 1;
							if (Intersector.overlaps(circle, collision_rect)) {
								y_collision = 1;
								checked = true;
							}
							circle.y = character_y - 32;
							if (Intersector.overlaps(circle, collision_rect)) {
								y_collision = 1;
								checked = true;
								jump = true;
								wall_jump = 0;
							}
						}
						if (!checked) {
							circle.x = character_x + 23;
							circle.y = character_y + 23;
							circle.radius = 1;
							if (Intersector.overlaps(circle, collision_rect)) {
								x_collision = 1;
								y_collision = 1;
							}
						}
						if (!checked) {
							circle.x = character_x - 23;
							circle.y = character_y + 23;
							circle.radius = 1;
							if (Intersector.overlaps(circle, collision_rect)) {
								x_collision = 1;
								y_collision = 1;
							}
						}
						if (!checked) {
							circle.x = character_x + 23;
							circle.y = character_y - 23;
							circle.radius = 1;
							if (Intersector.overlaps(circle, collision_rect)) {
								x_collision = 1;
								y_collision = 1;
								jump = true;
							}
						}
						if (!checked) {
							circle.x = character_x - 23;
							circle.y = character_y - 23;
							circle.radius = 1;
							if (Intersector.overlaps(circle, collision_rect)) {
								x_collision = 1;
								y_collision = 1;
								jump = true;
							}
						}
					}
				}
			}
		}


		if (x_collision == 1) {
			character_delta_x = -1 * character_delta_x;
			//character_delta_x = 0;
		}
		if (y_collision == 1) {
			int owo = 0;
			if (checked) {
				owo = 1;
			}
			character_delta_y = -0.5f - (owo / 2.0f) * character_delta_y;
			//character_delta_y = 0;
		}
	}

	public void hp_handling() {
		if (!health_initialized) {
			health_cur = health;
			health_initialized = true;
		}
		//health_cur --;
		if (health_cur < 0) {
			health_cur = 0;
			isDead = true;
		}
	}

	public void stamina_handling() {
		if (!stamina_initialized) {
			stamina_cur = stamina;
			stamina_initialized = true;
		}
		//stamina_cur --;
		if (stamina_cur < 0) {
			stamina_cur = 0;
		}
	}

	public void death() {
		character_x = 490;
		character_y = 490;
		character_delta_x = 0;
		character_delta_y = 0;
		health_initialized = false;
		stamina_initialized = false;
	}

	public void inventory_processing() {
		//43, 56 (bottom left)
		//43, 362(upper left)
		if (!inventory_initialized) {
			inventory_initialized = true;
			for (int i = 0; i < 25; i += 1) {
				inventory_item[i] = 0;
			}
		}
		//selection input
		if (Gdx.input.isTouched()) {
			if (Gdx.input.getX() > 1154 - 36 && Gdx.input.getX() < 1154 + 36) {
				if (Gdx.input.getY() > 952 - 36 && Gdx.input.getY() < 952 + 36) {
					inInventory = true;
				}
			}
		}
	}

//	public void movement() {
//		//character_x = 490;
//		//character_y = 490;
//		character_x += character_delta_x;
//		character_y += character_delta_y;
//		character_delta_x *= 0.97;
//		character_delta_y *= 0.97;
//		character_delta_y -= 0.69;
//	}

	public void others_move() {
	}

	@Override
	public void render() {
		batch_character.begin();
		batch_vignette.begin();
		health_bar.begin();
		stamina_bar.begin();
		death_screen.begin();
		inventory.begin();


		if (Gdx.input.isTouched()) {
			click_x = Gdx.input.getX();
			click_y = Gdx.input.getY();
		}

		//Health and stamina handling
		stamina_handling();
		hp_handling();
		health_region.setRegion(1, (int) character_y - 470, (int) (271 * (health_cur / health)), 21);
		stamina_region.setRegion(1, (int) character_y - 470, (int) (271 * (stamina_cur / stamina)), 21);
		if (isDead) {
			death();
			if (death_screen_opacity < 100) {
				death_screen_opacity++;
				death_screen.setColor(death_screen.getColor().r, death_screen.getColor().g, death_screen.getColor().b, (100 - death_screen_opacity) / 100f);
				death_screen.draw(you_died, character_x - 490, character_y - 490);
			} else {
				death_screen_opacity = 0;
				isDead = false;
			}
		}

		//calculate character velocity
		float sprint = 0.5f;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && stamina_cur > 0) {
			sprint = 1;
			stamina_cur--;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) character_delta_x -= 1 * sprint;
		if (Gdx.input.isKeyPressed(Input.Keys.D)) character_delta_x += 1 * sprint;
		if (Gdx.input.isKeyPressed(Input.Keys.W) && jump) {
			character_delta_y = 49 * 0.5f;
			jump = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) character_delta_y -= 1 * sprint;

		// collision
		new_collision();
//		new_movement();
		movement.doMovement();
		renderBackground();

		//tiledmap_type_debug();

		//rendering elements
		batch_character.draw(img_character, (int) character_x, (int) character_y);
		batch_vignette.draw(img_vignette, character_x - 490, character_y - 490);
		health_bar.draw(health_bar_bg, character_x + 335, character_y - 390);
		health_bar.draw(health_region, character_x + 335, character_y - 390);
		health_bar.draw(health_bar_outline, character_x + 331, character_y - 395);
		stamina_bar.draw(stamina_bar_bg, character_x + 335, character_y - 410);
		stamina_bar.draw(stamina_region, character_x + 335, character_y - 410);
		stamina_bar.draw(stamina_bar_outline, character_x + 331, character_y - 415);

		//inventory
		if (!inInventory) {
			inventory.draw(inventory_icon, character_x + 630, character_y - 420);
			if (Gdx.input.isTouched()) {
				// 1154 952 center
				if (click_x > 1154 - 36 && click_x < 1154 + 36 &&
						click_y > 952 - 36 && click_y < 952 + 36) {
					inInventory = true;
				}
			}
		} else {
			inventory_processing();
			inventory.draw(inventory_UI, character_x + 300, character_y - 250);
			//close
			if (Gdx.input.isTouched()) {
//				System.out.println("x = " + Gdx.input.getX());
//				System.out.println("y = " + Gdx.input.getY());
				// 1704 334 center
				if (Gdx.input.getX() > 1704 - 10 && Gdx.input.getX() < 1704 + 10) {
					if (Gdx.input.getY() > 334 - 10 && Gdx.input.getY() < 334 + 10) {
						inInventory = false;
//						System.out.println("owo");
					}
				}
			}

			// foods
			inventory.draw(sushi.item_texture, character_x + 300 + 43, character_y - 250 + 362);
			inventory.draw(cake.item_texture, character_x + 300 + 43 + 102, character_y - 250 + 362);
			inventory.draw(cookie.item_texture, character_x + 300 + 43 + 204, character_y - 250 + 362);
			inventory.draw(fries.item_texture, character_x + 300 + 43 + 306, character_y - 250 + 362);
			inventory.draw(juice.item_texture, character_x + 300 + 43 + 408, character_y - 250 + 362);

			inventory.draw(meat.item_texture, character_x + 300 + 43, character_y - 250 + 362 - 102);
			inventory.draw(pineapple.item_texture, character_x + 300 + 43 + 102, character_y - 250 + 362 - 102);
			inventory.draw(pizza.item_texture, character_x + 300 + 43 + 204, character_y - 250 + 362 - 102);
			inventory.draw(ramen.item_texture, character_x + 300 + 43 + 306, character_y - 250 + 362 - 102);
			inventory.draw(spaghetti.item_texture, character_x + 300 + 43 + 408, character_y - 250 + 362 - 102);

			inventory.draw(sunnyside_up_egg.item_texture, character_x + 300 + 43, character_y - 250 + 362 - 204);
			inventory.draw(taco.item_texture, character_x + 300 + 43 + 102, character_y - 250 + 362 - 204);


//			if(click_x-(character_x+343) % 102 < 90){
////				if(0 < (click_x-(character_x+343)) / 102 && (click_x-(character_x+343))/102 < 5 ){
//					if(click_y-(character_y-112) % 102 < 90){
//						if(0 < (click_y-(character_y-112)) / 102 && (click_y-(character_y-112))/102 < 5 ){
//							selected_item = (int) ((click_y-(character_y-112))/102*5+(click_x-(character_x+343))/102);
//						}
//					}
//				}
//			}
//			System.out.println(selected_item);
			if (selected_item != 0) {
				inventory.draw(inventory_selected, character_x + 343 + (selected_item % 5) * 102 - 1, character_y - 250 + 362 - (selected_item / 5) * 102 - 1);
			}
		}

//		inventory.draw(dot, (character_x/72)*72, (character_y/72)*72 );
		batch_character.end();
		batch_vignette.end();
		health_bar.end();
		stamina_bar.end();
		death_screen.end();
		inventory.end();
	}

	@Override
	public void dispose () {
		batch_character.dispose();
		img_character.dispose();
		batch_vignette.dispose();
		img_vignette.dispose();
		health_bar.dispose();
		stamina_bar.dispose();
		death_screen.dispose();
		inventory.dispose();
	}
}


/*
files
.tmx in ideograph advanture
.tsx in assets




*/


//	class Item {
//		private final String m_name;
//		private final String m_desc;
//		private final Texture m_texture;
//
//		private Item(String name, String desc){
//			this.m_name = name;
//			this.m_desc = desc;
//			this.m_texture = new Texture(this.m_name+".png");;
//		}
//	}
//
//
//	class Food extends Item {
//
//		private Food(String name, String desc){
//			super(name, desc);
//		}
//
//		public Texture getTexture(){
//			return this.m_texture;
//		}
//
//		public void use() {
//			System.out.println("You ate a " + this.name + " :yum: !");
//		}
//	}
//
//	class Pizza extends Food {
//		public Pizza() {
//			super("pizza", "拔掉鳳梨的披薩");
//		}
//
//		public void use() {
//			// ...
//		}
//	}
