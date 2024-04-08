package client;

import java.io.*;
import java.net.*;

public class CuatroEnLineaCliente2 {
    private static final String SERVIDOR_IP = "localhost";
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVIDOR_IP, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            String tipoJugador = entrada.readLine();
            System.out.println("Conectado como " + tipoJugador);

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            String respuesta;
            while ((respuesta = entrada.readLine()) != null) {
                System.out.println(respuesta);
                if (respuesta.startsWith("Su turno")) {
                    String columna = teclado.readLine();
                    salida.println(columna);
                } else if (respuesta.equals("¡Has ganado!") || respuesta.equals("¡Has perdido!") || respuesta.equals("¡Empate!")) {
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}