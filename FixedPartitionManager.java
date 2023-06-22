import java.util.ArrayList;
import java.util.List;

public class FixedPartitionManager implements MemoryManager {

    private class Segment {

        int start;
        int end;
        Proccess process;
        Segment left;
        Segment right;

        public Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int size() {
            return end - start + 1;
        }

        public boolean isEmpty() {
            return process == null;
        }

        public boolean contains(String pid) {
            return process.pid.equals(pid);
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }
    
    private int size;
    private Segment root;

    public FixedPartitionManager(int size) {
        this.size = size;
        root = new Segment(0, size - 1);
    }

    public void In(Proccess proc) {

       if (!insert(root, proc)) {
            System.out.println("[ERROR] - INSUFFICIENT MEMORY SPACE");
       };
    }

    private boolean insert(Segment node, Proccess proc) {

        if (node.left == null && node.right == null) {
            if (node.size() < proc.size || !node.isEmpty()) {
                //System.out.println("ocupado");
                return false;
            }
            if (node.size() < proc.size * 2) {
                if (!node.isEmpty()) return false;
                node.process = proc;
                return true;
            } else {
                int middle = (node.start + node.end) / 2;
                node.left = new Segment(node.start, middle);
                node.right = new Segment(middle + 1, node.end);
                return insert(node.left, proc) || insert(node.right, proc);
            }
        } else if (node.size() >= proc.size * 2) {
            return insert(node.left, proc) || insert(node.right, proc);
        }
        return false;
    }

    public void Out(String pid) {
  
        remove(root, pid);
    }

    private boolean remove(Segment node, String pid) {
        
        if (!node.isLeaf()) {
            if (remove(node.left, pid)) {
                if (node.right.isLeaf() && node.right.isEmpty()) {
                    node.left = null;
                    node.right = null;
                    return true;
                }
                return false;
            };
            if (remove(node.right, pid)) {
                if (node.left.isLeaf() && node.left.isEmpty()) {
                    node.left = null;
                    node.right = null;
                    return true;
                }
                return false;
            };
        } else if (!node.isEmpty() && node.contains(pid)) {
            node.process = null;
            return true;
        }
        return false;
    }

    public void Print() {
        
        List<Segment> allocatedNodes = new ArrayList<>();
        retrieveAllocatedNodes(root, allocatedNodes);

        int start = 0;
        for (int i = 0; i < allocatedNodes.size(); i++) {
            Segment seg = allocatedNodes.get(i);
            if (seg.start > start) {
                System.out.printf("[FREE] - START %d - END %d\n", start, seg.start - 1);
            }
            System.out.printf("[%s] START %d - END %d\n", seg.process.pid, seg.start, seg.end);
            start = seg.end + 1;
        } 

        if (start < size) {
            System.out.printf("[FREE] START %d - END %d\n", start, size - 1);
        }
    }

    private void retrieveAllocatedNodes(Segment node, List<Segment> nodes) {
        
        if (!node.isEmpty()) {
            nodes.add(node);
        } else {
            if (node.left != null) retrieveAllocatedNodes(node.left, nodes);
            if (node.right != null) retrieveAllocatedNodes(node.right, nodes);
        }
    }
}
