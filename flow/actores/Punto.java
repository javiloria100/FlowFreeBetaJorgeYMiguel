package com.flow.actores;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/** ACTOR*/

public class Punto extends Actor {

    private float finalX, finalY,inicioX,inicioY;

    //Posiciones de la casilla
    private float pos_x, pos_y;
    private Color color = Color.BLUE;
    private Texture texturaPunto;
    private ShapeRenderer renderer = new ShapeRenderer();
    private boolean linea=false; //despues
    private float width=42;
    private float height=42;

    public Punto(Texture texturaCasilla, float pos_x, float pos_y) {

        this.texturaPunto = texturaCasilla;
        this.pos_x = pos_x;
        this.pos_y = pos_y;

    }


    /**METODO QUE SE LLAMA SIEMPRE ES EL QUE CAPTURA LOS EVENTOS Y DECIDE SI LLAMA A TOUCH ... */
    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() != Touchable.enabled) return null;
        else {
            if((( x >= (pos_x+1)*50 -5) && x <(( (pos_x+1)*50) + width)) && ((y >=(pos_y+1)*50  -5) && y < ((pos_y+1)*50+height) )) {

                return this;
            }
            else return null;
        }
    }


    @Override
    public void act(float delta) {
        super.act(delta);   //sin este super  no se llamarian las acciones
    }

    @Override       //ESTE METODO SE MANDA ACTIVAR EN EL DRAW
    public void drawDebug(ShapeRenderer shapes) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color.BLUE);
        renderer.rectLine(inicioX, inicioY,finalX,finalY, 12);
        renderer.end();
    }

    /** ASIGNA LOS VALORES PARA DIBUJAR LAS LINEAS*/
    public void dibuja(float inicioX, float inicioY, float finalX, float finaly){
        this.inicioX=inicioX;
        this.inicioY=inicioY;
        this.finalX=finalX;
        this.finalY=finaly;

        //PARA ACTIVAR EL DRAWDEBUG
        setDebug(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color= getColor();
        batch.setColor(color.r,color.g,color.b,color.a*parentAlpha); //clave porque es la que termina de ponberle la tinta al batch para pintarla
        batch.draw(texturaPunto, ((pos_x + 1) * 50) + 10, ((pos_y + 1) * 50) + 10, 30, 30);
    }


    public float getPos_x() {
        return pos_x;
    }

    public float getPos_y() {
        return pos_y;
    }

    public void setPos_x(float pos_x) {
        this.pos_x = pos_x;
    }

    public void setPos_y(float pos_y) {
        this.pos_y = pos_y;
    }
}
