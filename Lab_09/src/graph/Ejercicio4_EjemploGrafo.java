package graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

public class Ejercicio4_EjemploGrafo {
     public static void main(String[] args) {
        // Crear un grafo dirigido
        Graph<String, DefaultEdge> grafo = new SimpleDirectedGraph<>(DefaultEdge.class);

        // Agregar vértices
        grafo.addVertex("Lima");
        grafo.addVertex("Arequipa");
        grafo.addVertex("Cusco");

        // Agregar aristas
        grafo.addEdge("Lima", "Arequipa");
        grafo.addEdge("Arequipa", "Cusco");
        grafo.addEdge("Lima", "Cusco");

        // Imprimir el grafo
        System.out.println("Vertices: " + grafo.vertexSet());
        System.out.println("Aristas: " + grafo.edgeSet());

        // Usar algoritmo de camino más corto (Dijkstra)
        var camino = DijkstraShortestPath.findPathBetween(grafo, "Lima", "Cusco");
        System.out.println("Camino más corto de Lima a Cusco: " + camino);
    }
}
