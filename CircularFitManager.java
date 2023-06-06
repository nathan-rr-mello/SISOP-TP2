import java.util.LinkedList;
import java.util.List;

public class CircularFitManager {

    int size;
    List<Partition> memory;
    int pointer;

    public CircularFitManager(int size) {
        this.size = size;
        this.memory = new LinkedList<>();
        this.pointer = 0;
    }

    public void In(Proccess proc) {

        if (memory.size() == 0) {
            Partition part = new Partition(0, proc.size, proc);
            memory.add(part);
            return;
        }

        int start = memory.get(pointer).end;
        for (int i = pointer + 1; i != pointer; i = (i + 1) % memory.size()) {
            if (i == memory.size() - 1) {
                if (size - start > proc.size) {
                    Partition part = new Partition(start, start + proc.size, proc);
                    memory.add(part);
                    return;
                }
            }

            Partition currentPartition = memory.get(i);
            if (currentPartition.start - start >= proc.size) {
                Partition part = new Partition(start, start + proc.size, proc);
                memory.add(i, part);
                return;
            }

            start = currentPartition.end;
            
        }

        System.out.println("[ERROR] - THERE IS NO SPACE LEFT ON DISK");
    }

    public void Out(String pid) {
        memory.removeIf(partition -> partition.proc.pid.equals(pid));
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
