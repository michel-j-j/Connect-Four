package client;

import java.io.*;
import java.net.*;

public class Player1 implements Client {
    private static final String SERVER_IP = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
    	Client  client = new Player1 ();
        client.start();
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

            String playerType = input.readLine();
            System.out.println("Connected as " + playerType);

            String response;
            while ((response = input.readLine()) != null) {
                System.out.println(response);
                if (response.startsWith("Your turn")) {
                    String column = keyboard.readLine();
                    output.println(column);
                } else if (response.equals("You win!") || response.equals("You lose!") || response.equals("Draw!")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
