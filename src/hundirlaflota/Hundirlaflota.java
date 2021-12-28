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
        int datosJuego [] = new int[2]; //Posición 0 los misiles, posición 1 los tocados        
        int dimensionesLancha = 1, dimensionesBuque = 3, dimensionesAcorazado = 4, dimensionesPortaaviones = 5, cantidad = -1, dificultadElegida; 
        char lancha = 'L', buque = 'B', acorazado = 'Z', portaaviones = 'P', charCoordenadaX = 'z';
        boolean okCoordenadaX = false;
        int coordenadaX, coordenadaY = 10; //inicializo a 10 por el while, sacandolo de rango y entrando en él
             
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
                + "4. Personalizada: elige las cantidades de cada tipo de barco, así como tu nº de misiles.");
            dificultadElegida = input.nextInt();
        } while(dificultadElegida < 1 || dificultadElegida > 4);
        
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
                datosJuego[1] = 4;//4 TOCADO para saber que hemos hundido todos
                break;
            case 4:
                datosJuego[0] = 0;//Inicializo posición Misiles
                datosJuego[1] = 0;//Inicializo posición Tocado
                System.out.println("Mínimo de misiles elegible 1 y máximo 100. Mínimo 1 Lancha máximo tablero lleno (100 espacios)."); 
                
                while(cantidad < 0 || cantidad > 3){
                    System.out.println("Dime número de Portaaviones del enemigo (ocupan 5 espacios) min 0, máx 3:");
                    cantidad = input.nextInt();
                }
                datosJuego[1] += cantidad * dimensionesPortaaviones;//Calculo y almaceno el nº de casillas ocupadas y por lo tanto el mínimo de misiles para ganar
                colocarBarcos(jugadorPC, cantidad, portaaviones, dimensionesPortaaviones); //5 casillas VERTICALES
                cantidad = -1;
                
                while(cantidad < 0 || cantidad > 4){
                    System.out.println("Dime número de Acorazados del enemigo (ocupan 4 espacios) min 0, máx 4:");
                    cantidad = input.nextInt();
                }
                datosJuego[1] += cantidad * dimensionesAcorazado;                 
                colocarBarcos(jugadorPC, cantidad, acorazado, dimensionesAcorazado); //4 casillas HORIZONTALES
                cantidad = -1;
                
                while(cantidad < 0 || cantidad > 5){
                System.out.println("Dime número de Buques del enemigo (ocupan 3 espacios) min 0, máx 5:");
                cantidad = input.nextInt();
                }
                datosJuego[1] += cantidad * dimensionesBuque;
                colocarBarcos(jugadorPC, cantidad, buque, dimensionesBuque); //3 casillas HORIZONTALES
                cantidad = -1;
                
                while(cantidad < 0 || cantidad > 10){
                System.out.println("Dime número de Lanchas del enemigo (ocupa 1 espacio) min 0, máx 10:");
                cantidad = input.nextInt();
                }
                datosJuego[1] += cantidad * dimensionesLancha;
                colocarBarcos(jugadorPC, cantidad, lancha, dimensionesLancha); //1 casilla        
                
                //Aseguramos que mínimo lleve los misiles necesarios para hundir todos los barcos sin fallos
                while(datosJuego[0] < datosJuego[1] || datosJuego[0] > 100){ 
                    System.out.println("Nº de misiles, mínimo: " + datosJuego[1] + " y máximo 100");
                    datosJuego[0] = input.nextInt();
                }
                break;
        }
        
        //verTablero(jugadorPC);//NOTA: Para ver el tablero del PC descomentar esta línea de código
        verTablero(jugadorHumano);
        
        input.nextLine();//Limpiamos el buffer de entrada para recoger el char siguiente pues viene con el \n del int previo.
                
        //Mientras me queden misiles o barcos por hundir...
        while(datosJuego[0] > 0 && datosJuego[1] > 0){//Posición 0 tenemos los misiles, posición 1 los impactos
                            
            System.out.println("Tenemos: " + datosJuego[0] + " misiles y deberíamos dar en el blanco " + datosJuego[1] + " veces para ganar");
            while(okCoordenadaX == false){ //Mientras no me de una coordenada correcta del eje X
                System.out.println("Dame la coordenada de disparo X (A - J)");
                charCoordenadaX = input.nextLine().toLowerCase().charAt(0);
                //Verificamos que lo recogido como letra por teclado está dentro del vector char de coordenadasValidasX
                //No hago un .sort del previo vector puesto que el vector char está en orden ASCII
                if(Arrays.binarySearch(coordenadasValidasX, charCoordenadaX)>=0)
                    okCoordenadaX = true;
            }
            while(coordenadaY < 0 || coordenadaY > 9){
                System.out.println("Dame la coordenada de disparo en eje Y (0 - 9)");
                coordenadaY = input.nextInt();
                input.nextLine();
            }           
            
            coordenadaX = Arrays.binarySearch(coordenadasValidasX, charCoordenadaX);//Obtengo la equivalencia del carácter en coordenada numérica para la matriz
            disparoMisil(jugadorPC, jugadorHumano, coordenadaX, coordenadaY, datosJuego);//Guardamos si hay return la actualización del fallo en repetir coordenada
            coordenadaY = 10;//Mantenemos el bucle de coordenadas en Y activo
            okCoordenadaX = false;//Volvemos a poner el "interruptor" a false para entrar a pedir coordenada X
        }
    }
    
    //******* FUNCIONES ********    
    
    //Lleno de '-' ambas matrices
    public static void llenadoInicialTableros(char jugador[][]){
        for(int i=0; i<jugador.length; i++) 
            Arrays.fill(jugador[i], '-');//Relleno már rápido por filas y no recorriendo la matriz con el método de vectores .fill
    }
    
    //Ver cómo estár relleno cualquier tablero
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
    
    //Colocación de los barcos dependiendo de si son Horizontales (todos los que no son 'P') y el Vertical en otra.
    public static void colocarBarcos(char jugadorPC[][], int cantidad, char tipo, int dimensionBarco){
        
        if(tipo != 'P'){//Colocamos los barcos que van en Horizontal
            colocaHorizontales(jugadorPC, cantidad, tipo, dimensionBarco);
        }
        
        else if(tipo == 'P'){//Colocamos el barco que va en Vertical
            colocaVertical(jugadorPC, cantidad, tipo, dimensionBarco);
        }
    }
    
    //Colocamos todos los tipos de barco Horizontales y la Lancha que es indiferente en esta función
    public static void colocaHorizontales(char jugadorPC[][], int cantidad, char tipo, int dimensionBarco){
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
    
    //Realizamos verificación de qué hay en las coordenadas recibidas por el usuario y actuamos
    public static void disparoMisil(char jugadorPC[][], char jugadorHumano[][], int col, int fil, int datosJuego[]){
        if(jugadorHumano[fil][col] != 'A' && jugadorHumano[fil][col] != 'X'){//Si la posición recibida no es ni Agua previa ni Tocado previo
            if(jugadorPC[fil][col] == '-'){//Si lo que hay es '-' fallo
                System.out.println("Mi Capitán, hemos fallado... ha caído al AGUA!");
                jugadorHumano[fil][col] = 'A';//Introducimos en la misma posición el símbolo del Agua
                verTablero(jugadorHumano);
            }
            else{//Si no ha entrado es que no era ni A ni X ni - ya que se ha saltado el if previo
                System.out.println("Mi Capitán, hemos dado al enemigo... TOCADO!");
                jugadorHumano[fil][col] = 'X';
                verTablero(jugadorHumano);
                datosJuego[1] -= 1;//Quitamos un impacto (un barco alcanzado, cuando todos hayan sido alcanzados, VICTORIA!)
                if(datosJuego[1] == 0)//Si no quedan impactos... la flota enemiga está hundida
                    System.out.println("Mi capitán... HEMOS GANADO!!... hemos HUNDIDO LA FLOTA enemiga!!!");
                    datosJuego[0] += 1;//Si casulamente alcanzamos con el último misil el último impacto, 
                    //evitamos entrar en el if de HEMOS PERDIDO por no tener misiles y sacar por pantalla ambos mensajes
            }
        }
        else{//Si hemos entrado aquí, era o A o X... por lo tanto... repetida la posición 
            System.out.println("Mi Capitán, esa posición ya ha sido atacada!! Deme otras coordenadas!");
            verTablero(jugadorHumano);
            datosJuego[0] += 1; //Añadimos el misil "lanzado" erróneamente a una posición ya disparada al jugador
        }
        
        datosJuego[0] -= 1;//Quitamos el misil
        
        if(datosJuego[0] == 0)//Si no quedan misiles... lamentablemente... FIN 
            System.out.println("Mi capitán... HEMOS PERDIDO!!... no tenemos más misiles");
    }    
}