import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

class WrongStudentName extends Exception {}
class WrongAge extends Exception {}
class WrongDateOfBirth extends Exception {}

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                int ex = menu();
                switch (ex) {
                    case 1:
                        exercise1(); 
                        break;
                    case 2:
                        exercise2(); 
                        break;
                    case 3:
                        exercise3(); 
                        break;
                    case 4:
                        (new Service()).clearStudents(); 
                        System.out.println("Lista studentów została wyczyszczona.");
                        break;
                    case 5:
                        searchByYear(); 
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Nieprawidłowa opcja menu.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Błędny format wyboru! Wprowadź cyfrę.");
                scan.nextLine(); 
            } catch (IOException e) {
                System.out.println("Błąd wejścia/wyjścia.");
            } catch (WrongStudentName e) {
                System.out.println("Błędne imię studenta!");
            } catch (WrongAge e) {
                System.out.println("Błędny wiek! Dozwolony zakres to 1-99.");
            } catch (WrongDateOfBirth e) {
                System.out.println("Błędna data urodzenia! Poprawny format to DD-MM-YYYY.");
            }
        }
    }

    public static int menu() {
        System.out.println("\nMenu:");
        System.out.println("1 - Dodaj studenta");
        System.out.println("2 - Wypisz wszystkich studentów");
        System.out.println("3 - Wyszukaj studenta po imieniu");
        System.out.println("4 - Wyczyść listę studentów");
        System.out.println("5 - Wyszukaj studentów po roku urodzenia");
        System.out.println("0 - Wyjdź z programu");
        return scan.nextInt();
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine(); 
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        while (true) {
            var name = ReadName();

            System.out.println("Podaj wiek: ");
            var age = scan.nextInt();
            scan.nextLine(); 
            if (age < 1 || age > 99) {
                throw new WrongAge();
            }

            System.out.println("Podaj datę urodzenia DD-MM-YYYY");
            var date = scan.nextLine();

            if (!date.matches("\\d{2}-\\d{2}-\\d{4}"))
                throw new WrongDateOfBirth();

            (new Service()).addStudent(new Student(name, age, date));
            System.out.println("Student dodany.");

            System.out.println("Czy chcesz dodać kolejnego studenta? (t/n): ");
            String cont = scan.nextLine();
            if (!cont.equalsIgnoreCase("t"))
                break;
        }
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        if (students.isEmpty()) {
            System.out.println("Brak studentów na liście.");
        } else {
            for (Student current : students) {
                System.out.println(current.ToString());
            }
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine(); 
        System.out.println("Podaj imię: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }

    public static void searchByYear() throws IOException {
        scan.nextLine(); 
        System.out.println("Podaj rok (YYYY): ");
        String year = scan.nextLine();

        var found = (new Service()).findStudentsByYear(year);
        if (found.isEmpty()) {
            System.out.println("Nie znaleziono studentów z podanym rokiem.");
        } else {
            System.out.println("Znaleziono studentów:");
            for (Student s : found) {
                System.out.println(s.ToString());
            }
        }
    }
}