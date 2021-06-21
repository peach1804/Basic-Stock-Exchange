package DSA;

public class Tree<T extends Comparable<T>> {

    private Node<T> root;
    private Node<T> n;
    private static int nodeCount;

    public Node<T> getRoot(){
        return root;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    // Inserts new node in the tree.
    // Sets the root of the tree to the node returned by the recursive insert function.
    public void insert(T data) {
        root = insert(root, data);
    }

    // Deletes node from the tree.
    // Sets the root of the tree to the node returned by the recursive delete function.
    public void delete(T data) {
        root = delete(root, data);
    }

    // Searches the tree for a node with a specific value.
    public Node<T> search(T data) {
        return search(root, data);
    }

    // Recursively moves through the tree to find the place of the new node.
    // Updates the heights of the ancestor nodes as the recursion unwinds and it moves back up the tree.
    // Calls the method to balance the nodes as it moves back up the tree.
    public Node<T> insert(Node<T> node, T data) {

        if (node == null) {
            return new Node<T>(data);
        }

        if (data.compareTo(node.getData()) < 0) {

            node.setLeft(insert(node.getLeft(), data));
            updateHeight(node);
            node = balanceNode(node);

        } else if (data.compareTo(node.getData()) > 0) {

            node.setRight(insert(node.getRight(), data));
            updateHeight(node);
            node = balanceNode(node);

        } else {
            return node;
        }

        return node;
    }

    // Recursively moves through the tree to find the node to be deleted.
    // When it finds the node it tests to see if the node has zero, one or two children.
    // If the node has one or zero children it deletes the node.
    // If the node has two children it finds the minimum node of the right sub-tree which it sets as a temp node, then
    // sets the current node's data equal to the temp node's.
    // Recursively moves through the current node's sub-tree and deletes the minimum node.
    public Node<T> delete(Node<T> node, T data) {

        if (node == null) {
            return node;
        }

        if (data.compareTo(node.getData()) < 0) {

            n = delete(node.getLeft(), data);
            node.setLeft(n);
            updateHeight(node);
            node = balanceNode(node);

        } else if (data.compareTo(node.getData()) > 0) {

            n = delete(node.getRight(), data);
            node.setRight(n);
            updateHeight(node);
            node = balanceNode(node);

        } else {
            Node<T> temp;

            if (node.getLeft() == null && node.getRight() == null) {

                node = null;

            } else if (node.getLeft() == null) {

                temp = node.getRight();
                node = temp;

            } else if (node.getRight() == null) {

                temp = node.getLeft();
                node = temp;

            } else {

                temp = getMinimum(node.getRight());
                node.setData(temp.getData());
                n = delete(node.getRight(), node.getData());
                node.setRight(n);
            }
        }

        return node;
    }

    // Returns the node containing the data that was searched for.
    // Returns a node with null value if the search is unsuccessful.
    public Node<T> search(Node<T> node, T data) {

        while (node != null) {

            if (data.compareTo(node.getData()) < 0) {

                node = node.getLeft();

            } else if (data.compareTo(node.getData()) > 0) {

                node = node.getRight();

            } else {
                return node;
            }
        }

        return null;
    }

    public int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Returns the height of a node.
    public int height(Node<T> node) {

        if (node == null) {
            return 0;
        }

        return node.getHeight();
    }

    // Finds the maximum height of the node's left and right children and adds 1.
    public void updateHeight(Node<T> node) {

        int nodeHeight = max(height(node.getLeft()), height(node.getRight())) + 1;

        node.setHeight(nodeHeight);
    }

    // Finds the balance factor of the node (height of left node minus height of right node).
    public int getBalance(Node<T> node) {

        if (node == null) {
            return 0;
        }

        return height(node.getLeft()) - height(node.getRight());
    }

    // Finds the minimum value node of the tree.
    public Node<T> getMinimum(Node<T> node) {

        if (node.getLeft() != null) {

            while (node.getLeft() != null) {
                node = node.getLeft();
            }
        }

        return node;
    }

    // Decides which rotations to perform on the nodes based on balance factors of the node and its children.
    public Node<T> balanceNode(Node<T> node) {

        int balance = getBalance(node);

        if (balance > 1) {

            if (getBalance(node.getLeft()) >= 0) {

                node = rightRotate(node);

            } else {

                n = leftRotate(node.getLeft());
                node.setLeft(n);
                node = rightRotate(node);
            }
        } else if (balance < -1) {

            if (getBalance(node.getRight()) <= 0) {

                node = leftRotate(node);

            } else {

                n = rightRotate(node.getRight());
                node.setRight(n);
                node = leftRotate(node);
            }
        }

        return node;
    }

    // Define surrounding nodes. (st means subtree)
    // Perform rotation
    // Update heights of nodes x & y (y is the new root)
    // Return new root
    public Node<T> leftRotate(Node<T> x) {

        Node<T> y = x.getRight();
        Node<T> st = y.getLeft();

        y.setLeft(x);
        x.setRight(st);

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Define surrounding nodes. (st means subtree)
    // Perform rotation
    // Update heights of y and x (x is the new root)
    // Return new root
    public Node<T> rightRotate(Node<T> y) {

        Node<T> x = y.getLeft();
        Node<T> st = x.getRight();

        x.setRight(y);
        y.setLeft(st);

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    public void countNodes() {

        nodeCount = 0;

        if (root != null) {
            ascending(root);
        }
    }

    // Traverses the tree in ascending order and counts the number of nodes.
    public void ascending(Node<T> node) {

        if (node.getLeft() != null) {
            ascending(node.getLeft());
        }

        nodeCount++;

        if (node.getRight() != null) {
            ascending(node.getRight());
        }
    }
}
