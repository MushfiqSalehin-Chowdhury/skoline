package id.co.skoline.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityMainBinding;
import id.co.skoline.databinding.ActivitySubjectsBinding;
import id.co.skoline.databinding.AdapterSampleBinding;
import id.co.skoline.databinding.AdapterSubjectListBinding;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.activities.MainActivity;
import id.co.skoline.view.activities.SubjectsActivity;
import id.co.skoline.view.activities.TopicScreenActivity;

public class SubjectsAdapter extends  RecyclerView.Adapter<SubjectsAdapter.ViewHolder>{

    Context context;
    List<SubjectResponse> subjectResponseList;
    List<KlassesResponse> klassesResponseList;
    OnItemClickListener onItemClickListener;
    String name,icon;
    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterSubjectListBinding adapterSubjectListBinding;
        ActivityMainBinding mainBinding;
        TextView subjectName;


        public ViewHolder(AdapterSubjectListBinding adapterSubjectListBinding) {
            super(adapterSubjectListBinding.getRoot());
            this.adapterSubjectListBinding = adapterSubjectListBinding;

        }
    }
    public SubjectsAdapter(Context context, List<SubjectResponse> subjectResponseList) {
        this.context = context;
        this.subjectResponseList = subjectResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_subject_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubjectResponse subjectResponse= subjectResponseList.get(position);
        Picasso.with(context).load(ShareInfo.getInstance().getBaseUrl()+subjectResponseList.get(position).getIconUrl()).into(holder.adapterSubjectListBinding.subjectIcon);
        holder.adapterSubjectListBinding.subjectTitle.setText(subjectResponseList.get(position).getName());

        try{
            holder.adapterSubjectListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(context, TopicScreenActivity.class);
                    intent.putExtra("subId",subjectResponse.getId());
                    intent.putExtra("subIcon",subjectResponse.getIconUrl());
                    intent.putExtra("subTitle",subjectResponse.getName());
                    context.startActivity(intent);*/
                    onItemClickListener.onItemClicked(subjectResponseList.get(position));
                }
            });
        }catch (Exception e){
            Log.i("clickError",String.valueOf(e));
        }
    }

    @Override
    public int getItemCount() {
        return subjectResponseList.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(SubjectResponse subjectResponse);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

  }


}
