#include <iostream>
#include <cmath>
#include <queue>
#include <string>
#include <limits>

template <typename T>
class BinarySearchTree {
    struct Node {
        T data;
        Node* left;
        Node* right;

        Node(T value) : data(value), left(nullptr), right(nullptr) {}
    };

    Node* root;

    Node* insertNode(Node* root, T data) {
        if (root == nullptr) {
            root = new Node(data);
            return root;
        }

        if (data < root->data)
            root->left = insertNode(root->left, data);
        else if (data > root->data)
            root->right = insertNode(root->right, data);

        return root;
    }

    bool searchNode(Node* root, T data) {
        if (root == nullptr)
            return false;

        if (data == root->data)
            return true;
        else if (data < root->data)
            return searchNode(root->left, data);
        else
            return searchNode(root->right, data);
    }

    Node* deleteNode(Node* root, T data) {
        if (root == nullptr)
            return root;

        if (data < root->data)
            root->left = deleteNode(root->left, data);
        else if (data > root->data)
            root->right = deleteNode(root->right, data);
        else {
            if (root->left == nullptr)
                return root->right;
            else if (root->right == nullptr)
                return root->left;

            root->data = minValue(root->right);
            root->right = deleteNode(root->right, root->data);
        }

        return root;
    }

    T minValue(Node* root) {
        T minValue = root->data;
        while (root->left != nullptr) {
            minValue = root->left->data;
            root = root->left;
        }
        return minValue;
    }

    void printSpace(double n, Node* removed) {
        for (; n > 0; n--) {
            std::cout << "\t";
        }
        if (removed == nullptr) {
            std::cout << " ";
        }
        else {
            std::cout << removed->data;
        }
    }

    int heightOfTree(Node* root) {
        if (root == nullptr) {
            return 0;
        }
        return 1 + std::max(heightOfTree(root->left), heightOfTree(root->right));
    }

public:
    BinarySearchTree() : root(nullptr) {}

    void insert(T data) {
        root = insertNode(root, data);
    }

    bool search(T data) {
        return searchNode(root, data);
    }

    void remove(T data) {
        root = deleteNode(root, data);
    }

    void printBinaryTree() {
        std::queue<Node*> treeLevel;
        treeLevel.push(root);
        std::queue<Node*> temp;
        int counter = 0;
        int height = heightOfTree(root) - 1;
        double numberOfElements = (std::pow(2, (height + 1)) - 1);
        while (counter <= height) {
            Node* removed = treeLevel.front();
            treeLevel.pop();
            if (temp.empty()) {
                printSpace(numberOfElements / std::pow(2, counter + 1), removed);
            }
            else {
                printSpace(numberOfElements / std::pow(2, counter), removed);
            }
            if (removed == nullptr) {
                temp.push(nullptr);
                temp.push(nullptr);
            }
            else {
                temp.push(removed->left);
                temp.push(removed->right);
            }

            if (treeLevel.empty()) {
                std::cout << "\n\n";
                treeLevel = temp;
                temp = std::queue<Node*>();
                counter++;
            }
        }
    }
};

template <typename T>
void handleOperations(BinarySearchTree<T>& tree) {
    while (true) {
        std::cout << "\nWybierz operacje: 1 - Insert, 2 - Search, 3 - Delete, 4 - Draw, 5 - Wyjscie\n";
        int operation;
        std::cin >> operation;
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

        switch (operation) {
            case 1: {
                std::cout << "Podaj wartosc do wstawienia:\n";
                T value;
                std::cin >> value;
                std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
                tree.insert(value);
                break;
            }
            case 2: {
                std::cout << "Podaj wartosc do przeszukania:\n";
                T value;
                std::cin >> value;
                std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
                std::cout << "Wynik przeszukiwania: " << (tree.search(value) ? "true" : "false") << "\n";
                break;
            }
            case 3: {
                std::cout << "Podaj wartosc do usuniecia:\n";
                T value;
                std::cin >> value;
                std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
                tree.remove(value);
                break;
            }
            case 4: {
                std::cout << "\nWyswietlanie drzewa:\n";
                tree.printBinaryTree();
                break;
            }
            case 5: {
                std::cout << "Wyjscie z programu.\n";
                return;
            }
            default:
                std::cout << "Nieprawidlowa operacja.\n";
                break;
        }
    }
}

int main() {
    std::cout << "Wybierz typ drzewa: 1 - Integer, 2 - Double, 3 - String\n";
    int choice;
    std::cin >> choice;
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    switch (choice) {
        case 1: {
            BinarySearchTree<int> intTree;
            handleOperations(intTree);
            break;
        }
        case 2: {
            BinarySearchTree<double> doubleTree;
            handleOperations(doubleTree);
            break;
        }
        case 3: {
            BinarySearchTree<std::string> stringTree;
            handleOperations(stringTree);
            break;
        }
        default:
            std::cout << "Nieprawidlowy wybor.\n";
            break;
    }

    return 0;
}