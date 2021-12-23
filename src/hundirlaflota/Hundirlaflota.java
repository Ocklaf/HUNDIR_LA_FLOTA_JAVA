package hundirlaflota;

/**
 *
 * @author José Vicente Falcó
 * @version 1.0
 */

import java.util.Scanner;
import java.util.Arrays;

public class Hundirlaflota {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        char jugadorPC [][] = new char[10][10];
        char jugadorHumano [][] = new char[10][10];
        char coordenadasValidasX[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        int disparos = 10;//empiezo por 10 para probar...pero DEBEN SER 50!!
        int cantidad; //empiezo por 10 para modo fácil
        char lancha = 'L', buque = 'B', acorazado = 'Z', portaaviones = 'P',charCoordenadaX = 'z';
        boolean okCoordenadaX = false;
        int coordenadaX = 10, coordenadaY = 10; //inicializo a 10 por el while, sacandolo de rango y entrando en él
        
        System.out.println("Bienvenido a HUNDIR LA FLOTA!!");
                
        llenadoInicialTableros(jugadorPC);
        llenadoInicialTableros(jugadorHumano);
        
        cantidad = 5;
        colocarBarcos(jugadorPC, cantidad, lancha);
        cantidad = 3;
        colocarBarcos(jugadorPC, cantidad, buque); //3 casillas HORIZONTALES
        cantidad = 1;
        colocarBarcos(jugadorPC, cantidad, acorazado);
        cantidad = 1;
        colocarBarcos(jugadorPC, cantidad, portaaviones);
        
        verTablero(jugadorPC);
        verTablero(jugadorHumano);
        
        //Mientras me queden disparos...
        while(disparos > 0){
            while(okCoordenadaX == false){ //Mientras no me de una coordenada correcta del eje X
                System.out.println("Dame la coordenada de disparo X (A - J)");
                charCoordenadaX = input.nextLine().toLowerCase().charAt(0);
                okCoordenadaX = compruebaCoordenadaX(charCoordenadaX, coordenadasValidasX);
            }
            while(coordenadaY < 0 || coordenadaY > 9){
                System.out.println("Dame la coordenada de disparo en eje Y (0 - 9)");
                coordenadaY = input.nextInt();
                input.nextLine();
            }
            coordenadaX = traduceCoordenadaX(charCoordenadaX, coordenadasValidasX);
//            System.out.println(coordenadaX + " " + coordenadaY);
            disparos = disparoMisil(jugadorPC, jugadorHumano, coordenadaX, coordenadaY, disparos);  // guardamos si hay return la actualización del fallo en repetir coordenada  
            disparos--;
//            System.out.println(disparos);
            coordenadaX = coordenadaY = 10;
            okCoordenadaX = false;
            charCoordenadaX = 'z';
        }
    }
    
    //******* FUNCIONES ********    
    
    //Lleno de - ambas matrices
    public static void llenadoInicialTableros(char jugador[][]){
        for(int i=0; i<jugador.length; i++)
            Arrays.fill(jugador[i], '-');
    }
    
