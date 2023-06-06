import java.util.LinkedList;
import java.util.List;

public class MemoryManager {
    
    List<Partition> memory;
    int size;

    public MemoryManager(int size) {
        memory = new LinkedList<>();
        this.size = size;
    }

    //WORST FIT IMPLEMENTATION
    public void In(Proccess proc) {

        if (memory.size() == 0) {
            Partition part = new Partition(0, proc.size, proc);
            memory.add(part);
            return;
        }

        int worstStart = 0;
        int currentWorst = 0;
        int indexToSwitch = -1;
        int start = 0;
        for (int i = 0; i < memory.size(); i++) {
            Partition currPartition = memory.get(i);
            int currentFreeSpace = currPartition.start - start;
            if (currentFreeSpace > currentWorst) {
                indexToSwitch = i;
                currentWorst = currentFreeSpace;
                worstStart = start;
            }
            start = currPartition.end;
        }

        if (this.size - start > currentWorst) {
            Partition part = new Partition(start, start + proc.size, proc);
            memory.add(part);
            return;
        }

        if (indexToSwitch != -1) {
            Partition part = new Partition(worstStart, worstStart + proc.size, proc);
            memory.add(indexToSwitch, part);
        } else {
            System.out.println("[ERROR] - THERE IS NO SPACE LEFT ON DISK");
        }
    } 

    public void Out(String pid) {
        memory.removeIf(x -> x.proc.pid.equals(pid));
    }

    public void Print() {

        int start = 0;
        for (int i = 0; i < memory.size(); i++) {
            Partition part = memory.get(i);
            if (part.start - start > 0) {
                System.out.printf("[FREE] - START %d - END %d\n", start, part.start);
            }
            System.out.printf("[%s] - START %d - END %d\n", part.proc.pid, part.start, part.end);
            start = part.end;
        }

        if (start < size) {
            System.out.printf("[FREE] - START %d - END %d\n", start, this.size);
        }
    }
}
