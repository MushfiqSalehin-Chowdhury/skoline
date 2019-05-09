package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.BonusVideoResponse;

public interface BonusVideoListener extends BaseApiCallListener{
    void onSuccess(List<BonusVideoResponse> bonusVideoResponseList);
    void onFailed(String message, int responseCode);
}
