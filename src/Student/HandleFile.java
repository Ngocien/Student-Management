package Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HandleFile {
    public static List<Student> read_csv(String csvFile) {
        List<Student> student = new ArrayList<>();
        File file = new File(csvFile);
        Path path = Paths.get(csvFile);
        if (!Files.exists(path)) {
            try {

                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String line = "";
                String[] tempArr;
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                    tempArr = line.split(",");
                    Student t = Student.createStudent(tempArr);
                    student.add(t);
                }
                br.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return student;
    }

    public static void write_file(List<Student> student) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("data.csv");
        for (Student i : student) {
            if (!i.getId().equals("") && !i.getName().equals(""))
                writer.println(i.getId().toString() + "," + i.getName().toString() + "," + i.getAge());
        }
        writer.close();
    }

}
