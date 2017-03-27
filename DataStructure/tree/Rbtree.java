package tree;

/**
 * 红黑树
 */
public class Rbtree <T extends Comparable> {
    private Node<T> root;
    private Node<T> nil = new Node(new Object(), null, Color.BLACK);

    public Rbtree() {
        this.root = nil;
    }

    public void print() {
        printInternal(root);
    }

    public void insert(T data) {
        if (root == nil) {
            root = new Node<T>(data, nil, Color.BLACK);
        } else {
            Node<T> x = nil;
            Node<T> y = root;

            while (y != nil) {
                int compareResult = data.compareTo(y.data);

                x = y;
                if (compareResult < 0) {
                    y = y.left;
                } else if (compareResult > 0) {
                    y = y.right;
                } else {
                    return;
                }
            }

            Node z = new Node(data, nil);
            z.parent = x;
            if (data.compareTo(x.data) < 0) {
                x.left = z;
            } else {
                x.right = z;
            }

            if (x.color == Color.RED) {
                //fixup(x);
            }
        }
    }

    private void printInternal(Node<T> node) {
        if (node == nil) {
            return;
        }

        printInternal(node.left);
        System.out.println(node.data + " ");
        printInternal(node.right);
    }

    // 插入修正函数
    private void fixup(Node<T> x) {
        // y为x节点的叔叔节点
        Node y = null;

        while (x.parent.color == Color.RED) {
            if (x.parent == x.parent.parent.left) {
                y = x.parent.parent.right;
                if (y.color == Color.RED) {
                    x.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    x.parent.parent.color = Color.RED;
                    x = x.parent.parent;
                } else {
                    if (x == x.parent.right) {
                        x = x.parent;
                        turnLeft(x);
                    }

                    x.parent.color = Color.BLACK;
                    x.parent.parent.color = Color.RED;
                    turnRight(x.parent.parent);
                }
            } else {
                y = x.parent.parent.left;
                if (y.color == Color.RED) {
                    x.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    x.parent.parent.color = Color.RED;
                    x = x.parent.parent;
                } else {
                    if (x == x.parent.left) {
                        x = x.parent;
                        turnRight(x);
                    }

                    x.parent.color = Color.BLACK;
                    x.parent.parent.color = Color.RED;
                    turnLeft(x.parent.parent);
                }
            }
        }

        root.color = Color.BLACK;
    }

    // 左旋操作
    private void turnLeft(Node<T> x) {
        Node<T> y = x.right;

        x.right = y.left;
        if (y.left != nil) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == nil) {
            root = y;
        } if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        x.parent = y;
        y.left = x;
    }

    // 右旋操作
    private void turnRight(Node<T> x) {
        Node<T> y = x.left;

        x.left = y.right;
        if (y.right != nil) {
            y.right.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        x.parent = y;
        y.right = x;
    }

    // 红黑树节点类
    class Node <T> {
        T data;
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        Color color;

        public Node(T data, Node<T> nil) {
            this(data, nil, nil, nil, Color.RED);
        }

        public Node(T data, Node<T> nil, Color color) {
            this(data, nil, nil, nil, color);
        }

        public Node(T data, Node<T> parent, Node<T> left, Node<T> right, Color color) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }
    }

    // 红黑树节点颜色枚举
    enum Color {
        RED, BLACK
    }
}
