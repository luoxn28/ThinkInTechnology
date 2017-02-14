package tree;

/**
 * AvlTree AVL树
 */
public class AvlTree<T extends Comparable> {
    private AvlNode<T> root;

    public AvlTree() {
        this.root = null;
    }

    // ---------------- Public Methods

    public void insert(T data) {
        this.root = insert(data, this.root);
    }

    public int height() {
        return height(this.root);
    }

    public boolean isEmpty() {
        return (this.root == null);
    }

    public void makeEmpty() {
        this.root = null;
    }

    public boolean contains(T data) {
        return contains(data, this.root);
    }

    public void printAll() {
        printNode(this.root);
    }

    // ---------------- Private Methods

    private AvlNode<T> insert(T data, AvlNode<T> node) {
        if (node == null) {
            return new AvlNode<T>(data);
        }

        int compareResult = data.compareTo(node.data);
        if (compareResult < 0) {
            node.left = insert(data, node.left);
            if ((height(node.left) - height(node.right)) == 2) {
                if (data.compareTo(node.left.data) < 0) {
                    node.left = rightRotate(node.left);
                } else {
                    node = doubleRightRotate(node);
                }
            }
        } else if (compareResult > 0) {
            node.right = insert(data, node.right);
            if ((height(node.right) - height(node.left)) == 2) {
                if (data.compareTo(node.right.data) > 0) {
                    node.right = leftRotate(node.right);
                } else {
                    node = doubleLeftRotate(node);
                }
            }
        }

        node.height = max(height(node.left), height(node.right)) + 1;
        return node;
    }

    private int max(int x, int y) {
        return (x > y) ? x : y;
    }

    private int height(AvlNode<T> node) {
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
    }

    private boolean contains(T data, AvlNode<T> node) {
        if (node == null) {
            return false;
        }

        int compareResult = data.compareTo(node.data);
        if (compareResult < 0) {
            return contains(data, node.left);
        } else if (compareResult > 0) {
            return contains(data, node.right);
        } else {
            return true;
        }
    }

    private void printNode(AvlNode<T> node) {
        if (node.left != null) {
            printNode(node.left);
        }

        System.out.println(node.data + " - height: " + node.height);
        if (node.right != null) {
            printNode(node.right);
        }
    }

    private AvlNode<T> leftRotate(AvlNode<T> node) {
        AvlNode<T> node2 = node.right;

        node.right = node2.left;
        node2.left = node;

        node.height = max(height(node.left), height(node2.right)) + 1;
        node2.height = max(node.height, height(node2.right)) + 1;

        return node2;
    }

    private AvlNode<T> rightRotate(AvlNode<T> node) {
        AvlNode<T> node2 = node.left;

        node.left = node2.right;
        node2.right = node;

        node.height = max(height(node.left), height(node.right)) + 1;
        node2.height = max(height(node2.left), node.height) + 1;

        return node2;
    }

    private AvlNode<T> doubleLeftRotate(AvlNode<T> node) {
        node.right = rightRotate(node.right);

        return leftRotate(node);
    }

    private AvlNode<T> doubleRightRotate(AvlNode<T> node) {
        node.left = leftRotate(node.left);

        return rightRotate(node);
    }

    // ---------------- Avl树节点类

    /**
     * AVL树节点
     */
    class AvlNode<T> {
        T data;
        AvlNode<T> left;
        AvlNode<T> right;
        int height;

        public AvlNode(T data) {
            this(data, null, null, 1);
        }

        public AvlNode(T data, AvlNode<T> left, AvlNode<T> right, int height) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }
}
