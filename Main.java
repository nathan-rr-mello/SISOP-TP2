public class Main {

    public static void main(String[] args) {
        
        PartitionFactory partFactory = new FixedPartitionFactory(8);
        MemoryManager mm = new CircularFitManager(32, partFactory);

        Proccess a = new Proccess("A", 4);
        Proccess b = new Proccess("B", 4);

        mm.In(a);
        mm.In(b);
        mm.Print();
        System.out.println("<====================>");
        mm.Out("A");
        mm.Print();
        



    }
}