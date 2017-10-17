import data.structure.SegmentTree;
import data.structure.opearation.IncrementOperation;
import data.structure.opearation.SetOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(),
                q = scanner.nextInt();
        List<Integer> data = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            data.add(scanner.nextInt());
        }

        SegmentTree segmentTree = new SegmentTree(data);
        IncrementOperation incrementOperation = new IncrementOperation(segmentTree);
        SetOperation setOperation = new SetOperation(segmentTree);

        int segmentTreeSize = segmentTree.getSize();

        for (int i = 0; i < q; ++i) {
            switch (scanner.nextInt()) {
                case 1:
                    setOperation.execute(1, 0, segmentTreeSize - 1, scanner.nextInt() - 1, scanner.nextInt());
                    break;
                case 2:
                    incrementOperation.execute(1, 0, segmentTreeSize - 1, scanner.nextInt() - 1, scanner.nextInt() - 1);
                    break;
                case 3:
                    System.out.println(segmentTree.getEvenSum(1, 0, segmentTreeSize - 1, scanner.nextInt() - 1, scanner.nextInt() - 1));
                    break;
                case 4:
                    System.out.println(segmentTree.getOddSum(1, 0, segmentTreeSize - 1, scanner.nextInt() - 1, scanner.nextInt() - 1));
                    break;
            }
        }
    }
}