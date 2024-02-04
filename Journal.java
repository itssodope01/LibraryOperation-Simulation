class Journal extends LibraryItem {
    private String eISSN;
    private String publisher;
    private String latestIssue;
    private String journalURL;

    public Journal(String title, int libraryId, String eISSN, String publisher,
                   String latestIssue, String journalURL) {
        super(title, libraryId);
        this.eISSN = eISSN;
        this.publisher = publisher;
        this.latestIssue = latestIssue;
        this.journalURL = journalURL;
    }

    @Override
    public int getMaxLoanDays() {
        return 3;
    }
}
