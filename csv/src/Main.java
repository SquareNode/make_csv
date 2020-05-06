import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void writeLine(Path file, List<String> line, StandardOpenOption... op){
        try {
            if(op.length == 1)
                Files.write(file, line, op[0]);
            else
                Files.write(file, line);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Path getFilename(Scanner sc){
        String filename;
        System.out.print("Enter file name: ");

        filename = null;

        if(sc.hasNextLine()){
            filename = sc.nextLine();
        }

        return Paths.get(filename + ".csv");
    }

    public static List<String> getHeaders(Scanner sc, int n, Path file) {

        List<String> res = new ArrayList<>(), toWrite = new ArrayList<>();
        String currHeader;
        StringBuilder sb = new StringBuilder(), entry = new StringBuilder();

        //Variable 'currHeader' might not have been initialized
        currHeader = null;

        for (int i = 0; i < n; i++) {
            System.out.printf("Enter header %d: ", i+1);
            //stdin flush? sc.hasNextInt triggers sc.hasNextLine ?
            if(i == 0)
                sc.nextLine();
            if(sc.hasNextLine()) {
                currHeader = sc.nextLine();
            }
            sb.delete(0, sb.length());
            sb.append(currHeader);
            res.add(currHeader);

            if(sb.indexOf(" ") != -1){
                sb.insert(0, "\"");
                sb.append("\"");
            }

            entry.append(sb);
            if(i != n-1)
                entry.append(", ");
        }

        toWrite.add(entry.toString());

        writeLine(file, toWrite);

        return res;
    }

    public static void makeEntry(Scanner sc, int n, Path file, List<String> headers) {

        String temp;
        StringBuilder sb = new StringBuilder(), entry = new StringBuilder();
        List <String> toWrite = new ArrayList<>();

        //Variable 'temp' might not have been initialized
        temp = null;

        for (int i = 0; i < n; i++) {
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
            if(i != n-1)
                entry.append(", ");

        }

        toWrite.add(entry.toString());
        writeLine(file, toWrite, StandardOpenOption.APPEND);

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Path file;
        int num_headers;
        String temp = new String();
        Boolean more;
        List<String> headers;

        file = getFilename(sc);

        //Variable 'num_headers' might not have been initialized
        num_headers = -1;

        System.out.print("Enter num headers: ");
        if(sc.hasNextInt()){
            num_headers = sc.nextInt();
        }

        headers = getHeaders(sc, num_headers, file);

        more = true;
        while(more) {
            makeEntry(sc, num_headers, file, headers);

            System.out.printf("Make another entry? yes/ye/y ");
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
