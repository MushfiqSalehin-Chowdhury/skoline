package id.co.skoline.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.AdapterBonusVideosBinding;
import id.co.skoline.databinding.AdapterSampleBinding;
import id.co.skoline.databinding.AdapterSearchResultBinding;
import id.co.skoline.model.response.BonusVideoResponse;
import id.co.skoline.model.response.SearchResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.utils.ShareInfo;

public class BonusVideoAdapter extends RecyclerView.Adapter<BonusVideoAdapter.ViewHolder>{

    Context context;
    List<BonusVideoResponse> bonusVideoResponseList;
    OnItemClickListener onItemClickListener;
   AdapterBonusVideosBinding bonusVideosBinding;

    public BonusVideoAdapter(Context context, List<BonusVideoResponse> bonusVideoResponseList) {
        this.context = context;
        this.bonusVideoResponseList=bonusVideoResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_bonus_videos, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BonusVideoResponse bonusVideoResponse= bonusVideoResponseList.get(position);
       // Picasso.with(context).load(ShareInfo.getInstance().getRootBaseUrl()+bonusVideoResponseList.get(position).getBanner()).into(holder.bonusVideosBinding.bonusVideoBanner);
      //  holder.bonusVideosBinding.bonusVideoTitle.setText(bonusVideoResponseList.get(position).getTitle());
        try{
            holder.bonusVideosBinding.getRoot().setOnClickListener(v -> {
                /*Intent intent = new Intent(context, TopicScreenActivity.class);
                intent.putExtra("subId",subjectResponse.getId());
                intent.putExtra("subIcon",subjectResponse.getIconUrl());
                intent.putExtra("subTitle",subjectResponse.getName());
                context.startActivity(intent);*/
                onItemClickListener.onItemClicked(bonusVideoResponseList.get(position));
            });
        }catch (Exception e){
            Log.i("clickError",String.valueOf(e));
        }

    }

    @Override
    public int getItemCount() {
        return bonusVideoResponseList.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(BonusVideoResponse bonusVideoResponse);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

     AdapterBonusVideosBinding bonusVideosBinding;

        public ViewHolder(AdapterBonusVideosBinding adapterBonusVideosBinding) {
            super(adapterBonusVideosBinding.getRoot());
            this.bonusVideosBinding = adapterBonusVideosBinding;
        }
    }
}
