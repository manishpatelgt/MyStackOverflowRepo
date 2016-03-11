package com.network;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Class working with network connectivity
 */
public class NetworkHelper {

    public static final String WEBADDRESS_GOOGLE_COM_URL = "http://www.google.com";
    // always verify the host - don't check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
    private static final int NETWORK_CONNECTION_POOL_TIMEOUT = 1000;
    private static final int NETWORK_CONNECTION_CONNECT_TIMEOUT = 10000;
    private static final int NETWORK_CONNECTION_READ_TIMEOUT = 10000;
    private static final int NETWORK_SOCKET_CONNECT_TIMEOUT = 10000;
    private static final String WEBADDRESS_GOOGLE_DNS_ADDRESS1 = "8.8.8.8";
    private static final String WEBADDRESS_ANDROID_COM_URL = "http://www.android.com/";
    private static final String WEBADDRESS_ANDROID_COM_IP = "74.125.24.102";
    private SSLSocketFactory defaultSSLSocketFactory = null;
    private HostnameVerifier defaultHostnameVerifier = null;

    private NetworkHelper() {
    }

    public static boolean connectedToWiFiNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectedToWiFiNetwork(connectivityManager);
    }

    public static boolean connectedToWiFiNetwork(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected)
            return false;

        return (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean connectedToMobileNetwork(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected)
            return false;

        return (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean connectedToWiFiOrMobileNetwork(Context context) {
        final ConnectivityManager cm = getConnectivityManager(context);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected)
            return false;

        return (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) ||
                (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Checks whether some network is connected and fully usable.
     */
    public static boolean connectedToNetwork(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public static boolean connectedToNetwork(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }



    /**
     * Verifies that the handset is connected to the Internet.
     *
     * @return
     */
    public static boolean connectedToInternet(Context context) throws IOException {

        if (!connectedToNetwork(getConnectivityManager(context)))
            return false;

        URL googleUrl = new URL(WEBADDRESS_GOOGLE_COM_URL);
        HttpURLConnection connection = (HttpURLConnection) googleUrl.openConnection();
        connection.setConnectTimeout(NETWORK_CONNECTION_CONNECT_TIMEOUT);
        connection.connect();

        if (connection.getResponseCode() == HttpStatus.SC_OK)
            return true;

        return false;

        /**
         *  Alternative way (NOT TESTED):
         *
         boolean result = false;

         URL url;
         HttpURLConnection connection = null;

         try {
         url = new URL(WEBADDRESS_ANDROID_COM_URL);

         connection = (HttpURLConnection) url.openConnection();
         connection.setConnectTimeout(NETWORK_CONNECTION_CONNECT_TIMEOUT);
         connection.setReadTimeout(NETWORK_CONNECTION_READ_TIMEOUT);

         InetAddress address = InetAddress.getByName(WEBADDRESS_ANDROID_COM_IP);

         result = address.isReachable(NETWORK_CONNECTION_CONNECT_TIMEOUT);

         // Redirect check is valid only after the response headers have been received
         // InputStream in = new BufferedInputStream(connection.getInputStream());

         //            if (url.getHost().equals(connection.getURL().getHost()))
         //                result = true;

         } catch (Exception e) {
         e.printStackTrace();
         } finally {
         connection.disconnect();
         }

         return result;
         */
    }

    /**
     * Checks that a route to the remote host exists.
     */
    public static boolean isHostRoutable(Context context, String hostname) {

        boolean result = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if (connectivity != null && networkInfo != null && networkInfo.isConnected()) {
            int networkType = networkInfo.getType();
            int ipAddress = lookupHost(hostname);
            result = connectivity.requestRouteToHost(networkType, ipAddress);
        }

        return result;
    }

    /**
     * Converts an IP address to an integer.
     */
    public static int lookupHost(String hostname) {
        InetAddress address;

        try {
            address = InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            return -1;
        }

        byte[] addrBytes = address.getAddress();

        return ((addrBytes[3] & 0xff) << 24) | ((addrBytes[2] & 0xff) << 16) | ((addrBytes[1] & 0xff) << 8) | (addrBytes[0] & 0xff);
    }

    private static ConnectivityManager getConnectivityManager(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm;
    }

    /**
     * Create a trust manager that does not validate SSL certificate chains.
     */
    public void trustAllHosts() {

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            // Backup the current SSL socket factory
            defaultSSLSocketFactory = HttpsURLConnection
                    .getDefaultSSLSocketFactory();
            // Install our all trusting manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}