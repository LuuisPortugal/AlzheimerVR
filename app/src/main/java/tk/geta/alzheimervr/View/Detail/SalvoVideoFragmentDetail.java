package tk.geta.alzheimervr.View.Detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import tk.geta.alzheimervr.Dao.SqLite.VideoSqLiteDao;
import tk.geta.alzheimervr.Interface.OnPostSqLiteVideoExecuteListenerInterface;
import tk.geta.alzheimervr.Model.Youtube.ThumbnailsYoutubeModel;
import tk.geta.alzheimervr.Model.Youtube.VideoYoutubeModel;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.View.Player.VrPlayerActivity;

public class SalvoVideoFragmentDetail extends AppCompatActivity implements OnPostSqLiteVideoExecuteListenerInterface {

    public static final String ARG_VIDEO_ID = "video_id";

    public long downloadServiceVideoResquestID;
    public Toolbar toolbar;
    public ProgressDialog progressDialog;
    public TextView textViewDescription;
    public TextView textViewViewsCount;
    public ImageView imageView;
    public VideoYoutubeModel video;
    public CollapsingToolbarLayout collapsingToolbarLayout;
    public FloatingActionButton floatingActionButtonAssistir;
    public FloatingActionMenu floatingActionMenu;
    public NestedScrollView activity_videos_detail_nested_scroll_view;

    public View.OnClickListener onClickListenerAssistir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (floatingActionMenu.isOpened())
                floatingActionMenu.close(true);

            Intent intent = new Intent(SalvoVideoFragmentDetail.this, VrPlayerActivity.class);
            intent.putExtra(NovoVideoFragmentDetail.ARG_VIDEO_ID, video.getIdVideo());
            startActivityForResult(intent, VrPlayerActivity.REQUEST_CODE_FOR_FINISH);
        }
    };

    public View.OnTouchListener onTouchListenerNestedScroll = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (floatingActionMenu.isOpened())
                floatingActionMenu.close(true);
            return activity_videos_detail_nested_scroll_view.onTouchEvent(event);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == VrPlayerActivity.REQUEST_CODE_FOR_FINISH) {
            if(resultCode == Activity.RESULT_OK) {
                if (data != null && data.getExtras() != null && data.getExtras().containsKey(ARG_VIDEO_ID)) {
                    String videoID = data.getStringExtra(ARG_VIDEO_ID);

                    if (videoID != null) {
                        new VideoSqLiteDao(this).setIdVideo(videoID)
                                .setOnPostSqLiteVideoExecuteListenerInterface(this)
                                .execute();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_detail);

        toolbar = (Toolbar) findViewById(R.id.activity_videos_detail_toolbar);
        textViewDescription = (TextView) findViewById(R.id.activity_videos_detail_description);
        textViewViewsCount = (TextView) findViewById(R.id.activity_videos_detail_view_count);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_videos_detail_toolbar_layout);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.activity_videos_detail_fab_menu);

        activity_videos_detail_nested_scroll_view = (NestedScrollView) findViewById(R.id.activity_videos_detail_nested_scroll_view);
        activity_videos_detail_nested_scroll_view.setOnTouchListener(onTouchListenerNestedScroll);

        floatingActionButtonAssistir = (FloatingActionButton) findViewById(R.id.activity_videos_detail_fab_menu_item_assistir);
        floatingActionButtonAssistir.setOnClickListener(onClickListenerAssistir);

        //Esconde o Botão
        findViewById(R.id.activity_videos_detail_fab_menu_item_download)
            .setVisibility(View.GONE);

        progressDialog = new ProgressDialog(SalvoVideoFragmentDetail.this);
        progressDialog.setMessage("Carregando");
        progressDialog.setIndeterminate(true);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_VIDEO_ID)) {
            String videoID = getIntent().getStringExtra(ARG_VIDEO_ID);

            if (videoID != null) {
                new VideoSqLiteDao(this).setIdVideo(videoID)
                        .setOnPostSqLiteVideoExecuteListenerInterface(this)
                        .execute();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (floatingActionMenu.isOpened()) {
            floatingActionMenu.close(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPostSqLiteVideoExecuteListener(List<VideoYoutubeModel> videoYoutubeModelList) {
        video = videoYoutubeModelList.get(0);

        toolbar.setTitle(video.getSnippet().getTitle());
        toolbar.setSubtitle(video.getSnippet().getChannelTitle());
        textViewDescription.setText(video.getSnippet().getDescription());

        imageView = ThumbnailsYoutubeModel.getMaxResolution(video.getSnippet().getThumbnails()).getBase64(this);
        collapsingToolbarLayout.setBackground(imageView.getDrawable());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
