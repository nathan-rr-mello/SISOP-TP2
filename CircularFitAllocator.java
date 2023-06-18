public class CircularFitAllocator extends MemoryAllocator {

    private int pointer;

    public CircularFitAllocator(int size) {
        super(size);
        this.pointer = 0;
    }

    public void In(Proccess proc) {

        if (memory.size() == 0) {
            Partition part = new Partition(0, proc.size - 1, proc);
            memory.add(part);
            pointer = 0;
            return;
        }

        int i = pointer;
        do {

            if (i == memory.size() - 1) {
                Partition curr = memory.get(i);
                if (super.size - memory.get(i).end >= proc.size) {
                    Partition part = new Partition(curr.end + 1, curr.end + proc.size, proc);
                    memory.add(part);
                    pointer = i + 1;
                    return;
                } else if (memory.get(0).start >= proc.size) {
                    Partition part = new Partition(0, proc.size - 1, proc);
                    memory.add(0, part);
                    pointer = 0;
                    return;
                }
            } else if (memory.get(i+1).start - memory.get(i).end >= proc.size) {
                int start = memory.get(i).end + 1;
                Partition part = new Partition(start, start + proc.size - 1, proc);
                memory.add(i+1, part);
                pointer = i+1;
                return;
            }

            i = (i+1) % memory.size();
        }while(i != pointer);

        System.out.println("[ERROR] - INSUFFICIENT MEMORY SPACE");
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
