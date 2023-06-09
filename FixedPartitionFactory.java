public class FixedPartitionFactory implements PartitionFactory {

    int size;

    public FixedPartitionFactory(int size) {
        this.size = size;
    }

    public boolean Fit(int start, int end, Proccess proc) {
        return end - start >= this.size;
    }

    public Partition Create(int start, Proccess proc) {
        return new Partition(start, start + this.size - 1, proc);
    }
}