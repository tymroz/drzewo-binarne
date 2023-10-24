import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private Socket socket;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        // Inicjalizacja trzech drzew binarnych dla typów Integer, Double i String
        BinarySearchTree<Integer> intTree = new BinarySearchTree<>();
        BinarySearchTree<Double> doubleTree = new BinarySearchTree<>();
        BinarySearchTree<String> stringTree = new BinarySearchTree<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

             // Odczyt typu drzewa wybranego przez klienta
            String treeType = in.readLine();
            System.out.println("Klient wybrał drzewo typu: " + treeType);

            while (true) {
                String inputLine = in.readLine();

                if (inputLine.equals("SEARCH")) {
                    // Wyszukiwanie wartości w drzewie
                    String value = in.readLine();
                    boolean result;

                    switch (treeType) {
                        case "Integer":
                            result = intTree.search(Integer.parseInt(value));
                            out.println(result ? "FOUND" : "NOT FOUND");
                            break;
                        case "Double":
                            result = doubleTree.search(Double.parseDouble(value));
                            out.println(result ? "FOUND" : "NOT FOUND");
                            break;
                        case "String":
                            result = stringTree.search(value);
                            out.println(result ? "FOUND" : "NOT FOUND");
                            break;
                        default:
                            out.println("INVALID TYPE");
                            break;
                    }
                } else if (inputLine.equals("INSERT")) {
                    // Wstawianie wartości do drzewa
                    String value = in.readLine();

                    switch (treeType) {
                        case "Integer":
                            intTree.insert(Integer.parseInt(value));
                            out.println("INSERTED");
                            break;
                        case "Double":
                            doubleTree.insert(Double.parseDouble(value));
                            out.println("INSERTED");
                            break;
                        case "String":
                            stringTree.insert(value);
                            out.println("INSERTED");
                            break;
                        default:
                            out.println("INVALID TYPE");
                            break;
                    }
                } else if (inputLine.equals("DELETE")) {
                    // Usuwanie wartości z drzewa
                    String value = in.readLine();

                    switch (treeType) {
                        case "Integer":
                            intTree.delete(Integer.parseInt(value));
                            out.println("DELETED");
                            break;
                        case "Double":
                            doubleTree.delete(Double.parseDouble(value));
                            out.println("DELETED");
                            break;
                        case "String":
                            stringTree.delete(value);
                            out.println("DELETED");
                            break;
                        default:
                            out.println("INVALID TYPE");
                            break;
                    }
                } else if (inputLine.equals("DRAW")) {
                    // Rysowanie drzewa
                    String drawResult;

                    out.println("DRAWING");

                    switch (treeType) {
                        case "Integer":
                            drawResult = intTree.getBinaryTreeAsString();
                            out.println("DRAWING");
                            out.println(drawResult);
                            out.println("END");
                            break;
                        case "Double":
                            drawResult = doubleTree.getBinaryTreeAsString();
                            out.println("DRAWING");
                            out.println(drawResult);
                            out.println("END");
                            break;
                        case "String":
                            drawResult = stringTree.getBinaryTreeAsString();
                            out.println("DRAWING");
                            out.println(drawResult);
                            out.println("END");
                            break;
                        default:
                            out.println("INVALID TYPE");
                            break;
                    }
                } else if (inputLine.equals("EXIT")) {
                    // Obsługa wyjścia z programu
                    out.println("EXITING");
                    socket.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}