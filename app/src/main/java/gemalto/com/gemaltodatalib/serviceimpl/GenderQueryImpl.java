package gemalto.com.gemaltodatalib.serviceimpl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gemalto.com.gemaltodatalib.GenderCallBack;
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
    private Response<GetGenderQueryInfoResponse> gendeQueryResponse;
    private Response<GetGenderQueryInfoResponse> manutest;
    private List<UserResult> userResultObjDUP;
    public static Response<GetGenderQueryInfoResponse> statResponse;

    public GenderQueryImpl(Context mActivity) {
        this.mActivityObj = mActivity;
        userResultObj = new ArrayList<>();
        //gendeQueryResponse = null;
    }

    public void getGenderQueryInfo(String queryParam) {
        //List<UserResult> userResultObjDUP = new ArrayList<>();

        try {
            if(CommonUtils.isConnectingToInternet(mActivityObj.getApplicationContext())){
                String url = GemaltoContants.END_POINT+"?gender="+queryParam;
                Uri genderQueryURL = Uri.parse(url);


                //CommonUtils.showBusyIndicator(mActivityObj);
                new Apiclient(mActivityObj.getApplicationContext()).getApiService().getGenderInfoAPI(genderQueryURL).enqueue(new Callback<GetGenderQueryInfoResponse>() {
                    @Override
                    public void onResponse(Call<GetGenderQueryInfoResponse> call, Response<GetGenderQueryInfoResponse> response) {
                        Log.d("TAG","onresponse in Genderqueryimpl=========== success::"+response.body().getResults().size());
                        userResultObj = new ArrayList<>();
                        userResultObj =  response.body().getResults();
                        userResultObjDUP = response.body().getResults();
                        gendeQueryResponse = response;
                        statResponse = response;
                        passback(response);
                        setManutest(response);
                       // if(mActivityObj instanceof MainActivity){
                        //RetrieveData.taxiAvailabilityResponse(response);
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
        Log.d("TAG","sending response from==================n in Genderqueryimpl::"+userResultObj);
        Log.d("TAG","sending response from==================n in gendeQueryResponse::"+gendeQueryResponse);
        //return statResponse.body().getResults();
    }

    public List<UserResult> passback(Response<GetGenderQueryInfoResponse> response) {
        return response.body().getResults();
    }

    public List<UserResult> getPassback(Response<GetGenderQueryInfoResponse> response) {
        return response.body().getResults();
    }

    public Response<GetGenderQueryInfoResponse> getManutest() {
        return manutest;
    }

    public void setManutest(Response<GetGenderQueryInfoResponse> manutest) {
        this.manutest = manutest;
    }



    public void triggerGenderInfoAPI( String gendervalue,@Nullable final GenderCallBack callbacks)
    {
        String url = GemaltoContants.END_POINT+"?gender="+gendervalue;
        Uri genderQueryURL = Uri.parse(url);
        new Apiclient(mActivityObj.getApplicationContext()).getApiService().getGenderInfoAPI(genderQueryURL)
                .enqueue(new Callback<GetGenderQueryInfoResponse>() {
                    @Override
                    public void onResponse(Call<GetGenderQueryInfoResponse> call, Response<GetGenderQueryInfoResponse> response)
                    {

                        callbacks.onSuccess(response.body());

                        /*JsonObject object = response.body();
                        boolean success = object.get("success").getAsBoolean(); //json objesinde dönen success alanı true ise
                        if (success)
                        {
                            JsonArray resultArray = object.get("data").getAsJsonObject().get("result").getAsJsonArray();
                            for (int i = 0; i < resultArray.size(); i++)
                            {
                                JsonObject jsonInfoResult = resultArray.get(i).getAsJsonObject();
                                String courtName=jsonInfoResult.get("name").getAsString();

                                if (callbacks != null)
                                    calbacks.onSuccess(courtName);
                            }

                        }*/

                    }

                    @Override
                    public void onFailure(Call<GetGenderQueryInfoResponse> call, Throwable t)
                    {
                        if (callbacks != null)
                            callbacks.onError(t);
                    }

                });
    }


}
