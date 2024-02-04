class Film extends LibraryItem {
    private String genre;
    private String director;
    private int year;
    private int runtimeMinutes;
    private String rating;

    public Film(String title, int libraryId, String genre, String director,
                int year, int runtimeMinutes, String rating) {
        super(title, libraryId);
        this.genre = genre;
        this.director = director;
        this.year = year;
        this.runtimeMinutes = runtimeMinutes;
        this.rating = rating;
    }

    @Override
    public int getMaxLoanDays() {
        return 2;
    }
}