import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvlTree<T> {
    private AvlNode<T> root;

    public AvlTree() {
        root = null;
    }

    private int height(AvlNode<T> n) {
        return n == null ? -1 : n.height;
    }

    private void updateHeight(AvlNode<T> n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    public void insert(T data, int dataIndex) {
        root = insertNode(root, data, dataIndex);
    }

    private AvlNode<T> insertNode(AvlNode<T> node, T data, int dataIndex) {
        if (node == null) {
            return new AvlNode<T>(data, dataIndex);
        } else if (node.compareData(data)) {
            node.left = insertNode(node.left, data, dataIndex);
        } else if (!node.compareData(data)) {
            node.right = insertNode(node.right, data, dataIndex);
        }

        return rebalance(node);
    }

    public void delete(T data) {
        AvlNode<T> nodoToDelete = deleteNode(root, data);
    }

    private AvlNode<T> deleteNode(AvlNode<T> node, T data) {
        if (node == null) {
            return node;
        } else if (node.compareData(data)) {
            node.left = deleteNode(node.left, data);
        } else if (!node.compareData(data)) {
            node.right = deleteNode(node.right, data);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                AvlNode<T> mostLeftChild = getMostLeftChild(node.right);
                node.data = mostLeftChild.data;
                node.right = deleteNode(node.right, node.data);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    public int search(T data) {
        return searchNode(root, data);
    }

    private int searchNode(AvlNode<T> node, T data) {
        AvlNode<T> current = node;
        while (current != null) {
            if (current.data.equals(data)) {
                return current.dataIndex;
            } else if (!current.compareData(data)) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return -1;
    }

    public List<Integer> searchByTerm(String term) {
        List<Integer> listOfIndexes = new ArrayList<>();
        if (root == null) {
            return listOfIndexes;
        }

        int sizeOfTerm = term.length();
        searchByTermImpl(root, term, listOfIndexes, sizeOfTerm);
        return listOfIndexes;
    }

    private void searchByTermImpl(AvlNode<T> node, String term, List<Integer> listOfIndexes, int sizeOfTerm) {
        String dataOfNode = (String) node.data;
        String dataOfNodeSplit = dataOfNode.substring(0, sizeOfTerm);

        if (dataOfNodeSplit.equals(term)) {
            listOfIndexes.add(node.dataIndex);
            if (node.left != null) {
                searchByTermImpl(node.left, term, listOfIndexes, sizeOfTerm);
            }
            if (node.right != null) {
                searchByTermImpl(node.right, term, listOfIndexes, sizeOfTerm);
            }
        } else {
            if (node.compareData((T) dataOfNodeSplit)) {
                if (node.left != null) {
                    searchByTermImpl(node.left, term, listOfIndexes, sizeOfTerm);
                }
            } else {
                if (node.right != null) {
                    searchByTermImpl(node.right, term, listOfIndexes, sizeOfTerm);
                }
            }
        }
    }

    public List<Integer> searchByDate(Date init, Date end) {
        List<Integer> listOfIndexes = new ArrayList<>();
        if (root == null) {
            return listOfIndexes;
        }

        searchByDateImpl(root, init, end, listOfIndexes);
        return listOfIndexes;
    }

    private void searchByDateImpl(AvlNode<T> node, Date init, Date end, List<Integer> listOfIndexes) {
        Date currentDate = (Date) node.data;
        if (currentDate.before(end) && currentDate.after(init)) {
            listOfIndexes.add(node.dataIndex);
            if (node.left != null) {
                searchByDateImpl(node.left, init, end, listOfIndexes);
            }
            if (node.right != null) {
                searchByDateImpl(node.right, init, end, listOfIndexes);
            }
        } else {
            if (currentDate.before(init)) {
                if (node.left != null) {
                    searchByDateImpl(node.left, init, end, listOfIndexes);
                }
            } else {
                if (node.right != null) {
                    searchByDateImpl(node.right, init, end, listOfIndexes);
                }
            }
        }
    }

    private int getBalance(AvlNode<T> n) {
        if (n == null)
            return 0;
        else
            return height(n.left) - height(n.right);
    }

    private AvlNode<T> rebalance(AvlNode<T> z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.left.left) > height(z.left.right))
                z = simpleRotationToRight(z);
            else {
                z.left = simpleRotationToLeft(z.left);
                z = simpleRotationToRight(z);
            }
        } else if (balance < -1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = simpleRotationToLeft(z);
            } else {
                z.right = simpleRotationToRight(z.right);
                z = simpleRotationToLeft(z);
            }
        }
        return z;
    }

    private AvlNode<T> simpleRotationToLeft(AvlNode<T> y) {
        AvlNode<T> x = y.right;
        AvlNode<T> z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AvlNode<T> simpleRotationToRight(AvlNode<T> y) {
        AvlNode<T> x = y.left;
        AvlNode<T> z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    public void inorder() {
        System.out.println("Em ordem: \n");
        inorder(root);
        System.out.println("\n");
    }

    private void inorder(AvlNode<T> n) {
        if (n != null) {
            inorder(n.left);
            System.out.print(n.data + " ");
            inorder(n.right);
        }
    }

    public void preorder() {
        System.out.println("Pré-ordem:\n ");
        preorder(root);
        System.out.println("\n");
    }

    private void preorder(AvlNode<T> r) {
        if (r != null) {
            System.out.print(r.data + " ");
            preorder(r.left);
            preorder(r.right);

        }
    }

    public void postorder() {
        System.out.println("Pós-ordem:\n ");
        postorder(root);
        System.out.println("\n");
    }

    private void postorder(AvlNode<T> r) {
        if (r != null) {
            postorder(r.left);
            postorder(r.right);
            System.out.print(r.data + " ");

        }
    }

    private AvlNode<T> getMostLeftChild(AvlNode<T> n) {
        AvlNode<T> current = n;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}
