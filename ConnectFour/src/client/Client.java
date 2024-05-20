package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Client {

	private static final String SERVER_IP = "localhost";
	private static final int PORT = 12345;

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

		} catch (ConnectException e) {
			System.out.println("The server is not on");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
