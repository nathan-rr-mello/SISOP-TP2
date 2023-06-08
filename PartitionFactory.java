public interface PartitionFactory {
    boolean Fit(int start, int end, Proccess proccess);
    Partition Create(int start, Proccess proccess);
}
