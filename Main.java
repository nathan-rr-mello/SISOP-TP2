public class Main {

    public static void main(String[] args) {
        
        MemoryManager mm = new MemoryManager(32);

        /* Proccess a = new Proccess("A", 8);
        Proccess b = new Proccess("B", 16);
        mm.In(a);
        mm.In(b);
        mm.Print();
        System.out.println("<==============>");
        mm.Out("A");
        mm.Print();
        Proccess c = new Proccess("C", 4);
        mm.In(c);
        
        mm.Print(); */

        CircularFitManager cfm = new CircularFitManager(32);
        Proccess a = new Proccess("A", 16);
        Proccess b = new Proccess("B", 4);
        Proccess c = new Proccess("C", 8);

        cfm.In(a);
        cfm.In(b);
        cfm.Print();
        System.out.println("<==============>");
        cfm.Out("A");
        cfm.Print();
        System.out.println("<==============>");
        cfm.In(c);

    }
}