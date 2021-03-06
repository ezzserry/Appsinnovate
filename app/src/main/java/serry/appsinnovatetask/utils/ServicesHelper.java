package serry.appsinnovatetask.utils;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

/**
 * Created by PC on 2/15/2017.
 */

public class ServicesHelper {
    private String URL;
    private static ServicesHelper sharedInstance;


    public synchronized static ServicesHelper getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new ServicesHelper();
        }
        return sharedInstance;
    }

    public void getCountriesList(Context context, Response.Listener<JSONArray> success, Response.ErrorListener errorListener) {
        String URL = Constants.URL_COUNTRIES_LIST;
        JsonArrayRequest request = new JsonArrayRequest(URL, success, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
}
