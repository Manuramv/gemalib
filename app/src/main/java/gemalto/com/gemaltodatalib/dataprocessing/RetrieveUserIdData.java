package gemalto.com.gemaltodatalib.dataprocessing;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import gemalto.com.gemaltodatalib.callbackinterface.PassUserIdInterface;
import gemalto.com.gemaltodatalib.callbackinterface.UserIdCallback;
import gemalto.com.gemaltodatalib.gemdatabase.DbHelper;
import gemalto.com.gemaltodatalib.networking.GemaltoContants;
import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import gemalto.com.gemaltodatalib.serviceimpl.CommonUtils;
import gemalto.com.gemaltodatalib.serviceimpl.UserIDQueryImpl;

/**
 * Created by Manuramv on 9/7/2018.
 */

public class RetrieveUserIdData  {

    private final AppCompatActivity mActivityObj;
    UserIDQueryImpl userIDQueryImplObj;
    UserIdCallback userIdCallbackObj;
    private ArrayList<UserResult> valueList;

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
            public void onSuccess(GetGenderQueryInfoResponse value) {
                Log.d("TAG","=======================Onsuccess mANU   INSIDE userid Data");
                valueList =value.getResults();
                CommonUtils.insertEmployeeintoDb(value,mActivityObj);


                passUserIdInterface.onReceivingUserIdDataFromlib(value);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("TAG","=======================ONERROR");
                //return null;

            }
        };

        if(CommonUtils.isConnectingToInternet(mActivityObj)){
            String url = GemaltoContants.END_POINT+"?seed="+seed;
            userIDQueryImplObj.triggerUserIdAPI(url,userIdCallbackObj);
        } else {
            Log.d("TAG","no internet and reading from database");
            GetGenderQueryInfoResponse obj = CommonUtils.returnSingleItemFromDb(mActivityObj, DbHelper.COLUMN_SEED,seed);
            if(obj!=null){
                passUserIdInterface.onReceivingUserIdDataFromlib(obj);
            } else {
                Log.d("TAG","No data from database");
                passUserIdInterface.onReceivingUserIdDataFromlib(null);
            }

        }

    }


}
