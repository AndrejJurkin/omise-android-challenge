package jurkin.tamboon.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrej Jurkin on 12/21/17.
 */

public class Charity {

    private int id;

    private String name;

    @SerializedName("logo_url")
    private String logoUrl;

    public Charity() {
        // Empty constructor needed for GSON
    }

    public Charity(int id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
