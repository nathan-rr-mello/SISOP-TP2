public class VariablePartitionManager implements MemoryManager {
    
    MemoryAllocator allocator;

    public VariablePartitionManager(MemoryAllocator allocator) {
        this.allocator = allocator;
    }
    
    public void In(Proccess proc) {
        allocator.In(proc);
    }

    public void Out(String pid) {
        allocator.Out(pid);
    }

    public void Print() {
        allocator.Print();
    }
}
