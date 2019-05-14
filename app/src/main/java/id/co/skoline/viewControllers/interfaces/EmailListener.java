package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.EmailResponse;

public interface EmailListener extends BaseApiCallListener {
    void onSuccess(EmailResponse emailResponse);
    void onFailed(String message, int responseCode);
}
