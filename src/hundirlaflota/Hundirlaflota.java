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
        int disparos = 20;//empiezo por 10 para probar...pero DEBEN SER 50!!
        int numLanchas = 10; //empiezo por 10 para modo fácil
        char lancha = 'L', charCoordenadaX;
        boolean okCoordenadaX = false;
        int coordenadaX = 10, coordenadaY = 10; //inicializo a 10 por el while, sacandolo de rango y entrando
        
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
                okCoordenadaX = compruebaCoordenadaX(charCoordenadaX);
            }
            while(coordenadaY < 0 || coordenadaY > 9){
                System.out.println("Dame la coordenada de disparo en eje Y (0 - 9)");
                coordenadaY = input.nextInt();
            }
            //disparoMisil(jugadorPC);    
            disparos--;
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
    
    public static boolean compruebaCoordenadaX(char coordenadaX){
        char coordenadasValidasX[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        if(Arrays.binarySearch(coordenadasValidasX, coordenadaX)>=0)
            return true;
        else return false;
    }
}
