import java.io.Serializable;

/**
 * Represents a single hotel room.
 * Implements Serializable so room data can be persisted to disk with File I/O.
 */
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Category {
        STANDARD(1500.0),
        DELUXE(2800.0),
        SUITE(4500.0);

        private final double basePrice;

        Category(double basePrice) {
            this.basePrice = basePrice;
        }

        public double getBasePrice() {
            return basePrice;
        }
    }

    private final int roomNumber;
    private final Category category;
    private boolean booked;

    public Room(int roomNumber, Category category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.booked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Category getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return category.getBasePrice();
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        String status = booked ? "BOOKED" : "AVAILABLE";
        return String.format("Room #%-4d | %-8s | Rs.%-8.2f/night | %s",
                roomNumber, category, getPricePerNight(), status);
    }
}
