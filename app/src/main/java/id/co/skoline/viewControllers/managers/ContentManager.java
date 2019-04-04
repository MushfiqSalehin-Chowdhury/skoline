package id.co.skoline.viewControllers.managers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.KlassesListener;
import okhttp3.ResponseBody;

import static id.co.skoline.model.configuration.ResponseCode.INVALID_JSON_RESPONSE;

public class ContentManager {
    private Context context;
    private ApiHandler apiHandler;
    KlassesListener klassesListener;
    private String reqIdKlasses;

    public ContentManager(Context context){
        this.context = context;
        apiHandler = new ApiHandler(context) {
            @Override
            public void startApiCall(String requestId) {
                if(requestId.equals(reqIdKlasses)){
                    klassesListener.startLoading(requestId);
                }
            }

            @Override
            public void endApiCall(String requestId) {
                if(requestId.equals(reqIdKlasses)){
                    klassesListener.endLoading(requestId);
                }
            }

            @Override
            public void successResponse(String requestId, ResponseBody responseBody, String baseUrl, String path, String requestType) {
                if(requestId.equals(reqIdKlasses)){
                    try {
                        Type listType = new TypeToken<List<KlassesResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        klassesListener.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        klassesListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }
            }

            @Override
            public void failResponse(String requestId, int responseCode, String message) {
                if(requestId.equals(reqIdKlasses)){
                    klassesListener.onFailed(message, responseCode);
                }
            }
        };
    }

    public String getKlasses(KlassesListener klassesListener){
        this.klassesListener = klassesListener;
        this.reqIdKlasses = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "api/v1/klasses", "get", reqIdKlasses, new HashMap());
        return reqIdKlasses;
    }

}
