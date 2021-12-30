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
        char coordenadasValidasX[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};//Vector con las coordenadas válidas en X
        int datosJuego [] = new int[2]; //Posición 0 los misiles, posición 1 los tocados        
        int coordenadaX, coordenadaY, cantidad, dificultadElegida; 
        char charCoordenadaX = 'z';
        boolean okCoordenadaX = false;
             
        //Instrucciones de uso del videojuego.
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
        
        //Llenado con el carácter '-' ambas matrices.
        llenadoInicialTableros(jugadorPC);
        llenadoInicialTableros(jugadorHumano);
        
        do{
            System.out.println("\nDime en que dificultad quieres jugar:\n"
                + "1. Fácil: tu enemigo tiene 5 Lanchas, 3 Buques, 1 Acorazado y 1 Portaaviones. Dispones de 50 misiles\n"
                + "2. Medio: tu enemigo tiene 2 Lanchas, 1 Buque, 1 Acorazado y 1 Portaaviones. Dispones de 30 misiles\n"
                + "3. Difícil: tu enemigo tiene 1 Lancha y 1 Buque. Dispones de 10 misiles.\n"
                + "4. Personalizada: elige las cantidades de cada tipo de barco, así como el nº de misiles.");
            dificultadElegida = input.nextInt();
        }while(dificultadElegida < 1 || dificultadElegida > 4);
        
        switch(dificultadElegida){
            case 1:
                colocaHorizontal(jugadorPC, 5, 'L', 1); //1 casilla Lancha (Horizontal o Vertical es indiferente, lo mando a Horizontal)
                colocaHorizontal(jugadorPC, 3, 'B', 3); //3 casillas HORIZONTALES Buque
                colocaHorizontal(jugadorPC, 1, 'Z', 4); //4 casillas HORIZONTALES Acorazado
                colocaVertical(jugadorPC, 1, 'P', 5); //5 casillas VERTICALES Portaaviones
                datosJuego[0] = 50;//Misiles para esta dificultad
                datosJuego[1] = 23;//23 TOCADO para saber que hemos hundido todos (Casillas de cada Barco x Cantidad)
                break;
            case 2:
                colocaHorizontal(jugadorPC, 2, 'L', 1); 
                colocaHorizontal(jugadorPC, 1, 'B', 3); 
                colocaHorizontal(jugadorPC, 1, 'Z', 4); 
                colocaVertical(jugadorPC, 1, 'P', 5); 
                datosJuego[0] = 30;
                datosJuego[1] = 13;
                break;
            case 3:
                colocaHorizontal(jugadorPC, 1, 'L', 1);
                colocaHorizontal(jugadorPC, 1, 'B', 3);
                datosJuego[0] = 10;
                datosJuego[1] = 4;
                break;
            case 4:                
                do{
                    System.out.println("Dime número de Portaaviones del enemigo (ocupan 5 espacios) min 0, máx 3:");
                    cantidad = input.nextInt();
                }while(cantidad < 0 || cantidad > 3);
                datosJuego[1] += cantidad * 5;//Calculo y almaceno el nº de casillas ocupadas y por lo tanto el mínimo de misiles para ganar
                colocaVertical(jugadorPC, cantidad, 'P', 5);
                
                do{
                    System.out.println("Dime número de Acorazados del enemigo (ocupan 4 espacios) min 0, máx 4:");
                    cantidad = input.nextInt();
                }while(cantidad < 0 || cantidad > 4);
                datosJuego[1] += cantidad * 4;              
                colocaHorizontal(jugadorPC, cantidad, 'Z', 4);
                
                do{
                    System.out.println("Dime número de Buques del enemigo (ocupan 3 espacios) min 0, máx 5:");
                    cantidad = input.nextInt();
                }while(cantidad < 0 || cantidad > 5);
                datosJuego[1] += cantidad * 3;
                colocaHorizontal(jugadorPC, cantidad, 'B', 3);
                
                do{
                    System.out.println("Dime número de Lanchas del enemigo (ocupa 1 espacio) min 0, máx 10:");
                    cantidad = input.nextInt();
                }while(cantidad < 0 || cantidad > 10);
                datosJuego[1] += cantidad * 1;
                colocaHorizontal(jugadorPC, cantidad, 'L', 1);     

                do{ 
                    System.out.println("Nº de misiles, mínimo: " + datosJuego[1] + " y máximo 100");
                    datosJuego[0] = input.nextInt();
                }while(datosJuego[0] < datosJuego[1] || datosJuego[0] > 100);//Aseguramos que mínimo lleve los misiles necesarios para hundir todos los barcos sin fallos
                break;
        }
        
        verTablero(jugadorPC);//NOTA: Para ver el tablero del PC descomentar esta línea de código
        verTablero(jugadorHumano);
        
        input.nextLine();//Limpiamos el buffer de entrada para recoger el char siguiente pues viene con el \n del int previo.
                
        //Mientras me queden misiles o barcos por hundir...
        while(datosJuego[0] > 0 && datosJuego[1] > 0){//Posición 0 tenemos los misiles, posición 1 los impactos
            //Mostramos marcador de Misiles restantes y los impactos necesarios para la victoria                
            System.out.println("Tenemos: " + datosJuego[0] + " misiles y deberíamos dar en el blanco " + datosJuego[1] + " veces para ganar");
            while(okCoordenadaX == false){ //Mientras no me de una coordenada correcta del eje X válida
                System.out.println("Dame la coordenada de disparo X (A - J)");
                charCoordenadaX = input.nextLine().toLowerCase().charAt(0);
                //A continuación verificamos que lo recogido como char por teclado está dentro del vector char de coordenadasValidasX
                //No hago un .sort previo del vector puesto que el vector char tiene sus valores ordenados en ASCII
                if(Arrays.binarySearch(coordenadasValidasX, charCoordenadaX)>=0)
                    okCoordenadaX = true;//Si existía dentro del vector, salimos del while.
            }
            do{
                System.out.println("Dame la coordenada de disparo en eje Y (0 - 9)");
                coordenadaY = input.nextInt();
                input.nextLine();
            }while(coordenadaY < 0 || coordenadaY > 9);           
            
            coordenadaX = Arrays.binarySearch(coordenadasValidasX, charCoordenadaX);//Obtengo la equivalencia del carácter en coordenada numérica para la matriz
            disparoMisil(jugadorPC, jugadorHumano, coordenadaX, coordenadaY, datosJuego);//Lanzamos el misil con todos los datos
            okCoordenadaX = false;//Volvemos a poner el "interruptor" a false para entrar a pedir coordenada X
        }
    }
    
    //******* FUNCIONES ********    
    
    //Lleno inicial de '-' ambas matrices
    public static void llenadoInicialTableros(char jugador[][]){
        for(int i=0; i<jugador.length; i++) 
            Arrays.fill(jugador[i], '-');//Relleno por filas y no recorriendo la matriz con el método de vectores .fill
    }
    
    //Ver cómo está relleno cualquier tablero
    public static void verTablero(char jugador[][]){
        System.out.println("  A B C D E F G H I J");//Pongo la cabecera de la matriz 
        for(int i=0; i<jugador.length; i++){
            System.out.print(i + " "); //Inicio cada fila mostrando el valor de i
            for(int j=0; j<jugador[i].length; j++){
                System.out.print(jugador[i][j] + " ");//Saco por pantalla cada posición de la matriz con su contenido
            }
            System.out.println();//Tras cada fila hago un salto de línea
        }
    }
    
    //Genera número aleatorio de 0 a 9
    public static int numAleaotorio(){
        return (int)(Math.random()*10); //De 0 a 9 porque como sólo coje 9.99999 el 9 sería la parte entera
    }
    
    //Colocamos todos los tipos de barco Horizontales y la Lancha que es indiferente en esta función
    public static void colocaHorizontal(char jugadorPC[][], int cantidad, char tipo, int dimensionBarco){
        int fil, col;
        boolean entraElBarco;
        
        while(cantidad > 0){ 
                entraElBarco = true;//Activo interruptor
            while(entraElBarco){
            do{    
                fil = numAleaotorio();
                col = numAleaotorio();
            }while(col + dimensionBarco > 9);//Mientras que las dimensiones del barco desborde de la fila desde su punto de partida
            for(int i = col; i<col + dimensionBarco; i++){//Como el barco cabe, vemos qué hay en esas mismas posiciones
                if(jugadorPC[fil][i] != '-'){
                    entraElBarco = false;//El barco tropieza con una posición '-' en cualquier punto
                    }
            }
            
            if(!entraElBarco){//Como ha tropezado, entramos a cambiar el interruptor para repetir el proceso
                entraElBarco = true;
            }
            else if(entraElBarco){//Cabía y no ha tropezado con nada          
                for(int i = col; i<col + dimensionBarco; i++){
                    jugadorPC[fil][i] = tipo;//Ponemos el barco 
                }
                cantidad--;//Descontamos el nº de barcos que se deban poner, si hemos acabado, saldremos del while
                entraElBarco = false;//Como hemos puesto un barco, debemos salir del while anidado para veriricar si queda cantidad
            }
            }     
            }
    }
    
    //Colocamos el único barco que hay Vertical
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
    
    //Realizamos verificación de qué hay en las coordenadas recibidas por el usuario al disparar el misil y actuamos
    public static void disparoMisil(char jugadorPC[][], char jugadorHumano[][], int col, int fil, int datosJuego[]){
        if(jugadorHumano[fil][col] != 'A' && jugadorHumano[fil][col] != 'X'){//Si la posición recibida no es ni Agua previa ni Tocado previo
            if(jugadorPC[fil][col] == '-'){//Si lo que hay es '-' no ha impactado en barco
                System.out.println("Mi Capitán, hemos fallado... AGUA!");
                jugadorHumano[fil][col] = 'A';//Introducimos en la misma posición el símbolo del Agua
                verTablero(jugadorHumano);
            }
            else{//Si no ha entrado es que no era ni A ni X ni - ya que se ha saltado el if previo
                System.out.println("Mi Capitán, hemos dado al enemigo... TOCADO!");
                jugadorHumano[fil][col] = 'X';//Introducimos en el tablero del jugador el impacto
                verTablero(jugadorHumano);
                datosJuego[1] -= 1;//Quitamos un impacto (un barco alcanzado, cuando todos hayan sido alcanzados, VICTORIA!)
            }
        }
        else{//Si hemos entrado aquí, era A o X... por lo tanto... repetida la posición 
            System.out.println("Mi Capitán, esa posición ya ha sido atacada!! Deme otras coordenadas!");
            verTablero(jugadorHumano);
            datosJuego[0] += 1; //Añadimos el misil "lanzado" erróneamente a una posición ya disparada al jugador
        }
        
        datosJuego[0] -= 1;//Quitamos el misil que ha impactado/agua
        
        if(datosJuego[0] == 0 && datosJuego[1] == 0)//Si con el último misil impactamos en la última posición VICTORIA
            System.out.println("Mi capitán... HEMOS GANADO!!... hemos HUNDIDO LA FLOTA enemiga!!!");
        else if(datosJuego[1] == 0)//Todos impactados VICTORIA
            System.out.println("Mi capitán... HEMOS GANADO!!... hemos HUNDIDO LA FLOTA enemiga!!!");
        else if(datosJuego[0] == 0)//Si no quedan misiles... lamentablemente... FIN 
            System.out.println("Mi capitán... HEMOS PERDIDO!!... no tenemos más misiles");
    }    
}