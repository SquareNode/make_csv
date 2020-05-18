import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void writeLine(FileWriter file, List<String> line){
        int n, curr;

        n = line.size();
        curr = 0;
        for(String s:line){
            if(s.strip().indexOf(" ") != -1) {
                try {
                    file.write("\"" + s + "\"" + (curr == n-1 ? "" : ","));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try{
                    file.write(s + (curr == n-1 ? "" : ","));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            curr++;
        }

        try {
            file.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileWriter getFilename(Scanner sc){
        String filename;
        FileWriter res;
        System.out.print("Enter file name: ");

        filename = null;

        if(sc.hasNextLine()){
            filename = sc.nextLine();
        }

        res = null;
        try {
            res = new FileWriter(filename + ".csv");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<String> getHeaders(Scanner sc, int n) {

        List<String> res = new ArrayList<>();
        String currHeader;

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
            res.add(currHeader);
        }
        return res;
    }

    public static void makeEntry(Scanner sc, int n, FileWriter file, List<String> headers) {

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
            toWrite.add(temp);
        }

        writeLine(file, toWrite);

    }

    public static void main(String[] args) {
        Scanner sc;
        FileWriter file;
        int num_headers;
        String temp;
        Boolean more;
        List<String> headers;

        sc =  new Scanner(System.in);
        file = getFilename(sc);

        //Variable 'num_headers' might not have been initialized
        num_headers = -1;

        System.out.print("Enter num headers: ");
        if(sc.hasNextInt()){
            num_headers = sc.nextInt();
        }

        headers = getHeaders(sc, num_headers);
        writeLine(file, headers);

        more = true;
        temp = null;
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

        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
