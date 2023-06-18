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
    }
    
    private int size;
    private Segment root;

    public FixedPartitionManager(int size) {
        this.size = size;
        root = new Segment(0, size - 1);
    }

    public void In(Proccess proc) {

       if (!insert(root, proc)) {
            System.out.println("[ERROR] - COULD NOT MOVE PROCESS TO MEMORY");
       };
    }

    private boolean insert(Segment node, Proccess proc) {

       /*  if (node.size() >= proc.size && node.size() < proc.size * 2) {
            node.process = proc;
            return true;
        } else {
            int middle = (node.start + node.end) / 2;
            if (node.left == null) node.left = new Segment(node.start, middle);
            if (insert(node.left, proc)) return true;
            if (node.right == null) node.right =  new Segment(middle + 1, node.end);
            if (insert(node.right, proc)) return true;
        }

        return false; */

        if (node.process != null) {
        return false; // Segmento já está ocupado
    }

    if (node.size() == proc.size) {
        node.process = proc;
        return true; // Segmento alocado com sucesso
    }

    if (node.size() < proc.size) {
        return false; // Segmento não é grande o suficiente para alocar o processo
    }

    // Divide o segmento em dois
    int middle = (node.start + node.end) / 2;
    node.left = new Segment(node.start, middle);
    node.right = new Segment(middle + 1, node.end);

    // Tenta alocar o processo no segmento esquerdo
    if (insert(node.left, proc)) {
        return true;
    }

    // Tenta alocar o processo no segmento direito
    if (insert(node.right, proc)) {
        return true;
    }

    // Não foi possível alocar o processo em nenhum dos segmentos filhos
    return false;
    }

    public void Out(String pid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Out'");


    }

    private boolean remove(Segment node, String pid) {

        if (node.left != null) {
            if (node.left.process.pid.equals(pid)) {

            }
        }  

        if (node.isEmpty()) {
            if (node.left != null && remove(node.left, pid)) {
                if (node.left.isEmpty() && node.right.isEmpty()) {
                    node.left = null;
                    node.right = null;
                }
                return true;
            } 
            if (node.right != null && remove(node.right, pid)) {
                if (node.left.isEmpty() && node.right.isEmpty()) {
                    node.left = null;
                    node.right = null;
                }
                return true;
            } 
        } else if (node.process.pid.equals(pid)) {
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
