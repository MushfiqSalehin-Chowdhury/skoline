package id.co.skoline.viewControllers.interfaces;

public interface PermissionListener {
    void permissionGranted();
    void permissionDenied(int position);
}
