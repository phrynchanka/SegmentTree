import java.util.Scanner;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        /**
         * Cmd input object.
         */
        Scanner sc = new Scanner(System.in);

        /**
         * Enter n, q.
         */
        int n = sc.nextInt();
        int q = sc.nextInt();

        /**
         * Initialize segmentTreeData.
         */
        List<Integer> segmentTreeData = new ArrayList<>(n);
        for (int p = 0; p < n; ++p) {
            segmentTreeData.add(sc.nextInt());
        }

        /**
         * Initialize segmentTree instance.
         */
        SegmentTree st = new SegmentTree(segmentTreeData);
        int stSize = st.treeScale;

        /**
         * Execute main flow.
         */
        for (int b = 0; b < q; ++b) {
            int currentNextInt = sc.nextInt();
            /**
             * 1 - Perform set operation.
             * 2 - Perform increment operation.
             * 3 - Print even evaluateSum.
             * 4 - Print odd evaluateSum.
             */
            if (currentNextInt == 1) {
                st.executeSetOperation(1, 0, stSize - 1,
                        sc.nextInt() - 1, sc.nextInt());

            } else if (currentNextInt == 2) {
                st.executeIncrementOperation(1, 0, stSize - 1,
                        sc.nextInt() - 1, sc.nextInt() - 1);

            } else if (currentNextInt == 3) {
                System.out.println(st.processEvenSumValue(1, 0, stSize - 1,
                        sc.nextInt() - 1, sc.nextInt() - 1));

            } else if (currentNextInt == 4) {
                System.out.println(st.processOddSumValue(1, 0, stSize - 1,
                        sc.nextInt() - 1, sc.nextInt() - 1));
            }
        }
    }

    static class V {

        V(int vl) {
            putValueToVertex(vl);
        }

        V(V l, V r) {
            refreshVertex(l, r);
        }

        int oddCountValue;

        long evenSumValue;

        long addedValue;

        long oddSumValue;

        int evenCountValue;

        void putValueToVertex(int vl) {
            addedValue = 0;
            evenSumValue = oddSumValue = evenCountValue = oddCountValue = 0;
            if ((vl % 2) == 0) {
                evenSumValue = vl;
                evenCountValue = 1;
            } else {
                oddSumValue = vl;
                oddCountValue = 1;
            }
        }

        int lookEvenCount() {
            return makeCount(evenCountValue, oddCountValue);
        }

        void refreshVertex(V l, V r) {
            evenCountValue = l.lookEvenCount() + r.lookEvenCount();
            oddCountValue = l.lookOddCount() + r.lookOddCount();
            evenSumValue = l.lookEvenSum() + r.lookEvenSum();
            oddSumValue = l.lookOddSum() + r.lookOddSum();
        }

        private int makeCount(int frts, int scnd) {
            return isAlter() ? frts : scnd;
        }

        long lookOddSum() {
            return evaluateSum(oddCountValue, oddSumValue, evenCountValue, evenSumValue);
        }

        private long evaluateSum(int frstCt, long frstS, int scndCt, long scndS) {
            return evaluate(isAlter() ? frstCt : scndCt, isAlter() ? frstS : scndS);
        }

        int lookOddCount() {
            return makeCount(oddCountValue, evenCountValue);
        }

        private boolean isAlter() {
            return addedValue % 2 == 0;
        }

        private long evaluate(int count, long sum) {
            return addedValue * count + sum;
        }

        long lookEvenSum() {
            return evaluateSum(evenCountValue, evenSumValue, oddCountValue, oddSumValue);
        }
    }

    static class SegmentTree {
        V[] vrtcsArray;
        int treeScale;

        SegmentTree(List<Integer> segmentTreeData) {
            this.treeScale = segmentTreeData.size();
            this.vrtcsArray = new V[4 * treeScale];
            initializeSegmentTree(segmentTreeData, 1, 0, segmentTreeData.size() - 1);
        }

        void executeIncrementOperation(int v, int cL, int cR, int l, int r) {
            if (l > r)
                return;
            if (l == cL && r == cR) {
                vrtcsArray[v].addedValue += 1;
                return;
            }
            executeOperationFlow(v, cL, cR, new Consumer<Integer>() {
                @Override
                public void accept(Integer mdl) {
                    SegmentTree.this.executeIncrementOperation(v * 2, cL, mdl, l, Math.min(r, mdl));
                    SegmentTree.this.executeIncrementOperation(v * 2 + 1, mdl + 1, cR, Math.max(l, mdl + 1), r);
                }
            });
        }

        void executeSetOperation(int v, int cL, int cR, int pos, int vl) {
            if (cL == cR) {
                vrtcsArray[v].putValueToVertex(vl);
                return;
            }
            executeOperationFlow(v, cL, cR, new Consumer<Integer>() {
                @Override
                public void accept(Integer mdl) {
                    if (pos <= mdl) {
                        SegmentTree.this.executeSetOperation(2 * v, cL, mdl, pos, vl);
                    } else {
                        SegmentTree.this.executeSetOperation(2 * v + 1, mdl + 1, cR, pos, vl);
                    }
                }
            });
        }

        void executeOperationFlow(int v, int cL, int cR, Consumer<Integer> f) {
            updateVrtcsChildrenArray(v);
            f.accept((cL + cR) / 2);
            updateVrtcsArray(v);
        }


        void initializeSegmentTree(List<Integer> segmetTreeData, int v, int l, int r) {
            if (l == r)
                vrtcsArray[v] = new V(segmetTreeData.get(l));
            else {
                int mdl = (l + r) / 2;
                initializeSegmentTree(segmetTreeData, v * 2, l, mdl);
                initializeSegmentTree(segmetTreeData, v * 2 + 1, mdl + 1, r);
                vrtcsArray[v] = new V(vrtcsArray[v * 2], vrtcsArray[v * 2 + 1]);
            }
        }

        long processOddSumValue(int v, int cL, int cR, int l, int r) {
            if (l > r)
                return 0;
            if (l == cL && r == cR) {
                return vrtcsArray[v].lookOddSum();
            }
            int middle = (cL + cR) / 2;
            updateVrtcsChildrenArray(v);
            updateVrtcsArray(v);
            return processOddSumValue(v * 2, cL, middle, l, Math.min(r, middle)) +
                    processOddSumValue(v * 2 + 1, middle + 1, cR, Math.max(l, middle + 1), r);
        }

        void updateVrtcsArray(int v) {
            vrtcsArray[v].refreshVertex(vrtcsArray[2 * v], vrtcsArray[2 * v + 1]);
        }

        void updateVrtcsChildrenArray(int v) {
            vrtcsArray[2 * v].addedValue += vrtcsArray[v].addedValue;
            vrtcsArray[2 * v + 1].addedValue += vrtcsArray[v].addedValue;
            vrtcsArray[v].addedValue = 0;
        }

        long processEvenSumValue(int v, int cL, int cR, int l, int r) {
            if (l > r)
                return 0;
            if (l == cL && r == cR) {
                return vrtcsArray[v].lookEvenSum();
            }
            int mdl = (cL + cR) / 2;
            executeOperationFlow(v, cL, cR, new Consumer<Integer>() {
                @Override
                public void accept(Integer none) {
                }
            });
            return processEvenSumValue(v * 2, cL, mdl, l, Integer.min(r, mdl)) +
                    processEvenSumValue(v * 2 + 1, mdl + 1, cR, Integer.max(l, mdl + 1), r);
        }
    }
}