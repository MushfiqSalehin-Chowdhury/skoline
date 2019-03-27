package id.co.skoline.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import id.co.skoline.R;
import id.co.skoline.view.activities.BaseActivity;

public class HomepageAdapter extends BaseAdapter {
    private Context mContext;


    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.class1,
            R.drawable.class2,
            R.drawable.class3,
            R.drawable.class4,
            R.drawable.class5,
            R.drawable.class6
    };

    // Constructor
    public HomepageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        gridView= new View(mContext);
        gridView= inflater.inflate(R.layout.classes,null);

        ImageView imageView =(ImageView) gridView.findViewById(R.id.grid_image);
        imageView.setImageResource(mThumbIds[position]);
       /* imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(600, 300));*/
        return imageView;
    }
}
