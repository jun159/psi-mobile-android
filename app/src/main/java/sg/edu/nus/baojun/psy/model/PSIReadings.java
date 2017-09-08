package sg.edu.nus.baojun.psy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class PSIReadings {
    @SerializedName("o3_sub_index")
    private PSIReading o3SubIndex;
    @SerializedName("pm10_twenty_four_hourly")
    private PSIReading pm10TwentyFourHourly;
    @SerializedName("pm10_sub_index")
    private PSIReading pm10SubIndex;
    @SerializedName("co_sub_index")
    private PSIReading coSubIndex;
    @SerializedName("pm25_twenty_four_hourly")
    private PSIReading pm25TwentyFourHourly;
    @SerializedName("so2_sub_index")
    private PSIReading so2SubIndex;
    @SerializedName("co_eight_hour_max")
    private PSIReading coEightHourMax;
    @SerializedName("no2_one_hour_max")
    private PSIReading no2OneHourMax;
    @SerializedName("so2_twenty_four_hourly")
    private PSIReading so2TwentyFourHourly;
    @SerializedName("pm25_sub_index")
    private PSIReading pm25SubIndex;
    @SerializedName("psi_twenty_four_hourly")
    private PSIReading psiTwentyFourHourly;
    @SerializedName("o3_eight_hour_max")
    private PSIReading o3EightHourMax;
    @SerializedName("psi_three_hourly")
    private PSIReading psiThreeHourly;

    public PSIReading getO3SubIndex() {
        return o3SubIndex;
    }

    public void setO3SubIndex(PSIReading o3SubIndex) {
        this.o3SubIndex = o3SubIndex;
    }

    public PSIReading getPm10TwentyFourHourly() {
        return pm10TwentyFourHourly;
    }

    public void setPm10TwentyFourHourly(PSIReading pm10TwentyFourHourly) {
        this.pm10TwentyFourHourly = pm10TwentyFourHourly;
    }

    public PSIReading getPm10SubIndex() {
        return pm10SubIndex;
    }

    public void setPm10SubIndex(PSIReading pm10SubIndex) {
        this.pm10SubIndex = pm10SubIndex;
    }

    public PSIReading getCoSubIndex() {
        return coSubIndex;
    }

    public void setCoSubIndex(PSIReading coSubIndex) {
        this.coSubIndex = coSubIndex;
    }

    public PSIReading getPm25TwentyFourHourly() {
        return pm25TwentyFourHourly;
    }

    public void setPm25TwentyFourHourly(PSIReading pm25TwentyFourHourly) {
        this.pm25TwentyFourHourly = pm25TwentyFourHourly;
    }

    public PSIReading getSo2SubIndex() {
        return so2SubIndex;
    }

    public void setSo2SubIndex(PSIReading so2SubIndex) {
        this.so2SubIndex = so2SubIndex;
    }

    public PSIReading getCoEightHourMax() {
        return coEightHourMax;
    }

    public void setCoEightHourMax(PSIReading coEightHourMax) {
        this.coEightHourMax = coEightHourMax;
    }

    public PSIReading getNo2OneHourMax() {
        return no2OneHourMax;
    }

    public void setNo2OneHourMax(PSIReading no2OneHourMax) {
        this.no2OneHourMax = no2OneHourMax;
    }

    public PSIReading getSo2TwentyFourHourly() {
        return so2TwentyFourHourly;
    }

    public void setSo2TwentyFourHourly(PSIReading so2TwentyFourHourly) {
        this.so2TwentyFourHourly = so2TwentyFourHourly;
    }

    public PSIReading getPm25SubIndex() {
        return pm25SubIndex;
    }

    public void setPm25SubIndex(PSIReading pm25SubIndex) {
        this.pm25SubIndex = pm25SubIndex;
    }

    public PSIReading getPsiTwentyFourHourly() {
        return psiTwentyFourHourly;
    }

    public void setPsiTwentyFourHourly(PSIReading psiTwentyFourHourly) {
        this.psiTwentyFourHourly = psiTwentyFourHourly;
    }

    public PSIReading getO3EightHourMax() {
        return o3EightHourMax;
    }

    public void setO3EightHourMax(PSIReading o3EightHourMax) {
        this.o3EightHourMax = o3EightHourMax;
    }

    public PSIReading getPsiThreeHourly() {
        return psiThreeHourly;
    }

    public void setPsiThreeHourly(PSIReading psiThreeHourly) {
        this.psiThreeHourly = psiThreeHourly;
    }
}
