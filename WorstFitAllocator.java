public class WorstFitAllocator extends MemoryAllocator {
    
    public WorstFitAllocator(int size) {
        super(size);
    }

    public void In(Proccess proc) {

        if (super.memory.size() == 0) {
            Partition part = new Partition(0, proc.size - 1, proc);
            super.memory.add(part);
            return;
        }

        int worstStart = 0;
        int currentWorst = 0;
        int indexToSwitch = -1;
        int start = 0;
        for (int i = 0; i < super.memory.size(); i++) {
            Partition currPartition = super.memory.get(i);
            int currentFreeSpace = currPartition.start - start;
            if (currentFreeSpace > currentWorst) {
                indexToSwitch = i;
                currentWorst = currentFreeSpace;
                worstStart = start;
            }
            start = currPartition.end + 1;
        }

        if (super.size - start > currentWorst) {
            Partition part = new Partition(start, start + proc.size - 1, proc);
            super.memory.add(part);
            return;
        }

        if (indexToSwitch != -1) {
            Partition part = new Partition(worstStart, worstStart + proc.size - 1, proc);
            super.memory.add(indexToSwitch, part);
        } else {
            System.out.println("[ERROR] - INSUFFICIENT MEMORY SPACE");
        }
    } 

    public void Out(String pid) {
        super.memory.removeIf(x -> x.proc.pid.equals(pid));
    }
}
