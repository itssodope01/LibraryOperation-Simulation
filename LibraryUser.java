
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


abstract class LibraryUser implements UserInterface{
    private String username;
    protected List<LibraryItem> borrowedItems;

    public LibraryUser(String username) {
        this.username = username;
        this.borrowedItems = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public abstract boolean canBorrowItem(LibraryItem item);

    public void borrowItem(LibraryItem item, int currentDay) {
        if (canBorrowItem(item)) {
            item.borrowItem();
            item.loanDays = currentDay;
            borrowedItems.add(item);
        }
    }



public LibraryItem returnItem(int currentDay) {
    Iterator<LibraryItem> iterator = borrowedItems.iterator();
    while (iterator.hasNext()) {
        LibraryItem item = iterator.next();
        int dueDay = item.getLoanDays() + item.getMaxLoanDays();
        if (dueDay <= currentDay) {
            iterator.remove();
            item.returnItem();
            item.loanDays = 0;
            return item;
        }
    }
    return null;
}

}

class Student extends LibraryUser {
    private static final int MAX_BOOKS = 3;
    private static final int MAX_JOURNALS = 3;
    private static final int MAX_FILMS = 1;

    public Student(String username) {
        super(username);
    }

    @Override
    public boolean canBorrowItem(LibraryItem item) {
        if (item instanceof Book && borrowedItems.stream().filter(i -> i instanceof Book).count() < MAX_BOOKS) {
            return true;
        } else if (item instanceof Journal && borrowedItems.stream().filter(i -> i instanceof Journal).count() < MAX_JOURNALS) {
            return true;
        } else return item instanceof Film && borrowedItems.stream().filter(i -> i instanceof Film).count() < MAX_FILMS;
    }
}

class Faculty extends LibraryUser {
    public Faculty(String username) {
        super(username);
    }

    @Override
    public boolean canBorrowItem(LibraryItem item) {
        return true;
    }
}
