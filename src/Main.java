import domain.Librarian;
import service.impl.LibrarianServiceImpl;

import java.util.Scanner;

public class Main {

    public static void loopLibrarian() {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("========= Librarian Accounnts Management =========");
            System.out.println("1. Add Librarian");
            System.out.println("2. Update Librarian");
            System.out.println("3. Delete Librarian");
            System.out.println("4. Find Librarian by ID");
            System.out.println("5. Find All Librarians");
            System.out.println("6. Back to Main Menu!\n====================================");
            System.out.print("Enter option: ");
            int opt = Integer.parseInt(input.nextLine());
            switch (opt) {
                case 1 -> {
                    System.out.println("========= Add Librarian =========");
                }
                case 2 -> System.out.println("========= Update Librarian =========");
                case 3 -> System.out.println("========= Delete Librarian =========");
                case 4 -> System.out.println("========= Find Librarian by ID =========");
                case 5 -> System.out.println("========= Find All Librarians==========");
                case 6 -> {
                    System.out.println("Back to Main Menu!");
                    loopLibrariansService();
                }
                default -> {
                    System.out.println("Invalid option");
                }
            }
        } while (true);
    }

    public static void loopBooks() {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("========= View Books =========");
            System.out.println("1. View All Books");
            System.out.println("2. View Books By ID");
            System.out.println("3. View Available Books");
            System.out.println("4. Back to Main Menu!\n====================================");
            System.out.print("Enter option: ");
            int opt = Integer.parseInt(input.nextLine());
            switch (opt) {
                case 1 -> {
                    System.out.println("========= View All Books =========");
                }
                case 2 -> System.out.println("========= View Books By ID =========");
                case 3 -> System.out.println("========= View Available Books =========");
                case 4 -> {
                    System.out.println("Back to Main Menu!");
                    loopLibrariansService();
                }
                default -> {
                    System.out.println("Invalid option");
                }
            }
        } while (true);
    }

    public static void loopMembers() {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("========= View Members =========");
            System.out.println("1. View All Members");
            System.out.println("2. View Members By ID");
            System.out.println("3. Back to Main Menu\n====================================");
            System.out.print("Enter option: ");
            int opt = Integer.parseInt(input.nextLine());
            switch (opt) {
                case 1 -> {
                    System.out.println("========= View All Members =========");

                }
                case 2 -> System.out.println("========= View Memers By ID =========");
                case 3 -> {
                    System.out.println("Back to Main Menu!");
                    loopLibrariansService();
                }
                default -> {
                    System.out.println("Invalid option");
                }
            }
        } while (true);
    }

    public static void loopLoans() {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("========= View Members =========");
            System.out.println("1. View All Loans");
            System.out.println("2. View Loans By ID");
            System.out.println("3. Back to Main Menu\n====================================");
            System.out.print("Enter option: ");
            int opt = Integer.parseInt(input.nextLine());
            switch (opt) {
                case 1 -> {
                    System.out.println("========= View All Members =========");

                }
                case 2 -> System.out.println("========= View Memers By ID =========");
                case 3 -> {
                    System.out.println("Back to Main Menu!");
                    loopLibrariansService();
                }
                default -> {
                    System.out.println("Invalid option");
                }
            }
        } while (true);
    }

    public static void loopLibrariansService() {
        Scanner input = new Scanner(System.in);
        Librarian newLibrarian = new Librarian();
        System.out.print("Input Name: ");
        String name = input.nextLine();

        newLibrarian.setName(name);

        Librarian librarian1 = new Librarian();
        librarian1.setId(1);
        librarian1.setName("ChhayAch1");
        librarian1.setEmail("chhayAch1@gmail.com");
        librarian1.setPhoneNumber("123456789");

        Librarian librarian2 = new Librarian();
        librarian2.setId(2);
        librarian2.setName("ChhayAch2");
        librarian2.setEmail("chhayach2@gmail.com");
        librarian2.setPhoneNumber("1234567890");

        Scanner inpu = new Scanner(System.in);
        do {
            System.out.println("========= Libraian Service =========");
            System.out.println("1. Librarian Accounts Management");
            System.out.println("2. View Books");
            System.out.println("3. View Members");
            System.out.println("4. View Loans");
            System.out.println("5. Exit the program\n====================================");
            System.out.print("Enter option: ");
            int option = Integer.parseInt(inpu.nextLine());
            switch (option) {
                case 1 -> loopLibrarian();
                case 2 -> loopBooks();
                case 3 -> System.out.println("View Loans");
                case 4 -> System.out.println("View Loans");
                case 5 -> {
                    System.out.println("Exit the program!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid option");
                }
            }
        } while (true);
    }

    public static void main(String[] args) {
        loopLibrariansService();
    }
}
