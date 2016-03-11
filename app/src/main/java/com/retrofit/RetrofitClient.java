package com.retrofit;

import android.content.Context;

import com.APICall.RestInterface;
import com.Utils.Consts;
import com.application.MainApplication;
import com.squareup.okhttp.OkHttpClient;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Manish on 31/10/2015.
 */
public class RetrofitClient {

    /**
     * Customization
     */

    private static final Context context;
    private static final OkHttpClient httpClient = new OkHttpClient();

    static {
        context = MainApplication.getContext();
    }

    /**
     * RestAdapters
     */
    private static final Builder commonBuilder = new Builder()
            .setLogLevel(LogLevel.FULL)
            .setClient(new ConnectivityAwareRetrofitClient(new OkClient(httpClient), context))
            .setErrorHandler(new RetrofitErrorHandler(context));

    private static final RestAdapter userInfoAdapter = commonBuilder.setEndpoint(Consts.mainURL).build();

    private static final RestAdapter userIdAdapter = commonBuilder.setEndpoint(Consts.mainURL2).build();

    /**
     * Web service definitions
     */

    private static final RestInterface USER_INFO_SERVICES = userInfoAdapter.create(RestInterface.class);

    public static RestInterface userInfoServices() {
        return USER_INFO_SERVICES;
    }

    private static final RestInterface USER_ID_SERVICES = userIdAdapter.create(RestInterface.class);

    public static RestInterface userIdServices() {
        return USER_ID_SERVICES;
    }

}
