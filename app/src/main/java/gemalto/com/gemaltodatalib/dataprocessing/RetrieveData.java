package gemalto.com.gemaltodatalib.dataprocessing;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gemalto.com.gemaltodatalib.GenderCallBack;
import gemalto.com.gemaltodatalib.networking.Apiclient;
import gemalto.com.gemaltodatalib.networking.GemaltoContants;
import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import gemalto.com.gemaltodatalib.serviceimpl.GenderQueryImpl;
import gemalto.com.gemaltodatalib.serviceimpl.PassGenderDataInterface;
import retrofit2.Call;

/**
 * Created by Manuramv on 9/5/2018.
 */

public class RetrieveData implements GenderCallBack {
 private AppCompatActivity mActivityObj;
    private java.lang.String server_response;

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

    public List<UserResult> initiateGenderQuery(String gender, final PassGenderDataInterface passGenderDataInterfaceCallBack){
        List<UserResult> userResult = new ArrayList<>();
        Log.d("TAG","AAR library initiateGenderQuery::"+mActivityObj);
         genderQueryObj = new GenderQueryImpl(mActivityObj);
        //genderQueryObj.getGenderQueryInfo(gender);
        //userResult = genderQueryObj.getGenderQueryInfo(gender);
        //Log.d("TAG","Returning data from the library project::"+userResult.toString()+"''size==+"+userResult.size()+"..manu phone"+userResult.get(0).getPhone());

         genderCallBackObj = new GenderCallBack() {
            @Override
            public List<UserResult> onSuccess(List<UserResult> value) {
                valueList =value;
                setValueList(value);
                Log.d("TAG","=======================ONSUCCESS mANU   INSIDE::::"+value.get(0).getPhone());
                Log.d("TAG","=======================ONSUCCESS mANU   INSIDE::::"+valueList.get(0).getPhone());
                passGenderDataInterfaceCallBack.onReceivingDataFromlib(value);

                return value;
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("TAG","=======================ONERROR mANU   INSIDE");
                //return null;

            }
        };
        //revealCourtPlace("ll",genderCallBackObj);
        //GenderCallBack kk = null;

        String url = GemaltoContants.END_POINT+"?gender="+"female";
        Uri genderQueryURL = Uri.parse(url);

       //genderQueryObj.revealCourtPlace("vv",genderCallBackObj);
        Log.d("TAG","=======================Value returning::"+valueList);
         //return getValueList();

        try {
            new MyAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return  valueList;

    }
    public void initiateSeedQuery(){
        Log.d("TAG","AAR library initiateGenderQuery::"+mActivityObj);
    }
    public void initiateResultQuery(){
        Log.d("TAG","AAR library initiateGenderQuery::"+mActivityObj);
    }

    public Call<GetGenderQueryInfoResponse> revealCourtPlace(String courtID, @Nullable GenderCallBack callbacks)
    {
        /*return BaseService.getInstance()
                .getUniqueCourt(PackageInstaller.Session.getToken(),courtID);*/
        String url = GemaltoContants.END_POINT+"?gender="+"female";
        Uri genderQueryURL = Uri.parse(url);

        return  new Apiclient(mActivityObj.getApplicationContext()).getApiService().getGenderInfoAPI(genderQueryURL);
    }

   @Override
    public List<UserResult> onSuccess(List<UserResult> value) {
        Log.d("TAG","=======================ONSUCCESS mANU");
       return value;
   }

    @Override
    public void onError(Throwable throwable) {
        Log.d("TAG","=======================ONFailure mANU");
    }




    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            genderQueryObj.revealCourtPlace("vv",genderCallBackObj);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

        }

    }

}
