import java.io.*;
import java.net.*;
import java.util.*;

public class ChatApp {
    // Shared list to keep track of all connected clients
    private static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Run as (server/client): ");
        String role = scanner.nextLine().trim();

        if (role.equalsIgnoreCase("server")) {
            runServer();
        } else if (role.equalsIgnoreCase("client")) {
            runClient(scanner);
        } else {
            System.out.println("Invalid input. Please enter 'server' or 'client'.");
        }
    }

    // ===================== SERVER =====================
    private static void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000.");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    // ===================== CLIENT =====================
    private static void runClient(Scanner scanner) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            out.println(name);

            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected.");
                }
            });
            reader.start();

            String msg;
            while ((msg = scanner.nextLine()) != null) {
                out.println(msg);
            }

        } catch (IOException e) {
            System.out.println("Unable to connect to server.");
        }
    }

    // ===================== CLIENT HANDLER =====================
    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                clientName = in.readLine();
                broadcast(clientName + " joined the chat.");

                String message;
                while ((message = in.readLine()) != null) {
                    broadcast(clientName + ": " + message);
                }
            } catch (IOException e) {
                System.out.println(clientName + " left.");
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
                clients.remove(this);
                broadcast(clientName + " left the chat.");
            }
        }

        private void broadcast(String message) {
            synchronized (clients) {
                for (ClientHandler client : clients) {
                    if (client != this) {
                        client.out.println(message);
                    }
                }
            }
        }
    }
}
