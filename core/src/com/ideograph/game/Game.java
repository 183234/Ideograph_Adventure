// PixelCat was once here :ohayo: !
// Thonk

package com.ideograph.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.utils.Null;
//import com.badlogic.gdx.utils.ScreenUtils;
//import org.graalvm.compiler.core.phases.EconomyHighTier;
//import java.util.ArrayList;
//import com.badlogic.gdx.maps.MapObjects;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;


public class Game extends ApplicationAdapter {
	static final int BLOCK_WIDTH = 72;
	static final int CHAR_WIDTH = 64;

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
	static TiledMap map;
	public boolean death_sound_played = false;
	public int death_screen_delay = 100;
	public int death_screen_opacity = 0;
	public boolean inInventory = false;
	public int selected_item = 0;
	static boolean chest_disappear = true;
	static int level = 0;
	static boolean next_level = false;
	static MapLoader maploader = new MapLoader();
	boolean inventory_initialized = false;
	ArrayList<int[]> collsions = new ArrayList<>();
	SpriteBatch batch_vignette;
	Texture img_vignette;
	SpriteBatch health_bar;
	SpriteBatch stamina_bar;
	static TiledMapTileLayer tiledLayer;
	Texture health_bar_bg;
	TextRenderer text_renderer;

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

	Texture cake_title;
	Texture cookie_title;
	Texture fries_title;
	Texture juice_title;
	Texture meat_title;
	Texture pineapple_title;
	Texture pizza_title;
	Texture ramen_title;
	Texture spaghetti_title;
	Texture egg_title;
	Texture sushi_title;
	Texture taco_title;

	Texture cake_description;
	Texture cookie_description;
	Texture fries_description;
	Texture juice_description;
	Texture meat_description;

	Texture pineapple_description;
	Texture pizza_description;
	Texture ramen_description;
	Texture spaghetti_description;
	Texture egg_description;

	Texture sushi_description;
	Texture taco_description;

	Texture use;
	Texture inventory_no_item;
	Texture inventory_health_stamina_full;
	Texture inventory_use_success;
	Texture inventory_temp;
	Texture attack_texture;

	float map_delta_x = 0;
	float map_delta_y = 0;
	float health = 49;
	float stamina = 4900;
	boolean jump = true;
	int wall_jump = 0;
	static int click_x = 0;
	static int click_y = 0;
	int isp = 0; // inventory selected position
	int inventory_das = 48;
	boolean inventory_dased = false;
	int inventory_arr = 12;
	int hint = 0;

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
	Sound menu_select;
	Sound menu_back;
	Sound death;
	static Sound hit;
	static Sound damage;
	static Sound chest_break;
	static Sound collide;
	static Sound consume;
	static Sound inv_select;
	Sound level_start;
	Sound chill_1;
	Sound chill_2;
	//tiled map stuff
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	Weapon owo = new Weapon();
	Weapon uwu = new Weapon();

	SpriteBatch enemies_batch;
	Enemies enemies;

	SpriteBatch attack_batch;



	static ArrayList<Attack> attacks;

	int attack_cd = 20;
	int attack_dx, attack_dy;

	static class Attack{
		float x, y;
		float dx, dy;
		int dmg;
		boolean dead;

		public Attack(float owo, float owowo, float aya, float ayaya){
			this.x = owo;//character_x;
			this.y = owowo; //character_y;
			this.dx = aya; //(float) (click_x/Math.sqrt(click_x*click_x+click_y*click_y+1)) * 5;
			this.dy = ayaya; //(float) (click_y/Math.sqrt(click_x*click_x+click_y*click_y+1)) * 5;
			this.dead = false;
			this.dmg = 5;
		}

		public void move(){
			x += dx;
			y -= dy;
			if(tiledLayer.getCell((int) (this.x/72), (int) (this.y/72)) != null){
				this.dead = true;
			}
			if(tiledLayer.getCell((int) ((this.x+49)/72), (int) (this.y/72)) != null){
				this.dead = true;
			}
			if(tiledLayer.getCell((int) (this.x/72), (int) ((this.y+49)/72)) != null){
				this.dead = true;
			}
			if(tiledLayer.getCell((int)((this.x+49)/72), (int) ((this.y+49)/72)) != null){
				this.dead = true;
			}
		}

