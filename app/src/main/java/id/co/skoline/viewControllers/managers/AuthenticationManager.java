package id.co.skoline.viewControllers.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.KlassesListener;
import id.co.skoline.viewControllers.interfaces.SignupListener;
import id.co.skoline.viewControllers.interfaces.UserListerner;
import okhttp3.ResponseBody;

import static id.co.skoline.model.configuration.ResponseCode.INVALID_JSON_RESPONSE;

public class AuthenticationManager {
    private Context context;
    ApiHandler apiHandler;
    private String reqIdUser;
    private String reqIdSignUp;
    UserListerner userListerner;
    SignupListener signupListener;
    public AuthenticationManager(Context context) {
        this.context=context;

        apiHandler= new ApiHandler(context) {
            @Override
            public void startApiCall(String requestId) {
                if(requestId.equals(reqIdUser)){
                    userListerner.startLoading(requestId);
                }
            }
            @Override
            public void endApiCall(String requestId) {
                if(requestId.equals(reqIdUser)){
                    userListerner.startLoading(requestId);
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
                }
            }
            @Override
            public void failResponse(String requestId, int responseCode, String message) {
                if(requestId.equals(reqIdUser)){
                   userListerner.onFailed(message, responseCode);
                }
            }
        };
    }
    public String getUsers(UserListerner userListerner){
        this.userListerner = userListerner;
        this.reqIdUser = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "/api/v1/users/4", "get", reqIdUser, new HashMap());
        return reqIdUser;
    }
    public String signUp (String childName, String phone,String uniqueName, String dateOfBirth,SignupListener signupListener){
        this.signupListener= signupListener;
        this.reqIdSignUp= ShareInfo.getInstance().getRequestId();
        HashMap hashMap = new HashMap();
        hashMap.put("user[child_name]",childName);
        hashMap.put("user[phone]",phone);
        hashMap.put("user[unique_name]",uniqueName);
        hashMap.put("user[birth_date]",dateOfBirth);
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(),"/api/v1/users","post",reqIdSignUp,hashMap);
        return reqIdSignUp;
    }
}
