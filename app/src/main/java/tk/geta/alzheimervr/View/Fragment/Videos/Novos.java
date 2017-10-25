package tk.geta.alzheimervr.View.Fragment.Videos;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import tk.geta.alzheimervr.Adapter.VideoAdapter;
import tk.geta.alzheimervr.Dao.Youtube.Search;
import tk.geta.alzheimervr.Interface.OnBackPressed;
import tk.geta.alzheimervr.Interface.OnCreateOptionsMenu;
import tk.geta.alzheimervr.Interface.OnGetPageTitle;
import tk.geta.alzheimervr.Interface.OnPostSearchExecute;
import tk.geta.alzheimervr.R;

public class Novos extends Fragment implements OnGetPageTitle, OnBackPressed, OnPostSearchExecute, View.OnScrollChangeListener, SearchView.OnQueryTextListener, OnCreateOptionsMenu {

    private String nextPageToken;
    private int pageInfoTotalResults;
    private SearchView appBarSearchVideos;
    private String queryAppBarSearchVideos;
    private RecyclerView recyclerViewVideos;
    private VideoAdapter adapterRecyclerViewVideos;
    private LinearLayoutManager linearLayoutManager;
    private boolean isRefreshingOnFinishRecicleview = false;
    private String defaultQueryAppBarSearchVideos = "Realidade Virtual 360";

    public Novos() {

    }

    public static Novos newInstance() {
        return new Novos();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Search(getContext()).byQuery(defaultQueryAppBarSearchVideos)
                .setOnPostExecuteListener(this)
                .setMaxResult(50)
                .execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutInflater = inflater.inflate(R.layout.videos_fragment_novos, container, false);

        recyclerViewVideos = (RecyclerView) layoutInflater.findViewById(R.id.videos_fragment_novos_list);
        recyclerViewVideos.setOnScrollChangeListener(this);

        adapterRecyclerViewVideos = new VideoAdapter();
        recyclerViewVideos.setAdapter(adapterRecyclerViewVideos);
        linearLayoutManager = (LinearLayoutManager) recyclerViewVideos.getLayoutManager();

        return layoutInflater;
    }

    @Override
    public void onBackPressedListener() {
        if(!appBarSearchVideos.isIconified())
            appBarSearchVideos.onActionViewCollapsed();
    }

    @Override
    public boolean onCreateOptionsMenuListener(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.activity_videos_toolbar, menu);

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        appBarSearchVideos = (SearchView) menu.findItem(R.id.app_bar_search_videos).getActionView();
        appBarSearchVideos.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        appBarSearchVideos.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query == null || query.isEmpty()) {
            queryAppBarSearchVideos = null;
            return true;
        }

        queryAppBarSearchVideos = query;
        new Search(getContext()).byQuery(query)
                .setOnPostExecuteListener(this)
                .setMaxResult(50)
                .execute();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int totalItemCount = linearLayoutManager.getItemCount();
        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!isRefreshingOnFinishRecicleview && (totalItemCount - 5) == lastVisibleItem) {
            if (adapterRecyclerViewVideos.getItemCount() < pageInfoTotalResults) {
                isRefreshingOnFinishRecicleview = true;

                String query = queryAppBarSearchVideos != null && !queryAppBarSearchVideos.isEmpty() ?
                        queryAppBarSearchVideos : defaultQueryAppBarSearchVideos;
                new Search(getContext()).byQuery(query)
                    .setOnPostExecuteListener(this)
                    .setNextPageToken(nextPageToken)
                    .setMaxResult(50)
                    .execute();
            }
        }
    }

    @Override
    public void onPostSearchExecuteListener(String searchMethodType, SearchListResponse searchListResponse) {
        nextPageToken = searchListResponse.getNextPageToken();
        pageInfoTotalResults = searchListResponse.getItems().size();
        if (searchListResponse.getPageInfo() != null)
            pageInfoTotalResults = searchListResponse.getPageInfo().getTotalResults();

        switch (searchMethodType) {
            case Search.BY_QUERY_SEARCH_METHOD_TYPE:
                if (isRefreshingOnFinishRecicleview){
                    isRefreshingOnFinishRecicleview = false;
                    for (Object searchResult : searchListResponse.getItems().toArray()) {
                        adapterRecyclerViewVideos
                                .add((SearchResult) searchResult)
                                .notifyDataSetChanged();
                    }
                }else{
                    adapterRecyclerViewVideos
                            .setValues(searchListResponse.getItems())
                            .notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public String onGetPageTitle() {
        return "Novos";
    }
}
