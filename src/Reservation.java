import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a booking that links a Guest to a Room for a date range,
 * including the simulated payment status.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status { BOOKED, CANCELLED }
    public enum PaymentStatus { PENDING, PAID, REFUNDED }

    private final int reservationId;
    private final Guest guest;
    private final int roomNumber;
    private final Room.Category roomCategory;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final double totalAmount;
    private Status status;
    private PaymentStatus paymentStatus;

    public Reservation(int reservationId, Guest guest, Room room,
                        LocalDate checkIn, LocalDate checkOut) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.roomNumber = room.getRoomNumber();
        this.roomCategory = room.getCategory();
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalAmount = nights * room.getPricePerNight();
        this.status = Status.BOOKED;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Room.Category getRoomCategory() {
        return roomCategory;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public long getNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return String.format(
                "Reservation #%d | Guest: %s | Room #%d (%s) | %s to %s (%d nights) | Total: Rs.%.2f | Status: %s | Payment: %s",
                reservationId, guest.getName(), roomNumber, roomCategory,
                checkIn, checkOut, getNights(), totalAmount, status, paymentStatus);
    }
}
