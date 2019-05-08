package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySearchResultBinding;
import id.co.skoline.model.response.SearchResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.adapters.SearchResultAdapter;
import id.co.skoline.view.adapters.SubjectsAdapter;
import id.co.skoline.viewControllers.interfaces.SearchListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class SearchResultActivity extends BaseActivity {

 ActivitySearchResultBinding searchResultBinding;
  String titleToolbar;
  ContentManager contentManager;
  List<SearchResponse> searchResponseList;
  SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchResultBinding = DataBindingUtil.setContentView(this,R.layout.activity_search_result);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(SearchResultActivity.this);
        searchResultBinding.searchRecyclerView.setLayoutManager(mlayoutManager);
        searchResultBinding.searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchResultBinding.searchProgress.setVisibility(View.GONE);
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.searchTitle),true,searchResultBinding.toolbarBinding);
      //  Toast.makeText(this, titleToolbar, Toast.LENGTH_SHORT).show();
        searchResultBinding.searchMagIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentManager = new ContentManager(SearchResultActivity.this);
                contentManager.getSearchResults(searchResultBinding.searchEditFrame.getText().toString(), new SearchListener() {
                    @Override
                    public void onSuccess(List<SearchResponse> searchResponseList) {
                        SearchResultActivity.this.searchResponseList = searchResponseList;
                        generateViewSubject(searchResponseList);
                    }
                    @Override
                    public void onFailed(String message, int responseCode) {
                        showToast(getString(R.string.couldnt_find));
                    }
                    @Override
                    public void startLoading(String requestId) {
                        searchResultBinding.searchProgress.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void endLoading(String requestId) {
                        searchResultBinding.searchProgress.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void generateViewSubject(List<SearchResponse> searchResponseList) {
        searchResultAdapter= new SearchResultAdapter(SearchResultActivity.this,searchResponseList);
        searchResultBinding.searchRecyclerView.setAdapter(searchResultAdapter);
        searchResultAdapter.notifyDataSetChanged();
      searchResultAdapter.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
          @Override
          public void onItemClicked(SearchResponse searchResponse) {
              Intent intent = new Intent(SearchResultActivity.this,VideoPlayActivity.class);
              intent.putExtra("videoUrl", ShareInfo.getInstance().getRootBaseUrl()+searchResponse.getVideoLink());
              intent.putExtra("videoId",searchResponse.getId());
              startActivity(intent);
          }
        });
    }
    public void search(View view) {
    }
}
