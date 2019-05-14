package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityHelpBinding;
import id.co.skoline.model.response.EmailResponse;
import id.co.skoline.model.response.FaqResponse;
import id.co.skoline.view.adapters.FaqAdapter;
import id.co.skoline.viewControllers.interfaces.EmailListener;
import id.co.skoline.viewControllers.interfaces.FaqListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class HelpActivity extends BaseActivity {

    ActivityHelpBinding helpBinding;
    FaqAdapter faqAdapter;
    ContentManager contentManager;
    List<FaqResponse> faqResponseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpBinding= DataBindingUtil.setContentView(this,R.layout.activity_help);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(HelpActivity.this);
        helpBinding.faqRecycle.setLayoutManager(mlayoutManager);
        helpBinding.faqRecycle.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.help),true,helpBinding.toolbarBinding);
        contentManager = new ContentManager(this);
        contentManager.getFaq(new FaqListener() {
            @Override
            public void onSuccess(List<FaqResponse> faqResponseList) {
                HelpActivity.this.faqResponseList=faqResponseList;
                showFaq (faqResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {
                Log.i("HelpActivity",message);
            }

            @Override
            public void startLoading(String requestId) {

            }

            @Override
            public void endLoading(String requestId) {

            }
        });
    }

    private void showFaq(List<FaqResponse> faqResponseList) {
        faqAdapter = new FaqAdapter(this,faqResponseList);
        helpBinding.faqRecycle.setAdapter(faqAdapter);
        faqAdapter.notifyDataSetChanged();
    }

    public void sendMail(View view) {
       /* Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","tanya@skoline.co.id", null));
        intent.putExtra(Intent.EXTRA_TEXT, helpBinding.mail.getText());
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));*/

       contentManager = new ContentManager(this);
       contentManager.sendEmail(helpBinding.mail.getText().toString(), new EmailListener() {
           @Override
           public void onSuccess(EmailResponse emailResponse) {
               showToast(emailResponse.getMessage());
           }

           @Override
           public void onFailed(String message, int responseCode) {
               showToast(message);
           }

           @Override
           public void startLoading(String requestId) {
               showToast(getString(R.string.sendEmail));
           }

           @Override
           public void endLoading(String requestId) {

           }
       });
    }
}
