package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityBonusvideoBinding;
import id.co.skoline.model.response.BonusVideoResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.adapters.BonusVideoAdapter;
import id.co.skoline.viewControllers.interfaces.BonusVideoListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class BonusVideoActivity extends BaseActivity {
    ActivityBonusvideoBinding bonusVideoBinding;
    ContentManager contentManager;
    BonusVideoAdapter bonusVideoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bonusVideoBinding= DataBindingUtil.setContentView(this,R.layout.activity_bonusvideo);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(BonusVideoActivity.this);
        bonusVideoBinding.bonusRecycle.setLayoutManager(mlayoutManager);
    }
    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.bonusToolbarTitle),true,bonusVideoBinding.toolbarBinding);
        contentManager=  new ContentManager(this);
        contentManager.getBonusVideos(new BonusVideoListener() {
            @Override
            public void onSuccess(List<BonusVideoResponse> bonusVideoResponseList) {
                generateViewBonusVideos(bonusVideoResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {

            }

            @Override
            public void startLoading(String requestId) {

            }

            @Override
            public void endLoading(String requestId) {

            }
        });

    }

    private void generateViewBonusVideos(List<BonusVideoResponse> bonusVideoResponseList) {
        bonusVideoAdapter= new BonusVideoAdapter(this,bonusVideoResponseList);
        bonusVideoBinding.bonusRecycle.setAdapter(bonusVideoAdapter);
        bonusVideoAdapter.notifyDataSetChanged();
        bonusVideoAdapter.setOnItemClickListener(bonusVideoResponse -> {
            Intent intent = new Intent(BonusVideoActivity.this,VideoPlayActivity.class);
            //intent.putExtra("videoUrl", ShareInfo.getInstance().getRootBaseUrl()+bonusVideoResponse.getVideoLink());
           // intent.putExtra("videoId",bonusVideoResponse.getId());
            startActivity(intent);
        });
    }
}
