package jurkin.tamboon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Charity model class used to serialize / deserialize related JSON objects
 *
 * Created by Andrej Jurkin on 12/21/17.
 */

public class Charity implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.logoUrl);
    }

    protected Charity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.logoUrl = in.readString();
    }

    public static final Parcelable.Creator<Charity> CREATOR = new Parcelable.Creator<Charity>() {
        @Override
        public Charity createFromParcel(Parcel source) {
            return new Charity(source);
        }

        @Override
        public Charity[] newArray(int size) {
            return new Charity[size];
        }
    };
}
