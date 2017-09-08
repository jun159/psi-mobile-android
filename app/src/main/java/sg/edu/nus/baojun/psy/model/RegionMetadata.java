package sg.edu.nus.baojun.psy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class RegionMetadata {
    @SerializedName("name")
    private String name;
    @SerializedName("label_location")
    private LabelLocation labelLocation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LabelLocation getLabelLocation() {
        return labelLocation;
    }

    public void setLabelLocation(LabelLocation labelLocation) {
        this.labelLocation = labelLocation;
    }
}
