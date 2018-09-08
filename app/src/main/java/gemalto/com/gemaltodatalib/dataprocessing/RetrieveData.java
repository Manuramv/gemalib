package gemalto.com.gemaltodatalib.dataprocessing;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gemalto.com.gemaltodatalib.GenderCallBack;
import gemalto.com.gemaltodatalib.gemdatabase.DbHelper;
import gemalto.com.gemaltodatalib.gemdatabase.Employee;
import gemalto.com.gemaltodatalib.gemdatabase.EmployeeOperations;
import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import gemalto.com.gemaltodatalib.serviceimpl.CommonUtils;
import gemalto.com.gemaltodatalib.serviceimpl.GenderQueryImpl;
import gemalto.com.gemaltodatalib.serviceimpl.PassGenderDataInterface;

/**
 * Created by Manuramv on 9/5/2018.
 */

public class RetrieveData  {
 private AppCompatActivity mActivityObj;
    private java.lang.String server_response;
    private EmployeeOperations employeeOps;
    private Employee newEmployee;

    public List<UserResult> getValueList() {
        return valueList;
    }

    public void setValueList(List<UserResult> valueList) {
        this.valueList = valueList;
    }

    private List<UserResult> valueList;
    GenderCallBack genderCallBackObj;
    GenderQueryImpl genderQueryObj;

    public RetrieveData(AppCompatActivity mActivityObj) {
        this.mActivityObj = mActivityObj;
    }

    public void initiateGenderQuery(String gender, final PassGenderDataInterface passGenderDataInterfaceCallBack){
        List<UserResult> userResult = new ArrayList<>();
        Log.d("TAG","AAR library initiateGenderQuery::"+mActivityObj);
         genderQueryObj = new GenderQueryImpl(mActivityObj);
         genderCallBackObj = new GenderCallBack() {
            @Override
            public List<UserResult> onSuccess(GetGenderQueryInfoResponse value) {
                valueList =value.getResults();
                setValueList(value.getResults());
                Log.d("TAG","=======================ONSUCCESS ");
                Log.d("TAG","=======================ONSUCCESS ");

                CommonUtils.insertEmployeeintoDb(value,mActivityObj);
                passGenderDataInterfaceCallBack.onReceivingDataFromlib(value);

                return valueList;
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("TAG","=======================ONERROR mANU   INSIDE");
                //return null;

            }
        };
        if(CommonUtils.isConnectingToInternet(mActivityObj)){
            Log.d("TAG","connected to internet and reading from APi");
            genderQueryObj.triggerGenderInfoAPI(gender,genderCallBackObj);
        } else {
            Log.d("TAG","no internet and reading from database");
            GetGenderQueryInfoResponse obj = CommonUtils.returnSingleItemFromDb(mActivityObj, DbHelper.COLUMN_GENDER,gender);
            if(obj!=null){
                passGenderDataInterfaceCallBack.onReceivingDataFromlib(obj);
            } else {
                Log.d("TAG","No data from database");
                passGenderDataInterfaceCallBack.onReceivingDataFromlib(null);
            }
        }
    }

}
