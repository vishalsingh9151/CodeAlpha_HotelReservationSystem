/**
 * CodeAlpha Task 4: Hotel Reservation System
 * Console-based front end. Search/view rooms, book, cancel, and view bookings.
 * All data is persisted to rooms.dat / reservations.dat via FileHandler.
 */
private static final Scanner sc = new Scanner(System.in);
private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
private static final Hotel hotel = new Hotel();

void main() {
    IO.println("=======================================");
    IO.println("   Welcome to CodeAlpha Grand Hotel");
    IO.println("=======================================");

    boolean running = true;
    while (running) {
        printMenu();
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1" -> viewAvailableRooms();
            case "2" -> bookRoom();
            case "3" -> cancelReservation();
            case "4" -> viewAllBookings();
            case "5" -> viewBookingDetails();
            case "6" -> {
                IO.println("Thank you for using CodeAlpha Grand Hotel. Goodbye!");
                running = false;
            }
            default -> IO.println("Invalid choice, please try again.");
        }
        IO.println();
    }
    sc.close();
}

private static void printMenu() {
    IO.println("---------------- MENU ----------------");
    IO.println("1. View available rooms");
    IO.println("2. Book a room");
    IO.println("3. Cancel a reservation");
    IO.println("4. View all bookings");
    IO.println("5. View a specific booking");
    IO.println("6. Exit");
    IO.print("Enter choice: ");
}

private static void viewAvailableRooms() {
    IO.println("Filter by category? (STANDARD / DELUXE / SUITE / ALL): ");
    String input = sc.nextLine().trim().toUpperCase();
    Room.Category category = null;
    if (!input.equals("ALL") && !input.isEmpty()) {
        try {
            category = Room.Category.valueOf(input);
        } catch (IllegalArgumentException e) {
            IO.println("Unknown category, showing all rooms.");
        }
    }

    List<Room> available = hotel.getAvailableRooms(category);
    if (available.isEmpty()) {
        IO.println("No available rooms match that filter.");
        return;
    }
    IO.println("Available rooms:");
    for (Room r : available) {
        IO.println("  " + r);
    }
}

private static void bookRoom() {
    IO.print("Guest name: ");
    String name = sc.nextLine().trim();
    IO.print("Guest phone: ");
    String phone = sc.nextLine().trim();
    IO.print("Guest email: ");
    String email = sc.nextLine().trim();
    Guest guest = new Guest(name, phone, email);

    viewAvailableRooms();
    IO.print("Enter room number to book: ");
    int roomNumber;
    try {
        roomNumber = Integer.parseInt(sc.nextLine().trim());
    } catch (NumberFormatException e) {
        IO.println("Invalid room number.");
        return;
    }

    LocalDate checkIn = readDate("Check-in date (yyyy-MM-dd): ");
    if (checkIn == null) return;
    LocalDate checkOut = readDate("Check-out date (yyyy-MM-dd): ");
    if (checkOut == null) return;

    Optional<Reservation> result = hotel.bookRoom(guest, roomNumber, checkIn, checkOut);
    if (result.isPresent()) {
        Reservation reservation = result.get();
        IO.println("Room booked successfully!");
        IO.println(reservation);

        IO.print("Proceed with payment simulation now? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            boolean paid = hotel.processPayment(reservation);
            IO.println(paid
                    ? "Payment successful. Payment status: " + reservation.getPaymentStatus()
                    : "Payment failed.");
        }
    } else {
        IO.println("Booking could not be completed.");
    }
}

private static void cancelReservation() {
    IO.print("Enter reservation ID to cancel: ");
    int id;
    try {
        id = Integer.parseInt(sc.nextLine().trim());
    } catch (NumberFormatException e) {
        IO.println("Invalid reservation ID.");
        return;
    }
    boolean success = hotel.cancelReservation(id);
    if (success) {
        IO.println("Reservation #" + id + " cancelled. Room is now available again.");
    }
}

private static void viewAllBookings() {
    List<Reservation> all = hotel.getAllReservations();
    if (all.isEmpty()) {
        IO.println("No bookings yet.");
        return;
    }
    IO.println("All bookings:");
    for (Reservation r : all) {
        IO.println("  " + r);
    }
}

private static void viewBookingDetails() {
    IO.print("Enter reservation ID: ");
    int id;
    try {
        id = Integer.parseInt(sc.nextLine().trim());
    } catch (NumberFormatException e) {
        IO.println("Invalid reservation ID.");
        return;
    }
    Optional<Reservation> res = hotel.findReservation(id);
    if (res.isPresent()) {
        IO.println(res.get());
    } else {
        IO.println("No reservation found with that ID.");
    }
}

private static LocalDate readDate(String prompt) {
    IO.print(prompt);
    String input = sc.nextLine().trim();
    try {
        return LocalDate.parse(input, DATE_FMT);
    } catch (DateTimeParseException e) {
        IO.println("Invalid date format. Please use yyyy-MM-dd.");
        return null;
    }
}
