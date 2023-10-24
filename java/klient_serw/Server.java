import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Serwer uruchomiony.");

            while (true) {
                // Oczekiwanie na połączenie klienta
                Socket clientsocket = serverSocket.accept();
                System.out.println("Nowy klient dolaczyl.");
 
                // Tworzenie nowego wątku obsługującego klienta
                new ServerThread(clientsocket).start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}