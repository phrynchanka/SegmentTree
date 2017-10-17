package data.structure;

public class V {

    public int oddCount;

    public long evenSum;

    public long added;

    public long oddSum;

    public int evenCount;

    public V(int value) {
        setValue(value);
    }

    public V(V left, V right) {
        update(left, right);
    }

    public void update(V left, V right) {
        evenCount = left.getEvenCount() + right.getEvenCount();
        oddCount = left.getOddCount() + right.getOddCount();
        evenSum = left.getEvenSum() + right.getEvenSum();
        oddSum = left.getOddSum() + right.getOddSum();
    }

    public void setValue(int value) {
        added = 0;
        evenSum = oddSum = evenCount = oddCount = 0;
        if ((value % 2) == 0) {
            evenSum = value;
            evenCount = 1;
        } else {
            oddSum = value;
            oddCount = 1;
        }
    }

    public long getOddSum() {
        return sum(oddCount, oddSum, evenCount, evenSum);
    }

    public long getEvenSum() {
        return sum(evenCount, evenSum, oddCount, oddSum);
    }

    public int getOddCount() {
        return count(oddCount, evenCount);
    }

    public int getEvenCount() {
        return count(evenCount, oddCount);
    }

    private long sum(int firstCount, long firstSum, int secondCount, long secondSum) {
        return calculate(changed() ? firstCount : secondCount, changed() ? firstSum : secondSum);
    }

    private int count(int first, int second) {
        return changed() ? first : second;
    }

    private long calculate(int count, long sum) {
        return added * count + sum;
    }

    private boolean changed() {
        return added % 2 == 0;
    }
}