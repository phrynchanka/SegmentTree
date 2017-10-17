package data.structure.opearation;

import data.structure.SegmentTree;

public abstract class Operation {

    protected SegmentTree segmentTree;

    public Operation(SegmentTree segmentTree) {
        this.segmentTree = segmentTree;
    }

    public void execute(int vertex, int currentLeft, int currentRight, int left, int right) {
        segmentTree.updateChildren(vertex);
        process(vertex, currentLeft, currentRight, left, right);
        segmentTree.update(vertex);
    }

    protected abstract void process(int vertex, int currentLeft, int currentRight, int left, int right);
}
