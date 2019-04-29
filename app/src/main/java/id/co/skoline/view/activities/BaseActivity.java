package id.co.skoline.view.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import id.co.skoline.R;
import id.co.skoline.databinding.ToolbarBinding;
import id.co.skoline.model.configuration.LanguageHelper;
import id.co.skoline.model.utils.LocalStorage;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.custom.CustomEditText;
import id.co.skoline.viewControllers.interfaces.PermissionListener;


public abstract class BaseActivity extends AppCompatActivity {

    private PermissionListener permissionListener;
    private static final int reqCode = 580;
    public DisplayMetrics displayMetrics = new DisplayMetrics();
    public ProgressDialog progressDialog;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private String language;

    protected BaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        progressDialog = new ProgressDialog(BaseActivity.this, R.style.CustomProgress);
        language = ShareInfo.getLanguageType(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        ShareInfo.goNextPage(this);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        ShareInfo.goNextPage(this);
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            ShareInfo.goPreviousPage(this);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        viewRelatedTask();
    }

    public void showToast(String message) {
        showToast(message, false);
    }

    public void showToast(String message, boolean isLong) {
        Toast.makeText(getApplicationContext(), message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    protected abstract void viewRelatedTask();

    public void checkPermission(String[] permission, PermissionListener permissionListener) {
        try {
            this.permissionListener = permissionListener;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean allGranted = true;
                int countPermission = 0;
                Log.e("permission_list", String.valueOf(permission.length));
                while (permission.length > countPermission) {
                    if ((ContextCompat.checkSelfPermission(this, permission[countPermission]) !=
                            PackageManager.PERMISSION_GRANTED)) {
                        allGranted = false;
                        break;
                    }
                    countPermission++;
                }
                if (!allGranted) ActivityCompat.requestPermissions(this, permission, reqCode);
                else this.permissionListener.permissionGranted();

            } else this.permissionListener.permissionGranted();

        } catch (Exception e) {
            this.permissionListener.permissionDenied(-1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        try {
            switch (requestCode) {
                case reqCode: {
                    if (permissions.length > 0) {
                        boolean allGranted = true;
                        int count = 0;
                        while (permissions.length > count) {
                            if (ContextCompat.checkSelfPermission(this, permissions[count]) !=
                                    PackageManager.PERMISSION_GRANTED) {
                                allGranted = false;
                                break;
                            }
                            count++;
                        }
                        if (allGranted) this.permissionListener.permissionGranted();
                        else this.permissionListener.permissionDenied(count);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String oldLanguage = language;
        language = ShareInfo.getLanguageType(this);
        if (!oldLanguage.equals(language)) {
            Log.d("onLanguageChange", "insideOnResume");
            recreate();
        }
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context
                    .INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager
                    .HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyboard(final CustomEditText etText){
        etText.requestFocus();
        etText.postDelayed(() -> {
            InputMethodManager keyboard=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(etText,0);
        }
        ,200);
    }

    public void showProgressDialog(String message, boolean isCancelable) {
        if(progressDialog==null){
            progressDialog = new ProgressDialog(BaseActivity.this, R.style.CustomProgress);
        }
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(R.style.CustomProgress);
        progressDialog.setCancelable(isCancelable);
        progressDialog.show();

        if (progressDialog.isShowing())
            Log.e("progress_", "is showing");
        else
            Log.e("progress_", "is not showing");
    }

    public void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public Toolbar setToolbar(String title, boolean hasBackButton, ToolbarBinding toolbarBinding) {
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarBinding.toolbarTitle.setText(title);
        /*if (hasBackButton) {
            toolbarBinding.toolbarTitle.setVisibility(View.VISIBLE);
            toolbarBinding.toolbarTitle.setText(title);
            toolbarBinding.appLogo.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbarBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        } else {
            toolbarBinding.appName.setVisibility(View.VISIBLE);
            toolbarBinding.appName.setText(title);
            toolbarBinding.menuOptions.setVisibility(View.VISIBLE);
        }*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        return toolbarBinding.toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToHome() {
        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        onBackPressed();
    }

    public void goToLoginScreen() {
        Intent intent = new Intent(BaseActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void logout(){
        LocalStorage.getInstance().clearData(this, "LOGGED_IN_USER");
    }

    public void refreshAnimation(final View view){
        YoYo.with(Techniques.RotateIn)
                .duration(750)
                .repeat(1)
                .playOn(view);
    }

    public void showView(final View view, Techniques techniques) {
        YoYo.with(techniques)
                .delay(100)
                .duration(500)
                .repeat(0)
                .playOn(view);

        new Thread(() -> {
            try {
                Thread.sleep(105);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> view.setVisibility(View.VISIBLE));
        }).start();
    }

    public void hideView(final View view, Techniques techniques) {
        YoYo.with(techniques)
                .duration(500)
                .repeat(0)
                .playOn(view);

        new Thread(() -> {
            try {
                Thread.sleep(490);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> view.setVisibility(View.GONE));
        }).start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    public void changeLanguage(String language) {
        /*the language is already default*/
        if (language.equals(ShareInfo.getLanguageType(this))) return;
        ShareInfo.setLanguageType(this, language);
        switchLanguage(BaseActivity.this, language);
    }

    public void switchLanguage(Activity activity, String languageCode) {
        LanguageHelper.setLanguage(activity, languageCode);
        Log.d("onLanguageChange", "insideSwitchLanguage");
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void hideViewIfKeyboardShowing(View contentView, View viewToHide){
        contentView.postDelayed(() -> runOnUiThread(() -> contentView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            Rect r = new Rect();
            contentView.getWindowVisibleDisplayFrame(r);
            int screenHeight = contentView.getRootView().getHeight();

            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                // keyboard is opened
                viewToHide.setVisibility(View.GONE);
            }
            else {
                // keyboard is closed
                viewToHide.setVisibility(View.VISIBLE);
            }
        })), 250);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

}
