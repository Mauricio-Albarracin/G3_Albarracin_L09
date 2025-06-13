package graph;

import ListLinked.ListLinked;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class GraphListEdge<V, E> {
    ListLinked<VertexObj<V, E>> secVertex;
    ListLinked<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ListLinked<>();
        this.secEdge = new ListLinked<>();
    }

    // a) Insertar vértice si no existe
    public void insertVertex(V v) {
        if (!searchVertex(v)) {
            secVertex.insertLast(new VertexObj<>(v, secVertex.length()));
        }
    }

    // b) Insertar arista si no existe
    public void insertEdge(V v, V z) {
        if (!searchEdge(v, z)) {
            VertexObj<V, E> vertex1 = getVertex(v);
            VertexObj<V, E> vertex2 = getVertex(z);

            if (vertex1 == null) {
                vertex1 = new VertexObj<>(v, secVertex.length());
                secVertex.insertLast(vertex1);
            }

            if (vertex2 == null) {
                vertex2 = new VertexObj<>(z, secVertex.length());
                secVertex.insertLast(vertex2);
            }

            EdgeObj<V, E> edge = new EdgeObj<>(vertex1, vertex2, null, secEdge.length());
            secEdge.insertLast(edge);
        }
    }

    // c) Buscar vértice
    public boolean searchVertex(V v) {
        return getVertex(v) != null;
    }

    // d) Buscar arista
    public boolean searchEdge(V v, V z) {
        for (int i = 0; i < secEdge.length(); i++) {
            EdgeObj<V, E> edge = secEdge.get(i);
            V v1 = edge.getEndVertex1().getInfo();
            V v2 = edge.getEndVertex2().getInfo();
            if ((v1.equals(v) && v2.equals(z)) || (v1.equals(z) && v2.equals(v))) {
                return true;
            }
        }
        return false;
    }

    // e) Recorrido BFS
    public void bfs(V v) {
        VertexObj<V, E> start = getVertex(v);
        if (start == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        Set<VertexObj<V, E>> visited = new HashSet<>();
        Queue<VertexObj<V, E>> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        System.out.println("Recorrido BFS desde: " + v);
        while (!queue.isEmpty()) {
            VertexObj<V, E> current = queue.poll();
            System.out.print(current.getInfo() + " ");

            for (int i = 0; i < secEdge.length(); i++) {
                EdgeObj<V, E> edge = secEdge.get(i);
                VertexObj<V, E> neighbor = null;

                if (edge.getEndVertex1().equals(current)) {
                    neighbor = edge.getEndVertex2();
                } else if (edge.getEndVertex2().equals(current)) {
                    neighbor = edge.getEndVertex1();
                }

                if (neighbor != null && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    // Helper: obtener vértice por valor
    private VertexObj<V, E> getVertex(V v) {
        for (int i = 0; i < secVertex.length(); i++) {
            VertexObj<V, E> vertex = secVertex.get(i);
            if (vertex.getInfo().equals(v)) {
                return vertex;
            }
        }
        return null;
    }
   // Devuelve un arreglo con el grado de entrada y salida de un nodo en un grafo dirigido
    public int[] gradoNodoDirigido(V data) {
        VertexObj<V, E> v = getVertex(data); // Busca el vértice correspondiente al valor
        if (v == null) return new int[]{-1, -1}; // Si no existe, retorna grados inválidos

        int entrada = 0;
        int salida = 0;

        for (int i = 0; i < secEdge.length(); i++) {
            EdgeObj<V, E> edge = secEdge.get(i);
            // Si el vértice actual es el origen de la arista, cuenta como salida
            if (edge.getEndVertex1().equals(v)) salida++;
            // Si el vértice actual es el destino de la arista, cuenta como entrada
            if (edge.getEndVertex2().equals(v)) entrada++;
        }

        return new int[]{entrada, salida}; // Retorna el par (entrada, salida)
    }

    // Verifica si el grafo dirigido es un camino (un único inicio y un único fin)
    public boolean esCaminoDirigido() {
        int inicio = 0, fin = 0;

        for (int i = 0; i < secVertex.length(); i++) {
            VertexObj<V, E> v = secVertex.get(i);
            int[] grado = gradoNodoDirigido(v.getInfo());

            // Nodo con salida 1 y sin entrada → inicio
            if (grado[0] == 0 && grado[1] == 1) inicio++;
            // Nodo con entrada 1 y sin salida → fin
            else if (grado[0] == 1 && grado[1] == 0) fin++;
            // Nodo intermedio: entrada y salida deben ser 1
            else if (grado[0] != 1 || grado[1] != 1) return false;
        }

        // Debe haber exactamente un inicio y un fin
        return inicio == 1 && fin == 1;
    }

    // Verifica si el grafo dirigido es un ciclo (todos los nodos tienen una entrada y una salida)
    public boolean esCicloDirigido() {
        for (int i = 0; i < secVertex.length(); i++) {
            VertexObj<V, E> v = secVertex.get(i);
            int[] grado = gradoNodoDirigido(v.getInfo());

            // Cada nodo debe tener exactamente una entrada y una salida
            if (grado[0] != 1 || grado[1] != 1) return false;
        }
        return true;
    }

    // Verifica si el grafo dirigido es una rueda
    // Una rueda dirigida tiene un centro que apunta a todos los demás, y el resto forman un ciclo entre ellos
    public boolean esRuedaDirigida() {
        int centroIndex = -1;
        int n = secVertex.length();

        // Buscar el nodo "centro", con grado de salida n-1 y entrada 0
        for (int i = 0; i < n; i++) {
            VertexObj<V, E> v = secVertex.get(i);
            int[] grado = gradoNodoDirigido(v.getInfo());

            if (grado[0] == 0 && grado[1] == n - 1) {
                if (centroIndex != -1) return false; // Si ya existe un centro, no puede haber otro
                centroIndex = i;
            }
        }

        if (centroIndex == -1) return false; // No se encontró un nodo centro

        // Los demás nodos deben tener al menos una entrada y una salida (conectados en ciclo y al centro)
        for (int i = 0; i < n; i++) {
            if (i == centroIndex) continue; // Omitir el centro

            VertexObj<V, E> v = secVertex.get(i);
            int[] grado = gradoNodoDirigido(v.getInfo());

            // Deben tener al menos una entrada y una salida
            if (grado[0] < 1 || grado[1] < 1) return false;
        }

        return true;
    }

    // Verifica si el grafo dirigido es completo (cada nodo tiene aristas hacia todos los demás y recibe de todos)
    public boolean esCompletoDirigido() {
        int n = secVertex.length();

        for (int i = 0; i < n; i++) {
            VertexObj<V, E> v = secVertex.get(i);
            int[] grado = gradoNodoDirigido(v.getInfo());

            // En un grafo dirigido completo, cada nodo tiene entrada y salida con todos los demás: n - 1
            if (grado[0] != n - 1 || grado[1] != n - 1) return false;
        }

        return true;
    }


}
