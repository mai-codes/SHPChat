import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client {
	private Socket socket = null;
	private InputStreamReader inputStream = null;
	private OutputStreamWriter outputStream = null;
	private BufferedReader bufferReader = null;
	private BufferedWriter bufferWriter = null;
	private String username;
	private static HashMap<String, String> credentials = new HashMap<String, String>();
	public ArrayList<String> messageLog = new ArrayList<String>();

	/* constructor for the client */
	public Client(String localhost, int port, String username) throws UnknownHostException, IOException {
		socket = new Socket(localhost, port);
		inputStream = new InputStreamReader(socket.getInputStream());
		outputStream = new OutputStreamWriter(socket.getOutputStream());
		bufferWriter = new BufferedWriter(outputStream);
		bufferReader = new BufferedReader(inputStream);
		this.username = username;
	}

	/* main method */
	public static void main(String args[]) throws IOException {
		/* please note, usernames and passwords are hardcoded */
		credentials.put("Joe", "pass1");
	    credentials.put("Sally", "pass2");
	    credentials.put("Mike", "pass3");
	    credentials.put("Erica", "pass4");
		Client client = new Client(args[0], Integer.valueOf(args[1]), "username");
		client.startConnection();
	}
	
	public void setUser(String newUser) {
		this.username = newUser;
	}

	
	public void startConnection()
			throws UnknownHostException, IOException {
		System.out.println("Welcome to SHP Chat! Please login by typing your username and password in this format <AUTH username password>:");
		Scanner kb = new Scanner(System.in);
		String input = kb.nextLine();
		/* begin authentication */
		if(input.contains("AUTH")) {
			String[] splited = input.split("\\s+");
			if (credentials.containsKey(splited[1]) && splited[2].equals(credentials.get(splited[1]))) {
				System.out.println("AUTH-RESPONSE: " + "1");
				this.setUser(splited[1]);
				receiveMessage();
				sendMessage();
			} else {
				System.out.println("AUTH-RESPONSE: " + "0");
				closeConnection();
			}
		}
		else {
			System.out.println("You need to log in to access the chat.");
		}
	}

	/* send message between clients */
	private void sendMessage() throws IOException {
		bufferWriter.write(username);
		bufferWriter.newLine();
		bufferWriter.flush();
		
		while (socket.isConnected()) {
			Scanner kb = new Scanner(System.in);
			String message = kb.nextLine();
			messageLog.add(message);
			if (message.contains("GET-HISTORY")) {
				if (messageLog.size() > 1){
					System.out.println("HISTORY-RESPONSE: 1");
					for (String msg : messageLog) {
						if (!msg.equals("GET-HISTORY")){
							System.out.println(msg);
						} 
					}
				} else {
					System.out.println("HISTORY-RESPONSE: 0");
				}
			} else {
				bufferWriter.write(username + ": " + message);
				bufferWriter.newLine();
				bufferWriter.flush();
			}
		}
	}


	/* receive messages from other clients */
	private void receiveMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String messageFromGroup;
				while (socket.isConnected()) {
					try {
						messageFromGroup = bufferReader.readLine();
						System.out.println(messageFromGroup);
					} catch (IOException e) {
						closeConnection();
						break;
					}
				}
			}
		}).start();
	}

	/* close all streams and socket */
	public void closeConnection() {
		try {
			inputStream.close();
			inputStream.close();
			outputStream.close();
			socket.close();
			bufferReader.close();
			bufferWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
