package id.co.skoline.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.AdapterSampleBinding;
import id.co.skoline.databinding.AdapterSubjectListBinding;
import id.co.skoline.databinding.AdapterTopicListBinding;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.response.TopicResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.activities.SubjectsActivity;
import id.co.skoline.view.activities.TopicItemsActivity;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>{

    Context context;
    List<TopicResponse>topicResponseList;
    OnItemClickListener onItemClickListener;
    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterTopicListBinding adapterTopicListBinding;
        TextView subjectName;

        public ViewHolder(AdapterTopicListBinding adapterTopicListBinding) {
            super(adapterTopicListBinding.getRoot());
            this.adapterTopicListBinding = adapterTopicListBinding;

        }
    }

    public TopicAdapter(Context context, List<TopicResponse> topicResponseList) {
        this.context = context;
        this.topicResponseList = topicResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_topic_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TopicResponse topicResponse = topicResponseList.get(position);
        Picasso.with(context).load(ShareInfo.getInstance().getBaseUrl()+ topicResponse.getBannerUrl()).into(holder.adapterTopicListBinding.topicBanner);
        holder.adapterTopicListBinding.topicTitle.setText(topicResponse.getName());


        holder.adapterTopicListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,TopicItemsActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return topicResponseList.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


}
