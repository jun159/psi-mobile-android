package sg.edu.nus.baojun.psy.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import sg.edu.nus.baojun.psy.model.APIInfo;
import sg.edu.nus.baojun.psy.model.PSIItem;
import sg.edu.nus.baojun.psy.model.RegionMetadata;

/**
 * Created by BAOJUN on 9/8/17.
 */

public class GetPSIResponse {
    @SerializedName("region_metadata")
    private List<RegionMetadata> regionMetadataList;
    @SerializedName("items")
    private List<PSIItem> psiItemList;
    @SerializedName("api_info")
    private APIInfo apiInfo;

    public List<RegionMetadata> getRegionMetadataList() {
        return regionMetadataList;
    }

    public void setRegionMetadataList(List<RegionMetadata> regionMetadataList) {
        this.regionMetadataList = regionMetadataList;
    }

    public List<PSIItem> getPsiItemList() {
        return psiItemList;
    }

    public void setPsiItemList(List<PSIItem> psiItemList) {
        this.psiItemList = psiItemList;
    }

    public APIInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(APIInfo apiInfo) {
        this.apiInfo = apiInfo;
    }
}
