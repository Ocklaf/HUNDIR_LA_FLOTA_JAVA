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
        int numLanchas = 10; //empiezo por 10 para modo fácil
        char lancha = 'L', charCoordenadaX = 'z';
        boolean okCoordenadaX = false;
        int coordenadaX = 10, coordenadaY = 10; //inicializo a 10 por el while, sacandolo de rango y entrando en él
        
        System.out.println("Bienvenido a HUNDIR LA FLOTA!!");
                
        llenadoInicialTableros(jugadorPC);
        llenadoInicialTableros(jugadorHumano);
        
        colocarBarcos(jugadorPC, numLanchas, lancha);
        
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
    
    //Colocación de los barcos
    public static void colocarBarcos(char jugador[][], int cantidad, char tipo){
        int x, y;
        
        while(cantidad > 0){
            x = (int)(Math.random()*10); //de 0 a 9 porque como sólo coje 9.99999 el 9 sería la parte entera
            y = (int)(Math.random()*10);
            if(jugador[x][y] == '-'){
                jugador[x][y] = tipo;
                cantidad--;
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
