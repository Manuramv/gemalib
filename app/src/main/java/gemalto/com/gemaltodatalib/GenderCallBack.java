package gemalto.com.gemaltodatalib;

import android.support.annotation.NonNull;

import java.util.List;

import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;

/**
 * Created by Manuramv on 9/6/2018.
 */

public interface GenderCallBack {
    List<UserResult> onSuccess(GetGenderQueryInfoResponse value);

    void onError( Throwable throwable);
}
