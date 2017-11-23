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

import java.util.ArrayList;

import tk.geta.alzheimervr.Adapter.NovoVideoAdapter;
import tk.geta.alzheimervr.Dao.Youtube.SearchYoutubeDao;
import tk.geta.alzheimervr.Interface.OnBackPressedListenerInterface;
import tk.geta.alzheimervr.Interface.OnCreateOptionsMenuListenerInterface;
import tk.geta.alzheimervr.Interface.OnGetPageTitleListenerInterface;
import tk.geta.alzheimervr.Interface.OnPostYoutubeSearchExecuteListenerInterface;
import tk.geta.alzheimervr.Model.Youtube.SnippetYoutubeModel;
import tk.geta.alzheimervr.Model.Youtube.ThumbnailDetailYoutubeModel;
import tk.geta.alzheimervr.Model.Youtube.ThumbnailsYoutubeModel;
import tk.geta.alzheimervr.Model.Youtube.VideoYoutubeModel;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.Error;
import tk.geta.alzheimervr.Util.Internet;
import tk.geta.alzheimervr.Util.Keyboard;

public class NovosVideosFragment extends Fragment implements OnGetPageTitleListenerInterface, OnBackPressedListenerInterface, OnPostYoutubeSearchExecuteListenerInterface, OnCreateOptionsMenuListenerInterface, View.OnScrollChangeListener, SearchView.OnQueryTextListener {

    private String nextPageToken;
    private int pageInfoTotalResults;
    private SearchView appBarSearchVideos;
    private String queryAppBarSearchVideos;
    private RecyclerView recyclerViewVideos;
    private NovoVideoAdapter adapterRecyclerViewVideos;
    private LinearLayoutManager linearLayoutManager;
    private boolean isRefreshingOnFinishRecicleview = false;
    private String defaultQueryAppBarSearchVideos = "Maquete 360";

    public NovosVideosFragment() {

    }

