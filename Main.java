import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        
        PartitionFactory partFactory = new FixedPartitionFactory(8);
        MemoryManager mm = new CircularFitManager(32, partFactory);

        try (BufferedReader br = new BufferedReader(new FileReader("asm/asm1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("IN")) {
                    String[] arg = line.replaceAll("[)]", "").split("\\(")[1].split(", ");
                    Proccess p = new Proccess( arg[0], Integer.parseInt(arg[1]));
                    mm.In(p);
                } else {
                    String[] arg = line.replaceAll("[)]", "").split("\\(")[1].split(", ");
                    mm.Out(arg[0]);
                }
                mm.Print();
                System.out.println("<==================================>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}