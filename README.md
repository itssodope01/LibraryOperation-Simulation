# LibraryOperation-Simulation
A simple program that simulates the operation of a library. The library users are 80 students and 20 faculty members. Out of them, 67 people always return the book on time, 33 can be delayed.. Simulation over 365 days.

All items have a unique library ID assigned. Books have Title, Author, Genre and
Publisher entries. Journals have Title, eISSN, Publisher, Latest issue and Journal URL entries.
Movies have Title, Genre, Director, Year, Runtime (Minutes) and Rating entries.

Abstract class LibraryItem (which is extended by Book, Journal and Film classes).
Method daysOverdue that returns the number of days this item is overdue.
Method isOverdue that produces a boolean value, that informs us whether the item is overdue on the given day.
Method computeFine, that computes the fine for this item, if the item is returned
with the delay.

All the instances, representing the library items are held in the Library class.
borrowItem method of the Library class that allows you to borrow any item on any
day. 

The methods ShowLoans and ShowStatistics, the first one prints all the items
that are one the loan with their details, the second one, prints how many and which items
were already lent, how many are on loan and overdue, and what is total fine collected.

UML Diagram is also presented.
