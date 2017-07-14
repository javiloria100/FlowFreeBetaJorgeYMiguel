package com.flow.pantallas;

import com.badlogic.gdx.Screen;
import com.flow.MainGame;

public abstract class BaseScreen implements Screen {

    protected MainGame mainGame;

    public BaseScreen(MainGame mainGame){
        this.mainGame = mainGame;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }
}