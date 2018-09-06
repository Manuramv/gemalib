package gemalto.com.gemaltodatalib.serviceimpl;

import java.util.List;

import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;

/**
 * Created by Manuramv on 9/6/2018.
 */

public interface PassGenderDataInterface {
    void onReceivingDataFromlib(List<UserResult> value);
}
