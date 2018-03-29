
package fr.ul.miage.weather.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Features {

    @SerializedName("conditions")
    @Expose
    private int conditions;

    public int getConditions() {
        return conditions;
    }

    public void setConditions(int conditions) {
        this.conditions = conditions;
    }

}
