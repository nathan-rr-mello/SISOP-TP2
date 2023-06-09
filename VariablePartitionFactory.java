public class VariablePartitionFactory implements PartitionFactory {
    
    public boolean Fit(int start, int end, Proccess proccess) {
        return end - start >= proccess.size;
    }

    public Partition Create(int start, Proccess proccess) {
        return new Partition(start, start + proccess.size - 1, proccess);
    }
}