		public void render(){
			move();
		}
	}

	public void attack(boolean clicked){
		ArrayList<Attack> remove = new ArrayList<Attack>();
		for(Attack attackkk : this.attacks){
			attackkk.render();
			if(attackkk.dead){
				remove.add(attackkk);
			}
		}
		for(Attack atk : remove) {
			this.attacks.remove(atk);
		}
		if(attack_cd > 0){
			attack_cd--;
		}else if (clicked){
			attack_cd = 37;
			attacks.add(new Attack(character_x, character_y, (float) ( (click_x-490)/Math.sqrt(click_x*click_x+click_y*click_y+0.1)) * 15, (float) ( (click_y-490) /Math.sqrt(click_x*click_x+click_y*click_y+0.1)) * 15));
		}
	}



	@Override
	public void create() {
		menu_select = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu_select.ogg"));
		menu_back = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu_back.ogg"));
		death = Gdx.audio.newSound(Gdx.files.internal("Sounds/death.ogg"));
		level_start = Gdx.audio.newSound(Gdx.files.internal("Sounds/level_start.ogg"));
		chill_1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/chill_1.ogg"));
		chill_2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/chill_2.ogg"));
		hit = Gdx.audio.newSound(Gdx.files.internal("Sounds/hit.ogg"));
		damage = Gdx.audio.newSound(Gdx.files.internal("Sounds/damage.ogg"));
		chest_break = Gdx.audio.newSound(Gdx.files.internal("Sounds/chest_break.ogg"));
		collide = Gdx.audio.newSound(Gdx.files.internal("Sounds/collision.ogg"));
		consume = Gdx.audio.newSound(Gdx.files.internal("Sounds/consume.ogg"));
		inv_select = Gdx.audio.newSound(Gdx.files.internal("Sounds/inv_select.ogg"));

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
		inventory_UI = new Texture("inventory_test.png");
		inventory_selected = new Texture("inventory_selected.png");

		//item titles
		cake_title = new Texture("cake_title.png");
		cookie_title = new Texture("cookie_title.png");
		fries_title = new Texture("fries_title.png");
		juice_title = new Texture("juice_title.png");
		meat_title = new Texture("meat_title.png");
		pineapple_title = new Texture("pineapple_title.png");
		pizza_title = new Texture("pizza_title.png");
		ramen_title = new Texture("ramen_title.png");
		spaghetti_title = new Texture("spaghetti_title.png");
		egg_title = new Texture("egg_title.png");
		sushi_title = new Texture("sushi_title.png");
		taco_title = new Texture("taco_title.png");

		cake_description = new Texture("cake_description.png");
		cookie_description = new Texture("cookie_description.png");
		fries_description = new Texture("fries_description.png");
		juice_description = new Texture("juice_description.png");
		meat_description = new Texture("meat_description.png");

		pineapple_description = new Texture("pineapple_description.png");
		pizza_description = new Texture("pizza_description.png");
		ramen_description = new Texture("ramen_description.png");
		spaghetti_description = new Texture("spaghetti_description.png");
		egg_description = new Texture("egg_description.png");

		sushi_description = new Texture("sushi_description.png");
		taco_description = new Texture("taco_description.png");



		use = new Texture("use.png");
		inventory_use_success = new Texture("inventory_use_success.png");
		inventory_health_stamina_full = new Texture("inventory_health_stamina_full.png");
		inventory_no_item = new Texture("inventory_no_item.png");
		inventory_temp = new Texture("inventory_temp.png");

		attack_texture = new Texture("attack.png");
		attacks = new ArrayList<Attack>();
		attack_batch = new SpriteBatch();

		//maploader(?
		maploader.loadmap(level);

		//Load Map
//		map = new TmxMapLoader().load("level/0.tmx");
		tiledLayer = (TiledMapTileLayer) maploader.map_current.getLayers().get(0);

		// camera stuff
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(maploader.map_current);


		/*
		item_id / item_name / description
		0  / sushi   		  / 好吃的壽司
		1  / cake    		  / 看起來很好吃的蛋糕 ||實際上已經放了兩個月||
		2  / cookie  		  /  一片手工餅乾
		3  / fries            /  麥X勞的薯條
		4  / juice   		  / 一杯果汁 ||內含酒精||
		5  / meat    		  / 一塊肉
		6  / pineapple 	      / 從鳳梨披薩拔下來的鳳梨
		7  / pizza            / 拔掉鳳梨的披薩
		8  / ramen 		  	  / 抽槳用拉麵
		9  / sunnyside_up_egg / 一顆蛋 似乎跟端午節立的蛋是同一顆
		10 / spaghetti 		  / 原本在冷凍包裝中的義大利麵
		11 / taco             / 一個taco
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

		text_renderer = TextRenderer.getInstance();
		enemies_batch = new SpriteBatch();
		enemies = Enemies.getEnemies(0);
	}

	public void renderBackground() {
		Gdx.gl.glClearColor(39 / 255f, 40 / 255f, 34 / 255f, 0.9f);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(character_x + 490, character_y, 0);
		camera.update();


		if (isDead) {
			camera.position.set((int) character_x + 490, character_y, 0);
		}

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	public void hp_handling() {
		if (!health_initialized) {
			health_cur = health;
			health_initialized = true;
		}
		//health_cur --;
		if (health_cur <= 0) {
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
		if(!death_sound_played) {
			death.play();
			death_sound_played = true;
		}
		character_x = 490;
		character_y = 490;
		character_delta_x = 0;
		character_delta_y = 0;

	}

	public void inventory_processing() {
		//43, 56 (bottom left)
		//43, 362(upper left)
		if (!inventory_initialized) {
			inventory_initialized = true;

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

	public void tiledmap_type_debug() {
		TiledMapTileLayer.Cell cell;
		cell = tiledLayer.getCell((int) character_x/Game.BLOCK_WIDTH, (int) (character_y/Game.BLOCK_WIDTH)-1);
		if(cell != null) {
			System.out.println("block type: "+cell.getTile().getId());
		}
	}






	@Override
	public void render() {
		batch_character.begin();
		batch_vignette.begin();
		health_bar.begin();
		stamina_bar.begin();
		death_screen.begin();
		inventory.begin();
		enemies_batch.begin();
		attack_batch.begin();

		if (next_level) {
			chill_2.stop();
			level++;
			maploader.loadmap(level);
			tiledMapRenderer = new OrthogonalTiledMapRenderer(MapLoader.map_current);
			tiledLayer = (TiledMapTileLayer) MapLoader.map_current.getLayers().get(0);
			character_x = 490;
			character_y = 490;
			stamina_initialized = false;
			health_initialized = false;
			next_level = false;
			level_start.play(0.5f);
			chill_2.loop(0.1f);
			enemies.dispose();
			enemies = Enemies.getEnemies(level);
		}

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
				if(death_screen_delay > 0){
					death_screen_delay--;
				}else{
					death_screen_opacity++;
				}
				death_screen.setColor(death_screen.getColor().r, death_screen.getColor().g, death_screen.getColor().b, (100 - death_screen_opacity) / 100f);
				death_screen.draw(you_died, character_x - 530, character_y - 490);
			} else {
				death_screen_opacity = 0;
				death_screen_delay = 100;
				isDead = false;
				death_sound_played = false;
				health_initialized = false;
				stamina_initialized = false;
				inInventory = false;
			}
		}

		// collision
		Movement.doMovement();
		renderBackground();
		collsions = Movement.getCollisions();
		for (int[] cell : collsions) {
//			System.out.println(tiledLayer.getCell(cell[0], cell[1]).getTile().getId());
			int x = cell[0];
			int y = cell[1];
			if (InteractiveBlock.isInteractive(x, y)) {
//				System.out.println("you collided with a interactive block!");
				if (Gdx.input.isKeyPressed(Input.Keys.F)) {
					InteractiveBlock.Interact(x, y);
				}
			}
		}


		//tiledmap_type_debug();

		// rendering elements
		if(!isDead) {
			batch_character.draw(img_character, (int) character_x, (int) character_y);
			batch_vignette.draw(img_vignette, character_x - 470, character_y - 530);
			health_bar.draw(health_bar_bg, character_x + 335, character_y - 390);
			health_bar.draw(health_region, character_x + 335, character_y - 390);
			health_bar.draw(health_bar_outline, character_x + 331, character_y - 395);
			stamina_bar.draw(stamina_bar_bg, character_x + 335, character_y - 410);
			stamina_bar.draw(stamina_region, character_x + 335, character_y - 410);
			stamina_bar.draw(health_bar_outline, character_x + 331, character_y - 415);
			text_renderer.draw(stamina_bar, ((int) stamina_cur) + "/" + ((int) stamina), character_x + 337, character_y - 398);
			text_renderer.draw(stamina_bar, ((int) health_cur) + "/" + ((int) health), character_x + 337, character_y - 373);
		}
		// inventory
		if(!isDead) {
			if (!inInventory) {
				inventory.draw(inventory_icon, character_x + 630, character_y - 420);
				if (Gdx.input.isTouched()) {
					// 1154 952 center
					// 1136 905 new center
//				System.out.println("x = " + Gdx.input.getX());
//				System.out.println("y = " + Gdx.input.getY());
					if (click_x > 1136 - 36 && click_x < 1136 + 36 &&
							click_y > 905 - 36 && click_y < 905 + 36) {
						menu_select.play();
						inInventory = true;
						hint = 0;
//					next_level = true;
					}
				}
			} else {
				inventory_processing();
				inventory.draw(inventory_UI, character_x + 300, character_y - 334);
				//close
				if (Gdx.input.isTouched()) {
//				System.out.println("x = " + Gdx.input.getX());
//				System.out.println("y = " + Gdx.input.getY());
					// 1704 334 center
					// 1685 286 new center
					if (Gdx.input.getX() > 1685 - 17 && Gdx.input.getX() < 1685 + 17) {
						if (Gdx.input.getY() > 286 - 17 && Gdx.input.getY() < 286 + 17) {
							menu_back.play();
							inInventory = false;
//						System.out.println("owo");
						}
					}
					if (Gdx.input.isKeyPressed(Input.Keys.P)) {
						System.out.print("x = ");
						System.out.println(Gdx.input.getX() - 490);
						System.out.print("y = ");
						System.out.println(Gdx.input.getY() - 490);
					}

					int selected = 0;
					int check_x = 0;
					int check_y = 0;
					if (click_x > 490 + 338 && click_x < 490 + 836) {
						if ((click_x - 338 - 490) % 102 < 90) {
							selected += ((click_x - 338 - 490) / 102);
							check_x = 1;
						}
					}
//					System.out.println(selected);
					if (click_y >= 333 && click_y <= 692) {
						if ((click_y - 333) % 122 < 109) {
							selected += (5 * (((click_y - 333) / 122)));
							check_y = 1;
						}
					}

					if (check_x * check_y > 0 && selected < 12) {
						isp = selected;
//						System.out.println(isp);
					}
				}


				// wrong offset. fix later
				for (int i = 0; i < 12; i++) {
					int tmp = Inventory.Food_Inv[i];
					int digit = 0;
					while (tmp > 0) {
						tmp = (int) (tmp / 10);
						digit++;
					}
					text_renderer.draw(inventory, Integer.toString(Inventory.Food_Inv[i]), character_x + 300 + 43 + 33 + (i % 5) * 102 - digit, character_y - 250 + 362 - 7 - (int) (i / 5) * 122);
				}
				if (click_x - (character_x + 343) % 102 < 90) {
//				if(0 < (click_x-(character_x+343)) / 102 && (click_x-(character_x+343))/102 < 5 ){
					if (click_y - (character_y - 112) % 102 < 90) {
						if (0 < (click_y - (character_y - 112)) / 102 && (click_y - (character_y - 112)) / 102 < 5) {
							selected_item = (int) ((click_y - (character_y - 112)) / 102 * 5 + (click_x - (character_x + 343)) / 102);
							inv_select.play();
						}
					}
				}

				// foods
				inventory.draw(sushi.item_texture, character_x + 300 + 43, character_y - 250 + 362);
				inventory.draw(cake.item_texture, character_x + 300 + 43 + 102, character_y - 250 + 362);
				inventory.draw(cookie.item_texture, character_x + 300 + 43 + 204, character_y - 250 + 362);
				inventory.draw(fries.item_texture, character_x + 300 + 43 + 306, character_y - 250 + 362);
				inventory.draw(juice.item_texture, character_x + 300 + 43 + 408, character_y - 250 + 362);

				inventory.draw(meat.item_texture, character_x + 300 + 43, character_y - 250 + 362 - 122);
				inventory.draw(pineapple.item_texture, character_x + 300 + 43 + 102, character_y - 250 + 362 - 122);
				inventory.draw(pizza.item_texture, character_x + 300 + 43 + 204, character_y - 250 + 362 - 122);
				inventory.draw(ramen.item_texture, character_x + 300 + 43 + 306, character_y - 250 + 362 - 122);
				inventory.draw(spaghetti.item_texture, character_x + 300 + 43 + 408, character_y - 250 + 362 - 122);

				inventory.draw(sunnyside_up_egg.item_texture, character_x + 300 + 43, character_y - 250 + 362 - 244);
				inventory.draw(taco.item_texture, character_x + 300 + 43 + 102, character_y - 250 + 362 - 244);

				if (isp == 0) {
					inventory.draw(sushi_title, character_x + 852, character_y + 5);
					inventory.draw(sushi_description, character_x + 852, character_y -200);
				}
				if (isp == 1) {
					inventory.draw(cake_title, character_x + 851, character_y + 5);
					inventory.draw(cake_description, character_x + 852, character_y -200);
				}
				if (isp == 2) {
					inventory.draw(cookie_title, character_x + 852, character_y + 5);
					inventory.draw(cookie_description, character_x + 852, character_y -200);
				}
				if (isp == 3) {
					inventory.draw(fries_title, character_x + 852, character_y + 5);
					inventory.draw(fries_description, character_x + 852, character_y -200);
				}
				if (isp == 4) {
					inventory.draw(juice_title, character_x + 852, character_y + 5);
					inventory.draw(juice_description, character_x + 852, character_y -200);
				}

				if (isp == 5) {
					inventory.draw(meat_title, character_x + 852, character_y + 5);
					inventory.draw(meat_description, character_x + 852, character_y -200);
				}
				if (isp == 6) {
					inventory.draw(pineapple_title, character_x + 852, character_y + 5);
					inventory.draw(pineapple_description, character_x + 852, character_y -200);
				}
				if (isp == 7) {
					inventory.draw(pizza_title, character_x + 852, character_y + 5);
					inventory.draw(pizza_description, character_x + 852, character_y -200);
				}
				if (isp == 8) {
					inventory.draw(ramen_title, character_x + 852, character_y + 5);
					inventory.draw(ramen_description, character_x + 852, character_y -200);
				}
				if (isp == 9) {
					inventory.draw(spaghetti_title, character_x + 852, character_y + 5);
					inventory.draw(spaghetti_description, character_x + 852, character_y -200);
				}

				if (isp == 10) {
					inventory.draw(egg_title, character_x + 852, character_y + 5);
					inventory.draw(egg_description, character_x + 852, character_y -200);
				}
				if (isp == 11) {
					inventory.draw(taco_title, character_x + 852, character_y + 5);
					inventory.draw(taco_description, character_x + 852, character_y -200);
				}
				// hint = 0 temp
				// hint = 1 use_success
				// hint = 2 no_item
				// hint = 3 hp/stamina full
				inventory.draw(use, character_x + 852, character_y -240);
				if(Gdx.input.isTouched()) {
					if (click_x > 490 + 833 && click_x < 490 + 833 + 65) {
						if (click_y - 490 > 233 && click_y - 490 < 272) {
							float stamina_add = 490f;
							float health_add = 49f;
							if(!inventory_dased) {
								inventory_dased = true;
								if(Inventory.Food_Inv[isp] > 0 && (stamina_cur < stamina || health_cur < health) ) {
									stamina_cur += stamina_add;
									health_cur += health_add;
									Inventory.Food_Inv[isp]--;
									hint = 1;
									consume.play();
								}else if(Inventory.Food_Inv[isp] == 0){
									hint = 2;
								}else{
									hint = 3;
								}
							}else if(inventory_das <= 0 && inventory_arr == 12){
								inventory_arr--;
								if (Inventory.Food_Inv[isp] > 0 && (stamina_cur < stamina || health_cur < health)) {
									stamina_cur += stamina_add;
									health_cur += health_add;
									Inventory.Food_Inv[isp]--;
									hint = 1;
									consume.play();
								} else if(Inventory.Food_Inv[isp] == 0){
									hint = 2;
									// owo
									// aya
								}else{
									hint = 3;
								}
							}else if(inventory_das >= 0){
								inventory_das--;
							}else{
								inventory_arr--;
								if(inventory_arr == 0) {
									inventory_arr = 12;
								}
							}
						}else{
							inventory_dased = false;
							inventory_das = 48;
							inventory_arr = 12;
						}
					}else{
						inventory_dased = false;
						inventory_das = 48;
						inventory_arr = 12;
					}
				}
				if(health_cur > health){
					health_cur = health;
				}
				if(stamina_cur > stamina){
					stamina_cur = stamina;
				}
				if(hint == 0) {
					inventory.draw(inventory_temp, character_x + 852 + 64, character_y - 240);
				}
				if(hint == 1) {
					inventory.draw(inventory_use_success, character_x + 852 + 64, character_y - 240);
				}
				if(hint == 2) {
					inventory.draw(inventory_no_item, character_x + 852 + 64, character_y - 240);
				}
				if(hint == 3) {
					inventory.draw(inventory_health_stamina_full, character_x + 852 + 64, character_y - 240);
				}


//			System.out.println(selected_item);
				inventory.draw(inventory_selected, character_x + 338 + (isp % 5) * 102 - 1, character_y - 250 + 362 - 22 - (isp / 5) * 122 - 1);
			}
		}

		if(Gdx.input.isTouched()) {
			attack(true);
		}else{
			attack(false);
		}

		for(Attack attackkk : this.attacks){
			attack_batch.draw(attack_texture, (int)attackkk.x, (int)attackkk.y);
		}

		enemies.render(enemies_batch);
		enemies.attack();
		enemies.be_attacked();

//		System.out.println(character_x/72);
//		System.out.println(character_y/72);
//		System.out.println("ayaya");




		//tiledLayer.setCell();

//		inventory.draw(dot, (character_x/72)*72, (character_y/72)*72 );

		batch_character.end();
		enemies_batch.end();
		batch_vignette.end();
		death_screen.end();
		health_bar.end();
		stamina_bar.end();
		inventory.end();
		attack_batch.end();
	}


	@Override
	public void dispose() {
		batch_character.dispose();
		img_character.dispose();
		batch_vignette.dispose();
		img_vignette.dispose();
		stamina_bar.dispose();
		health_bar.dispose();
		death_screen.dispose();
		attack_batch.dispose();
		inventory.dispose();
		text_renderer.dispose();
		level_start.dispose();
		menu_select.dispose();
		menu_back.dispose();
		death.dispose();

	}
}


/*
files
.tmx in ideograph advanture
.tsx in assets
*/
