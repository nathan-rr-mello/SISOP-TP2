public class CircularFitManager extends MemoryManager {

    int pointer;

    public CircularFitManager(int size, PartitionFactory partFactory) {
        super(size, partFactory);
        this.pointer = 0;
    }

    public void In(Proccess proc) {

        if (memory.size() == 0) {
            Partition part = partFactory.Create(0, proc);
            memory.add(part);
            pointer = 0;
            return;
        }

        int i = pointer;
        do {

            if (i == memory.size() - 1) {
                Partition curr = memory.get(i);
                if (partFactory.Fit(memory.get(i).end, super.size, proc)) {
                    Partition part = partFactory.Create(curr.end + 1, proc);
                    memory.add(part);
                    pointer = i + 1;
                    return;
                } else if (partFactory.Fit(0, memory.get(0).start, proc)) {
                    Partition part = partFactory.Create(0, proc);
                    memory.add(0, part);
                    pointer = 0;
                    return;
                }
            } else if (partFactory.Fit(memory.get(i).end, memory.get(i+1).start, proc)) {
                int start = memory.get(i).end + 1;
                Partition part = partFactory.Create(start, proc);
                memory.add(i+1, part);
                pointer = i+1;
                return;
            }

            i = (i+1) % memory.size();
        }while(i != pointer);

        System.out.println("[ERROR] - THERE IS NO SPACE LEFT ON DISK");
    }

    public void Out(String pid) {

        for (int i = 0; i < memory.size(); i++) {
            Partition part = memory.get(i);
            if (part.proc.pid.equals(pid)) {
                memory.remove(i);
                if (i <= pointer) {
                    pointer--;
                }
                return;
            }
        }
    }
}
