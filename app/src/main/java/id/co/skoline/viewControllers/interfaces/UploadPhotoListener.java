package id.co.skoline.viewControllers.interfaces;

public interface UploadPhotoListener extends BaseApiCallListener {
    void uploadPhotoListenerSuccess(String message);

    void uploadPhotoListenerFail(int responseCode, String message);
}
