# LibraryOperation-Simulation

## Overview

This program simulates the operation of a library over a 365-day period. It models a library system with various types of items, users, and borrowing behaviors.

## Features

### Library Users

- Total users: 100
  - 80 students
  - 20 faculty members
- User behavior:
  - 67 users always return items on time
  - 33 users may delay returns

### Library Items

All items have a unique library ID and are represented by the following classes:

1. **Books**

   - Attributes: Title, Author, Genre, Publisher

2. **Journals**

   - Attributes: Title, eISSN, Publisher, Latest issue, Journal URL

3. **Movies**
   - Attributes: Title, Genre, Director, Year, Runtime (Minutes), Rating

### Class Structure

- Abstract class `LibraryItem` (extended by `Book`, `Journal`, and `Film` classes)
- Methods in `LibraryItem`:
  - `daysOverdue()`: Returns the number of days an item is overdue
  - `isOverdue()`: Returns a boolean indicating if the item is overdue on a given day
  - `computeFine()`: Calculates the fine for an overdue item

### Library Management

- `Library` class holds all library item instances
- Methods in `Library` class:
  - `borrowItem()`: Allows borrowing any item on any day
  - `showLoans()`: Displays all items currently on loan with their details
  - `showStatistics()`: Provides statistics on lent items, overdue items, and total fines collected

## UML Diagram

A UML diagram is provided to illustrate the class structure and relationships.

## Usage

The main file to run the simulation is located in the `src` directory:

```
src/LibraryManagementSystem.java
```

## Compiling and Running the Program

### Prerequisites

- Ensure you have Java Development Kit (JDK) installed on your system. You can check this by running `java -version` in your terminal or command prompt.

### Compiling the Program

1. Open a terminal or command prompt.
2. Navigate to the `src` directory of the project:
   ```
   cd path/to/LibraryOperation-Simulation/src
   ```
3. Compile the Java files using the following command:
   ```
   javac LibraryManagementSystem.java
   ```
   This will compile the main file and all necessary dependent classes.

### Running the Program

1. After successful compilation, run the program using:
   ```
   java LibraryManagementSystem
   ```
2. The simulation results will be displayed in the terminal.

Note: If you encounter any classpath issues, you may need to set the classpath to the current directory:

```
java -cp . LibraryManagementSystem
```

## Output

Simulation results are displayed in the terminal.

## Getting Started

1. Clone the repository
2. Navigate to the `src` directory
3. Compile the Java files as described above
4. Run `LibraryManagementSystem`

## Contributing

Feel free to fork this project and submit pull requests with improvements or bug fixes.

## License

MIT License
