package data.structure.opearation;

import data.structure.SegmentTree;

public class SetOperation extends Operation {
    public SetOperation(SegmentTree segmentTree) {
        super(segmentTree);
    }

    @Override
    protected void process(int vertex, int currentLeft, int currentRight, int left, int right) {
        if (currentLeft == currentRight) {
            segmentTree.getVertices()[vertex].setValue(right);
            return;
        }
        int middle = (currentLeft + currentRight) / 2;
        if (left <= middle) {
            process(2 * vertex, currentLeft, middle, left, right);
        } else {
            process(2 * vertex + 1, middle + 1, currentRight, left, right);
        }
    }
}
