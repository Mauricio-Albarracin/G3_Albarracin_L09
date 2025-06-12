package ListLinked;

public class ListLinked<T> {
    private Node<T> head;

    public ListLinked() {
        this.head = null;
    }

    // Inserta un elemento al final de la lista
    public void insertLast(T data) {
        Node<T> newNode = new Node<>(data, null);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    // Busca el índice del dato (devuelve -1 si no lo encuentra)
    public int search(T data) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.getData().equals(data)) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1;
    }

    // Devuelve el elemento en la posición indicada
    public T get(int index) {
        Node<T> current = head;
        int count = 0;
        while (current != null) {
            if (count == index) {
                return current.getData();
            }
            current = current.getNext();
            count++;
        }
        return null; // o lanzar una excepción
    }

    // Devuelve true si la lista contiene el dato
    public boolean contains(T data) {
        return search(data) != -1;
    }

    // Devuelve la longitud (cantidad de elementos) de la lista
    public int length() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    // Elimina la primera aparición del dato en la lista
    public void remove(T data) {
        if (head == null) return;

        if (head.getData().equals(data)) {
            head = head.getNext();
            return;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(data)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }

    // Devuelve la representación en String de la lista
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(" -> ");
            }
            current = current.getNext();
        }
        return sb.toString();
    }
}
