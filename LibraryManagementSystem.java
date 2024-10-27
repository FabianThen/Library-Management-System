import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    String title;
    String author;
    int availableCopies;
    int totalCopies;

    public Book(String title, String author, int totalCopies) {
        this.title = title;
        this.author = author;
        this.availableCopies = totalCopies;
        this.totalCopies = totalCopies;
    }

    public void checkOut() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }
}

class User {
    String name;
    List<Book> checkedOutBooks;

    public User(String name) {
        this.name = name;
        this.checkedOutBooks = new ArrayList<>();
    }

    public void checkOutBook(Book book) {
        if (book.availableCopies > 0) {
            checkedOutBooks.add(book);
            book.checkOut();
            System.out.println(name + " checked out " + book.title);
        } else {
            System.out.println(book.title + " is not available.");
        }
    }

    public void returnBook(Book book) {
        if (checkedOutBooks.remove(book)) {
            book.returnBook();
            System.out.println(name + " returned " + book.title);
        } else {
            System.out.println(name + " does not have " + book.title);
        }
    }
}

class Library {
    List<Book> books;
    List<User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addBook(String title, String author, int totalCopies) {
        books.add(new Book(title, author, totalCopies));
        System.out.println("Added book: " + title + " by " + author + " (Total Copies: " + totalCopies + ")");
    }

    public void addUser(String name) {
        users.add(new User(name));
        System.out.println("Added user: " + name);
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public User findUser(String name) {
        for (User user : users) {
            if (user.name.equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    public void listBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : books) {
            System.out.println(book.title + " by " + book.author + " - Available Copies: " + book.availableCopies);
        }
    }

    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("library_data.txt"))) {
            for (Book book : books) {
                writer.write(book.title + "," + book.author + "," + book.totalCopies + "\n");
            }
            System.out.println("Library data saved.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("library_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                addBook(parts[0], parts[1], Integer.parseInt(parts[2]));
            }
            System.out.println("Library data loaded.");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        library.loadData(); // Load existing data

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Check Out Book");
            System.out.println("4. Return Book");
            System.out.println("5. List Books");
            System.out.println("6. Save Data");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter total copies: ");
                    int totalCopies = Integer.parseInt(scanner.nextLine());
                    library.addBook(title, author, totalCopies);
                    break;

                case "2":
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    library.addUser(userName);
                    break;

                case "3":
                    System.out.print("Enter user name: ");
                    userName = scanner.nextLine();
                    User user = library.findUser(userName);
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    Book bookToCheckOut = library.findBook(title);
                    if (user != null && bookToCheckOut != null) {
                        user.checkOutBook(bookToCheckOut);
                    } else {
                        System.out.println("User or book not found.");
                    }
                    break;

                case "4":
                    System.out.print("Enter user name: ");
                    userName = scanner.nextLine();
                    user = library.findUser(userName);
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    bookToCheckOut = library.findBook(title);
                    if (user != null && bookToCheckOut != null) {
                        user.returnBook(bookToCheckOut);
                    } else {
                        System.out.println("User or book not found.");
                    }
                    break;

                case "5":
                    library.listBooks();
                    break;

                case "6":
                    library.saveData();
                    break;

                case "7":
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!command.equals("7"));

        scanner.close();
    }
}
