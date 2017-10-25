package tk.geta.alzheimervr.View.Fragment.Videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.geta.alzheimervr.Adapter.VideoAdapter;
import tk.geta.alzheimervr.Interface.OnGetPageTitle;
import tk.geta.alzheimervr.R;

public class Salvos extends Fragment implements OnGetPageTitle {

    private RecyclerView recyclerViewVideos;

    public Salvos() {

    }

    public static Salvos newInstance() {
        return new Salvos();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutInflater = inflater.inflate(R.layout.videos_fragment_novos, container, false);

        recyclerViewVideos = (RecyclerView) layoutInflater.findViewById(R.id.videos_fragment_novos_list);
        recyclerViewVideos.setAdapter(new VideoAdapter());

        return layoutInflater;
    }

    @Override
    public String onGetPageTitle() {
        return "Salvos";
    }
}
