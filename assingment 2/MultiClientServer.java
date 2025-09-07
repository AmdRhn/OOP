import java.io.*;
import java.net.*;

// Thread class to handle a single client
class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String message;
            out.println("Connected to server. Type 'exit' to disconnect.");

            while ((message = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(message.trim())) {
                    out.println("Goodbye!");
                    break;
                }
                System.out.println("Client [" + clientSocket.getInetAddress() + "] says: " + message);
                out.println("Server received: " + message);
            }

        } catch (IOException e) {
            System.out.println("Error with client " + clientSocket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MultiClientServer {
    public static void main(String[] args) {
        int port = 5555;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                // Create a new thread for each client
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
