package id.co.skoline.viewControllers.interfaces;

public interface ForgetUniqueNameListerner extends BaseApiCallListener {

    void onSuccess(String message);
    void onFailed(String message, int responseCode);
}
