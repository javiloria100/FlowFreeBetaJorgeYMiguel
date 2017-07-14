package com.flow.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class JsonLeer{

    // ------ ATRIBUTOS -------

    private int dimensiones;
    private boolean error;

    //POR AHORA
    private int maxima = 10, minima = 4, minimoPuntos = 4;
    private String mayusculas = "ABC";
    private FileHandle f;
    private static String ruta = "mapa 2.json";

    // ------ CONSTRUCTOR -------

    public JsonLeer(FileHandle f){

        this.dimensiones = 0;
        this.error = false;

        ruta = f.name();
    this.f=f;
    }


    /*Objetivo: Leer el archivo y realizar todas las validaciones correspondientes
    Retorna: (true) El archivo es correcto
              (false) El archivo presenta errores*/
    public boolean JsonLeer(){

        //Atributo que se utilizará para leer el archivo .json
        JSONParser parser = new JSONParser();

        try {
			
			/*Se le pasa la dirección de memoria para ubicar el archivo. El archivo debe encontrarse en 
		     la carpeta maps dentro de la carpeta del proyecto*/
            f= Gdx.files.local("mapa 2.json");
			Object obj = parser.parse(new FileReader(ruta));
	            
            /*Guarda en un atributo "jsonObject" de tipo JSONObject un cast de un objeto "obj" de tipo Object a tipo JSONObject 
            con el fin de utilizar los metodos de la clase JSONObject, de sus padres o de las clases que importa*/
            JSONObject jsonObject = (JSONObject) obj;

            //Cast de Object a String
            String dimensionesString = (String) jsonObject.get("dimension");
            /*El metodo get perteneciente a la clase HashMap que importa la clase JSONObject que tiene como finalidad retornar
            el valor que acompaña la palabra que se busca; en nuestro caso la palabra es "dimension" y el valor que retorna es la 
            dimensión cuadrada del tablero*/


            //En Caso de que el archivo no tenga la palabra "dimension" se ejecuta el error
            if(dimensionesString == null){
                System.out.println("   ERROR. El archivo no posee la palabra dimension");
                this.error = true;
            }
            else{

                this.dimensiones = Integer.parseInt(dimensionesString);

                //Valida que las dimensiones esten dentro del rango permitido por el juego
                if( (this.dimensiones <= minima)||(this.dimensiones >= maxima) ){
                    System.out.println("   ERROR. Dimensiones fuera del rango establecido [" + (minima + 1) + " , " + (maxima - 1) + "]");
                    this.error = true;
                }
                else{

                    JSONArray tablero = (JSONArray) jsonObject.get("tablero");

                    //En Caso de que el archivo no tenga la palabra "tablero" se ejecuta el error
                    if(tablero == null)
                        System.out.println("   ERROR. El archivo no posee la palabra tablero");
                    else
                        this.error = validarTablero(tablero);

                }

            }

        }
		/*Este error se ejecuta si no existe el archivo o si existe el archivo y es de solo lectura.
		Además es importante destacar que esta excepción pertenece a IOException*/
        catch (FileNotFoundException ex1) {
            System.out.println("   ERROR. NO existe el archivo para esa ruta");
            this.error = true;

            //Este error se ejecuta si se presenta un error de entrada o salida de datos
        } catch (IOException ex2) {
            System.out.println("   ERROR. Se produjo un error desconocido al leer el archivo");
            this.error = true;

            //Se ejecuta en caso de que el achivo .json presente errores
        } catch (ParseException ex3) {
            System.out.println("   ERROR. Archivo existente, pero presenta errores");
            this.error = true;

            //Se ejecuta si el valor de dimensiones no es numerico
        }catch (NumberFormatException ex4){
            System.out.println("   ERROR. Las dimensiones SOLO puenden ser números");
            this.error = true;

        }


        //Se crea el Nivel
        if(error){
            System.out.println("   ERROR. NO se creo el Nivel... :( ");
            return false;
        }

        return true;


    }



    /*Objetivo: Verificar si el tablero cumple con las condicones dadas por el juego
    Parametros Recibidos: El tablero como un JSONArray
    Retorna: (false) Si es valido el tablero
               (true) Tablero NO valido*/
    public boolean validarTablero(JSONArray tablero){

        boolean error = false;

        if(tablero.size() < this.dimensiones)
            System.out.println("   ERROR. Hay MENOS Filas que las indicadas en las dimensiones");
        else
        if(tablero.size() > this.dimensiones)
            System.out.println("   ERROR. Hay MAS Filas que las indicadas en las dimensiones");

            //El número de dimensiones concuerda con el número de filas
        else{

            String stringTablero = tablero.toString();

            //Recortar la primera y última posición del tablero, las cuales son solo corchetes '[' y ']' respectivamente
            stringTablero = stringTablero.substring(1, stringTablero.length() - 1);

            //Validar el número de columnas
            error = validarColumnas(stringTablero);

            //Si validarColumnas no dio error, entonces se prosigue a validar los puntos
            if(!error)
                error = validarPuntos(stringTablero);
        }

        return error;


    }


    /*Objetivo: Verificar la cantidad de columnas en cada fila del tablero comparandolas con las dimensiones
    Parametros Recibidos: El tablero en forma de String
    Retorna: (false) Si el tablero no presenta errores
               (true) El tablero presenta fallas*/
    public boolean validarColumnas(String stringTablero){

        boolean error = false;
        int i = 0, contColumnas = 0, num, suma;

        while( (i < stringTablero.length())&&(!error) ){

            if(stringTablero.charAt(i) != '['){

                if(stringTablero.charAt(i) != ']'){

                    //Si entra en el condicional significa que debe ser un número
                    if(stringTablero.charAt(i) != ','){

                        //Bloque try catch para verificar que es un número
                        try{

                            num = Integer.parseInt(stringTablero.substring(i, i + 1));

                            //Se encarga de concatenar numero mayores a 9
                            while( (stringTablero.charAt(i + 1) != ',')&&(stringTablero.charAt(i + 1) != '[')&&(stringTablero.charAt(i + 1) != ']') ){
                                suma = Integer.parseInt(stringTablero.substring(i + 1, i + 2));

                                num = num*10 + suma;
                                i++;
                            }

                            contColumnas++;

                            //Si se ejecuta el catch es que el tablero posee caracteres
                        }catch(NumberFormatException ex){
                            System.out.println("   ERROR. El Tablero SOLO puede tener números POSITIVOS");
                            error = true;
                        }

                    }


                }
                //Caso de que se haya llegado al final de la fila
                else{

                    if(contColumnas < this.dimensiones){
                        System.out.println("   ERROR. Hay MENOS Columnas que las indicadas en las dimensiones");
                        error = true;
                    }
                    else
                    if(contColumnas > this.dimensiones){
                        System.out.println("   ERROR. Hay MAS Columnas que las indicadas en las dimensiones");
                        error = true;
                    }
                    //El número de dimensiones concuerda con el número de columnas en una fila
                    else
                        contColumnas = 0;

                }
            }

            i++;

        }//Fin del while

        return error;

    }



    /*Objetivo: Validar que cada punto del tablero tenga su pareja, es decir, solo pueden haber 2 puntos
                 del mismo color
    Parametros Recibidos: El tablero como String
                           El minimo de puntos que puede tener el tablero
    Retorna: (false) Si todos los puntos tienen pareja
               (true) Si aunque sea un punto no tiene pareja o hay mas de 2 puntos por un mismo color*/
    public boolean validarPuntos(String stringTablero){

        boolean error = false;
        int i = 0, num, cont, suma, cantDePuntos = 0;

        while( (i < stringTablero.length())&&(!error) ){

            //Si entra en el condicional significa que la posición actual es un punto
            if( (stringTablero.charAt(i) != '[')&&(stringTablero.charAt(i) != ']')&&(stringTablero.charAt(i) != '0')&&(stringTablero.charAt(i) != ',') ){

                //Tranforma el numero (Punto) de String a int
                num = Integer.parseInt(stringTablero.substring(i, i + 1));

                //Se encarga de concatenar numero mayores a 9
                while( (stringTablero.charAt(i + 1) != ',')&&(stringTablero.charAt(i + 1) != '[')&&(stringTablero.charAt(i + 1) != ']') ){
                    suma = Integer.parseInt(stringTablero.substring(i + 1, i + 2));

                    num = num*10 + suma;
                    i++;
                }

                try{
                    //Solo es para probar que el número del punto no supera el rango de representación de los puntos
                    char aux = mayusculas.charAt(num - 1);
                }catch(StringIndexOutOfBoundsException ex){
                    System.out.println("   ERROR. Los puntos deben estar en el rango [1," + (mayusculas.length()) + "]");
                    error = true;
                }

                if(!error){
                    cont = contarPuntos(stringTablero,num);

                    //Cuenta la cantidad de puntos de tablero
                    cantDePuntos++;

                    if(cont < 2){
                        System.out.println("   ERROR. El tablero presenta un solo punto: " + num);
                        error = true;
                    }
                    else
                    if(cont > 2){
                        System.out.println("   ERROR. El tablero presenta mas de dos puntos: " + num);
                        error = true;
                    }
                }

            }

            i++;

        }

        if(!error){

            //Divide a la mitad ya que se quiere la cantidad de pares de puntos, no todos los puntos
            cantDePuntos /= 2;

            //Se ejecuta el error si la cantidad de puntos se sale del rango permitido
            if( (cantDePuntos < minimoPuntos)||(cantDePuntos > dimensiones) ){
                if(cantDePuntos < minimoPuntos)
                    System.out.println("   ERROR. Para las dimensiones de este tablero NO pueden haber menos de " + minimoPuntos + " puntos");
                else
                    System.out.println("   ERROR. Para las dimensiones de este tablero NO pueden haber mas de " + dimensiones + " puntos");

                System.out.println("   Cantidad de puntos ingresadas: " + cantDePuntos);
                System.out.println("   Rango de cantidad de puntos: [" + minimoPuntos + "," + dimensiones + "]");
                error = true;
            }

        }

        return error;

    }



    /*Objetivo: Contar todos los puntos de un mismo color
    Parametros Recibidos: El tablero como String y el color de los puntos que se van a contar
    Retorna: El número de puntos con el color que se busca*/
    public int contarPuntos(String stringTablero, int num){

        int cont = 0, valorActual, suma;

        for(int i = 0; i < stringTablero.length(); i++){

            //Si entra en el condicional significa que la posición actual es un punto
            if( (stringTablero.charAt(i) != '[')&&(stringTablero.charAt(i) != ']')&&(stringTablero.charAt(i) != '0')&&(stringTablero.charAt(i) != ',') ){

                valorActual = Integer.parseInt(stringTablero.substring(i, i + 1));

                //Se encarga de concatenar numero mayores a 9
                while( (stringTablero.charAt(i + 1) != ',')&&(stringTablero.charAt(i + 1) != '[')&&(stringTablero.charAt(i + 1) != ']') ){
                    suma = Integer.parseInt(stringTablero.substring(i + 1, i + 2));

                    valorActual = valorActual*10 + suma;
                    i++;
                }

                if(valorActual == num)
                    cont++;

            }

        }

        return cont;
    }




    /*Objetivo: Transformar el tablero del Archivo .json en String
    Retorna: El tablero como String*/
    public String JSONtoStringTablero(){

        JSONParser parser = new JSONParser();

        Object obj = null;
        String stringTablero = null;

        try {
            obj = parser.parse(new FileReader(ruta));

        } catch (FileNotFoundException ex1) {
            System.out.println("   ERROR 1");
        } catch (IOException ex2) {
            System.out.println("   ERROR 2");
        } catch (ParseException ex3) {
            System.out.println("   ERROR 3");
        }

        if(obj != null){
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray tablero = (JSONArray) jsonObject.get("tablero");

            stringTablero = tablero.toString();
            stringTablero = stringTablero.substring(1, stringTablero.length() - 1);
        }
        else
            System.out.println("   ERROR 4");

        return stringTablero;

    }


    //Getter

    public int getDimensiones(){
        return dimensiones;
    }

}