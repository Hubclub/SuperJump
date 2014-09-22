package com.hubclub.superjump;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Platform implements Poolable {
	private Rectangle platform;
	private Texture texture;
	private OrthographicCamera cam;
	
	public Platform (int i,OrthographicCamera cam) {
		this.cam=cam;
		texture = new Texture("platform.png");
		
		platform = new Rectangle();
		switch (i) {
		case 1 : SmallPlatform(); break;
		case 2 : MediumPlatform(); break;
		case 3 : BigPlatform(); break;
		case 4 : ResizablePlatform(); break;
		case 5 : BreakablePlatform(); break;
		}
	}
	
	public void SmallPlatform() {
		platform.set(randomX(), randomY(), Constants.PLAT_WIDTH, Constants.PLAT_HEIGHT);
	}
	
	public void MediumPlatform() {
		
	}
	
	public void BigPlatform() {
		
	}
	
	public void ResizablePlatform() {
		
	}
	
	public void BreakablePlatform() {
		
	}
	
	private float randomX () {
		System.out.println(cam.position.x-cam.viewportWidth/2+ "  "+ cam.position.x);
		return MathUtils.random(cam.position.x+cam.viewportWidth/2,cam.position.x+cam.viewportWidth*3/2-platform.width);
		
		
	}
	
	private float randomY(){
		return MathUtils.random(0, cam.viewportHeight-platform.height);
	}
	
	public void dispose () {
		texture.dispose();
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(texture,platform.x,platform.y,platform.width,platform.height);
	}
	
	public boolean OutOfBoundaries (Platform plat) {
		if (platform.x<cam.position.x-cam.viewportWidth/2-platform.width) {
			return true;
		}
		else return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		//dispose();
	}

}
