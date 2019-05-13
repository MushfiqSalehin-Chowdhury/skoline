package id.co.skoline.view.activities;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityProfileBinding;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.ImageGetListener;
import id.co.skoline.viewControllers.interfaces.PermissionListener;
import id.co.skoline.viewControllers.interfaces.UploadPhotoListener;
import id.co.skoline.viewControllers.interfaces.UserListerner;
import id.co.skoline.viewControllers.managers.AuthenticationManager;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

public class ProfileActivity extends ImageActivity {

    ActivityProfileBinding profileBinding;
    AuthenticationManager authenticationManager;
    UserResponse userResponseList;
    private String[] dob;
    String imageUrl;
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(2, getResources().getColor(R.color.GreenDark)).setLabel("IPS" + "\n" + "2%"));
        pieData.add(new SliceValue(41, getResources().getColor(R.color.purple)).setLabel("IPA" + "\n" + "41%"));
        pieData.add(new SliceValue(32, getResources().getColor(R.color.colorAccent)).setLabel("MTK" + "\n" + "32%"));
        pieData.add(new SliceValue(15, getResources().getColor(R.color.colorPrimaryDark2)).setLabel("B.ENG" + "\n" + "15%"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.colorPrimaryDark)).setLabel("B.IND" + "\n" + "10%"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        profileBinding.chart.setPieChartData(pieChartData);
        profileBinding.englishTitle.setText("English" + "(0/0 video)");
        profileBinding.englishProgress.setProgress(30);
        profileBinding.englishProgressPercent.setText("30%");
        profileBinding.mathProgress.setProgress(50);
        profileBinding.mathProgressPercent.setText("50%");
        profileBinding.socialProgress.setProgress(50);
        profileBinding.socialProgressPercent.setText("10%");
        profileBinding.pendidicanProgress.setProgress(0);
        profileBinding.pendidicanProgressPercent.setText("0%");
        profileBinding.ilmuProgress.setProgress(40);
        profileBinding.ilmuProgressPercent.setText("40%");
        profileBinding.indonesiaProgress.setProgress(80);
        profileBinding.indonesiaProgressPercent.setText("80%");


        authenticationManager = new AuthenticationManager(this);
        authenticationManager.getUsers(new UserListerner() {
            @Override
            public void onSuccess(UserResponse userResponseList) {
                ProfileActivity.this.userResponseList = userResponseList;
                Log.e("ProfileActivity", new Gson().toJson(userResponseList));
                generateViewUser(userResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {
                ProfileActivity.this.userResponseList = null;
            }
            @Override
            public void startLoading(String requestId) {
                showProgressDialog(getString(R.string.loading_data), false);
            }

            @Override
            public void endLoading(String requestId) {
                dismissProgressDialog();
            }
        });

        profileBinding.menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        profileBinding.changeProfilePicture.setOnClickListener(v -> checkPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void permissionGranted() {
                showImagePickerOptions();
            }

            @Override
            public void permissionDenied(int position) {
                showToast("Permission denied");
            }
        }));

    }

    @Override
    protected void viewRelatedTask() {
        setToolbar("Profile", true, profileBinding.toolbarBinding);
    }

    public void showOptions() {
        PopupMenu popup = new PopupMenu(ProfileActivity.this, profileBinding.menuOption);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {

            if (item.getTitle().equals("Help")) {
                startActivity(new Intent(this, HelpActivity.class));
            } else if (item.getTitle().equals("Logout")) {
                ShareInfo.getInstance().logout(this);
                finish();
            }
            return true;
        });
        popup.show();//showing popup menu
    }

    public void showImagePickerOptions() {
        PopupMenu popup = new PopupMenu(ProfileActivity.this, profileBinding.changeProfilePicture);
        popup.getMenuInflater().inflate(R.menu.popupcam, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {

            if (item.getTitle().equals(getString(R.string.take_picture))) {
                fromCamera();
            } else {
                fromGallery();
            }
            return true;
        });
        popup.show();//showing popup menu
    }

    private void fromCamera() {
        getImageFromCamera(System.currentTimeMillis() + "", new ImageGetListener() {
            @Override
            public void successfullyGetImage(File imageFile, Bitmap imageBitmap) {
                try {
                    setProfileImage(imageBitmap, imageFile.getPath());
                } catch (Exception e) {
                    showToast(getString(R.string.couldnt_upload_image));
                    Log.e("ProfileActivity", "exception", e);
                }
            }

            @Override
            public void failToGetImage(String message) {
                showToast(getString(R.string.couldnt_upload_image) + "\n"+message);
            }
        });
    }

    private void fromGallery() {
        getImageFromGallery(System.currentTimeMillis() + "", new ImageGetListener() {
            @Override
            public void successfullyGetImage(File imageFile, Bitmap imageBitmap) {
                try {
                    setProfileImage(imageBitmap, imageFile.getPath());
                } catch (Exception e) {
                    showToast(getString(R.string.couldnt_upload_image));
                    Log.e("ProfileActivity", "exception", e);
                }
            }

            @Override
            public void failToGetImage(String message) {
                showToast(getString(R.string.couldnt_upload_image) + "\n"+message);
            }
        });
    }

    private void setProfileImage(Bitmap imageBitmap, String imagePath) {
        profileBinding.profilePicture.setImageBitmap(imageBitmap);
        authenticationManager.uploadPhoto(imagePath, new UploadPhotoListener() {
            @Override
            public void uploadPhotoListenerSuccess(String message) {
                showToast(getString(R.string.image_update_successful));
            }

            @Override
            public void uploadPhotoListenerFail(int responseCode, String message) {
                showToast(getString(R.string.couldnt_upload_image));
                profileBinding.profilePicture.setImageBitmap(null);
                Picasso.with(ProfileActivity.this)
                        .load(ShareInfo.getInstance().getRootBaseUrl()+userResponseList.getUser().getAvatarUrl().toString())
                        .error(R.drawable.fajar)
                        .placeholder(R.drawable.fajar)
                        .into(profileBinding.profilePicture);
            }

            @Override
            public void startLoading(String requestId) {
                showProgressDialog(getString(R.string.uploading_photo), false);
            }

            @Override
            public void endLoading(String requestId) {
                dismissProgressDialog();
            }
        });
    }

    private void generateViewUser(UserResponse userResponseList) {

        dob = userResponseList.getUser().getBirthDate().toString().split("-");
        String age = getAge(Integer.valueOf(dob[0]), Integer.valueOf(dob[1]), Integer.valueOf(dob[2]));
        profileBinding.userFullname.setText(userResponseList.getUser().getChildName());
        profileBinding.userAge.setText(String.format("%s %s", age, getString(R.string.years)));

        Log.i("profileImage",ShareInfo.getInstance().getRootBaseUrl()+userResponseList.getUser().getAvatarUrl());
        Log.i("profileImageUser",new Gson().toJson(userResponseList));

        Picasso.with(this)
                .load(ShareInfo.getInstance().getRootBaseUrl()+userResponseList.getUser().getAvatarUrl().toString())
                .error(R.drawable.fajar)
                .placeholder(R.drawable.fajar)
                .into(profileBinding.profilePicture);
        /*   topicScreenBinding.adventureDetails.setText(topicItemsResponseList.getTopic().getAdventure().getDescription());*/
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageS;
    }
}
