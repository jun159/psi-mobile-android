package sg.edu.nus.baojun.psy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class PSIReading implements Parcelable {
    public static final String WEST = "west";
    public static final String NATIONAL = "national";
    public static final String EAST = "east";
    public static final String CENTRAL = "central";
    public static final String SOUTH = "south";
    public static final String NORTH = "north";

    @SerializedName(WEST)
    private double west;
    @SerializedName(NATIONAL)
    private double national;
    @SerializedName(EAST)
    private double east;
    @SerializedName(CENTRAL)
    private double central;
    @SerializedName(SOUTH)
    private double south;
    @SerializedName(NORTH)
    private double north;

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    public double getNational() {
        return national;
    }

    public void setNational(double national) {
        this.national = national;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getCentral() {
        return central;
    }

    public void setCentral(double central) {
        this.central = central;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    private PSIReading(Parcel in) {
        west = in.readDouble();
        national = in.readDouble();
        east = in.readDouble();
        central = in.readDouble();
        south = in.readDouble();
        north = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(west);
        dest.writeDouble(national);
        dest.writeDouble(east);
        dest.writeDouble(central);
        dest.writeDouble(south);
        dest.writeDouble(north);
    }

    public static final Creator<PSIReading> CREATOR = new Creator<PSIReading>() {
        @Override
        public PSIReading createFromParcel(Parcel in) {
            return new PSIReading(in);
        }

        @Override
        public PSIReading[] newArray(int size) {
            return new PSIReading[size];
        }
    };
}
