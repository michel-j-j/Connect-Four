package engine;

import java.io.*;
import java.net.*;

public class CuatroEnLineaServidor {
    private static final int PUERTO = 12345;
    private static ServerSocket servidor;

    public static void main(String[] args) {
        try {
        	if(servidor!=null)
        		servidor.close();
        	
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Cuatro en Línea iniciado. Esperando jugadores...");

            Socket jugador1Socket = servidor.accept();
            System.out.println("Jugador 1 conectado.");

            Socket jugador2Socket = servidor.accept();
            System.out.println("Jugador 2 conectado.");

            BufferedReader entradaJugador1 = new BufferedReader(new InputStreamReader(jugador1Socket.getInputStream()));
            PrintWriter salidaJugador1 = new PrintWriter(jugador1Socket.getOutputStream(), true);
            BufferedReader entradaJugador2 = new BufferedReader(new InputStreamReader(jugador2Socket.getInputStream()));
            PrintWriter salidaJugador2 = new PrintWriter(jugador2Socket.getOutputStream(), true);

            CuatroEnLinea juego = new CuatroEnLinea();

            salidaJugador1.println("Jugador 1");
            salidaJugador2.println("Jugador 2");

            while (!juego.haTerminado()) {
                // Turno del Jugador 1
                salidaJugador1.println(juego.getTablero());
                salidaJugador1.println("Su turno. Ingrese la columna (1-7): ");
                int columnaJ1 = Integer.parseInt(entradaJugador1.readLine()) - 1;
                juego.colocarFicha(columnaJ1, 'X');

                if (juego.haGanado('X')) {
                    salidaJugador1.println("¡Has ganado!");
                    salidaJugador2.println("¡Has perdido!");
                    break;
                } else if (juego.tableroLleno()) {
                    salidaJugador1.println("¡Empate!");
                    salidaJugador2.println("¡Empate!");
                    break;
                }

                // Turno del Jugador 2
                salidaJugador2.println(juego.getTablero());
                salidaJugador2.println("Su turno. Ingrese la columna (1-7): ");
                int columnaJ2 = Integer.parseInt(entradaJugador2.readLine()) - 1;
                juego.colocarFicha(columnaJ2, 'O');

                if (juego.haGanado('O')) {
                    salidaJugador2.println("¡Has ganado!");
                    salidaJugador1.println("¡Has perdido!");
                    break;
                } else if (juego.tableroLleno()) {
                    salidaJugador1.println("¡Empate!");
                    salidaJugador2.println("¡Empate!");
                    break;
                }
            }

            jugador1Socket.close();
            jugador2Socket.close();
            servidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}