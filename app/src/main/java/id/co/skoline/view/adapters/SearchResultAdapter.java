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
import id.co.skoline.databinding.AdapterSampleBinding;
import id.co.skoline.databinding.AdapterSearchResultBinding;
import id.co.skoline.model.response.SearchResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.utils.ShareInfo;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{

    Context context;
    List<SearchResponse> searchResponseList;
    OnItemClickListener onItemClickListener;

    public SearchResultAdapter(Context context, List<SearchResponse> searchResponsesList) {
        this.context = context;
        this.searchResponseList = searchResponsesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_search_result, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SearchResponse searchResponse= searchResponseList.get(position);
       Picasso.with(context).load(ShareInfo.getInstance().getRootBaseUrl()+searchResponseList.get(position).getBanner()).into(holder.adaptersearchResultBinding.topicBanner);
        holder.adaptersearchResultBinding.topicTitle.setText(searchResponseList.get(position).getTitle());

        try{
            holder.adaptersearchResultBinding.getRoot().setOnClickListener(v -> {
                /*Intent intent = new Intent(context, TopicScreenActivity.class);
                intent.putExtra("subId",subjectResponse.getId());
                intent.putExtra("subIcon",subjectResponse.getIconUrl());
                intent.putExtra("subTitle",subjectResponse.getName());
                context.startActivity(intent);*/
                onItemClickListener.onItemClicked(searchResponseList.get(position));
            });
        }catch (Exception e){
            Log.i("clickError",String.valueOf(e));
        }

    }

    @Override
    public int getItemCount() {
        return searchResponseList.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(SearchResponse searchResponse);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterSearchResultBinding adaptersearchResultBinding;

        public ViewHolder(AdapterSearchResultBinding adapterSearchResultBinding) {
            super(adapterSearchResultBinding.getRoot());
            this.adaptersearchResultBinding = adapterSearchResultBinding;

        }
    }
}
