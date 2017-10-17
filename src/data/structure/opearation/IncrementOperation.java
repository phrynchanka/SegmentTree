package data.structure.opearation;

import data.structure.SegmentTree;

public class IncrementOperation extends Operation {
    public IncrementOperation(SegmentTree segmentTree) {
        super(segmentTree);
    }

    @Override
    protected void process(int vertex, int currentLeft, int currentRight, int left, int right) {
        if (left > right)
            return;
        if (left == currentLeft && right == currentRight) {
            segmentTree.getVertices()[vertex].added += 1;
            return;
        }
        int middle = (currentLeft + currentRight) / 2;
        process(vertex * 2, currentLeft, middle, left, Math.min(right, middle));
        process(vertex * 2 + 1, middle + 1, currentRight, Math.max(left, middle + 1), right);
    }
}
