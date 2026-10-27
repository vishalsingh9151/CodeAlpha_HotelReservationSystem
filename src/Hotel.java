import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Core OOP controller class: manages the room inventory and all reservations,
 * and persists both to disk through FileHandler after every change.
 */
public class Hotel {

    private final List<Room> rooms;
    private final List<Reservation> reservations;
    private int nextReservationId;

    public Hotel() {
        this.rooms = FileHandler.loadRooms();
        this.reservations = FileHandler.loadReservations();

        if (rooms.isEmpty()) {
            initializeDefaultRooms();
        }
        this.nextReservationId = reservations.stream()
                .mapToInt(Reservation::getReservationId)
                .max()
                .orElse(0) + 1;
    }

    /** Seeds the hotel with a default set of rooms on first run. */
    private void initializeDefaultRooms() {
        int roomNumber = 101;
        for (int i = 0; i < 5; i++) rooms.add(new Room(roomNumber++, Room.Category.STANDARD));
        roomNumber = 201;
        for (int i = 0; i < 3; i++) rooms.add(new Room(roomNumber++, Room.Category.DELUXE));
        roomNumber = 301;
        for (int i = 0; i < 2; i++) rooms.add(new Room(roomNumber++, Room.Category.SUITE));
        FileHandler.saveRooms(rooms);
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public List<Room> getAvailableRooms(Room.Category category) {
        List<Room> result = new ArrayList<>();
        for (Room r : rooms) {
            if (!r.isBooked() && (category == null || r.getCategory() == category)) {
                result.add(r);
            }
        }
        return result;
    }

    private Optional<Room> findRoom(int roomNumber) {
        return rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst();
    }

    /**
     * Books a room for a guest across the given date range.
     * Returns the created Reservation, or empty if the room is unavailable
     * or the dates are invalid.
     */
    public Optional<Reservation> bookRoom(Guest guest, int roomNumber,
                                           LocalDate checkIn, LocalDate checkOut) {
        Optional<Room> roomOpt = findRoom(roomNumber);
        if (roomOpt.isEmpty()) {
            System.out.println("No such room number.");
            return Optional.empty();
        }
        Room room = roomOpt.get();
        if (room.isBooked()) {
            System.out.println("Room " + roomNumber + " is already booked.");
            return Optional.empty();
        }
        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Check-out date must be after check-in date.");
            return Optional.empty();
        }

        Reservation reservation = new Reservation(nextReservationId++, guest, room, checkIn, checkOut);
        room.setBooked(true);
        reservations.add(reservation);

        FileHandler.saveRooms(rooms);
        FileHandler.saveReservations(reservations);

        return Optional.of(reservation);
    }

    /** Simulates processing payment for a reservation. */
    public boolean processPayment(Reservation reservation) {
        // Simulated payment gateway: always succeeds in this simulation.
        reservation.setPaymentStatus(Reservation.PaymentStatus.PAID);
        FileHandler.saveReservations(reservations);
        return true;
    }

    public boolean cancelReservation(int reservationId) {
        Optional<Reservation> resOpt = reservations.stream()
                .filter(r -> r.getReservationId() == reservationId && r.getStatus() == Reservation.Status.BOOKED)
                .findFirst();

        if (resOpt.isEmpty()) {
            System.out.println("No active reservation found with that ID.");
            return false;
        }

        Reservation reservation = resOpt.get();
        reservation.setStatus(Reservation.Status.CANCELLED);
        if (reservation.getPaymentStatus() == Reservation.PaymentStatus.PAID) {
            reservation.setPaymentStatus(Reservation.PaymentStatus.REFUNDED);
        }

        findRoom(reservation.getRoomNumber()).ifPresent(room -> room.setBooked(false));

        FileHandler.saveRooms(rooms);
        FileHandler.saveReservations(reservations);
        return true;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Optional<Reservation> findReservation(int reservationId) {
        return reservations.stream().filter(r -> r.getReservationId() == reservationId).findFirst();
    }
}
