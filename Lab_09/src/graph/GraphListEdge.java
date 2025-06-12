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
}
