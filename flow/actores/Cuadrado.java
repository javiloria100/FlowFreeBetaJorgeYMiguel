package com.flow.actores;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Cuadrado extends Casilla {

    private int pos_x, pos_y;
    private Texture texturaCasilla;
    private ShapeRenderer renderer =new ShapeRenderer();
    private float pos_inicialX,pos_inicialY,pos_finalX,pos_finalY,height,width;
    private boolean der=false,izq=false,arr=false,aba=false;
    Color color= new Color();
    public Cuadrado(Texture texturaCasilla,int pos_x, int pos_y){
        //Textura de la Casilla (Forma)
        this.texturaCasilla = texturaCasilla;
        this.setDebug(false);

        width=55;
        height=55;
        //Posiciones
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        setOrigin(pos_x,pos_y);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color.BLUE);
        renderer.rectLine(pos_inicialX,pos_inicialY,pos_finalX,pos_finalY, 12);
        renderer.end();
    }
    @Override
    public void act(float delta) {
        // Gdx.input.setInputProcessor(p);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color= getColor();
        batch.setColor(color.r,color.g,color.b,color.a*parentAlpha); //clave porque es la que termina de ponberle la tinta al batch para pintarla
        batch.draw(texturaCasilla, (pos_x +1)*50, (pos_y +1)*50, 50, 50);

    }


    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() != Touchable.enabled) return null;
        else {
            if((( x >= (pos_x+1)*50 -22) && x <( (pos_x+1)*50) + width) && ((y >=(pos_y+1)*50 -22) && y < ((pos_y+1)*50+height) )) {
                return this;
            }
            else return null;
        }
    }



}

