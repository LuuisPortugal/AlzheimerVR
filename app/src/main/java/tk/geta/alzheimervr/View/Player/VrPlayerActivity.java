package tk.geta.alzheimervr.View.Player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;
import java.util.List;

import tk.geta.alzheimervr.Dao.SqLite.VideoSqLiteDao;
import tk.geta.alzheimervr.Interface.OnPostSqLiteVideoExecuteListenerInterface;
import tk.geta.alzheimervr.Model.Youtube.VideoYoutubeModel;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.Error;
import tk.geta.alzheimervr.View.Detail.NovoVideoFragmentDetail;
import tk.geta.alzheimervr.View.Detail.SalvoVideoFragmentDetail;

public class VrPlayerActivity extends AppCompatActivity implements OnPostSqLiteVideoExecuteListenerInterface {

    public static final int REQUEST_CODE_FOR_FINISH = 1010;
    public boolean isPlaying = false;
    public String videoID;
    private VideoYoutubeModel video;
    private static final int UI_ANIMATION_DELAY = 300;
    public MenuItem pauseMenuItem;
    public MenuItem playMenuItem;
    private final Handler mHideHandler = new Handler();
    private VrVideoView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private VrVideoEventListener vrVideoEventListener = new VrVideoEventListener(){
        @Override
        public void onCompletion() {
            super.onCompletion();
            onBackPressed();
        }

        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);

            Error.execute(VrPlayerActivity.this, new Exception(errorMessage));
            onBackPressed();
        }

        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();

            toggleVideo(true);
            delayedHide(100);
        }
    };

    @Override
    public void onBackPressed() {
        toggleVideo(false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = new Intent(this, SalvoVideoFragmentDetail.class);
        intent.putExtra(NovoVideoFragmentDetail.ARG_VIDEO_ID, videoID);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vr_player);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVisible = true;
        mContentView = (VrVideoView) findViewById(R.id.fullscreen_content);
        mContentView.setEventListener(vrVideoEventListener);
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toggle();
                return false;
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (getIntent().getExtras().containsKey(NovoVideoFragmentDetail.ARG_VIDEO_ID)) {
            videoID = getIntent().getStringExtra(NovoVideoFragmentDetail.ARG_VIDEO_ID);

            if (videoID != null) {
                new VideoSqLiteDao(this).setIdVideo(videoID)
                        .setOnPostSqLiteVideoExecuteListenerInterface(this)
                        .execute();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_vr_player_toolbar, menu);

        playMenuItem = menu.findItem(R.id.activity_vr_player_play);
        pauseMenuItem = menu.findItem(R.id.activity_vr_player_pause);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.onBackPressed();
                return true;

            case R.id.activity_vr_player_pause :
                toggleVideo(false);
                return true;

            case R.id.activity_vr_player_play :
                toggleVideo(true);
                return true;
        }

        return false;
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
            delayedHide(5000);
        }
    }

    public void toggleVideo(boolean play){
        if (play) {
            isPlaying = true;
            playMenuItem.setVisible(false);
            pauseMenuItem.setVisible(true);

            if (mVisible)
                hide();

            mContentView.playVideo();
        } else {
            isPlaying = false;
            playMenuItem.setVisible(true);
            pauseMenuItem.setVisible(false);

            if (!mVisible)
                show();

            mContentView.pauseVideo();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onPostSqLiteVideoExecuteListener(List<VideoYoutubeModel> videoYoutubeModelList) {
        try {
            video = videoYoutubeModelList.get(0);
            String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath();
            String path = dir.concat("/" + getString(R.string.app_name));
            String filename = video.getIdVideo().concat("." + video.getFormat().getExt());

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(video.getSnippet().getTitle());
            }

            VrVideoView.Options options = new VrVideoView.Options();
            mContentView.loadVideo(Uri.parse(path.concat("/" + filename)), options);
        } catch (IOException e) {
            Error.execute(this, e);
            onBackPressed();
        }
    }
}
