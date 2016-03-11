package com.retrofit;

import android.content.Context;
import android.text.TextUtils;
import com.mystacky.R;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Provide custom error handling for Retrofit requests.
 */
public class RetrofitErrorHandler implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger("c*.r*.RetrofitErrorHan*");
    private final Context context;

    public RetrofitErrorHandler(Context context) {
        this.context = context;
    }

    @Override
    public Throwable handleError(RetrofitError error) {
        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            // Phone is not connected at all
            return new NoConnectivityException(context.getString(R.string.no_internet_connection));
        }

        if (error.getKind().equals(RetrofitError.Kind.CONVERSION)) {
            // Web services usually return JSON, so the phone is probably trying to use mobile data without credit
            return new NoConnectivityException(context.getString(R.string.no_internet_connection_mobile_out_of_credit));
        }

        Response response = error.getResponse();
        String errorDescription = "Unknown Retrofit error";

        if (response == null) {
            return error;
        }

        switch (response.getStatus()) {
            case HttpStatus.SC_BAD_REQUEST:
                // 404 returned from the server means there is no matching data
                errorDescription = error.getMessage().replace("400", "");
                break;

            case  HttpStatus.SC_UNAUTHORIZED:
                //return new UnauthorizedException(error);

            case HttpStatus.SC_NOT_FOUND:
                if (!TextUtils.isEmpty(error.getMessage())) {
                    // 404 returned from the server means there is no matching data
                    errorDescription = error.getMessage().replace("404", "");
                }
                break;

            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                if (!TextUtils.isEmpty(error.getMessage())) {
                    // 500 returned from the server means the server error
                    errorDescription = error.getMessage().replace("500", "");
                }
                break;

            case HttpStatus.SC_SERVICE_UNAVAILABLE:
                return new NoConnectivityException("Server is not responding");

            default:
                try {
                    errorDescription = context.getString(R.string.error_network_http_error, error.getResponse().getStatus());
                } catch (Exception ex2) {
                    logger.error("handleError: " + ex2.getLocalizedMessage());
                    errorDescription = context.getString(R.string.error_unknown);
                }
                break;
        }


        //return error;
        return new Exception(errorDescription);
    }
}
