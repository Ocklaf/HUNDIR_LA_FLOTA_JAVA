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
        int datosJuego [] = new int[2]; //Posición 0 los disparos, posición 1 los tocados        
        int dimensionesLancha = 1, dimensionesBuque = 3, dimensionesAcorazado = 4, dimensionesPortaaviones = 5, cantidad, dificultadElegida, totalPosicionesTablero = 100; //empiezo por 10 para modo fácil
        char lancha = 'L', buque = 'B', acorazado = 'Z', portaaviones = 'P', charCoordenadaX = 'z';
        boolean okCoordenadaX = false;
        int coordenadaX = 10, coordenadaY = 10; //inicializo a 10 por el while, sacandolo de rango y entrando en él
        
        System.out.println("Bienvenido a HUNDIR LA FLOTA!!\n"
                + "\nINTRUCCIONES DEL JUEGO:\n"
                + "- Serás el Capitán de esta fragata de guerra y ordenarás los ataques.\n"
                + "- Cada disparo consumirá 1 misil.\n"
                + "- Si la posición ha sido previamente atacada, serás avisado y tu misil no será lanzado.\n"
                + "- Si destruyes todos los barcos enemigos, ganarás.\n"
                + "- Si gastas tus misiles sin destruir todos los barcos, habrás perdido.\n"
                + "- Marcaremos en tu tablero los disparos al agua con A y los impactos a un barco enemigo con X.\n"
                + "- Primero te pediremos la coordenada de disparo en el eje X que irá de A a J.\n"
                + "- Segundo te pediremos la coordenada de disparo en el eje Y que irá de 0 a 9.\n"
                + "- Elige el modo de dificultad y empieza el ataque!!");
                
        llenadoInicialTableros(jugadorPC);
        llenadoInicialTableros(jugadorHumano);
        
        do{
            System.out.println("\nDime en que dificultad quieres jugar:\n"
                + "1. Fácil: tu enemigo tiene 5 Lanchas, 3 Buques, 1 Acorazado y 1 Portaaviones. Dispones de 50 misiles\n"
                + "2. Medio: tu enemigo tiene 2 Lanchas, 1 Buque, 1 Acorazado y 1 Portaaviones. Dispones de 30 misiles\n"
                + "3. Difícil: tu enemigo tiene 1 Lancha y 1 Buque. Dispones de 10 misiles.\n"
                + "4. Personalizada: elige las cantidades y tipos de barco, así como tu nº de misiles.");
            dificultadElegida = input.nextInt();
        } while(dificultadElegida < 1 || dificultadElegida > 4);
        
        input.nextLine();
        
        switch(dificultadElegida){
            case 1:
                cantidad = 5;
                colocarBarcos(jugadorPC, cantidad, lancha, dimensionesLancha); //1 casilla
                cantidad = 3;
                colocarBarcos(jugadorPC, cantidad, buque, dimensionesBuque); //3 casillas HORIZONTALES
                cantidad = 1;
                colocarBarcos(jugadorPC, cantidad, acorazado, dimensionesAcorazado); //4 casillas HORIZONTALES
                cantidad = 1;
                colocarBarcos(jugadorPC, cantidad, portaaviones, dimensionesPortaaviones); //5 casillas VERTICALES
                datosJuego[0] = 50;//Misiles
                datosJuego[1] = 23;//23 TOCADO para saber que hemos hundido todos
                break;
            case 2:
                cantidad = 2;
                colocarBarcos(jugadorPC, cantidad, lancha, dimensionesLancha); //1 casilla
                cantidad = 1;
                colocarBarcos(jugadorPC, cantidad, buque, dimensionesBuque); //3 casillas HORIZONTALES
                cantidad = 1;
                colocarBarcos(jugadorPC, cantidad, acorazado, dimensionesAcorazado); //4 casillas HORIZONTALES
                cantidad = 1;
                colocarBarcos(jugadorPC, cantidad, portaaviones, dimensionesPortaaviones); //5 casillas VERTICALES
                datosJuego[0] = 30;//Misiles
                datosJuego[1] = 13;//13 TOCADO para saber que hemos hundido todos
                break;
            case 3:
                cantidad = 1;
                colocarBarcos(jugadorPC, cantidad, lancha, dimensionesLancha); //1 casilla
                cantidad = 1;
                colocarBarcos(jugadorPC, cantidad, buque, dimensionesBuque); //3 casillas HORIZONTALES
                datosJuego[0] = 10;//Misiles
                datosJuego[1] = 4;//13 TOCADO para saber que hemos hundido todos
                break;
            case 4:
                datosJuego[0] = 0;//Inicializo posición Misiles
                datosJuego[1] = 0;//Inicializo posición Tocado
                System.out.println("Mínimo de misiles elegible 1 y máximo 100. Mínimo 1 Lancha máximo tablero lleno (100 espacios)."); 
                
                System.out.println("Dime número de Portaaviones del enemigo (ocupan 5 espacios):");
                cantidad = input.nextInt();
                totalPosicionesTablero -= cantidad * dimensionesPortaaviones;
                datosJuego[1] += cantidad * dimensionesPortaaviones;
                System.out.println("Quedan: " + totalPosicionesTablero + " espacios en el tablero");
                colocarBarcos(jugadorPC, cantidad, portaaviones, dimensionesPortaaviones); //5 casillas VERTICALES
                
                System.out.println("Dime número de Acorazados del enemigo (ocupan 4 espacios):");
                cantidad = input.nextInt();
                datosJuego[1] = totalPosicionesTablero -= cantidad * dimensionesAcorazado;
                datosJuego[1] += cantidad * dimensionesAcorazado;
                System.out.println("Quedan: " + datosJuego[1] + " espacios en el tablero");
                colocarBarcos(jugadorPC, cantidad, acorazado, dimensionesAcorazado); //4 casillas HORIZONTALES
                
                System.out.println("Dime número de Buques del enemigo (ocupan 3 espacios):");
                cantidad = input.nextInt();
                datosJuego[1] = totalPosicionesTablero -= cantidad * dimensionesBuque;
                datosJuego[1] += cantidad * dimensionesBuque;
                System.out.println("Quedan: " + totalPosicionesTablero + " espacios en el tablero");
                colocarBarcos(jugadorPC, cantidad, buque, dimensionesBuque); //3 casillas HORIZONTALES
                
                System.out.println("Dime número de Lanchas del enemigo (ocupa 1 espacio):");
                cantidad = input.nextInt();
                totalPosicionesTablero -= cantidad * dimensionesLancha;
                datosJuego[1] += cantidad * dimensionesLancha;
                System.out.println("Quedan: " + totalPosicionesTablero + " espacios en el tablero");
                colocarBarcos(jugadorPC, cantidad, lancha, dimensionesLancha); //1 casilla               
                
                System.out.println("Nº de misiles, mínimo: " + datosJuego[1]);
                datosJuego[0] = input.nextInt();
                break;
        }
        
        verTablero(jugadorPC); //Provisional, para eliminar, es para pruebas
        verTablero(jugadorHumano);
        
        input.nextLine(); //Limpio el buffer
        
        //Mientras me queden disparos... disparos > 0 && tocados > 0
        while(datosJuego[0] > 0 && datosJuego[1] > 0){ //Posición 0 tenemos los disparos, posición 1 los impactos
            System.out.println("Tenemos: " + datosJuego[0] + " misiles y deberíamos dar en el blanco " + datosJuego[1] + " veces para ganar");
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
            disparoMisil(jugadorPC, jugadorHumano, coordenadaX, coordenadaY, datosJuego);  // guardamos si hay return la actualización del fallo en repetir coordenada  
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
    
    //Visionado del tablero
    public static void verTablero(char jugador[][]){
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
    
    //Colocación de los barcos, Lanchas directos en función, Horizontales en otra función y el vertical en otra.
    public static void colocarBarcos(char jugadorPC[][], int cantidad, char tipo, int dimensionBarco){
        int fil, col;
        boolean entraElBarco;
        
        if(tipo == 'L'){
            while(cantidad > 0){
            if(jugadorPC[fil = numAleaotorio()][col = numAleaotorio()] == '-'){
                jugadorPC[fil][col] = tipo;
                cantidad--;
                }
            }
        }
        else if(tipo != 'P'){
            colocaHorizontales(jugadorPC, cantidad, tipo, dimensionBarco);
        }
        
        else if(tipo == 'P'){
            colocaVertical(jugadorPC, cantidad, tipo, dimensionBarco);
        }
    }
    
    public static void colocaHorizontales(char jugadorPC[][], int cantidad, char tipo, int dimensionBarco){
        int fil, col;
        boolean entraElBarco;
        
        while(cantidad > 0){ 
                entraElBarco = true;
            while(entraElBarco){
            do{    
                fil = numAleaotorio();
                col = numAleaotorio();
            }while(col + dimensionBarco > 9);//Evitar que si sumamos las dimensiones del barco nos salgamos de la fila en número de columnas
            for(int i = col; i<col + dimensionBarco; i++){ //mirar de cambiar por un binarySearch?? pensar en cómo y lo que devuelve...
                if(jugadorPC[fil][i] != '-'){
                    entraElBarco = false;
                    }
            }
            
            if(!entraElBarco){
                entraElBarco = true;
            }
            else if(entraElBarco){          
                for(int i = col; i<col + dimensionBarco; i++){
                    jugadorPC[fil][i] = tipo;
                }
                cantidad--;
                entraElBarco = false;
            }
            }     
            }
    }
    
    public static void colocaVertical(char jugadorPC[][], int cantidad, char tipo, int dimensionBarco){
        int fil, col;
        boolean entraElBarco;
        
        while(cantidad > 0){ 
                entraElBarco = true;
            while(entraElBarco){
            do{    
                fil = numAleaotorio();
                col = numAleaotorio();
            }while(fil + dimensionBarco > 9);//Evitar que si sumamos 4 posiciones (inical + 4 = 5 dimensión Portaaviones) nos salbamos de la fila en número de columnas
            for(int i = fil; i<fil + dimensionBarco; i++){ //mirar de cambiar por un binarySearch?? pensar en cómo y lo que devuelve...
                if(jugadorPC[i][col] != '-'){
                    entraElBarco = false;
                    }
            }
            
            if(!entraElBarco){
                entraElBarco = true;
            }
            else if(entraElBarco){          
                for(int i = fil; i<fil + dimensionBarco; i++){
                    jugadorPC[i][col] = tipo;
                }
                cantidad--;
                entraElBarco = false;
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
    public static void disparoMisil(char jugadorPC[][], char jugadorHumano[][], int x, int y, int datosJuego[]){
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
                datosJuego[1] -= 1; //Quitamos un impacto (un barco alcanzado, cuando todos hayan sido impactados, VICTORIA!
                if(datosJuego[1] == 0)
                            System.out.println("Mi capitán... HEMOS GANADO!!... hemos HUNDIDO LA FLOTA enemiga!!!");
            }
        }
        else{ 
            System.out.println("Mi Capitán, esa posición ya ha sido atacada!! Dame otras coordenadas!");
            verTablero(jugadorHumano);
            datosJuego[0] += 1; //Añadimos el misil lanzado erróneamente a una posición ya disparada
        }
        datosJuego[0] -= 1; //Quitamos un misil
        if(datosJuego[0] == 0)
            System.out.println("Mi capitán... HEMOS PERDIDO!!... no tenemos más misiles");
    }    
}