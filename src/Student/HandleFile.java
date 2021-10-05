package Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HandleFile {
    public static List<Student> read_csv(String csvFile) {
        List<Student> student = new ArrayList<>();
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(",");
                Student t = Student.createStudent(tempArr);
                student.add(t);
                }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return student;
    }

    public static void write_file(List<Student> student) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("src/data/data.csv");
        for (Student i : student) {
            if (!i.getId().equals("") && !i.getName().equals(""))
                writer.println(i.getId().toString() + "," + i.getName().toString() + "," + i.getAge());
        }
        writer.close();
    }

}
