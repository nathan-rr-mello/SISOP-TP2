import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        
        /* PartitionFactory partFactory = new FixedPartitionFactory(8);
        MemoryManager mm = new CircularFitManager(32, partFactory);

        try (BufferedReader br = new BufferedReader(new FileReader("asm/asm1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("IN")) {
                    String[] arg = line.replaceAll("[)]", "").split("\\(")[1].split(", ");
                    Proccess p = new Proccess( arg[0], Integer.parseInt(arg[1]));
                    mm.In(p);
                } else {
                    String arg = line.replaceAll("[)]", "").split("\\(")[1];
                    mm.Out(arg);
                }
                mm.Print();
                System.out.println("<==================================>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } */

        FixedPartitionManager fpm = new FixedPartitionManager(32);

        Proccess a = new Proccess("A", 4);
        Proccess b = new Proccess("B", 8);

        fpm.In(a);
        fpm.In(b);
        fpm.Print();

    }
}