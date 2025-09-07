import java.io.*;
import java.net.*;

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
                System.out.println("Client says: " + message);
                out.println("Server received: " + message);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class SingleClientServer {
    public static void main(String[] args) {
        int port = 5555;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();

                // Ensure only ONE client is handled at a time
                clientThread.join();
            }

        } catch (IOException | InterruptedException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
