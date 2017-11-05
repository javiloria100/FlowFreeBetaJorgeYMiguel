package com.flow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.flow.actores.Cuadrado;
import com.flow.actores.Punto;

/**
 * Created by jorge & Cholo on 08/07/17.
 * Clase que controla la captura de entradas y el manejo de eventos
 */

public class PuntoUserImput extends InputListener {

    private Texture texturaPunto;

    private Punto punto,punto2;
    private Cuadrado cuadrado;
    private ShapeRenderer renderer=new ShapeRenderer();
    private Color color;
    private float inicio_X,inicio_Y,distancia_x,distancia_y, constantelado;
    private final float AMPLIFICADOR= 50; //Usada para escalar o aumentar el tamaño
    private  final float SEPARACION=27; //usada para ver la distancia minima que se utiliza para activar union entre lados *pixeles*
    private int button;

    public PuntoUserImput(Punto punto,Cuadrado cuadrado){

        texturaPunto = new Texture("circulo3.png");
        this.punto=punto;
        this.cuadrado=cuadrado;
        punto2=new Punto(texturaPunto,punto.getPos_x(),punto.getPos_y());

    }



    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        //if (cuadrado.hit(x, y, true) != null) {

            distancia_x = x - inicio_X;      //distancias para ver si se mueve hacia arr,aba,der,izq
            distancia_y = y - inicio_Y;      // final - inicial


            //validacion para conectar hacia arriba
            System.out.println("x:    "+x+"    y:  "+y+"   distancia:  "+distancia_x+"   :  "+distancia_y);

            if (distancia_y >= 35) {
                constantelado = ((punto.getPos_x() + 1) * AMPLIFICADOR) + 25;     //lado que no se mueve al ser una recta
                //interaccion del actor para renderizar la pantalla

                if(y<295) {
                    punto.dibuja(constantelado, ((punto2.getPos_y() + 1) * AMPLIFICADOR + 18.7f), constantelado, (punto2.getPos_y() + 2) * AMPLIFICADOR + 28.6f);
                    this.inicio_Y = y;        //posiciones finales a iniciales para continuar
                    this.inicio_X = x;
                    punto2.setPos_y(punto2.getPos_y()+1);

                    System.out.println("UNIIIII "+punto2.getPos_y()+"  :  "+punto2.getPos_x());
                }
                    //*****Probar a ver si esto soluciona lo de unir varios puntos sin levantar dedo *************



            }

                constantelado = ((punto.getPos_x() + 1) * AMPLIFICADOR) + 25; //lado que no se mueve al ser una recta


                //validar hacia abajo

                if (distancia_y <= -27) {
                    if (y>76) {
                        punto.dibuja(constantelado, ((punto.getPos_y() + 1) * AMPLIFICADOR - 30), constantelado, (punto.getPos_y() + 2) * AMPLIFICADOR - 30);

                        this.inicio_Y = y;        //posiciones finales a iniciales para continuar
                        this.inicio_X = x;
                    }

                }
                    constantelado = (punto.getPos_y() + 1) * AMPLIFICADOR + 24; //lado que no se mueve al ser una recta
                    //validar hacia la derecha



                    if (distancia_x >= SEPARACION) {

                        if(x<290) {
                            punto.dibuja(((punto.getPos_x() + 1) * AMPLIFICADOR + 18.3f), constantelado, ((punto.getPos_x() + 1) * AMPLIFICADOR + 80), constantelado);

                            this.inicio_Y = y;        //posiciones finales a iniciales para continuar
                            this.inicio_X = x;

                        }

                    }


                        if (distancia_x <= -27) {
                            if (x > 48) {
                                punto.dibuja(((punto.getPos_x() + 1) * AMPLIFICADOR + 28), constantelado, ((punto.getPos_x() + 1) * AMPLIFICADOR - 31), constantelado);

                                this.inicio_Y = y;        //posiciones finales a iniciales para continuar
                                this.inicio_X = x;

                            }
                        }




        //}
    }


    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        this.inicio_X=x;
        this.inicio_Y=y;
        this.button=button;
        System.out.println("TOUCH:  "+inicio_X+"       : y      "+inicio_Y);
        return true ;




    }


}
