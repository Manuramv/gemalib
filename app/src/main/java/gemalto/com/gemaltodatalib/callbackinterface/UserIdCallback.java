package gemalto.com.gemaltodatalib.callbackinterface;

import java.util.List;

import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;

/**
 * Created by Manuramv on 9/7/2018.
 */

public interface UserIdCallback {
    void onSuccess(List<UserResult> value);

    void onError( Throwable throwable);
}
