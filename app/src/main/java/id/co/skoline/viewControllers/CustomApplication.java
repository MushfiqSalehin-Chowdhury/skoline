package id.co.skoline.viewControllers;

import android.app.Application;
import android.content.Context;

import id.co.skoline.view.custom.TypefaceUtil;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/customfont/font-normal.ttf");
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
