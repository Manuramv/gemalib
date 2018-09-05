package gemalto.com.gemaltodatalib.serviceimpl;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gemalto.com.gemaltodatalib.networking.Apiclient;
import gemalto.com.gemaltodatalib.networking.GemaltoContants;
import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manuramv on 9/6/2018.
 */

public class GenderQueryImpl {
    private Context mActivityObj;
    List<UserResult> userResultObj;

    public GenderQueryImpl(Context mActivity) {
        this.mActivityObj = mActivity;
        userResultObj = new ArrayList<>();
    }

    public List<UserResult> getGenderQueryInfo(String queryParam) {

        try {
            if(CommonUtils.isConnectingToInternet(mActivityObj.getApplicationContext())){
                String url = GemaltoContants.END_POINT+"?gender="+queryParam;
                Uri genderQueryURL = Uri.parse(url);


                //CommonUtils.showBusyIndicator(mActivityObj);
                new Apiclient(mActivityObj.getApplicationContext()).getApiService().getGenderInfoAPI(genderQueryURL).enqueue(new Callback<GetGenderQueryInfoResponse>() {
                    @Override
                    public void onResponse(Call<GetGenderQueryInfoResponse> call, Response<GetGenderQueryInfoResponse> response) {
                        userResultObj =  response.body().getResults();
                       // if(mActivityObj instanceof MainActivity){
                           // ((MainActivity) mActivityObj).taxiAvailabilityResponse(response);
                       // }
                    }

                    @Override
                    public void onFailure(Call<GetGenderQueryInfoResponse> call, Throwable t) {
                        Log.d("TAG","onfailure n in Genderqueryimpl::"+t.toString());

                    }

                });
            } else {
                //  utils.showCustomPopupMessage(mActivityObj, mActivityObj.getResources().getString(R.string.internet_warn));
            }
        } catch (Exception e) {
            Log.d("TAG","Exception in Genderqueryimpl::"+e);
            e.printStackTrace();
        }
        return userResultObj;
    }

}
