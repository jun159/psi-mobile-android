package sg.edu.nus.baojun.psy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the PSI Readings for each region
 */

public class PSIRegionReadings implements Parcelable {
    @SerializedName("o3_sub_index")
    private String o3SubIndex;
    @SerializedName("pm10_twenty_four_hourly")
    private String pm10TwentyFourHourly;
    @SerializedName("pm10_sub_index")
    private String pm10SubIndex;
    @SerializedName("co_sub_index")
    private String coSubIndex;
    @SerializedName("pm25_twenty_four_hourly")
    private String pm25TwentyFourHourly;
    @SerializedName("so2_sub_index")
    private String so2SubIndex;
    @SerializedName("co_eight_hour_max")
    private String coEightHourMax;
    @SerializedName("no2_one_hour_max")
    private String no2OneHourMax;
    @SerializedName("so2_twenty_four_hourly")
    private String so2TwentyFourHourly;
    @SerializedName("pm25_sub_index")
    private String pm25SubIndex;
    @SerializedName("psi_twenty_four_hourly")
    private String psiTwentyFourHourly;
    @SerializedName("o3_eight_hour_max")
    private String o3EightHourMax;

    public PSIRegionReadings(String o3SubIndex, String pm10TwentyFourHourly, String pm10SubIndex,
                             String coSubIndex, String pm25TwentyFourHourly, String so2SubIndex,
                             String coEightHourMax, String no2OneHourMax, String so2TwentyFourHourly,
                             String pm25SubIndex, String psiTwentyFourHourly, String o3EightHourMax) {
        this.o3SubIndex = o3SubIndex;
        this.pm10TwentyFourHourly = pm10TwentyFourHourly;
        this.pm10SubIndex = pm10SubIndex;
        this.coSubIndex = coSubIndex;
        this.pm25TwentyFourHourly = pm25TwentyFourHourly;
        this.so2SubIndex = so2SubIndex;
        this.coEightHourMax = coEightHourMax;
        this.no2OneHourMax = no2OneHourMax;
        this.so2TwentyFourHourly = so2TwentyFourHourly;
        this.pm25SubIndex = pm25SubIndex;
        this.psiTwentyFourHourly = psiTwentyFourHourly;
        this.o3EightHourMax = o3EightHourMax;
    }

    public String getO3SubIndex() {
        return o3SubIndex;
    }

    public void setO3SubIndex(String o3SubIndex) {
        this.o3SubIndex = o3SubIndex;
    }

    public String getPm10TwentyFourHourly() {
        return pm10TwentyFourHourly;
    }

    public void setPm10TwentyFourHourly(String pm10TwentyFourHourly) {
        this.pm10TwentyFourHourly = pm10TwentyFourHourly;
    }

    public String getPm10SubIndex() {
        return pm10SubIndex;
    }

    public void setPm10SubIndex(String pm10SubIndex) {
        this.pm10SubIndex = pm10SubIndex;
    }

    public String getCoSubIndex() {
        return coSubIndex;
    }

    public void setCoSubIndex(String coSubIndex) {
        this.coSubIndex = coSubIndex;
    }

    public String getPm25TwentyFourHourly() {
        return pm25TwentyFourHourly;
    }

    public void setPm25TwentyFourHourly(String pm25TwentyFourHourly) {
        this.pm25TwentyFourHourly = pm25TwentyFourHourly;
    }

    public String getSo2SubIndex() {
        return so2SubIndex;
    }

    public void setSo2SubIndex(String so2SubIndex) {
        this.so2SubIndex = so2SubIndex;
    }

    public String getCoEightHourMax() {
        return coEightHourMax;
    }

    public void setCoEightHourMax(String coEightHourMax) {
        this.coEightHourMax = coEightHourMax;
    }

    public String getNo2OneHourMax() {
        return no2OneHourMax;
    }

    public void setNo2OneHourMax(String no2OneHourMax) {
        this.no2OneHourMax = no2OneHourMax;
    }

    public String getSo2TwentyFourHourly() {
        return so2TwentyFourHourly;
    }

    public void setSo2TwentyFourHourly(String so2TwentyFourHourly) {
        this.so2TwentyFourHourly = so2TwentyFourHourly;
    }

    public String getPm25SubIndex() {
        return pm25SubIndex;
    }

    public void setPm25SubIndex(String pm25SubIndex) {
        this.pm25SubIndex = pm25SubIndex;
    }

    public String getPsiTwentyFourHourly() {
        return psiTwentyFourHourly;
    }

    public void setPsiTwentyFourHourly(String psiTwentyFourHourly) {
        this.psiTwentyFourHourly = psiTwentyFourHourly;
    }

    public String getO3EightHourMax() {
        return o3EightHourMax;
    }

    public void setO3EightHourMax(String o3EightHourMax) {
        this.o3EightHourMax = o3EightHourMax;
    }

    private PSIRegionReadings(Parcel in) {
        o3SubIndex = in.readString();
        pm10TwentyFourHourly = in.readString();
        pm10SubIndex = in.readString();
        coSubIndex = in.readString();
        pm25TwentyFourHourly = in.readString();
        so2SubIndex = in.readString();
        coEightHourMax = in.readString();
        no2OneHourMax = in.readString();
        so2TwentyFourHourly = in.readString();
        pm25SubIndex = in.readString();
        psiTwentyFourHourly = in.readString();
        o3EightHourMax = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(o3SubIndex);
        dest.writeString(pm10TwentyFourHourly);
        dest.writeString(pm10SubIndex);
        dest.writeString(coSubIndex);
        dest.writeString(pm25TwentyFourHourly);
        dest.writeString(so2SubIndex);
        dest.writeString(coEightHourMax);
        dest.writeString(no2OneHourMax);
        dest.writeString(so2TwentyFourHourly);
        dest.writeString(pm25SubIndex);
        dest.writeString(psiTwentyFourHourly);
        dest.writeString(o3EightHourMax);
    }

    public static final Creator<PSIRegionReadings> CREATOR = new Creator<PSIRegionReadings>() {
        @Override
        public PSIRegionReadings createFromParcel(Parcel in) {
            return new PSIRegionReadings(in);
        }

        @Override
        public PSIRegionReadings[] newArray(int size) {
            return new PSIRegionReadings[size];
        }
    };
}
