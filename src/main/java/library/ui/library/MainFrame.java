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

	private void showBookMenu() {
		getContentPane().removeAll();

	    menuPanel = new JPanel();
	    menuPanel.setLayout(new BorderLayout());

	    JPanel infoPanel = new JPanel();
	    infoPanel.setLayout(new GridLayout(5, 2, 10, 10));

	    titleField = new JTextField(selectedBook.getTitle());
	    authorField = new JTextField(selectedBook.getAuthor());
	    publisherField = new JTextField(selectedBook.getPublisher());
		yearField = new JTextField(String.valueOf(selectedBook.getPublicationYear()));
	
	    infoPanel.add(new JLabel("Title"));
	    infoPanel.add(titleField);

	    infoPanel.add(new JLabel("Author"));
	    infoPanel.add(authorField);

	    infoPanel.add(new JLabel("Publisher"));
	    infoPanel.add(publisherField);

	    infoPanel.add(new JLabel("Year"));
	    infoPanel.add(yearField);

	    JLabel linesLabel = new JLabel("Lines : " + service.countLines(selectedBook));

	    infoPanel.add(linesLabel);

	    JPanel buttonsPanel = new JPanel();

	    JButton saveMetadataButton = new JButton("Save Metadata");

	    JButton readBookButton = new JButton("Read Book");

	    JButton editBookButton = new JButton("Edit Book");

    	JButton backButton = new JButton("Back");

    	saveMetadataButton.addActionListener(event -> {

	        try {

	            boolean updated = service.editBookMetadata(selectedBook.getId(), titleField.getText(), authorField.getText(), publisherField.getText(), Integer.parseInt(yearField.getText()));
	
        	    if (updated) {
    	            JOptionPane.showMessageDialog(this, "Metadata saved successfully.");
	            }

				else {
            	    JOptionPane.showMessageDialog(this, "Book not found.");
    	        }
	
        	} catch (Exception exception) {
    	        JOptionPane.showMessageDialog(this, "Invalid data.");
 	       }
	    });

	    readBookButton.addActionListener(event -> openBook(false));

 	   editBookButton.addActionListener(event -> openBook(true));

	    backButton.addActionListener(event -> createBooksPanel());	

    	buttonsPanel.add(saveMetadataButton);
	    buttonsPanel.add(readBookButton);
    	buttonsPanel.add(editBookButton);
	    buttonsPanel.add(backButton);

    	menuPanel.add(infoPanel, BorderLayout.CENTER);
	    menuPanel.add(buttonsPanel, BorderLayout.SOUTH);

	    add(menuPanel);

    	revalidate();
	    repaint();
	}
}
