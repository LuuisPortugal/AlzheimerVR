package tk.geta.alzheimervr.View.Fragment.Videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tk.geta.alzheimervr.Adapter.VideoAdapter;
import tk.geta.alzheimervr.Dao.SqLite.VideoSqLiteDao;
import tk.geta.alzheimervr.Interface.OnGetPageTitleListenerInterface;
import tk.geta.alzheimervr.Interface.OnPostSqLiteListVideoExecuteListenerInterface;
import tk.geta.alzheimervr.Model.Youtube.VideoYoutubeModel;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.Error;

public class SalvosVideosFragment extends Fragment implements OnGetPageTitleListenerInterface, OnPostSqLiteListVideoExecuteListenerInterface {

    private RecyclerView recyclerViewVideos;
    private VideoAdapter adapterRecyclerViewVideos;

    public SalvosVideosFragment() {

    }

    public static SalvosVideosFragment newInstance() {
        return new SalvosVideosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new VideoSqLiteDao(getContext())
            .setOnPostSqLiteListVideoExecuteListenerInterface(this)
            .execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutInflater = inflater.inflate(R.layout.videos_fragment_salvos, container, false);

        recyclerViewVideos = (RecyclerView) layoutInflater.findViewById(R.id.videos_fragment_salvos_list);
        adapterRecyclerViewVideos = new VideoAdapter();
        recyclerViewVideos.setAdapter(adapterRecyclerViewVideos);

        return layoutInflater;
    }

    @Override
    public String onGetPageTitleListener() {
        return "Salvos";
    }

    @Override
    public void onPostSqLiteListVideoExecuteListener(List<VideoYoutubeModel> videoList) {
        try {
            adapterRecyclerViewVideos
                .setValues(videoList)
                .notifyDataSetChanged();
        } catch (Exception e) {
            Error.execute(getContext(), e);
        }
    }
}
