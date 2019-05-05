package id.co.skoline.viewControllers.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.OtpResponse;
import id.co.skoline.model.response.SignupErrorResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.response.SubscriptionResponse;
import id.co.skoline.model.response.TokenResponse;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.OtpListener;
import id.co.skoline.viewControllers.interfaces.ConfirmSubscriptionListener;
import id.co.skoline.viewControllers.interfaces.SignInListener;
import id.co.skoline.viewControllers.interfaces.SignupListener;
import id.co.skoline.viewControllers.interfaces.SubscriptionListener;
import id.co.skoline.viewControllers.interfaces.UploadPhotoListener;
import id.co.skoline.viewControllers.interfaces.UserListerner;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.PartMap;

import static id.co.skoline.model.configuration.ResponseCode.INVALID_JSON_RESPONSE;

public class AuthenticationManager {

    private Context context;
    ApiHandler apiHandler;
    private String reqIdUser;
    private String reqIdSignUp;
    private String reqIdSignIn;
    private String reqIdSubscription;
    private String reqIdUploadPhoto;
    private String reqIdOtp;
    private String reqIdConfirmSubscription;

    UserListerner userListerner;
    SignupListener signupListener;
    SignInListener signInListener;
    UploadPhotoListener uploadPhotoListener;
    SubscriptionListener subscriptionListenerList;
    OtpListener otpListener;
    ConfirmSubscriptionListener confirmSubscriptionListener;

