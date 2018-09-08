package gemalto.com.gemaltodatalib.networking.response.genderquery;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuramv on 9/6/2018.
 */

public class GetGenderQueryInfoResponse implements Parcelable {

    @SerializedName("results")
    @Expose
    public ArrayList<UserResult> results;
    @SerializedName("info")
    @Expose
    public UserInfo info;

    public GetGenderQueryInfoResponse(Parcel in) {
        results = in.createTypedArrayList(UserResult.CREATOR);
    }

    public static final Creator<GetGenderQueryInfoResponse> CREATOR = new Creator<GetGenderQueryInfoResponse>() {
        @Override
        public GetGenderQueryInfoResponse createFromParcel(Parcel in) {
            return new GetGenderQueryInfoResponse(in);
        }

        @Override
        public GetGenderQueryInfoResponse[] newArray(int size) {
            return new GetGenderQueryInfoResponse[size];
        }
    };

    public GetGenderQueryInfoResponse() {

    }

    public ArrayList<UserResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<UserResult> results) {
        this.results = results;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
    }
}
