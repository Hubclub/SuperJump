package com.hubclub.superjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;


public class World implements Screen {
	
	Pool<Platform> platformPool = new Pool<Platform>() {
		protected Platform newObject() {
			
			return new Platform(1, cam);
		}
	};
	
	private float deltaTime;
	private Character ninja;
	private SpriteBatch batch;
	private Platform plat;
	private OrthographicCamera cam;
	private Array<Platform> platformArray = new Array<Platform>();
	
	
	public World () {
		batch = new SpriteBatch();
		ninja = new Character();
		cam=new OrthographicCamera();
		cam.viewportWidth=Gdx.graphics.getWidth();
		cam.viewportHeight=Gdx.graphics.getHeight();
		cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
		
		for (int i=0; i<5; i++) {
			Platform plat = platformPool.obtain();
			platformPool.free(plat);
		}
		
		
		
		plat=new Platform(1,cam);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		createPlatform(delta);
		
		batch.setProjectionMatrix(cam.combined);
		ninja.render(delta);
		
		batch.begin();
		ninja.draw(batch);
		drawAndFreePlatform(batch);
		batch.end();
		
		cam.translate(.5F, 0);
		cam.update();
		
	}
	
	private void drawAndFreePlatform (SpriteBatch batch) {
		for (Platform plat : platformArray) {
			plat.draw(batch);
			if (plat.OutOfBoundaries(plat)) {
				//plat.dispose();
				platformPool.free(plat);
			}
		}
	}
	
	private void createPlatform (float delta) {
		deltaTime+=delta;
		if(deltaTime>=3){
			Platform platform = platformPool.obtain();
			platform.SmallPlatform();
			platformArray.add(platform);
			deltaTime=0;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
