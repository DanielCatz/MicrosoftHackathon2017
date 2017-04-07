
package com.sequencing.sample.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Region implements Serializable
{

    @SerializedName("center")
    @Expose
    private Center center;
    private final static long serialVersionUID = -4137440021795043105L;

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

}
