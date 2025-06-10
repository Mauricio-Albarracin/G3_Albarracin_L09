package graph;

public class Edge<E> {
    // Referencia al vértice destino de la arista
    private Vertex<E> refDest;

    // Peso de la arista (si no se usa, se pone -1 por defecto)
    private int weight;

    // Constructor para arista no ponderada (peso -1)
    public Edge(Vertex<E> refDest) {
        this(refDest, -1); // Llama al constructor completo con peso -1
    }

    // Constructor completo que recibe vértice destino y peso
    public Edge(Vertex<E> refDest, int weight) {
        this.refDest = refDest;
        this.weight = weight;
    }

    /**
     * Compara si dos aristas son iguales, es decir,
     * si tienen el mismo vértice destino (sin importar el peso).
     */
    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<E> e = (Edge<E>) o;
            return this.refDest.equals(e.refDest); // Compara solo el vértice destino
        }
        return false; // No son del mismo tipo
    }

    /**
     * Retorna una representación en texto de la arista.
     * Si tiene peso válido, lo muestra. Si no, solo el destino.
     */
    public String toString() {
        if (this.weight > -1)
            return refDest.getData() + " [" + this.weight + "], ";
        else
            return refDest.getData() + ", ";
    }
}
