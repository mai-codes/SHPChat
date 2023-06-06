import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private ServerSocket serverSocket;
	private Socket socket;
	private List<ClientHandler> clients = new ArrayList<ClientHandler>();
	private static int port;

	public Server(ServerSocket serverSocket) throws SocketException {
		this.serverSocket = serverSocket;
	}

	public static void main(String[] args) throws IOException {
		port = Integer.valueOf(args[0]);
		ServerSocket serverSocket = new ServerSocket(port);
		Server server = new Server(serverSocket);
		server.createServerConnection(ExecutorWrapper.getInstance());
	}

	/* connect clients to server and begins a new client thread */
	protected void createServerConnection(ExecutorWrapper executorWrapper)
			throws IOException {
		while (true) {
			synchronized (serverSocket) {
				socket = serverSocket.accept();
			}
			System.out.println("New client is connected on port " + port + "!");
			/* begin separate thread for new client */
			ClientHandler clientThread = new ClientHandler(socket);
			clients.add(clientThread);

			/* checks for max thread pool size (4) */
			if (clients.size() <= executorWrapper.getNumThreads()) {
				executorWrapper.execute(clientThread);
			} else {
				System.out.println("Maximum number of clients is 4!");
			}
		}
	}
}
