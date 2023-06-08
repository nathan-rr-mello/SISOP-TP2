public class Main {

    public static void main(String[] args) {
        
        MemoryManager mm = new WorstFitManager(32);

        Proccess a = new Proccess("A", 8);
        Proccess b = new Proccess("B", 8);

        mm.In(a);
        mm.In(b);
        mm.Print();
        System.out.println("<====================>");
        mm.Out("A");
        mm.Print();
        System.out.println("<====================>");
        Proccess c = new Proccess("C", 4);
        mm.In(c);
        mm.Print();
        



    }
}