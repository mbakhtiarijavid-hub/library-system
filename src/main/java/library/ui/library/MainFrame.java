package library.ui.library;

import library.model.library.Book;
import library.service.library.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private LibraryService service;

    private JPanel booksPanel;
    private JPanel menuPanel;
    private JPanel readerPanel;

    private Book selectedBook;

    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField yearField;

    private JTextArea pageArea;

    private List<String> pages;
    private int currentPage;

    public MainFrame() {

        service = new LibraryService();

        setTitle("Personal Library System");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBooksPanel();

        setVisible(true);
    }

    private void createBooksPanel() {

        getContentPane().removeAll();

        booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());

        JPanel booksListPanel = new JPanel();
        booksListPanel.setLayout(new GridLayout(0, 1, 10, 10));

        List<Book> books = service.getAllBooks();

        for (Book book : books) {

            JButton bookButton = new JButton(book.getTitle());

            bookButton.addActionListener(event -> {

                selectedBook = book;
                showBookMenu();
            });

            booksListPanel.add(bookButton);
        }

        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(event -> createBooksPanel());

        booksPanel.add(new JScrollPane(booksListPanel), BorderLayout.CENTER);

        booksPanel.add(refreshButton, BorderLayout.SOUTH);

        add(booksPanel);

        revalidate();
        repaint();
    }
}
