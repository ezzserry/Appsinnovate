package serry.appsinnovatetask.utils;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.HashMap;

import serry.appsinnovatetask.models.Countries;

/**
 * Created by PC on 2/13/2017.
 */

public class MyApplication extends Application {
    public static HashMap<String,ArrayList<Countries>> myList;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
