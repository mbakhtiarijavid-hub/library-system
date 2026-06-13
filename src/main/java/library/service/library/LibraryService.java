package library.service.library;

import library.model.library.Book;
import library.util.library.FileManager;

import java.io.IOException;
import java.util.List;

public class LibraryService {

    private List<Book> books;

    public LibraryService() {
        books = FileManager.loadBooksFromCSV();
        FileManager.saveBooksToCSV(books);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book findBookById(int id) {

        for (Book book : books) {

            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    public void saveBooks() {
        FileManager.saveBooksToCSV(books);
    }

    public boolean editBookMetadata(int bookId, String newTitle, String newAuthor, String newPublisher, Integer newYear) {

        Book book = findBookById(bookId);

        if (book == null) {
            return false;
        }

        if (newTitle != null && !newTitle.trim().isEmpty()) {
            book.setTitle(newTitle);
        }

        if (newAuthor != null && !newAuthor.trim().isEmpty()) {
            book.setAuthor(newAuthor);
        }

        if (newPublisher != null && !newPublisher.trim().isEmpty()) {
            book.setPublisher(newPublisher);
        }

        if (newYear != null) {
            book.setPublicationYear(newYear);
        }

        saveBooks();

        return true;
    }

    public List<String> getBookPages(Book book, int linesPerPage) {

        return FileManager.readBookPages(book.getTextFilePath(), linesPerPage);
    }

    public String readFullText(int bookId) {

        Book book = findBookById(bookId);

        if (book == null) {
            return "";
        }

        return FileManager.readFullText(book.getTextFilePath());
    }

    public boolean editBookContent(int bookId, String newContent) throws IOException {

        Book book = findBookById(bookId);

        if (book == null) {
            return false;
        }

        FileManager.writeBookText(book.getTextFilePath(), newContent);

        return true;
    }

    public int countLines(Book book) {

        return FileManager.countLines(book.getTextFilePath());
    }
}
