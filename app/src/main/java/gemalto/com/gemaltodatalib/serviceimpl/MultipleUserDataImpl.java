package gemalto.com.gemaltodatalib.serviceimpl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import gemalto.com.gemaltodatalib.callbackinterface.MultipleUserCallback;
import gemalto.com.gemaltodatalib.callbackinterface.UserIdCallback;
import gemalto.com.gemaltodatalib.networking.Apiclient;
import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manuramv on 9/7/2018.
 */

public class MultipleUserDataImpl {

    private Context mActivityObj;
    List<UserResult> userResultObj;
    private Response<GetGenderQueryInfoResponse> gendeQueryResponse;
    private Response<GetGenderQueryInfoResponse> manutest;
    private List<UserResult> userResultObjDUP;
    public static Response<GetGenderQueryInfoResponse> statResponse;

    public MultipleUserDataImpl(Context mActivity) {
        this.mActivityObj = mActivity;
        userResultObj = new ArrayList<>();
        //gendeQueryResponse = null;
    }


    public void triggerMultipleUserAPI(String constructedURl, @Nullable final MultipleUserCallback callbacks)
    {
        String url = constructedURl;
        Uri genderQueryURL = Uri.parse(url);
        new Apiclient(mActivityObj.getApplicationContext()).getApiService().getMultiplUserInfoAPI(genderQueryURL)
                .enqueue(new Callback<GetGenderQueryInfoResponse>() {
                    @Override
                    public void onResponse(Call<GetGenderQueryInfoResponse> call, Response<GetGenderQueryInfoResponse> response)
                    {

                        callbacks.onSuccess(response.body());

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
