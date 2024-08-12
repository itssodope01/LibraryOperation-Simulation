class Book extends LibraryItem {
    private String author;
    private String genre;
    private String publisher;

    public Book(String title, int libraryId, String author, String genre, String publisher) {
        super(title, libraryId);
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
    }

    @Override
    public int getMaxLoanDays() {
        return 14;
    }
}
