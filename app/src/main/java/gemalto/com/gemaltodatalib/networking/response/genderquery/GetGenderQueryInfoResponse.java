package gemalto.com.gemaltodatalib.networking.response.genderquery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Manuramv on 9/6/2018.
 */

public class GetGenderQueryInfoResponse {

    @SerializedName("results")
    @Expose
    private List<UserResult> results = null;
    @SerializedName("info")
    @Expose
    private UserInfo info;

    public List<UserResult> getResults() {
        return results;
    }

    public void setResults(List<UserResult> results) {
        this.results = results;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }
}
