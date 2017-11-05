package com.flow;

import com.badlogic.gdx.Game;
import com.flow.pantallas.MainGameScreen;


public class MainGame extends Game{

	@Override
	public void create() {
		setScreen(new MainGameScreen(this));
	}

}

