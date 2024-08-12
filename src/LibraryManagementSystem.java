import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVParserBuilder;
import java.io.PrintWriter;
import java.io.FileWriter;


abstract class LibraryItem {
    private static final double BOOK_OVERDUE_FEE = 0.5;
    private static final double JOURNAL_OVERDUE_FEE = 2.0;
    private static final double FILM_OVERDUE_FEE = 5.0;

    private String title;
    protected int loanDays;
    private int libraryId;
    private boolean onLoan;

    public LibraryItem(String title, int libraryId) {
        this.title = title;
        this.libraryId = libraryId;
        this.onLoan = false;
        this.loanDays = 0;
    }

    public String getTitle() {
        return title;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void borrowItem() {
        onLoan = true;
    }

    public void returnItem() {
        onLoan = false;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public abstract int getMaxLoanDays();

    public int daysOverdue(int currentDay) {
        int dueDay = getLoanDays() + getMaxLoanDays();
        return currentDay > dueDay ? currentDay - dueDay : 0;
    }



    public boolean isOverdue(int currentDay) {
        return daysOverdue(currentDay) > 0;
    }

    public double computeFine(int currentDay) {
        int daysOverdue = daysOverdue(currentDay);

        if (daysOverdue > 0) {
            if (this instanceof Book) {
                return daysOverdue * BOOK_OVERDUE_FEE; 
            } else if (this instanceof Journal) {
                return daysOverdue * JOURNAL_OVERDUE_FEE;
            } else if (this instanceof Film) {
                return daysOverdue * FILM_OVERDUE_FEE;
            }
        }

        return 0;
    }
}

class Library {
    private List<LibraryItem> libraryItems;
    private List<LibraryUser> users;
    private List<LibraryItem> itemsOnLoan;
    private double totalFineCollected;
    private int currentDay;


    public Library(List<Book> books, List<Film> films, List<Journal> journals, List<LibraryUser> users) {
        this.libraryItems = new ArrayList<>();
        this.libraryItems.addAll(books);
        this.libraryItems.addAll(films);
        this.libraryItems.addAll(journals);
        this.users = users;
        this.itemsOnLoan = new ArrayList<>();
        this.totalFineCollected = 0.0;
    }

public void dailyOperation(int currentDay) {
        for (LibraryUser user : users) {
            double alphaBook = 0.67; //67% return on time
            double beta = 0.33; //33% can be delayed

            if (Math.random() < alphaBook && user instanceof Student) {
                borrowItem(user, currentDay);
            }
            if (Math.random() < alphaBook && user instanceof Faculty) {
                borrowItem(user, currentDay);
            }
            if (Math.random() < beta) {
                returnItem(user, currentDay);
            }
        }

        for (LibraryItem item : itemsOnLoan) {
            if (item.isOverdue(currentDay)) {
                double fine = item.computeFine(currentDay);
                totalFineCollected += fine;
            }
        }
    }

    public void borrowItem(LibraryUser user, int currentDay) {
        if (libraryItems.isEmpty()) {
            System.out.println("No available items to borrow.");
            return;
        }

        Random rand = new Random();
        LibraryItem item = libraryItems.get(rand.nextInt(libraryItems.size()));

        if (user.canBorrowItem(item)) {
            user.borrowItem(item, currentDay);
            libraryItems.remove(item);
            itemsOnLoan.add(item);

            
            writeTransaction("Borrowed", user, item, currentDay);
        } 
    }

    public void returnItem(LibraryUser user, int currentDay) {
        LibraryItem item = user.returnItem(currentDay);

        if (item != null) {
            libraryItems.add(item);
            itemsOnLoan.remove(item);

            
            writeTransaction("Returned", user, item, currentDay);
        } 
    }

    private void writeTransaction(String action, LibraryUser user, LibraryItem item, int currentDay) {
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {
            writer.println(action + " - " + user.getUsername() + " - " + item.getTitle() +
                    " (ID: " + item.getLibraryId() + ") on day " + currentDay);
        }
         catch (IOException e) {
            e.printStackTrace();
        }
    }

public void simulateLibraryOperation(int simulationDays) {
    for (int day = 1; day <= simulationDays; day++) {
        currentDay = day;
        dailyOperation(currentDay);
    }
    showLoans();
    showStatistics();
}

    public void showLoans() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("loans.txt"))) {
            writer.println("Items on Loan:");

            for (LibraryItem item : itemsOnLoan) {
                writer.println("Title: " + item.getTitle() +
                        ", ID: " + item.getLibraryId() +
                        ", Due Day: " + (item.getLoanDays() + item.getMaxLoanDays()));
            }

            System.out.println("Loan items information saved to loans.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void showStatistics() {
    int lentItems = libraryItems.size() + itemsOnLoan.size();
    int onLoanItems = itemsOnLoan.size();
    int overdueItems = 0;

    double totalFineCollected = 0.0;

    
    for (LibraryItem item : itemsOnLoan) {
        if (item.isOverdue(currentDay)) {
            overdueItems++;
            totalFineCollected += item.computeFine(currentDay);
        }
    }

    System.out.println("Library Statistics:");
    System.out.println("Items Lent: " + lentItems);
    System.out.println("Items on Loan: " + onLoanItems);
    System.out.println("Overdue Items: " + overdueItems);
    System.out.println("Total Fine Collected: $" + totalFineCollected);
}



    public static int generateLibraryId() {
        Random rand = new Random();
        return rand.nextInt(1000) + 1; 
    }

    public static List<Book> readBooksFromCSV(String filePath) {
    List<Book> books = new ArrayList<>();

    try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
            .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
            .build()) {
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String title = nextLine[0];
            String author = nextLine[1];
            String genre = nextLine[2];

            
            books.add(new Book(title, generateLibraryId(), author, genre, "Unknown Publisher"));
        }
    } catch (IOException | CsvException e) {
        e.printStackTrace();
    }

