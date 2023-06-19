import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FixedPartitionManager fpm = null;
        MemoryAllocator ma = null;
        String type = "", sizeAux = "", varAux = "", fileName = "";
        boolean fileExists = false; // Indica se o arquivo ASM existe

        // Solicita o tipo de particionamento
        Scanner scan = new Scanner(System.in);
        System.out.println("\nInforme o tipo de particionamento: \n 1 - Sistema Buddy \n 2 - Variável ");
        type = scan.nextLine();

        // Solicita o tamanho da memória
        while (sizeAux.isEmpty() || !sizeAux.matches("[0-9]*")) {
            System.out.println("\n\nInforme o expoente de dois referente ao tamanho da memória: ");
            sizeAux = scan.nextLine();
        }
        int size = (int) Math.pow(2.0, Double.parseDouble(sizeAux));
        System.out.println("Tamanho da memória: " + size);

        if (type.equals("1")) {
            // Utiliza o sistema Buddy para particionamento
            fpm = new FixedPartitionManager(size);
        } else {
            System.out.println("\n\nInforme o método: \n 1 - Worst Fit \n 2 - Circular Fit ");
            varAux = scan.nextLine();
            if (varAux.equals("1")) {
                // Utiliza o Worst-Fit para alocação variável
                ma = new WorstFitAllocator(size);
            } else {
                // Utiliza o Circular-Fit para alocação variável
                ma = new CircularFitAllocator(size);
            }
        }

        // Solicita o nome do arquivo
        while (!fileExists) {
            System.out.println("\n\nInforme o nome do arquivo: ");
            fileName = scan.nextLine();
            File file = new File("asm/" + fileName);
            if (file.exists())
                fileExists = true;
        }

        System.out.println("\n\n<==================================>");
        if (type.equals("1")) {
            fpm.Print();
        } else {
            ma.Print();
        }
        System.out.println("<==================================>");

        // Lê o arquivo e processa as instruções
        try (BufferedReader br = new BufferedReader(new FileReader("asm/" + fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("IN")) {
                    // Instrução de entrada
                    String[] arg = line.replaceAll("[)]", "").split("\\(")[1].split(", ");
                    Proccess p = new Proccess(arg[0], Integer.parseInt(arg[1]));
                    if (type.equals("1")) {
                        fpm.In(p);
                    } else {
                        ma.In(p);
                    }
                } else {
                    // Instrução de saída
                    String arg = line.replaceAll("[)]", "").split("\\(")[1];
                    if (type.equals("1")) {
                        fpm.Out(arg);
                    } else {
                        ma.Out(arg);
                    }
                }
                if (type.equals("1")) {
                    fpm.Print();
                } else {
                    ma.Print();
                }
                System.out.println("<==================================>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}