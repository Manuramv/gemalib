package gemalto.com.gemaltodatalib.networking.response.genderquery;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import gemalto.com.gemaltodatalib.networking.response.genderquery.Dob;
import gemalto.com.gemaltodatalib.networking.response.genderquery.Id;
import gemalto.com.gemaltodatalib.networking.response.genderquery.Login;
import gemalto.com.gemaltodatalib.networking.response.genderquery.Registered;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserLocation;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserName;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserPicture;

/**
 * Created by Manuramv on 9/6/2018.
 */

public class UserResult implements Parcelable {
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("name")
    @Expose
    public UserName name;
    @SerializedName("location")
    @Expose
    private UserLocation location;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("login")
    @Expose
    private Login login;
    @SerializedName("dob")
    @Expose
    private Dob dob;
    @SerializedName("registered")
    @Expose
    private Registered registered;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("cell")
    @Expose
    private String cell;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("picture")
    @Expose
    private UserPicture picture;
    @SerializedName("nat")
    @Expose
    private String nat;

    public UserResult(Parcel in) {
        gender = in.readString();
        email = in.readString();
        phone = in.readString();
        cell = in.readString();
        nat = in.readString();
        //name = in.readParcelable(getClass().getClassLoader());
        name = in.readParcelable(UserName.class.getClassLoader());
        id = in.readParcelable(UserName.class.getClassLoader());
        dob = in.readParcelable(UserName.class.getClassLoader());

    }

    public static final Creator<UserResult> CREATOR = new Creator<UserResult>() {
        @Override
        public UserResult createFromParcel(Parcel in) {
            return new UserResult(in);
        }

        @Override
        public UserResult[] newArray(int size) {
            return new UserResult[size];
        }
    };

    public UserResult() {

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public UserPicture getPicture() {
        return picture;
    }

    public void setPicture(UserPicture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(gender);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(cell);
        parcel.writeString(nat);
        parcel.writeParcelable(name, i);
        parcel.writeParcelable(id, i);
        parcel.writeParcelable(dob, i);

    }
}
