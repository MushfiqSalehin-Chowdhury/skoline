package id.co.skoline.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponse {
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