    //Visionado actualizado del tablero
    public static void verTablero(char jugador[][]){
        //sólo para ver lo que ha llenado y me sirve ya como para visualizar
        System.out.println("  A B C D E F G H I J");
        for(int i=0; i<jugador.length;i++){
            System.out.print(i + " ");
            for(int j=0; j<jugador[i].length; j++){
                System.out.print(jugador[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    //Genera número aleatorio de 0 a 9
    public static int numAleaotorio(){
        return (int)(Math.random()*10); //de 0 a 9 porque como sólo coje 9.99999 el 9 sería la parte entera
    }
    
    //Colocación de los barcos
    public static void colocarBarcos(char jugadorPC[][], int cantidad, char tipo){
        int fil, col;
        boolean entraElBarco;
        
        if(tipo == 'L'){
            while(cantidad > 0){
            fil = numAleaotorio();
            col = numAleaotorio();
            if(jugadorPC[fil][col] == '-'){
                jugadorPC[fil][col] = tipo;
                cantidad--;
                }
            }
        }
        else if(tipo == 'B'){
            while(cantidad > 0){ 
                entraElBarco = true;
            while(entraElBarco){
            do{    
                fil = numAleaotorio();
                col = numAleaotorio();
            }while(col + 2 > 9);//Evitar que si sumamos 2 posiciones nos salbamos de la fila en número de columnas
            for(int i = col; i<=col+2; i++){ //mirar de cambiar por un binarySearch?? pensar en cómo y lo que devuelve...
                if(jugadorPC[fil][i] != '-'){
                    entraElBarco = false;
                    }
            }
            
            if(!entraElBarco){
                entraElBarco = true;
            }
            else{          
                for(int i = col; i<=col+2; i++){
                    jugadorPC[fil][i] = tipo;
                }
                cantidad--;
                entraElBarco = false;
            }
            }     
            }
        }
        
        else if(tipo == 'Z'){
            while(cantidad > 0){ 
                entraElBarco = true;
            while(entraElBarco){
            do{    
                fil = numAleaotorio();
                col = numAleaotorio();
            }while(col + 3 > 9);//Evitar que si sumamos 3 posiciones (inical + 3 = 4 dimensión Acorazado) nos salbamos de la fila en número de columnas
            for(int i = col; i<=col+3; i++){ //mirar de cambiar por un binarySearch?? pensar en cómo y lo que devuelve...
                if(jugadorPC[fil][i] != '-'){
                    entraElBarco = false;
                    }
            }
            
            if(!entraElBarco){
                entraElBarco = true;
            }
            else{          
                for(int i = col; i<=col+3; i++){
                    jugadorPC[fil][i] = tipo;
                }
                cantidad--;
                entraElBarco = false;
            }
            }     
            }
        }
        
        else if(tipo == 'P'){
            while(cantidad > 0){ 
                entraElBarco = true;
            while(entraElBarco){
            do{    
                fil = numAleaotorio();
                col = numAleaotorio();
            }while(fil + 4 > 9);//Evitar que si sumamos 3 posiciones (inical + 3 = 4 dimensión Acorazado) nos salbamos de la fila en número de columnas
            for(int i = fil; i<=fil+3; i++){ //mirar de cambiar por un binarySearch?? pensar en cómo y lo que devuelve...
                if(jugadorPC[i][col] != '-'){
                    entraElBarco = false;
                    }
            }
            
            if(!entraElBarco){
                entraElBarco = true;
            }
            else{          
                for(int i = fil; i<=fil+4; i++){
                    jugadorPC[i][col] = tipo;
                }
                cantidad--;
                entraElBarco = false;
            }
            }     
            }
        }
    }
    
    
    //Verificamos que lo recogido como letra por teclado está dentro del vector char de coordenadasValidasX
    //No hago un .sort del vector puesto que el vector char está en orden ASCII
    public static boolean compruebaCoordenadaX(char coordenadaX, char coordenadasValidasX[]){
        if(Arrays.binarySearch(coordenadasValidasX, coordenadaX)>=0)
            return true;
        else return false;
    }
    
    //Convierto el carácter recogido por teclado a numérico en función de la posición del vector
    //De este modo obtengo la equivalencia del carácter en coordenada numérica para la matriz
    public static int traduceCoordenadaX(char coordenadaX, char coordandasValidasX[]){
        return Arrays.binarySearch(coordandasValidasX, coordenadaX);
    }
    
    //Realizamos verificación de qué hay en las coordenadas dadas y actuamos
    //las coordenadas las tenemos que poner invertidas en la matriz ya que
    //al jugador le pedimos X (A-J) siendo este eje en la matriz las columas (segundos corchetes)
    //y la Y (0 - 9) siendo estas la FILAS, por ello invertimos el orden.
    public static int disparoMisil(char jugadorPC[][], char jugadorHumano[][], int x, int y, int disparos){
        if(jugadorHumano[y][x] != 'A' && jugadorHumano[y][x] != 'X'){
            if(jugadorPC[y][x] == '-'){
                System.out.println("Mi Capitán, hemos fallado... ha caído al AGUA!");
                jugadorHumano[y][x] = 'A';
                verTablero(jugadorHumano);
            }
            else{
                System.out.println("Mi Capitán, hemos dado al enemigo... TOCADO!");
                jugadorHumano[y][x] = 'X';
                verTablero(jugadorHumano);
            }
        }
        else{ 
            System.out.println("Mi Capitán, esa posición ya ha sido atacada!! Dame otras coordenadas!");
            verTablero(jugadorHumano);
            return disparos+1;
        }
        return disparos;
    }    
}