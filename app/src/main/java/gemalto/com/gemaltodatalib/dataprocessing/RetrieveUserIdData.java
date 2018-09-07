package gemalto.com.gemaltodatalib.dataprocessing;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gemalto.com.gemaltodatalib.GenderCallBack;
import gemalto.com.gemaltodatalib.callbackinterface.PassUserIdInterface;
import gemalto.com.gemaltodatalib.callbackinterface.UserIdCallback;
import gemalto.com.gemaltodatalib.networking.GemaltoContants;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import gemalto.com.gemaltodatalib.serviceimpl.GenderQueryImpl;
import gemalto.com.gemaltodatalib.serviceimpl.PassGenderDataInterface;
import gemalto.com.gemaltodatalib.serviceimpl.UserIDQueryImpl;

/**
 * Created by Manuramv on 9/7/2018.
 */

public class RetrieveUserIdData  {

    private final AppCompatActivity mActivityObj;
    UserIDQueryImpl userIDQueryImplObj;
    UserIdCallback userIdCallbackObj;

    public RetrieveUserIdData(AppCompatActivity mActivityObj) {
        this.mActivityObj = mActivityObj;
    }


    public void initiateUserIDQuery(String seed, final PassUserIdInterface passUserIdInterface){
        userIDQueryImplObj = new UserIDQueryImpl(mActivityObj);
        userIdCallbackObj = new UserIdCallback() {
           /* @Override
            public void onSuccess(List<UserResult> value) {
                passGenderDataInterfaceCallBack.onReceivingDataFromlib(value);
            }*/

            @Override
            public void onSuccess(List<UserResult> value) {
                Log.d("TAG","=======================Onsuccess mANU   INSIDE userid Data");
                passUserIdInterface.onReceivingUserIdDataFromlib(value);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("TAG","=======================ONERROR mANU   INSIDE userid Data");
                //return null;

            }
        };
        String url = GemaltoContants.END_POINT+"?seed="+seed;
        userIDQueryImplObj.triggerUserIdAPI(url,userIdCallbackObj);

    }
}
