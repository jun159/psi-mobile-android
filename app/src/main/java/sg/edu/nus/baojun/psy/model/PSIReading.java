package sg.edu.nus.baojun.psy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class PSIReading {
    @SerializedName("national")
    private int national;
    @SerializedName("south")
    private int south;
    @SerializedName("north")
    private int north;
    @SerializedName("east")
    private int east;
    @SerializedName("central")
    private int central;
    @SerializedName("west")
    private int west;

    public int getNational() {
        return national;
    }

    public void setNational(int national) {
        this.national = national;
    }

    public int getSouth() {
        return south;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getCentral() {
        return central;
    }

    public void setCentral(int central) {
        this.central = central;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }
}
