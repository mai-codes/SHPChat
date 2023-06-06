import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	private Socket clientSocket = null;
	private InputStreamReader inputStream = null;
	private OutputStreamWriter outputStream = null;
	private BufferedWriter bufferWriter = null;
	private BufferedReader bufferReader = null;
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
	public String clientUser;
	public int clientNum;


	public ClientHandler(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		inputStream = new InputStreamReader(clientSocket.getInputStream());
		outputStream = new OutputStreamWriter(clientSocket.getOutputStream());
		bufferReader = new BufferedReader(inputStream);
		bufferWriter = new BufferedWriter(outputStream);
		clientHandlers.add(this);
		this.clientUser = bufferReader.readLine();
		if (clientUser != null) {
			broadcastMessage("SHP Server: " + clientUser + " has entered the chat!");
		} 
	}

	/* new thread for client, this is the while loop that enables messages to be sent back and forth between clients */
	@Override
	public void run() {
		String messageFromClient;
		while (clientSocket.isConnected()) {
			try {
				messageFromClient = bufferReader.readLine();
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeConnection();
				break;
			}
		}
	}

	/* broadcast messages to other clients */
	public void broadcastMessage(String messageToSend) {
		for (ClientHandler clientHandler : clientHandlers) {
			try {
				if (clientHandler != this && clientHandler != null) {
					clientHandler.bufferWriter.write(messageToSend);
					clientHandler.bufferWriter.newLine();
					clientHandler.bufferWriter.flush();
				}
			}
			catch (IOException e) {
				closeConnection();
			}
		}
	}
	
	/* remove client from server */
	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadcastMessage("SHP SERVER: " + clientUser + " has left the chat.");
	}
	
	/* close all streams */
	public void closeConnection() {
		removeClientHandler();
		try {
			inputStream.close();
			inputStream.close();
			outputStream.close();
			clientSocket.close();
			bufferReader.close();
			bufferWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
