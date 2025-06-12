package graph;

public class VertexObj<V,E>{
    protected V info;
    protected int position;

    public VertexObj(V info, int position){
        this.info = info;
        this.position = position;
    }
    // otros métodos
    public V getInfo() {
        return info;
    }
}
