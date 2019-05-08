package id.co.skoline.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.AdapterProgressListBinding;
import id.co.skoline.model.response.SearchResponse;
import id.co.skoline.model.response.UserResponse;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder>{

    Context context;
    List<UserResponse.Progress> progressList;
    OnItemClickListener onItemClickListener;
    AdapterProgressListBinding adapterProgressListBinding;

    public ProgressAdapter(Context context, List<UserResponse.Progress> progressList) {
        this.context = context;
        this.progressList= progressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_progress_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       UserResponse.Progress progress= progressList.get(position);
       holder.progressListBinding.subjectTitle.setText(progressList.get(position).getSubjectName()+"("+progressList.get(position).getCompleteAdventures()+")");
       holder.progressListBinding.subjectProgress.setProgress(50);

    }

    @Override
    public int getItemCount() {
        return progressList.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(UserResponse.Progress userProgress);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       AdapterProgressListBinding progressListBinding;

        public ViewHolder(AdapterProgressListBinding progressListBinding) {
            super(progressListBinding.getRoot());
            this.progressListBinding = progressListBinding;

        }
    }
}

