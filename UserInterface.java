interface UserInterface {
    String getUsername();
    boolean canBorrowItem(LibraryItem item);
    void borrowItem(LibraryItem item, int currentDay);
    LibraryItem returnItem(int currentDay);
    }
