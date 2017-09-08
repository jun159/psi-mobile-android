package sg.edu.nus.baojun.psy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BAOJUN on 9/6/17.
 */

public class RegionMetadata {
    @SerializedName("name")
    private String name;
    @SerializedName("label_location")
    private LabelLocation label_location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LabelLocation getLabel_location() {
        return label_location;
    }

    public void setLabel_location(LabelLocation label_location) {
        this.label_location = label_location;
    }
}
