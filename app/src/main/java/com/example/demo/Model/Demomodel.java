
package com.example.demo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Demomodel {


    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Usid")
    @Expose
    private String usid;

    public Demomodel(String statusCode, String usid) {
        this.statusCode = statusCode;
        this.usid = usid;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

}
