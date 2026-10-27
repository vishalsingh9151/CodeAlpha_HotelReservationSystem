import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading Room and Reservation data to/from disk
 * using Java object serialization (File I/O persistence layer).
 */
public class FileHandler {

    private static final String ROOMS_FILE = "rooms.dat";
    private static final String RESERVATIONS_FILE = "reservations.dat";

    @SuppressWarnings("unchecked")
    public static List<Room> loadRooms() {
        File file = new File(ROOMS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Room>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Warning: could not load rooms.dat (" + e.getMessage() + "). Starting fresh.");
            return new ArrayList<>();
        }
    }

    public static void saveRooms(List<Room> rooms) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROOMS_FILE))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            System.out.println("Error saving rooms: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Reservation> loadReservations() {
        File file = new File(RESERVATIONS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Reservation>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Warning: could not load reservations.dat (" + e.getMessage() + "). Starting fresh.");
            return new ArrayList<>();
        }
    }

    public static void saveReservations(List<Reservation> reservations) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RESERVATIONS_FILE))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }
}
