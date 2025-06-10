package graph;
import ListLinked.*;

public class GraphLink<E> {
    // Lista de vértices del grafo
    protected ListLinked<Vertex<E>> listVertex;

    // Constructor: inicializa la lista de vértices vacía
    public GraphLink() {
        listVertex = new ListLinked<Vertex<E>>();
    }

    // Inserta un nuevo vértice al grafo si no existe aún.
    public void insertVertex(E data) {
        Vertex<E> v = new Vertex<>(data);  // Crear vértice con el dato dado
        if (listVertex.search(v) == -1) {  // Verifica si ya existe en la lista
            listVertex.insertLast(v);     // Si no existe, lo inserta al final
        }
    }

    // Inserta una arista dirigida del vértice verOri al vértice verDes.
    public void insertEdge(E verOri, E verDes) {
        // Crear vértices temporales para búsqueda
        Vertex<E> ori = new Vertex<>(verOri);
        Vertex<E> des = new Vertex<>(verDes);

        // Buscar sus posiciones en la lista de vértices
        int posOri = listVertex.search(ori);
        int posDes = listVertex.search(des);

        // Insertar los vértices si no existen
        if (posOri == -1) {
            insertVertex(verOri);
            posOri = listVertex.search(ori); // Actualizar posición
        }
        if (posDes == -1) {
            insertVertex(verDes);
            posDes = listVertex.search(des); // Actualizar posición
        }

        // Obtener las referencias reales de los vértices desde la lista
        Vertex<E> vOri = listVertex.get(posOri);
        Vertex<E> vDes = listVertex.get(posDes);

        // Crear la arista desde vOri hacia vDes
        Edge<E> edge = new Edge<>(vDes);

        // Verificar si la arista ya existe en la lista de adyacencia
        if (vOri.listAdj.search(edge) == -1) {
            vOri.listAdj.insertLast(edge);  // Insertar arista si no está duplicada
        }
    }

    // a) Buscar un vértice: retorna true si existe
    public boolean searchVertex(E v) {
        Vertex<E> vertex = new Vertex<>(v);
        return listVertex.search(vertex) != -1;
    }

    // b) Buscar una arista: retorna true si existe una arista de v hacia z
    public boolean searchEdge(E v, E z) {
        Vertex<E> ori = new Vertex<>(v);
        Vertex<E> des = new Vertex<>(z);

        int posOri = listVertex.search(ori);
        int posDes = listVertex.search(des);

        if (posOri == -1 || posDes == -1) {
            return false; // Uno o ambos vértices no existen
        }

        Vertex<E> vOri = listVertex.get(posOri);
        Vertex<E> vDes = listVertex.get(posDes);

        Edge<E> edge = new Edge<>(vDes);
        return vOri.listAdj.search(edge) != -1;
    }

    // Representación en cadena del grafo
    public String toString() {
        return this.listVertex.toString();
    }
}
