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
import id.co.skoline.databinding.AdapterFaqListBinding;
import id.co.skoline.databinding.AdapterSampleBinding;
import id.co.skoline.databinding.AdapterSearchResultBinding;
import id.co.skoline.model.response.FaqResponse;
import id.co.skoline.model.response.SearchResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.utils.ShareInfo;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder>{

    Context context;
    List<FaqResponse> faqResponsesList;

    public FaqAdapter(Context context, List<FaqResponse> faqResponsesList) {
        this.context = context;
        this.faqResponsesList = faqResponsesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_faq_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FaqResponse faqResponse= faqResponsesList.get(position);
        holder.faqListBinding.faqQuestion.setText(faqResponsesList.get(position).getQuestion());
        holder.faqListBinding.faqAnswers.setText(faqResponsesList.get(position).getAnswer());

    }

    @Override
    public int getItemCount() {
        return faqResponsesList.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(SearchResponse searchResponse);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterFaqListBinding faqListBinding;

        public ViewHolder(AdapterFaqListBinding faqListBinding) {
            super(faqListBinding.getRoot());
            this.faqListBinding = faqListBinding;

        }
    }
}
