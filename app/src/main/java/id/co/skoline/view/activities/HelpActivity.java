package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityHelpBinding;

public class HelpActivity extends BaseActivity {

    ActivityHelpBinding helpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helpBinding= DataBindingUtil.setContentView(this,R.layout.activity_help);

    }

    @Override
    protected void viewRelatedTask() {

        setToolbar("Help",true,helpBinding.toolbarBinding);
    }

    public void sendMail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","tanya@skoline.co.id", null));
        intent.putExtra(Intent.EXTRA_TEXT, helpBinding.mail.getText());
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }
}
