package com.retrofit;

import android.content.Context;
import com.network.NetworkHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

public class ConnectivityAwareRetrofitClient implements Client {

    private static final Logger logger = LoggerFactory.getLogger("c*.r*.ConnectivityAwar*");

    private Client client;
    private Context context;

    public ConnectivityAwareRetrofitClient(Client client, Context context) {
        this.client = client;
        this.context = context;
    }

    @Override
    public Response execute(Request request) throws IOException {
        if (!NetworkHelper.connectedToNetwork(context)) {
            logger.debug("No connectivity %s ", request);
            throw new NoConnectivityException("No connectivity");
        }

        return client.execute(request);
    }
}
