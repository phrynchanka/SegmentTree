package data.structure;

import java.util.List;

public class SegmentTree {
    private int size;
    private V[] vertices;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public V[] getVertices() {
        return vertices;
    }

    public void setVertices(V[] vertices) {
        this.vertices = vertices;
    }

    public SegmentTree(List<Integer> data) {
        this.size = data.size();
        this.vertices = new V[4 * size];
        initializeTree(data, 1, 0, data.size() - 1);
    }

    private void initializeTree(List<Integer> data, int vertex, int left, int right) {
        if (left == right)
            vertices[vertex] = new V(data.get(left));
        else {
            int middle = (left + right) / 2;
            initializeTree(data, vertex * 2, left, middle);
            initializeTree(data, vertex * 2 + 1, middle + 1, right);
            vertices[vertex] = new V(vertices[vertex * 2], vertices[vertex * 2 + 1]);
        }
    }

    public long getOddSum(int vertex, int currentLeft, int currentRight, int left, int right) {
        if (left > right)
            return 0;
        if (left == currentLeft && right == currentRight) {
            return vertices[vertex].getOddSum();
        }
        int middle = (currentLeft + currentRight) / 2;
        updateChildren(vertex);
        update(vertex);
        return getOddSum(vertex * 2, currentLeft, middle, left, Math.min(right, middle)) +
                getOddSum(vertex * 2 + 1, middle + 1, currentRight, Math.max(left, middle + 1), right);
    }

    public long getEvenSum(int vertex, int currentLeft, int currentRight, int left, int right) {
        if (left > right)
            return 0;
        if (left == currentLeft && right == currentRight) {
            return vertices[vertex].getEvenSum();
        }
        int middle = (currentLeft + currentRight) / 2;

        updateChildren(vertex);
        update(vertex);

        return getEvenSum(vertex * 2, currentLeft, middle, left, Integer.min(right, middle)) +
                getEvenSum(vertex * 2 + 1, middle + 1, currentRight, Integer.max(left, middle + 1), right);
    }

    public void update(int vertex) {
        vertices[vertex].update(vertices[2 * vertex], vertices[2 * vertex + 1]);
    }

    public void updateChildren(int vertex) {
        vertices[2 * vertex].added += vertices[vertex].added;
        vertices[2 * vertex + 1].added += vertices[vertex].added;
        vertices[vertex].added = 0;
    }
}