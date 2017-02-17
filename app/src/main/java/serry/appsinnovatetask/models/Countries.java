package serry.appsinnovatetask.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 2/15/2017.
 */

public class Countries {
    @SerializedName("name")
    String sCountryName;

    public String getsCountryName() {
        return sCountryName;
    }

    public void setsCountryName(String sCountryName) {
        this.sCountryName = sCountryName;
    }
}
