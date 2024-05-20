package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import model.ConnectFour;

public class ConnectFourServer {
	private static final int PORT = 12345;
	private static ServerSocket server;

	public static void main(String[] args) {
		try {

			if (server != null)
				server.close();

			server = new ServerSocket(PORT);

			System.out.println("Connect Four Server started. Waiting for players...");

			Socket player1Socket = server.accept();
			System.out.println("Player 1 connected.");

			Socket player2Socket = server.accept();
			System.out.println("Player 2 connected.");

			BufferedReader player1Input = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
			PrintWriter player1Output = new PrintWriter(player1Socket.getOutputStream(), true);

			BufferedReader player2Input = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
			PrintWriter player2Output = new PrintWriter(player2Socket.getOutputStream(), true);

			ConnectFour game = new ConnectFour();

			player1Output.println("Player 1");
			player2Output.println("Player 2");

			while (!game.isGameOver()) {
				// Player 1's turn
				handlePlayerTurn(player1Input, player1Output, player2Output, game, 'X');

				// Player 2's turn
				handlePlayerTurn(player2Input, player2Output, player1Output, game, 'O');
			}

			player1Socket.close();
			player2Socket.close();

			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handlePlayerTurn(BufferedReader input, PrintWriter output, PrintWriter otherPlayerOutput,
			ConnectFour game, char playerSymbol) throws IOException {
		output.println(game.getBoard());
		output.println("Your turn. Enter the column (1-7): ");

		int column = Integer.parseInt(input.readLine()) - 1;

		game.dropPiece(column, playerSymbol);
		output.println(game.getBoard());

		if (game.hasWinner(playerSymbol)) {
			output.println("You win!");
			otherPlayerOutput.println("You lose!");
		} else if (game.isBoardFull()) {
			output.println("Draw!");
			otherPlayerOutput.println("Draw!");
		}
	}
}
