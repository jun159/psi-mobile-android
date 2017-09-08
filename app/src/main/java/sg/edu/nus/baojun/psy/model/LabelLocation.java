package sg.edu.nus.baojun.psy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class LabelLocation {
    @SerializedName("longtitude")
    private double longtitude;
    @SerializedName("latitude")
    private double latitude;

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
