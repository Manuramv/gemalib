package gemalto.com.gemaltodatalib.dataprocessing;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gemalto.com.gemaltodatalib.callbackinterface.MultipleUserCallback;
import gemalto.com.gemaltodatalib.callbackinterface.PassMultipleUserIdInterface;
import gemalto.com.gemaltodatalib.callbackinterface.PassUserIdInterface;
import gemalto.com.gemaltodatalib.callbackinterface.UserIdCallback;
import gemalto.com.gemaltodatalib.gemdatabase.DbHelper;
import gemalto.com.gemaltodatalib.networking.GemaltoContants;
import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import gemalto.com.gemaltodatalib.serviceimpl.CommonUtils;
import gemalto.com.gemaltodatalib.serviceimpl.MultipleUserDataImpl;
import gemalto.com.gemaltodatalib.serviceimpl.UserIDQueryImpl;

/**
 * Created by Manuramv on 9/7/2018.
 */

public class RetrieveMultipleUserData {
    private final AppCompatActivity mActivityObj;
    MultipleUserDataImpl multipleUserDataImplObj;
    MultipleUserCallback multipleUserCallbackObj;

    public RetrieveMultipleUserData(AppCompatActivity mActivityObj) {
        this.mActivityObj = mActivityObj;
    }


    public void initiateMultipleUserQuery(String count, final PassMultipleUserIdInterface passMultipleUserIdInterface){
        multipleUserDataImplObj = new MultipleUserDataImpl(mActivityObj);
        multipleUserCallbackObj = new MultipleUserCallback() {
            @Override
            public void onSuccess(GetGenderQueryInfoResponse value) {
                Log.d("TAG","=======================Onsuccess mANU   INSIDE multiple user Data");

                CommonUtils.insertAvailableEmployeeintoDb(value,mActivityObj);
                passMultipleUserIdInterface.onReceivingMultipleUserDataFromlib(value);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("TAG","=======================ONERROR mANU   INSIDE userid Data");
                //return null;

            }
        };

        if(CommonUtils.isConnectingToInternet(mActivityObj)){
            String url = GemaltoContants.END_POINT+"?results="+count;
            multipleUserDataImplObj.triggerMultipleUserAPI(url,multipleUserCallbackObj);

        } else {
            Log.d("TAG","no internet and reading from database");
            GetGenderQueryInfoResponse obj = CommonUtils.returnSingleItemFromDb(mActivityObj, DbHelper.COLUMN_SEED,count);
            if(obj!=null){
                passMultipleUserIdInterface.onReceivingMultipleUserDataFromlib(obj);
            } else {
                Log.d("TAG","No data from database");
                passMultipleUserIdInterface.onReceivingMultipleUserDataFromlib(null);
            }

        }

    }
}
