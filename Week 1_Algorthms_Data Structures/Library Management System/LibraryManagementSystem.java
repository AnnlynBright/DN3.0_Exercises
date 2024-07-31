import java.util.*;

class Book {
    private String bookId;
    private String title;
    private String author;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

public class LibraryManagementSystem {
    public Book linearSearch(Book[] books, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public Book binarySearch(Book[] books, String title, int left, int right) {
        if (right >= left) {
            int mid = left + (right - left) / 2;
            int comparison = books[mid].getTitle().compareToIgnoreCase(title);

            if (comparison == 0) {
                return books[mid];
            }

            if (comparison > 0) {
                return binarySearch(books, title, left, mid - 1);
            } else {
                return binarySearch(books, title, mid + 1, right);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManagementSystem lms = new LibraryManagementSystem();

        System.out.print("Enter the number of books: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Book[] books = new Book[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for book " + (i + 1) + ":");
            System.out.print("Book ID: ");
            String bookId = scanner.nextLine();
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Author: ");
            String author = scanner.nextLine();
            books[i] = new Book(bookId, title, author);
        }

        System.out.println("Choose search algorithm:");
        System.out.println("1. Linear Search");
        System.out.println("2. Binary Search");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the title to search: ");
        String searchTitle = scanner.nextLine();

        Book foundBook = null;
        if (choice == 1) {
            foundBook = lms.linearSearch(books, searchTitle);
        } else if (choice == 2) {
            Arrays.sort(books, (b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));
            foundBook = lms.binarySearch(books, searchTitle, 0, books.length - 1);
        } else {
            System.out.println("Invalid choice");
            
        }

        if (foundBook != null) {
            System.out.println("Book found: " + foundBook);
        } else {
            System.out.println("Book not found.");
        }
    }
}
