package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.w3c.dom.Text;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {

    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sound dropSound;
    Music music;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Sprite bucketSprite;

    Vector2 touchPos;

    Array<Sprite> dropSprites;

    float dropTimer;

    @Override
    public void create() {
        // Prepare your application here.
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8,5);

        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1,1);

        touchPos = new Vector2();

        dropSprites = new Array<>();


    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.

        viewport.update(width,height,true);
    }

    @Override
    public void render() {
        // Draw your application here.
        input();
        logic();
        draw();
    }

    private  void input(){
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY());
            viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            bucketSprite.translateX(speed * delta);
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bucketSprite.translateX(-speed * delta);
        }

    }
    private void logic(){
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(),0,worldWidth-bucketWidth));

        float delta = Gdx.graphics.getDeltaTime();

        for(Sprite dropSprite: dropSprites){
            dropSprite.translateY(-2f * delta);
        }


        //createDroplet();
        dropTimer += delta;
        if(dropTimer > 1f){
            dropTimer =0;
            createDroplet();
        }
    }
    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

    float worldWidth = viewport.getWorldWidth();
    float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture,0,0,worldWidth,worldHeight);
        // spriteBatch.draw(bucketTexture, 0,0,1,1);

 bucketSprite.draw(spriteBatch);

        for(Sprite dropSprite: dropSprites){
            dropSprite.draw(spriteBatch);
        }



        spriteBatch.end();
    }

    private  void createDroplet(){
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        //dropSprite.setX(0);
        dropSprite.setX(MathUtils.random(0f, worldWidth- dropWidth));
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);

    }
    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }
}