    return books;
}

     public static List<Film> readFilmsFromCSV(String filePath) {
        List<Film> films = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            
            reader.readNext();

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String title = nextLine[1];
                String genre = nextLine[2];
                String director = nextLine[4];
                
                
                int year = nextLine[6].equals("Year") ? 0 : Integer.parseInt(nextLine[6]);

                int runtimeMinutes = Integer.parseInt(nextLine[7]);
                String rating = nextLine[8];

                
                films.add(new Film(title, generateLibraryId(), genre, director, year, runtimeMinutes, rating));
            }
        } catch (IOException | CsvException | NumberFormatException e) {
            e.printStackTrace();
        }

        return films;
    }


   public static List<Journal> readJournalsFromCSV(String filePath) {
        List<Journal> journals = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String title = nextLine[0];
                String eISSN = nextLine[3];
                String publisher = nextLine[4];
                String latestIssue = nextLine[6];
                String journalURL = nextLine[11];

                
                journals.add(new Journal(title, generateLibraryId(), eISSN, publisher, latestIssue, journalURL));
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return journals;
    }


    public static void main(String[] args) {
        System.out.println("Transactions information saved to transactions.txt");
        
        String booksFilePath = "/csv/books.csv";
        String filmsFilePath = "/csv/movies.csv";
        String journalsFilePath = "/csv/jlist.csv";

        
        List<Book> books = readBooksFromCSV(booksFilePath);
        List<Film> films = readFilmsFromCSV(filmsFilePath);
        List<Journal> journals = readJournalsFromCSV(journalsFilePath);

        //80 students and 20 faculty members
        List<LibraryUser> users = new ArrayList<>();
        for (int i = 1; i <= 80; i++) {
        users.add(new Student("Student" + i));
        }
        for (int i = 1; i <= 20; i++) {
        users.add(new Faculty("Faculty" + i));
        }

        
        Library library = new Library(books, films, journals, users);
        library.simulateLibraryOperation(365);
    }
}