    public AuthenticationManager(Context context) {
        this.context=context;

        apiHandler= new ApiHandler(context) {
            @Override
            public void startApiCall(String requestId) {
                if(requestId.equals(reqIdUser)){
                    userListerner.startLoading(requestId);
                } else if(requestId.equals(reqIdSignIn)){
                    signInListener.startLoading(requestId);
                } else if(requestId.equals(reqIdSignUp)){
                    signupListener.startLoading(requestId);
                } else if(requestId.equals(reqIdSubscription)){
                    subscriptionListenerList.startLoading(requestId);
                } else if(requestId.equals(reqIdUploadPhoto)){
                    uploadPhotoListener.startLoading(requestId);
                } else if(requestId.equals(reqIdOtp)){
                    otpListener.startLoading(requestId);
                } else if(requestId.equals(reqIdConfirmSubscription)){
                    confirmSubscriptionListener.startLoading(requestId);
                }
            }
            @Override
            public void endApiCall(String requestId) {
                if(requestId.equals(reqIdUser)){
                    userListerner.endLoading(requestId);
                } else if(requestId.equals(reqIdSignIn)){
                    signInListener.endLoading(requestId);
                } else if(requestId.equals(reqIdSignUp)){
                    signupListener.endLoading(requestId);
                } else if(requestId.equals(reqIdSubscription)){
                    subscriptionListenerList.endLoading(requestId);
                } else if(requestId.equals(reqIdUploadPhoto)){
                    uploadPhotoListener.endLoading(requestId);
                }else if(requestId.equals(reqIdOtp)){
                    otpListener.endLoading(requestId);
                } else if(requestId.equals(reqIdConfirmSubscription)){
                    confirmSubscriptionListener.endLoading(requestId);
                }
            }
            @Override
            public void successResponse(String requestId, ResponseBody responseBody, String baseUrl, String path, String requestType) {
                if ( requestId.equals(reqIdUser)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        userListerner.onSuccess(new Gson().fromJson(jsonObject.toString(), UserResponse.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                        userListerner.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if ( requestId.equals(reqIdSignIn)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        signInListener.onSuccess(new Gson().fromJson(jsonObject.toString(), TokenResponse.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                        signInListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if ( requestId.equals(reqIdSignUp)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        signupListener.onSuccess(new Gson().fromJson(jsonObject.toString(), SignupErrorResponse.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                        signupListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if(requestId.equals(reqIdUploadPhoto)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        uploadPhotoListener.uploadPhotoListenerSuccess("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        uploadPhotoListener.uploadPhotoListenerFail(INVALID_JSON_RESPONSE, "Invalid JSON Response");
                    }
                } else if(requestId.equals(reqIdSubscription)){
                    try {
                        Type listType = new TypeToken<List<SubscriptionResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        subscriptionListenerList.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriptionListenerList.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if(requestId.equals(reqIdConfirmSubscription)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        confirmSubscriptionListener.onSuccess("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        confirmSubscriptionListener.onFailed( "Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }
                    else if(requestId.equals(reqIdOtp)){
                    try {
                        Type listType = new TypeToken<List<OtpResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        subscriptionListenerList.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriptionListenerList.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }
            }

            @Override
            public void failResponse(String requestId, int responseCode, String message) {
                if(requestId.equals(reqIdUser)){
                    userListerner.onFailed(message, responseCode);
                } else if(requestId.equals(reqIdSignIn)){
                    signInListener.onFailed(message, responseCode);
                } else if(requestId.equals(reqIdSignUp)){
                    signupListener.onFailed(message, responseCode);
                } else if(requestId.equals(reqIdSubscription)){
                    subscriptionListenerList.onFailed(message, responseCode);
                } else if(requestId.equals(reqIdUploadPhoto)){
                    uploadPhotoListener.uploadPhotoListenerFail(responseCode, message);
                } else if(requestId.equals(reqIdOtp)){
                    otpListener.onFailed(message, responseCode);
                } else if(requestId.equals(reqIdConfirmSubscription)){
                    confirmSubscriptionListener.onFailed(message, responseCode);
                }
            }
        };
    }
    public String getUsers(UserListerner userListerner){
        this.userListerner = userListerner;
        this.reqIdUser = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "users/user_profile", "get", reqIdUser, new HashMap());
        return reqIdUser;
    }

    public void uploadPhoto(String imagePath, UploadPhotoListener uploadPhotoListener) {
        this.uploadPhotoListener = uploadPhotoListener;
        this.reqIdUploadPhoto = ShareInfo.getInstance().getRequestId();
        RequestBody requestFileForImage = null;
        
        HashMap<String, RequestBody> hashMap = new HashMap<>();

        try {
            File file = new File(imagePath);
            requestFileForImage = RequestBody.create(MediaType.parse("image/jpg"), file);
        } catch (Exception ignored) {
            Log.d("TAG", "ignored image:" + ignored.getMessage());
        }

        hashMap.put("avatar\"; filename=\"image_" + System.currentTimeMillis() + ".jpg\"", requestFileForImage);

        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "users/upload_avatar", "post_image", reqIdUploadPhoto, hashMap);
    }


    public String signUp (String childName, String phone,String uniqueName, String dateOfBirth,SignupListener signupListener){
        this.signupListener= signupListener;
        this.reqIdSignUp= ShareInfo.getInstance().getRequestId();
        HashMap hashMap = new HashMap();
        hashMap.put("user[child_name]",childName);
        hashMap.put("user[phone]",phone);
        hashMap.put("user[unique_name]",uniqueName);
        hashMap.put("user[birth_date]",dateOfBirth);
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(),"users","post",reqIdSignUp,hashMap);
        return reqIdSignUp;
    }


    public String signIn (String uniqueName, String dateOfBirth,SignInListener signInListener){
        this.signInListener= signInListener;
        this.reqIdSignIn= ShareInfo.getInstance().getRequestId();
        HashMap hashMap = new HashMap();
        hashMap.put("unique_name",uniqueName);
        hashMap.put("date_of_birth",dateOfBirth);
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(),"users/login","post",reqIdSignIn,hashMap);
        return reqIdSignIn;
    }

    public String getSubscriptionList(SubscriptionListener subscriptionListenerList){
        this.subscriptionListenerList= subscriptionListenerList;
        this.reqIdSubscription=ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(),"subscriptions","get",reqIdSubscription,new HashMap<>());
        return  reqIdSubscription;
    }
    public String checkOtp (String phone,String uniqueName,OtpListener otpListener){
        this.otpListener=otpListener;
        this.reqIdOtp = ShareInfo.getInstance().getRequestId();
        HashMap hashMap = new HashMap();
        hashMap.put("phone",phone);
        hashMap.put("unique_name",uniqueName);
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(),"users/fetch_otp","get",reqIdOtp,new HashMap());
        return  reqIdOtp;
    }
    public String confirmSubscription(String subscription_id, ConfirmSubscriptionListener confirmSubscriptionListener){
        this.confirmSubscriptionListener = confirmSubscriptionListener;
        this.reqIdConfirmSubscription = ShareInfo.getInstance().getRequestId();
        HashMap hashMap = new HashMap();
        hashMap.put("subscription_id", subscription_id);
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(),"users/activate_subscription","post",reqIdConfirmSubscription, hashMap);
        return  reqIdConfirmSubscription;
    }
}
