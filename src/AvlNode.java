import java.util.Date;

public class AvlNode<K> {
    AvlNode<K> left, right;
    K data;
    int dataIndex;
    int height;

    public AvlNode() {
        left = null;
        right = null;
        data = null;
        height = 0;
    }

    public AvlNode(K d, int index) {
        left = null;
        right = null;
        data = d;
        dataIndex = index;
        height = 0;
    }

    @Override
    public String toString() {
        return "Nó: " + this.data + " ; Esquerda: " + left.data + " ; Direita: " + right.data + "\n";
    }

    Boolean compareData(K y) {
        if (this.data.getClass().getSimpleName().equals("String")) {
            return ((String) this.data).compareTo(((String) y)) > 0;
        } else if (this.data.getClass().getSimpleName().equals("Long")) {
            return (Long) this.data > (Long) y;
        } else {
            return ((Date) this.data).before((Date) y);
        }
    }
}

