package com.hubclub.superjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Character  {
	
	private final int JUMPSPEED=10,ACC=10;
	
	private Rectangle mainBox,legBox;
	
	private int speedX=75,speedPlatform=50;
	
	private float speedY;
	private float animTime,jumpTime;
	
	private boolean onPlatform;
	
	private TextureRegion[][] textures, textures2;
	private Texture texture, texture2;
	private Animation anim, anim2;
	private TextureRegion ninja;
	
	private boolean jumping, falling;
	
	public Character(){
		mainBox = new Rectangle(0,Constants.CHAR_START,Constants.CHAR_WIDTH,Constants.CHAR_HEIGHT);
		legBox = new Rectangle(0,Constants.CHAR_START,Constants.LEG_WIDTH,Constants.LEG_HEIGHT);
		
		texture=new Texture("Spritesheet.png");
		texture2 = new Texture("Spritesheet2.png");
		textures=TextureRegion.split(texture,texture.getWidth()/5,texture.getHeight()/5);
		textures2=TextureRegion.split(texture2,texture2.getWidth()/5,texture2.getHeight()/5);
		
		anim=new Animation(.1F,textures[0]);
		anim2 = new Animation(.1F,textures2[0]);
		ninja=textures[0][0];
		
		onPlatform=true;
	}
	
	
	public void render(float delta){
		if(animTime>0.5f)
			animTime=0;
		animTime+=delta;
		
		input(delta);
		
		if(jumping){
			jump(delta);
		}
		
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(ninja,mainBox.x,mainBox.y,mainBox.width,mainBox.height);
	}
	
	public void input(float delta){
		if(onPlatform){
			if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
				setX(mainBox.x+speedPlatform*delta*Gdx.input.getAccelerometerY());
				if(Gdx.input.getAccelerometerY()>=0){
					ninja=anim.getKeyFrame(animTime);
				}
				else{
					ninja=anim2.getKeyFrame(animTime);
					}
			}
			else{
				if(Gdx.input.isKeyPressed(Keys.RIGHT)){
					setX(mainBox.x+speedPlatform*delta);
					ninja=anim.getKeyFrame(animTime);
				}
				else if(Gdx.input.isKeyPressed(Keys.LEFT)){
					setX(mainBox.x-speedPlatform*delta);
					ninja=anim2.getKeyFrame(animTime);
				}
			}
		}
		else{
			if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
				setX(mainBox.x+speedX*delta*Gdx.input.getAccelerometerY());
			}
			else{
				if(Gdx.input.isKeyPressed(Keys.RIGHT)){
					setX(mainBox.x+speedX*delta);
				}
				else if(Gdx.input.isKeyPressed(Keys.LEFT)){
					setX(mainBox.x-speedX*delta);
				}
			}
			
		}
		
		if(Gdx.input.justTouched()){
			jumping=true;
		}
		
		
	}
	
	private void setX(float x){
		mainBox.x=x;
		legBox.x=x;
	}
	
	private void setY(float y) {
		mainBox.y=y;
		legBox.y=y;
	}
	
	private void setJumping (boolean jumping) {
		this.jumping = jumping;
	}
	
	private void jump(float delta){
		jumpTime+=delta;
		speedY=JUMPSPEED-ACC*jumpTime;
		setY(mainBox.y+speedY);
		
		if(legBox.y<100){
			legBox.y=100;
			jumping=false;
			speedY=0;
			jumpTime=0;
		}
	}
}
