import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Path fajl;
        int num_headers = -1;
        String filename = new String(), temp = new String();
        StringBuilder sb = new StringBuilder(), entry = new StringBuilder();
        Boolean more = true;
        List<String> line = new ArrayList<>(), headers = new ArrayList<>();

        System.out.print("Enter file name: ");
        if(sc.hasNextLine()){
            filename = sc.nextLine();
        }

        fajl = Paths.get(filename + ".csv");

        System.out.print("Enter num headers: ");
        if(sc.hasNextInt()){
            num_headers = sc.nextInt();
        }

        for (int i = 0; i < num_headers; i++) {
            System.out.printf("Enter header %d: ", i+1);
            //stdin flush? sc.hasNextInt triggers sc.hasNextLine ?
            if(i == 0)
                sc.nextLine();
            if(sc.hasNextLine()) {
                temp = sc.nextLine();
            }
            sb.delete(0, sb.length());
            sb.append(temp);
            headers.add(temp);

            if(sb.indexOf(" ") != -1){
                sb.insert(0, "\"");
                sb.append("\"");
            }

            entry.append(sb);
            if(i != num_headers-1)
                entry.append(", ");
        }

        entry.append("\n");
        line.add(entry.toString());
        try {
            Files.write(fajl, line);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        while(more) {

            for (int i = 0; i < num_headers; i++) {
                System.out.printf("Enter %s: ", headers.get(i));
                if(sc.hasNextLine()) {
                    temp = sc.nextLine();
                }
                sb.delete(0, sb.length());
                sb.append(temp);
                if(sb.indexOf(" ") != -1){
                    sb.insert(0, "\"");
                    sb.append("\"");
                }

                entry.append(sb);
                if(i != num_headers-1)
                    entry.append(", ");

            }

            line.clear();
            entry.append("\n");
            line.add(entry.toString());
            try {
                Files.write(fajl, line);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            System.out.printf("Make another entry? yes/ye/y");
            if(sc.hasNext())
                temp = sc.next();
            if(!(temp.equalsIgnoreCase("yes") || temp.equalsIgnoreCase("ye")
            || temp.equalsIgnoreCase("y")))
                more = false;

            //flush?
            sc.nextLine();
        }

    }
}
