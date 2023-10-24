import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Nawiązanie połączenia z serwerem
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Wybór typu drzewa
            System.out.println("Podaj typ drzewa: Integer, Double, String");
            String treeType = scanner.nextLine();
            out.println(treeType);

            while (true) {
                // Wybór operacji
                System.out.println("\nWybierz operację: 1 - Insert, 2 - Search, 3 - Delete, 4 - Draw, 5 - Exit");
                int operation = scanner.nextInt();
                scanner.nextLine();

                switch (operation) {
                    case 1:
                        // Operacja Insert
                        System.out.println("Wybrano Insert.");
                        System.out.println("Podaj wartość do wstawienia:");
                        String insertValue = scanner.nextLine();
                        out.println("INSERT");
                        out.println(insertValue);
                        String insertResponse = in.readLine();
                        System.out.println("Serwer: " + insertResponse);
                        break;
                    case 2:
                        // Operacja Search
                        System.out.println("Wybrano Search.");
                        System.out.println("Podaj wartość do przeszukania:");
                        String searchValue = scanner.nextLine();
                        out.println("SEARCH");
                        out.println(searchValue);
                        String searchResponse = in.readLine();
                        System.out.println("Serwer: " + searchResponse);
                        break;
                    case 3:
                        // Operacja Delete
                        System.out.println("Wybrano Delete.");
                        System.out.println("Podaj wartość do usunięcia:");
                        String deleteValue = scanner.nextLine();
                        out.println("DELETE");
                        out.println(deleteValue);
                        String deleteResponse = in.readLine();
                        System.out.println("Serwer: " + deleteResponse);
                        break;
                    case 4:
                        // Operacja Draw
                        System.out.println("Wybrano Draw.");
                        out.println("DRAW");
                        String drawResponse = in.readLine();
                        if (drawResponse.equals("DRAWING")) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = in.readLine()) != null && !line.equals("END")) {
                                stringBuilder.append(line).append("\n");
                            }
                            System.out.println("Serwer:\n" + stringBuilder.toString());
                        } else {
                            System.out.println("Serwer: " + drawResponse);
                        }

                        break;
                    case 5:
                        // Operacja Exit
                        System.out.println("Wybrano Exit.");
                        out.println("EXIT");
                        String exitResponse = in.readLine();
                        System.out.println("Serwer: " + exitResponse);
                        in.close();
                        out.close();
                        socket.close();
                        return;
                    default:
                        System.out.println("Nieprawidłowa operacja.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}