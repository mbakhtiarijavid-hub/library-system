package library.util.library;

import library.model.library.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileManager {

    public static final String BOOKS_TEXT_DIR = "data/text_books/";
    public static final String BOOK_LIST_CSV = "data/Book_List.txt";

    private FileManager() {
    }

    public static List<Book> loadBooksFromCSV() {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(BOOK_LIST_CSV))) {

            reader.readLine();

            String line;
            int id = 1;

            while ((line = reader.readLine()) != null) {
                Book book = createBook(line, id++);

                if (book != null) {
                    books.add(book);
                }
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return books;
    }

    public static void saveBooksToCSV(List<Book> books) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(BOOK_LIST_CSV))) {

            writer.write("title,author,publisher,year,file");
            writer.newLine();

            for (Book book : books) {
                writer.write(convertBookToCsvRow(book));
                writer.newLine();
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static List<String> readBookPages(String filePath, int linesPerPage) {

        List<String> pages = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists()) {
            pages.add("File not found.");
            return pages;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            StringBuilder currentPage = new StringBuilder();
            String line;
            int currentLineCount = 0;

            while ((line = reader.readLine()) != null) {

                currentPage.append(line)
                        .append(System.lineSeparator());

                currentLineCount++;

                if (currentLineCount == linesPerPage) {

                    pages.add(currentPage.toString());

                    currentPage.setLength(0);
                    currentLineCount = 0;
                }
            }

            if (!currentPage.isEmpty()) {
                pages.add(currentPage.toString());
            }

        } catch (IOException exception) {

            pages.clear();
            pages.add(exception.getMessage());
        }

        return pages;
    }

    public static int countLines(String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            return 0;
        }

        int lineCount = 0;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            while (reader.readLine() != null) {
                lineCount++;
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return lineCount;
    }

    public static void writeBookText(String filePath,
                                     String content) throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(filePath))) {

            writer.write(content);
        }
    }

    public static String readFullText(String filePath) {

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                content.append(line)
                        .append(System.lineSeparator());
            }

        } catch (IOException exception) {
            return "";
        }

        return content.toString();
    }

    private static Book createBook(String csvLine, int id) {

        String[] values = csvLine.split(",");

        if (values.length < 5) {
            return null;
        }

        String title = values[0].trim();
        String author = values[1].trim();
        String publisher = values[2].trim();
        int publicationYear = Integer.parseInt(values[3].trim());
        String textFilePath = values[4].trim();

        return new Book(
                id,
                title,
                textFilePath,
                author,
                publisher,
                publicationYear
        );
    }

    private static String convertBookToCsvRow(Book book) {

        return book.getTitle() + "," +
                book.getAuthor() + "," +
                book.getPublisher() + "," +
                book.getPublicationYear() + "," +
                book.getTextFilePath();
    }
}
