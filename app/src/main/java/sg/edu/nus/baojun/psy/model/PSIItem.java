package sg.edu.nus.baojun.psy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class PSIItem {
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("update_timestamp")
    private String updateTimestamp;
    @SerializedName("readings")
    private PSIReadings psiReadings;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public PSIReadings getPsiReadings() {
        return psiReadings;
    }

    public void setPsiReadings(PSIReadings psiReadings) {
        this.psiReadings = psiReadings;
    }
}
