import java.io.Serializable;

/**
 * Represents a hotel guest.
 */
public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String phone;
    private final String email;

    public Guest(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " (" + phone + ", " + email + ")";
    }
}
