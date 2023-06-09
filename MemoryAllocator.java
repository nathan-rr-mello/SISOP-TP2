import java.util.LinkedList;
import java.util.List;

public abstract class MemoryAllocator {
    
    int size;
    List<Partition>memory;

    public MemoryAllocator(int size) {
        this.size = size;
        this.memory =  new LinkedList<>();
    }

    public abstract void In(Proccess proc);
    public abstract void Out(String pid);

    public void Print() {

        int start = 0;
        for (int i = 0; i < memory.size(); i++) {
            Partition part = memory.get(i);
            if (part.start - start > 0) {
                System.out.printf("[FREE] - START %d - END %d\n", start, part.start - 1);
            }
            System.out.printf("[%s] - START %d - END %d\n", part.proc.pid, part.start, part.end);
            start = part.end + 1;
        }

        if (start < size) {
            System.out.printf("[FREE] - START %d - END %d\n", start, this.size - 1);
        }
    }
}
