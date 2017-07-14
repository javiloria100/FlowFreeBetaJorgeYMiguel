package com.flow.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flow.MainGame;
import com.flow.PuntoUserImput;
import com.flow.actores.Cuadrado;
import com.flow.actores.Punto;
import com.flow.json.JsonLeer;

import java.util.HashMap;
import java.util.Map;

public class MainGameScreen extends BaseScreen {

    private int dimensiones_x = 8, dimensiones_y = 8;

    private int dimensiones;

    private OrthographicCamera camera;
    private ShapeRenderer renderer;
    private Stage stage;
    private Texture texturaPunto;
    private Texture texturaCasilla;
    private Punto punto;
    private Cuadrado cuadrado;
    private float alto;
    private Color color;


    //Gama de colores del Juego
    private Map<Integer, Color> colores;

    //Constructor
    public MainGameScreen(MainGame mainGame) {

        super(mainGame);

        texturaPunto = new Texture("circulo3.png");
        texturaCasilla = new Texture("cuadrado6.jpg");

        colores = new HashMap<Integer, Color>();

    }


    @Override
    //Create en la pantalla
    public void show() {

        camera = new OrthographicCamera(10, 7.51f);
        renderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());

        alto = Gdx.graphics.getHeight();

        asignarColores();
        crearTablero();


        System.out.print(Gdx.input.getInputProcessor());


        Gdx.input.setInputProcessor(stage);

        System.out.print(Gdx.input.getInputProcessor());
    }


    @Override
    //Elimina el stage y las texturas apenas se sale de la pantalla
    public void hide() {

        stage.dispose();
        texturaCasilla.dispose();
        texturaPunto.dispose();

    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Tell the camera to propagate any changes to it's matrices
        // Tell our ShapeRenderer to use the camera's view of the world

        stage.act();
        stage.draw();


        camera.update();
        renderer.setProjectionMatrix(camera.combined);           //CREA EL VECTOr de proyeccion

    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = 0.9f * width / height;
        stage.getViewport().update(width, height, true);
        // stage.getViewport().update(640,480);
        //  camera.setToOrtho(false, width, height);
        // camera.viewportHeight = 2 * 2000; //Probar con factor 1
        //camera.viewportWidth = aspectRatio * camera.viewportHeight;
        //Gdx.app.log("resize", "viewportHeight=" + camera.viewportHeight + ",camera.viewportWidth=" + camera.viewportWidth);
        //Gdx.app.log("resize", "center viewportWidth=" + (0.5f * camera.viewportWidth));
    }

    @Override
    public void dispose() {

        texturaCasilla.dispose();
        texturaPunto.dispose();
        stage.dispose();

    }


    //Asigna los colores
    public void asignarColores(){

        colores.put(0,Color.BLACK);
        colores.put(1,Color.GREEN);
        colores.put(2,Color.ORANGE);
        colores.put(3,Color.RED);
        colores.put(4,Color.BLUE);
        colores.put(5,Color.YELLOW);
        colores.put(6,Color.PINK);
        colores.put(7,Color.GRAY);
        colores.put(8,Color.GOLD);
        colores.put(9,Color.CYAN);

    }


    /* CREA EL TABLERO */
    public void crearTablero() {

        FileHandle f = Gdx.files.internal("mapa 2.json");
        JsonLeer jsonLeer = new JsonLeer(f);
        String stringTablero = jsonLeer.JSONtoStringTablero();
        System.out.print(stringTablero);

        stringTablero = stringTablero.replace("[","");
        stringTablero = stringTablero.replace("]","");
        stringTablero = stringTablero.replace(",","");

        System.out.print(stringTablero);

        dimensiones_x = (int)( Math.sqrt(stringTablero.length()) );
        dimensiones_y = dimensiones_x;

        int x, y;
        this.dimensiones = dimensiones_x;
        int valor;

        System.out.println("DIMENSIONES " + stringTablero.length());

        for(int i = 0; i < stringTablero.length(); i++){

            x = i % this.dimensiones;
            y = i / this.dimensiones;

            punto = new Punto(texturaPunto, x, y);
            cuadrado = new Cuadrado(texturaCasilla, x, y);

            valor = stringTablero.charAt(i);
            valor-= 48;

            System.out.println(" VALOR: " + valor);

            if(colores.get(valor) != null)
                punto.setColor(colores.get(valor));

            //Agrega al escenario tanto al punto como al cuadrado
            stage.addActor(cuadrado);
            stage.addActor(punto);
            punto.addListener(new PuntoUserImput(punto,cuadrado));

        }

        /*
        for (int j = 0; j < dimensiones_y; j++) {

            for (int i = 0; i < dimensiones_x; i++) {

                //Crea los Puntos y Cuadrados
                punto = new Punto(texturaPunto, i, j);
                cuadrado = new Cuadrado(texturaCasilla, i, j);
                punto.setColor(colores.get(stringTablero.charAt(i)));
                if(i == 0)
                    punto.setColor(color.RED);

                //Agrega al escenario tanto al punto como al cuadrado
                stage.addActor(cuadrado);
                stage.addActor(punto);
                punto.addListener(new PuntoUserImput(punto,cuadrado));

            }

        }*/

    }



}