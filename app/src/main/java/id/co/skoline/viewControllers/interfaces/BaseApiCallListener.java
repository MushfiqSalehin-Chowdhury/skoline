package id.co.skoline.viewControllers.interfaces;

public interface BaseApiCallListener {
    void startLoading(String requestId);
    void endLoading(String requestId);
}
