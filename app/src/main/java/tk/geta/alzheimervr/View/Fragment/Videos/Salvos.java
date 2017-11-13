package tk.geta.alzheimervr.View.Fragment.Videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tk.geta.alzheimervr.Adapter.VideoAdapter;
import tk.geta.alzheimervr.Dao.SqLite.Video;
import tk.geta.alzheimervr.Interface.OnGetPageTitleListener;
import tk.geta.alzheimervr.Interface.OnPostSqLiteListVideoExecuteListener;
import tk.geta.alzheimervr.R;

public class Salvos extends Fragment implements OnGetPageTitleListener, OnPostSqLiteListVideoExecuteListener {

    private RecyclerView recyclerViewVideos;
    private VideoAdapter adapterRecyclerViewVideos;

    public Salvos() {

    }

    public static Salvos newInstance() {
        return new Salvos();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Video(getContext())
            .setOnPostSqLiteListVideoExecuteListener(this)
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
    public String onGetPageTitle() {
        return "Salvos";
    }

    @Override
    public void onPostSqLiteListVideoExecute(ArrayList<com.google.api.services.youtube.model.Video> videoList) {

        for (com.google.api.services.youtube.model.Video video : videoList) {
            System.out.println(video);
        }
    }
}
