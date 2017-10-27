package tk.geta.alzheimervr.View.Detail;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.google.api.services.youtube.model.VideoListResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import tk.geta.alzheimervr.Dao.Youtube.Video;
import tk.geta.alzheimervr.Interface.OnPostVideoExecuteListener;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.Error;

public class Videos extends AppCompatActivity implements OnPostVideoExecuteListener {

    public com.google.api.services.youtube.model.Video video;
    public static final String ARG_VIDEO_ID = "video_id";
    public TextView videos_detail_description;
    public Toolbar toolbar;
    public CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_detail);

        toolbar = (Toolbar) findViewById(R.id.activity_videos_detail_toolbar);
        videos_detail_description = (TextView) findViewById(R.id.activity_videos_detail_description);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_videos_detail_toolbar_layout);

        if (getIntent().getExtras().containsKey(ARG_VIDEO_ID)) {
            String videoID = getIntent().getStringExtra(ARG_VIDEO_ID);

            if (videoID != null) {
                new Video(this).byIDs(videoID)
                    .setOnPostExecuteListener(this)
                    .execute();
            }
        }
    }

    @Override
    public void onPostVideoExecute(String videoMethodType, VideoListResponse videoListResponse) {
        switch (videoMethodType) {
            case Video.BY_IDS_VIDEO_METHOD_TYPE:
                synchronized (this) {
                    video = videoListResponse.getItems().get(0);

                    toolbar.setTitle(video.getSnippet().getTitle());
                    toolbar.setSubtitle(video.getSnippet().getChannelTitle());

                    String url = getMaxResolution(video.getSnippet().getThumbnails()).getUrl();
                    if (url != null && !url.isEmpty()) {
                        Picasso.with(this)
                            .load(url)
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    ImageView imageView = new ImageView(Videos.this);
                                    imageView.setImageBitmap(bitmap);

                                    collapsingToolbarLayout.setBackground(imageView.getDrawable());
                                    setSupportActionBar(toolbar);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    Error.execute(Videos.this, new Exception("Falha ao carregar a Imagem"));
                                    collapsingToolbarLayout.setBackground(errorDrawable);
                                    setSupportActionBar(toolbar);
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    collapsingToolbarLayout.setBackground(placeHolderDrawable);
                                    setSupportActionBar(toolbar);
                                }
                            });

                        setSupportActionBar(toolbar);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }

                    videos_detail_description.setText(video.getSnippet().getDescription());
                }
            break;
        }
    }

    public Thumbnail getMaxResolution(ThumbnailDetails thumbnailDetails){
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
