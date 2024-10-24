import java.util.ArrayList;
import java.util.List;

class Book {
    String title;
    String author;
    boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
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
        if (book.isAvailable) {
            checkedOutBooks.add(book);
            book.isAvailable = false;
            System.out.println(name + " checked out " + book.title);
        } else {
            System.out.println(book.title + " is not available.");
        }
    }

    public void returnBook(Book book) {
        if (checkedOutBooks.remove(book)) {
            book.isAvailable = true;
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

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Added book: " + title + " by " + author);
    }

    public void addUser(String name) {
        users.add(new User(name));
        System.out.println("Added user: " + name);
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.title.equals(title)) {
                return book;
            }
        }
        return null;
    }

    public User findUser(String name) {
        for (User user : users) {
            if (user.name.equals(name)) {
                return user;
            }
        }
        return null;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Adding books
        library.addBook("1984", "George Orwell");
        library.addBook("To Kill a Mockingbird", "Harper Lee");

        // Adding users
        library.addUser("Alice");
        library.addUser("Bob");

        // User actions
        User alice = library.findUser("Alice");
        Book book1 = library.findBook("1984");

        if (alice != null && book1 != null) {
            alice.checkOutBook(book1); // Alice checks out 1984
        }

        Book book2 = library.findBook("To Kill a Mockingbird");
        if (alice != null && book2 != null) {
            alice.checkOutBook(book2); // Alice checks out To Kill a Mockingbird
        }

        // Returning a book
        alice.returnBook(book1); // Alice returns 1984
    }
}
