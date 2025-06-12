package graph;

import java.util.ArrayList;
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

    // a) Elimina el vértice ‘v’ en caso exista. 
    // Asegúrese de no dejar aristas de entrada y/o salida del vértice.
    public void removeVertex(E v) {
        Vertex<E> target = new Vertex<>(v);
        int pos = listVertex.search(target);

        if (pos == -1) return; // Si no existe, no se hace nada

        // Eliminar aristas entrantes desde otros vértices
        for (int i = 0; i < listVertex.length(); i++) {
            Vertex<E> current = listVertex.get(i);
            if (current != null && !current.equals(target)) {
                current.listAdj.remove(new Edge<>(target));
            }
        }

        // Eliminar el vértice (sus aristas salientes también se eliminan)
        listVertex.remove(target);
    }

    // b) Elimina la arista que une a los vértices ‘v’ y ‘z’ en caso exista.
    public void removeEdge(E v, E z) {
        int posOri = listVertex.search(new Vertex<>(v));
        int posDes = listVertex.search(new Vertex<>(z));

        if (posOri == -1 || posDes == -1) return; // Uno o ambos vértices no existen

        Vertex<E> vOri = listVertex.get(posOri);
        Vertex<E> vDes = listVertex.get(posDes);

        vOri.listAdj.remove(new Edge<>(vDes)); // Elimina la arista de v hacia z
    }

    // c) Realiza el recorrido en profundidad a partir de v del grafo y muestra los vértices que se vayan visitando.
    public void dfs(E v) {
        int pos = listVertex.search(new Vertex<>(v));
        if (pos == -1) return; // Si el vértice no existe, no se hace nada

        boolean[] visitado = new boolean[listVertex.length()];
        dfsRecursive(pos, visitado);
    }

    // Método recursivo auxiliar para DFS
    private void dfsRecursive(int pos, boolean[] visitado) {
        visitado[pos] = true;
        Vertex<E> vertex = listVertex.get(pos);
        System.out.println(vertex.getData()); // Muestra el vértice visitado

        for (int i = 0; i < vertex.listAdj.length(); i++) {
            Edge<E> edge = vertex.listAdj.get(i);
            int nextPos = listVertex.search(edge.getRefDest());
            if (nextPos != -1 && !visitado[nextPos]) {
                dfsRecursive(nextPos, visitado);
            }
        }
    }

    // Elercicio 1
    // a) Realiza el recorrido en anchura (BFS) a partir de v y muestra los vértices visitados
    public void bfs(E v) {
        int pos = listVertex.search(new Vertex<>(v));
        if (pos == -1) return; // Si el vértice no existe, no se hace nada

        boolean[] visitado = new boolean[listVertex.length()];
        ListLinked<Integer> queue = new ListLinked<>(); // Cola de posiciones (índices de vértices)

        visitado[pos] = true;
        queue.insertLast(pos); // Encolar vértice inicial

        while (queue.length() > 0) {
            // Desencolar
            int currentPos = queue.get(0);
            queue.remove(currentPos);

            Vertex<E> currentVertex = listVertex.get(currentPos);
            System.out.println(currentVertex.getData()); // Mostrar vértice

            // Recorrer los adyacentes
            for (int i = 0; i < currentVertex.listAdj.length(); i++) {
                Edge<E> edge = currentVertex.listAdj.get(i);
                int nextPos = listVertex.search(edge.getRefDest());
                if (nextPos != -1 && !visitado[nextPos]) {
                    visitado[nextPos] = true;
                    queue.insertLast(nextPos); // Encolar adyacente no visitado
                }
            }
        }
    }

    //Ejercicio 1
    /* b) bfsPath(v, z): que es la especialización del método bfs() que devuelva el camino (secuencia de vértices) 
    entre el vértice ‘v’ y ‘z’ en caso exista en un ArrayList. */ 
    
    public ArrayList<E> bfsPath(E v, E z) {
    ArrayList<E> path = new ArrayList<>();
    int inicio = listVertex.search(new Vertex<>(v));
    int meta = listVertex.search(new Vertex<>(z));

    if (inicio == -1 || meta == -1) return path; // Si alguno no existe, retorno vacío

    boolean[] visitado = new boolean[listVertex.length()];
    int[] padre = new int[listVertex.length()];

    // Inicializa los padres en -1
    for (int i = 0; i < padre.length; i++) padre[i] = -1;

    // Simulación de una cola usando ListLinked de enteros (posiciones)
    ListLinked<Integer> queue = new ListLinked<>();
    queue.insertLast(inicio);
    visitado[inicio] = true;

    while (queue.length() > 0) {
        int current = queue.get(0);
        queue.remove(current); // Elimina de la cabeza (simula dequeue)

        Vertex<E> vCurrent = listVertex.get(current);

        for (int i = 0; i < vCurrent.listAdj.length(); i++) {
            Edge<E> edge = vCurrent.listAdj.get(i);
            int neighbor = listVertex.search(edge.getRefDest());

            if (!visitado[neighbor]) {
                visitado[neighbor] = true;
                padre[neighbor] = current;
                queue.insertLast(neighbor);

                if (neighbor == meta) break; // Se encontró el destino
            }
        }
    }

    // Si no hay camino al destino
    if (!visitado[meta]) return path;

    // Reconstruir el camino desde z a v
    ArrayList<E> reversePath = new ArrayList<>();
    for (int at = meta; at != -1; at = padre[at]) {
        reversePath.add(0, listVertex.get(at).getData());
    }
    return reversePath;
    }

}
