package ListLinked;

public class Node<T> {
    private T data; //T es el dato del nodo
    private Node<T> next; //Es la referencia de la posición del siguiente nodo

    //Constructor del nodo que inicializa el dato y la referencia de posición del siguiente nodo
    public Node (T data, Node<T> next){
        this.data = data;
        this.next = next;
    }

    //Setters y getters del dato del nodo para modificar su valor en el main
    public void setData(T data){
        this.data = data;
    }
    
    public T getData(){
        return data;
    }

    //Setters y getters de la referencia de posición del siguiente nodo para modificarlo en el main
    public void setNext(Node<T> next){
        this.next = next;
    }
    
    public Node<T> getNext(){
        return next;
    }
}
