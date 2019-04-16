package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.SubjectResponse;

public interface SubjectsListener extends BaseApiCallListener {

    void onSuccess(List<SubjectResponse> subjectResponseList);
    void onFailed(String message, int responseCode);
}
