package DSA;

public class Node<T extends Comparable> {

    private T data;
    private int height;
    private Node<T> left;
    private Node<T> right;

    public Node(T data) {
        this.data = data;
        height = 1;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> node) {
        this.left = node;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> node) {
        this.right = node;
    }
}
