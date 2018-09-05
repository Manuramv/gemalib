package gemalto.com.gemaltodatalib.dataprocessing;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import gemalto.com.gemaltodatalib.serviceimpl.GenderQueryImpl;

/**
 * Created by Manuramv on 9/5/2018.
 */

public class RetrieveData {
 private AppCompatActivity mActivityObj;

    public RetrieveData(AppCompatActivity mActivityObj) {
        this.mActivityObj = mActivityObj;
    }

    public List<UserResult> initiateGenderQuery(String gender){
        List<UserResult> userResult = new ArrayList<>();
        Log.d("TAG","AAR library initiateGenderQuery::"+mActivityObj);
        GenderQueryImpl genderQueryObj = new GenderQueryImpl(mActivityObj);
        userResult = genderQueryObj.getGenderQueryInfo(gender);
        Log.d("TAG","Returning data from the library project::"+userResult.toString()+"''size==+"+userResult.size()+"..manu phone"+userResult.get(0).getPhone());
         return userResult;
    }
    public void initiateSeedQuery(){
        Log.d("TAG","AAR library initiateGenderQuery::"+mActivityObj);
    }
    public void initiateResultQuery(){
        Log.d("TAG","AAR library initiateGenderQuery::"+mActivityObj);
    }

}
