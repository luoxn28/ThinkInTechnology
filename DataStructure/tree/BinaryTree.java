package tree;

/**
 * 二叉树
 */
public class BinaryTree<T extends Comparable> {
    /* 二叉树根节点 */
    private BinaryNode<T> root;

    public BinaryTree() {
        this.root = null;
    }

    // ------------------ Public methods

    public void insert(T data) {
        this.root = insert(data, this.root);
    }

    public void remove(T data) {
        this.root = remove(data, this.root);
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

    // ------------------ Private methods

    private BinaryNode<T> insert(T data, BinaryNode<T> node) {
        if (node == null) {
            return new BinaryNode<T>(data, null, null);
        }

        int compareResult = data.compareTo(node.data);
        if (compareResult < 0) {
            node.left = insert(data, node.left);
        } else if (compareResult > 0) {
            node.right = insert(data, node.right);
        }

        return node;
    }

    private BinaryNode<T> remove(T data, BinaryNode<T>node) {
        if (node == null) {
            return null;
        }

        int compareResult = data.compareTo(node.data);
        if (compareResult < 0) {
            node.left = remove(data, node.left);
        } else if (compareResult > 0){
            node.right = remove(data, node.right);
        } else {
            if ((node.left != null) && (node.right != null)) {
                /* 左右子树都非空 */
                BinaryNode<T> tmpNode = findMin(node.right);
                node.data = tmpNode.data;
                node.right = remove(tmpNode.data, node.right);
            } else {
                /* 左子树为空或者右子树为空 */
                if (node.left == null) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
        }

        return node;
    }

    private boolean contains(T data, BinaryNode<T> node) {
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

    private BinaryNode<T> findMin(BinaryNode<T> node) {
        BinaryNode<T> minNode = null;

        while (node != null) {
            minNode = node;
            node = node.left;
        }
        return minNode;
    }

    private BinaryNode<T> findMax(BinaryNode<T> node) {
        BinaryNode<T> minNode = null;

        while (node != null) {
            minNode = node;
            node = node.right;
        }
        return minNode;
    }

    private void printNode(BinaryNode<T> node) {
        if (node.left != null) {
            printNode(node.left);
        }

        System.out.print(node.data + " ");
        if (node.right != null) {
            printNode(node.right);
        }
    }

    // ------------------ BinaryNode class

    /**
     * 二叉树节点
     */
    static class BinaryNode<T> {
        T  data;
        BinaryNode<T> left;
        BinaryNode<T> right;

        public BinaryNode(T data) {
            this(data, null, null);
        }

        public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}
