package graph;

// Importa la clase de lista enlazada genérica
import ListLinked.*;

public class Vertex<E> {
    // Dato genérico almacenado en el vértice (por ejemplo: nombre, ID, etc.)
    private E data;

    // Lista de adyacencia: contiene todas las aristas (edges) salientes desde este vértice
    protected ListLinked<Edge<E>> listAdj;

    // Constructor: crea un vértice con el dato dado y una lista vacía de adyacencias
    public Vertex(E data) {
        this.data = data;
        listAdj = new ListLinked<Edge<E>>();
    }

    // Método para obtener el dato almacenado en el vértice
    public E getData() {
        return data;
    }

    /**
     * Compara dos vértices por su dato (no por su lista de adyacencia).
     * Devuelve true si el dato contenido en ambos vértices es igual.
     */
    public boolean equals(Object o) {
        if (o instanceof Vertex<?>) {
            Vertex<E> v = (Vertex<E>) o;
            return this.data.equals(v.data);
        }
        return false;
    }

    /**
     * Devuelve una representación en texto del vértice.
     * Muestra el dato seguido de todas las aristas salientes.
     * Ejemplo: A-->B, C, D,
     */
    public String toString() {
        return this.data + "-->" + this.listAdj.toString() + "\n";
    }
}
