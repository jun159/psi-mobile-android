package sg.edu.nus.baojun.psy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class PSIItem {
    @SerializedName("timestamp")
    private String timeStamp;
    @SerializedName("update_timestamp")
    private String updateTimeStamp;
    @SerializedName("readings")
    private PSIReadings psiReadings;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timestamp) {
        this.timeStamp = timestamp;
    }

    public String getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(String updateTimestamp) {
        this.updateTimeStamp = updateTimestamp;
    }

    public PSIReadings getPsiReadings() {
        return psiReadings;
    }

    public void setPsiReadings(PSIReadings psiReadings) {
        this.psiReadings = psiReadings;
    }
}
