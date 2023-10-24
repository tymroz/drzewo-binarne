import java.util.Scanner;
import java.util.LinkedList;

class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    private class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public void insert(T data) {
        root = insertNode(root, data);
    }

    private Node<T> insertNode(Node<T> root, T data) {
        if (root == null) {
            root = new Node<>(data);
            return root;
        }

        if (data.compareTo(root.data) < 0)
            root.left = insertNode(root.left, data);
        else if (data.compareTo(root.data) > 0)
            root.right = insertNode(root.right, data);

        return root;
    }

    public boolean search(T data) {
        return searchNode(root, data);
    }

    private boolean searchNode(Node<T> root, T data) {
        if (root == null)
            return false;

        if (data.compareTo(root.data) == 0)
            return true;
        else if (data.compareTo(root.data) < 0)
            return searchNode(root.left, data);
        else
            return searchNode(root.right, data);
    }

    public void delete(T data) {
        root = deleteNode(root, data);
    }

    private Node<T> deleteNode(Node<T> root, T data) {
        if (root == null)
            return root;

        if (data.compareTo(root.data) < 0)
            root.left = deleteNode(root.left, data);
        else if (data.compareTo(root.data) > 0)
            root.right = deleteNode(root.right, data);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.data = minValue(root.right);
            root.right = deleteNode(root.right, root.data);
        }

        return root;
    }

    private T minValue(Node<T> root) {
        T minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    public void printBinaryTree1(){
        printBinaryTree(root);
    }

   public void printBinaryTree(Node root)
    {
        LinkedList<Node> treeLevel = new LinkedList<Node>();
        treeLevel.add(root);
        LinkedList<Node> temp = new LinkedList<Node>();
        int counter = 0;
        int height = heightOfTree(root) - 1;
        double numberOfElements = (Math.pow(2, (height + 1)) - 1);
        while (counter <= height) {
            Node removed = treeLevel.removeFirst();
            if (temp.isEmpty()) {
                printSpace(numberOfElements / Math.pow(2, counter + 1), removed);
            }
            else {
                printSpace(numberOfElements / Math.pow(2, counter), removed);
            }
            if (removed == null) {
                temp.add(null);
                temp.add(null);
            }
            else {
                temp.add(removed.left);
                temp.add(removed.right);
            }
 
            if (treeLevel.isEmpty()) {
                System.out.println("");
                System.out.println("");
                treeLevel = temp;
                temp = new LinkedList<>();
                counter++;
            }
        }
    }
 
    public void printSpace(double n, Node removed)
    {
        for (; n > 0; n--) {
            System.out.print("\t");
        }
        if (removed == null) {
            System.out.print(" ");
        }
        else {
            System.out.print(removed.data);
        }
    }
 
    public int heightOfTree(Node root)
    {
        if (root == null) {
            return 0;
        }
        return 1
            + Math.max(heightOfTree(root.left),
                       heightOfTree(root.right));
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz typ drzewa: 1 - Integer, 2 - Double, 3 - String");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                BinarySearchTree<Integer> intTree = new BinarySearchTree<>();
                handleOperations(intTree, Integer.class, scanner);
                break;
            case 2:
                BinarySearchTree<Double> doubleTree = new BinarySearchTree<>();
                handleOperations(doubleTree, Double.class, scanner);
                break;
            case 3:
                BinarySearchTree<String> stringTree = new BinarySearchTree<>();
                handleOperations(stringTree, String.class, scanner);
                break;
            default:
                System.out.println("Nieprawidłowy wybór.");
                break;
        }
    }

    private static <T extends Comparable<T>> void handleOperations(BinarySearchTree<T> tree, Class<T> type, Scanner scanner) {
        while (true) {
            System.out.println("\nWybierz operację: 1 - Insert, 2 - Search, 3 - Delete, 4 - Draw, 5 - Wyjście");
            int operation = scanner.nextInt();
            scanner.nextLine();

            switch (operation) {
                case 1:
                    System.out.println("Podaj wartość do wstawienia:");
                    if (type == Integer.class) {
                        int value = scanner.nextInt();
                        tree.insert(type.cast(value));
                    } else if (type == Double.class) {
                        double value = scanner.nextDouble();
                        tree.insert(type.cast(value));
                    } else if (type == String.class) {
                        String value = scanner.nextLine();
                        tree.insert(type.cast(value));
                    }
                    break;
                case 2:
                    System.out.println("Podaj wartość do przeszukania:");
                    if (type == Integer.class) {
                        int value = scanner.nextInt();
                        System.out.println("Wynik przeszukiwania: " + tree.search(type.cast(value)));
                    } else if (type == Double.class) {
                        double value = scanner.nextDouble();
                        System.out.println("Wynik przeszukiwania: " + tree.search(type.cast(value)));
                    } else if (type == String.class) {
                        String value = scanner.nextLine();
                        System.out.println("Wynik przeszukiwania: " + tree.search(type.cast(value)));
                    }
                    break;
                case 3:
                    System.out.println("Podaj wartość do usunięcia:");
                    if (type == Integer.class) {
                        int value = scanner.nextInt();
                        tree.delete(type.cast(value));
                    } else if (type == Double.class) {
                        double value = scanner.nextDouble();
                        tree.delete(type.cast(value));
                    } else if (type == String.class) {
                        String value = scanner.nextLine();
                        tree.delete(type.cast(value));
                    }
                    break;
                case 4:
                    System.out.println("\nWyświetlanie drzewa:");
                    tree.printBinaryTree1();
                    break;
                case 5:
                    System.out.println("Wyjście z programu.");
                    return;
                default:
                    System.out.println("Nieprawidłowa operacja.");
                    break;
            }
        }
    }
}