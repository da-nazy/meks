package com.example.meks_enginering.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIResponse {
    private static String TAG = APIResponse.class.getSimpleName();

    public static <T> void callRetrofit(Call<T> call, final String strApiName, Context context, final ApiListener apiListener) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        call.enqueue(new Callback<T>() {
            public void onResponse(Call<T> call, Response<T> response) {
                if (strApiName.equalsIgnoreCase(" ") || strApiName.isEmpty()) {
                    strApiName.equalsIgnoreCase("");
                    return;
                }
                String str = "onResponse: ";
                if (response.isSuccessful()) {
                    Log.d(APIResponse.TAG, str + response.body().toString());
                    apiListener.success(strApiName, response.body());
                    progressDialog.dismiss();
                    return;
                }
                try {
                    Log.d(APIResponse.TAG, str + response.errorBody().string());
                    apiListener.error(strApiName, response.errorBody().string());
                    progressDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<T> call, Throwable t) {
                Log.d(APIResponse.TAG, "onFailure: " + t.toString());
                apiListener.failure(strApiName, t.toString());
                if (strApiName.equalsIgnoreCase("searchNearbyTest")) {
                    apiListener.failure(strApiName, t.toString());
                }
                progressDialog.dismiss();
            }
        });
    }
}
