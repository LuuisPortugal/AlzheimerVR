package tk.geta.alzheimervr.View.Detail;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.google.api.services.youtube.model.VideoListResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import tk.geta.alzheimervr.Dao.Youtube.VideoYoutubeDao;
import tk.geta.alzheimervr.Interface.OnPostYoutubeVideoExecuteListenerInterface;
import tk.geta.alzheimervr.Model.Youtube.VideoYoutubeModel;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.Error;

public class NovosVideosFragmentDetail extends AppCompatActivity implements OnPostYoutubeVideoExecuteListenerInterface {

    public static final String ARG_VIDEO_ID = "video_id";

    public long downloadServiceVideoResquestID;
    public Toolbar toolbar;
    public ProgressDialog progressDialog;
    public TextView textViewDescription;
    public com.google.api.services.youtube.model.Video video;
    public CollapsingToolbarLayout collapsingToolbarLayout;
    public FloatingActionButton floatingActionButtonAssistir;
    public FloatingActionButton floatingActionButtonDownload;
    public FloatingActionMenu floatingActionMenu;
    public NestedScrollView activity_videos_detail_nested_scroll_view;
    public IntentFilter intentFilterBroadcastReceiverSuccessful;
    public Intent intentBroadcastReceiverSuccessful;

    public View.OnClickListener onClickListenerAssistir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getId()));
            if (intent.resolveActivity(getPackageManager()) == null) {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getId()));
            }

            floatingActionMenu.close(true);
            startActivity(intent);
        }
    };

    public View.OnClickListener onClickListenerDownload = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            progressDialog.show();
            floatingActionMenu.close(true);

            if (video != null) {
                youTubeExtractor.extract(video.getId(), true, false);
            } else {
                progressDialog.dismiss();
                Error.execute(NovosVideosFragmentDetail.this, new Exception("Falha ao pegar as informações"));
            }
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

    public YouTubeExtractor youTubeExtractor = new YouTubeExtractor(NovosVideosFragmentDetail.this) {
        @Override
        protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
            if (sparseArray != null) {
                int itag = 22;
                YtFile file = sparseArray.get(itag);

                if (file != null) {
                    String path = Environment.DIRECTORY_MOVIES.concat("/" + getString(R.string.app_name));
                    String filename = video.getId().concat("." + file.getFormat().getExt());
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(file.getUrl()));

                    request.allowScanningByMediaScanner();
                    request.setTitle(video.getSnippet().getTitle());
                    request.setDestinationInExternalPublicDir(path, filename);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

                    downloadServiceVideoResquestID = ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
                    progressDialog.dismiss();
                }
            }
        }
    };

    BroadcastReceiver broadcastReceiverSuccessful = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
            if (id == downloadServiceVideoResquestID) {
                try {
                    DownloadManager.Query query = new DownloadManager
                        .Query()
                        .setFilterById(id);
                    Cursor cursor = ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).query(query);

                    if (cursor.moveToFirst()) {
                        int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
                            new VideoYoutubeModel(video);
                        } else {
                            Toast.makeText(NovosVideosFragmentDetail.this, "Falha ao fazer o Download do Arquivo", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(NovosVideosFragmentDetail.this, "Falha ao capturar informações do VideoYoutubeDao", Toast.LENGTH_LONG).show();
                    }

                    cursor.close();
                } catch (Exception e) {
                    Error.execute(NovosVideosFragmentDetail.this, e);
                }
            }
        }
    };

    public Target targetThumbnail = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ImageView imageView = new ImageView(NovosVideosFragmentDetail.this);
            imageView.setImageBitmap(bitmap);

            collapsingToolbarLayout.setBackground(imageView.getDrawable());
            setSupportActionBar(toolbar);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Error.execute(NovosVideosFragmentDetail.this, new Exception("Falha ao carregar a Imagem"));
            collapsingToolbarLayout.setBackground(errorDrawable);
            setSupportActionBar(toolbar);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            collapsingToolbarLayout.setBackground(placeHolderDrawable);
            setSupportActionBar(toolbar);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_detail);

        toolbar = (Toolbar) findViewById(R.id.activity_videos_detail_toolbar);
        textViewDescription = (TextView) findViewById(R.id.activity_videos_detail_description);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_videos_detail_toolbar_layout);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.activity_videos_detail_fab_menu);

        activity_videos_detail_nested_scroll_view = (NestedScrollView) findViewById(R.id.activity_videos_detail_nested_scroll_view);
        activity_videos_detail_nested_scroll_view.setOnTouchListener(onTouchListenerNestedScroll);

        floatingActionButtonAssistir = (FloatingActionButton) findViewById(R.id.activity_videos_detail_fab_menu_item_assistir);
        floatingActionButtonAssistir.setOnClickListener(onClickListenerAssistir);

        floatingActionButtonDownload = (FloatingActionButton) findViewById(R.id.activity_videos_detail_fab_menu_item_download);
        floatingActionButtonDownload.setOnClickListener(onClickListenerDownload);

        progressDialog = new ProgressDialog(NovosVideosFragmentDetail.this);
        progressDialog.setMessage("Carregando");
        progressDialog.setIndeterminate(true);

        intentFilterBroadcastReceiverSuccessful = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        if(intentBroadcastReceiverSuccessful == null)
            intentBroadcastReceiverSuccessful = registerReceiver(broadcastReceiverSuccessful, intentFilterBroadcastReceiverSuccessful);

        if (getIntent().getExtras().containsKey(ARG_VIDEO_ID)) {
            String videoID = getIntent().getStringExtra(ARG_VIDEO_ID);

            if (videoID != null) {
                new VideoYoutubeDao(this).byIDs(videoID)
                        .setOnPostYoutubeVideoExecuteListenerInterface(this)
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
    public void onPostYoutubeVideoExecuteListener(String videoMethodType, VideoListResponse videoListResponse) {
        switch (videoMethodType) {
            case VideoYoutubeDao.BY_IDS_VIDEO_METHOD_TYPE:
                video = videoListResponse.getItems().get(0);

                toolbar.setTitle(video.getSnippet().getTitle());
                toolbar.setSubtitle(video.getSnippet().getChannelTitle());
                textViewDescription.setText(video.getSnippet().getDescription());

                String url = getMaxResolution(video.getSnippet().getThumbnails()).getUrl();
                if (url != null && !url.isEmpty()) {
                    Picasso.with(this)
                            .load(url)
                            .into(targetThumbnail);

                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                break;
        }
    }

    public Thumbnail getMaxResolution(ThumbnailDetails thumbnailDetails) {
        if (thumbnailDetails.getMaxres() != null)
            return thumbnailDetails.getMaxres();
        else if (thumbnailDetails.getHigh() != null)
            return thumbnailDetails.getHigh();
        else if (thumbnailDetails.getMedium() != null)
            return thumbnailDetails.getMedium();
        else if (thumbnailDetails.getStandard() != null)
            return thumbnailDetails.getStandard();
        else
            return thumbnailDetails.getDefault();
    }
}