    public static NovosVideosFragment newInstance() {
        return new NovosVideosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (Internet.isConnect(getContext())) {
            new SearchYoutubeDao(getContext()).byQuery(defaultQueryAppBarSearchVideos)
                    .setOnPostExecuteListener(this)
                    .setMaxResult(50)
                    .execute();
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutInflater = inflater.inflate(R.layout.videos_fragment_novos, container, false);

        recyclerViewVideos = (RecyclerView) layoutInflater.findViewById(R.id.videos_fragment_novos_list);
        recyclerViewVideos.setOnScrollChangeListener(this);

        adapterRecyclerViewVideos = new NovoVideoAdapter();
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
        new SearchYoutubeDao(getContext()).byQuery(query)
                .setOnPostExecuteListener(this)
                .setMaxResult(50)
                .execute();

        Keyboard.hide(getContext());
        appBarSearchVideos.clearFocus();

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
                new SearchYoutubeDao(getContext()).byQuery(query)
                    .setOnPostExecuteListener(this)
                    .setNextPageToken(nextPageToken)
                    .setMaxResult(50)
                    .execute();
            }
        }
    }

    @Override
    public void onPostYoutubeSearchExecuteListener(String searchMethodType, SearchListResponse searchListResponse) {
        nextPageToken = searchListResponse.getNextPageToken();
        pageInfoTotalResults = searchListResponse.getItems().size();
        if (searchListResponse.getPageInfo() != null)
            pageInfoTotalResults = searchListResponse.getPageInfo().getTotalResults();

        switch (searchMethodType) {
            case SearchYoutubeDao.BY_QUERY_SEARCH_METHOD_TYPE:
                try {
                    if (!isRefreshingOnFinishRecicleview)
                        adapterRecyclerViewVideos.setValues(new ArrayList<VideoYoutubeModel>());

                    for (SearchResult searchResult : searchListResponse.getItems()) {
                        VideoYoutubeModel youtubeModel = new VideoYoutubeModel();
                        SnippetYoutubeModel snippetYoutubeModel = new SnippetYoutubeModel();

                        if (searchResult.getSnippet().getTitle() != null)
                            snippetYoutubeModel.setTitle(searchResult.getSnippet().getTitle());
                        if (searchResult.getSnippet().getChannelId() != null)
                            snippetYoutubeModel.setChannelId(searchResult.getSnippet().getChannelId());
                        if (searchResult.getSnippet().getChannelTitle() != null)
                            snippetYoutubeModel.setChannelTitle(searchResult.getSnippet().getChannelTitle());
                        if (searchResult.getSnippet().getDescription() != null)
                            snippetYoutubeModel.setDescription(searchResult.getSnippet().getDescription());
                        if (searchResult.getSnippet().getPublishedAt() != null)
                            snippetYoutubeModel.setPublishedAt(searchResult.getSnippet().getPublishedAt());
                        if (searchResult.getSnippet().getThumbnails() != null) {
                            snippetYoutubeModel.setThumbnails(new ThumbnailsYoutubeModel());
                            if (searchResult.getSnippet().getThumbnails().getDefault() != null) {
                                snippetYoutubeModel.getThumbnails().setDefault(
                                        new ThumbnailDetailYoutubeModel()
                                                .setHeight(searchResult.getSnippet().getThumbnails().getDefault().getHeight())
                                                .setUrl(searchResult.getSnippet().getThumbnails().getDefault().getUrl())
                                                .setWidth(searchResult.getSnippet().getThumbnails().getDefault().getWidth())
                                );
                            }

                            if (searchResult.getSnippet().getThumbnails().getStandard() != null) {
                                snippetYoutubeModel.getThumbnails().setStandard(
                                        new ThumbnailDetailYoutubeModel()
                                                .setHeight(searchResult.getSnippet().getThumbnails().getStandard().getHeight())
                                                .setUrl(searchResult.getSnippet().getThumbnails().getStandard().getUrl())
                                                .setWidth(searchResult.getSnippet().getThumbnails().getStandard().getWidth())
                                );
                            }

                            if (searchResult.getSnippet().getThumbnails().getHigh() != null) {
                                snippetYoutubeModel.getThumbnails().setHigh(
                                        new ThumbnailDetailYoutubeModel()
                                                .setHeight(searchResult.getSnippet().getThumbnails().getHigh().getHeight())
                                                .setUrl(searchResult.getSnippet().getThumbnails().getHigh().getUrl())
                                                .setWidth(searchResult.getSnippet().getThumbnails().getHigh().getWidth())
                                );
                            }

                            if (searchResult.getSnippet().getThumbnails().getMedium() != null) {
                                snippetYoutubeModel.getThumbnails().setMedium(
                                        new ThumbnailDetailYoutubeModel()
                                                .setHeight(searchResult.getSnippet().getThumbnails().getMedium().getHeight())
                                                .setUrl(searchResult.getSnippet().getThumbnails().getMedium().getUrl())
                                                .setWidth(searchResult.getSnippet().getThumbnails().getMedium().getWidth())
                                );
                            }

                            if (searchResult.getSnippet().getThumbnails().getMaxres() != null) {
                                snippetYoutubeModel.getThumbnails().setMaxres(
                                        new ThumbnailDetailYoutubeModel()
                                                .setHeight(searchResult.getSnippet().getThumbnails().getMaxres().getHeight())
                                                .setUrl(searchResult.getSnippet().getThumbnails().getMaxres().getUrl())
                                                .setWidth(searchResult.getSnippet().getThumbnails().getMaxres().getWidth())
                                );
                            }
                        }

                        youtubeModel.setIdVideo(searchResult.getId().getVideoId());
                        youtubeModel.setSnippet(snippetYoutubeModel);
                        adapterRecyclerViewVideos
                                .add(youtubeModel)
                                .notifyDataSetChanged();
                    }

                    isRefreshingOnFinishRecicleview = false;
                } catch (Exception e) {
                    Error.execute(getContext(), e);
                }
                break;
        }
    }

    @Override
    public String onGetPageTitleListener() {
        return "Novos";
    }
}
