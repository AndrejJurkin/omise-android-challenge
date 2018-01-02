package jurkin.tamboon.model;

/**
 * Donation model class used to serialize / deserialize the related JSON objects
 *
 * Created by Andrej Jurkin on 12/21/17.
 */

public class Donation {

    private String name;

    private String token;

    private long amount;

    public Donation() {
        // Empty constructor for GSON
    }

    public Donation(String name, String token, long amount) {
        this.name = name;
        this.token = token;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public long getAmount() {
        return amount;
    }
}
