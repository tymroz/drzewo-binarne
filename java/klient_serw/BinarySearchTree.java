import java.util.LinkedList;

/**
 * Jest klasą główną reprezentującą drzewo binarne.
 */
class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    /**
     * Jest klasą wewnętrzną reprezentującą węzeł drzewa binarnego.
     */
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

    /**
     * Dodaje nowy węzeł z wartością data do drzewa binarnego.
     * @param data wartość węzła
     */
    public void insert(T data) {
        root = insertNode(root, data);
    }

    /**
     * Rekurencyjnie wstawia nowy węzeł z wartością data do drzewa binarnego.
     * @param root Zmienna przechowująca korzeń drzewa.
     * @param data wartość węzła
     * @return Jeśli wskazany węzeł root jest pusty, tworzy nowy węzeł i zwraca go jako nowy korzeń. W przeciwnym razie porównuje wartość data z wartością węzła root i wstawia nowy węzeł do odpowiedniego poddrzewa.
     */
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

    /**
     * Sprawdza, czy węzeł o wartości data istnieje w drzewie binarnym.
     * @param data wartość węzła
     * @return Wywołuje prywatną metodę searchNode()
     */
    public boolean search(T data) {
        return searchNode(root, data);
    }

    /**
     * Rekurencyjnie przeszukuje drzewo binarne w poszukiwaniu węzła o wartości data
     * @param root Zmienna przechowująca korzeń drzewa.
     * @param data wartość węzła
     * @return Jeśli wskazany węzeł root jest pusty, zwraca wartość logiczną false. W przeciwnym razie porównuje wartość data z wartością węzła root i kontynuuje przeszukiwanie odpowiedniego poddrzewa.
     */
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

    /**
     * Usuwa węzeł o wartości data z drzewa binarnego. Wywołuje prywatną metodę deleteNode()
     * @param data wartość węzła
     */
    public void delete(T data) {
        root = deleteNode(root, data);
    }

    /**
     * Rekurencyjnie usuwa węzeł o wartości data z drzewa binarnego.
     * @param root Zmienna przechowująca korzeń drzewa.
     * @param data wartość węzła
     * @return Jeśli wskazany węzeł root jest pusty, zwraca go bez zmian. W przeciwnym razie porównuje wartość data z wartością węzła root i usuwa odpowiedni węzeł, zachowując strukturę drzewa.
     */
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

    /**
     * Znajduje najmniejszą wartość w poddrzewie o korzeniu root.
     * @param root Zmienna przechowująca korzeń drzewa.
     * @return Przechodzi w lewo aż do osiągnięcia liścia i zwraca wartość liścia.
     */
    private T minValue(Node<T> root) {
        T minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    /**
     * Wykorzystuje algorytm poziomego przeglądu drzewa i tworzy tekstową reprezentację drzewa.
     * @return Zwraca drzewo binarne w postaci łańcucha znaków.
     */
    public String getBinaryTreeAsString() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Node> treeLevel = new LinkedList<>();
        treeLevel.add(root);
        LinkedList<Node> temp = new LinkedList<>();
        int counter = 0;
        int height = heightOfTree(root) - 1;
        double numberOfElements = (Math.pow(2, (height + 1)) - 1);
        while (counter <= height) {
            Node removed = treeLevel.removeFirst();
            if (temp.isEmpty()) {
                appendSpace(sb, numberOfElements / Math.pow(2, counter + 1), removed);
            } else {
                appendSpace(sb, numberOfElements / Math.pow(2, counter), removed);
            }
            if (removed == null) {
                temp.add(null);
                temp.add(null);
            } else {
                temp.add(removed.left);
                temp.add(removed.right);
            }

            if (treeLevel.isEmpty()) {
                sb.append("\n\n");
                treeLevel = temp;
                temp = new LinkedList<>();
                counter++;
            }
        }
        return sb.toString();
    }

    /**
     * Dołącza do łańcucha znaków sb odpowiednią liczbę znaków tabulacji w celu wygenerowania wcięcia w zależności od poziomu drzewa. Jeśli węzeł removed jest pusty, dołącza pojedynczy spację, w przeciwnym razie dołącza wartość węzła.
     * @param sb Obiekt klasy StringBuilder, który jest wykorzystywany do tworzenia łańcucha znaków reprezentującego drzewo.
     * @param n liczba tabulacji
     * @param removed węzeł
     */
    private void appendSpace(StringBuilder sb, double n, Node removed) {
        for (; n > 0; n--) {
            sb.append("\t");
        }
        if (removed == null) {
            sb.append(" ");
        } else {
            sb.append(removed.data);
        }
    }

    /**
     * Oblicza wysokość drzewa o korzeniu root. Jeśli wskazany korzeń jest pusty, zwraca 0. W przeciwnym razie oblicza wysokość jako 1 plus maksimum z wysokości lewego i prawego poddrzewa.
     * @param root Zmienna przechowująca korzeń drzewa.
     * @return wysokość drzewa
     */
    public int heightOfTree(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(heightOfTree(root.left), heightOfTree(root.right));
    }
}