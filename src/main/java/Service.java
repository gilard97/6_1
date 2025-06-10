import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Service {

    private static final String FILE_NAME = "db.txt";

    public void addStudent(Student student) throws IOException {
        try (var b = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            b.write(student.ToString());
            b.newLine();
        }
    }

    public Collection<Student> getStudents() throws IOException {
        var ret = new ArrayList<Student>();
        try (var reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ret.add(Student.Parse(line));
            }
        }
        return ret;
    }

    public Student findStudentByName(String name) throws IOException {
        for (Student current : getStudents()) {
            if (current.GetName().equalsIgnoreCase(name))
                return current;
        }
        return null;
    }

    public void clearStudents() throws IOException {
        try (var writer = new FileWriter(FILE_NAME, false)) {
        }
    }

    public List<Student> findStudentsByYear(String year) throws IOException {
        var result = new ArrayList<Student>();
        for (Student s : getStudents()) {
            String dob = s.GetDateOfBirth();
            if (dob.matches("\\d{2}-\\d{2}-\\d{4}") && dob.endsWith(year)) {
                result.add(s);
            }
        }
        return result;
    }
}