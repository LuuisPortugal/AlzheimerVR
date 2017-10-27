package tk.geta.alzheimervr.Dao.Youtube;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;

import tk.geta.alzheimervr.Inicio;
import tk.geta.alzheimervr.Interface.OnPostVideoExecuteListener;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.Error;

public class Video extends AsyncTask<Void, Integer, VideoListResponse> {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    public static final String BY_IDS_VIDEO_METHOD_TYPE = "byId";

    private Context context;
    private YouTube.Videos.List videos;
    private ProgressDialog progressDialog;
    private OnPostVideoExecuteListener onPostVideoExecuteListener;
    private String videoMethodType;
    private long maxResult = 25;
    private String nextPageToken;

    public Video setNextPageToken(String nextPageTokenParam) {
        nextPageToken = nextPageTokenParam;
        return this;
    }

    public Video setMaxResult(long maxResultParam) {
        maxResult = maxResultParam;
        return this;
    }

    public Video(@NonNull Context ctx) {
        context = ctx;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Video.this.cancel(true);
                progressDialog.setProgress(0);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onPreExecute(){
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected VideoListResponse doInBackground(Void... params) {
        try {
            if (nextPageToken != null && !nextPageToken.isEmpty())
                videos.setPageToken(nextPageToken);

            videos.setMaxResults(maxResult);
            videos.setKey(Inicio.YOUTUBE_API_KEY);
            return videos.execute();
        } catch (GoogleJsonResponseException e) {
            Error.execute(context, e, "There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            Error.execute(context, e, "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            Error.execute(context, t);
        }

        return null;
    }

    public Video byIDs(String param) {
        try {
            videoMethodType = BY_IDS_VIDEO_METHOD_TYPE;

            YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName(context.getString(R.string.app_name)).build();

            videos = youtube.videos().list("id,snippet,contentDetails,statistics");
            videos.setId(param);

        } catch (IOException e) {
            Error.execute(context, e, "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }

        return this;
    }

    @Override
    protected void onPostExecute(VideoListResponse videoListResponse) {
        if(onPostVideoExecuteListener != null)
            onPostVideoExecuteListener.onPostVideoExecute(videoMethodType, videoListResponse);

        progressDialog.dismiss();
        super.onPostExecute(videoListResponse);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(values[0]);
        super.onProgressUpdate(values);
    }

    public Video setOnPostExecuteListener(OnPostVideoExecuteListener onPostExecuteListener) {
        this.onPostVideoExecuteListener = onPostExecuteListener;
        return this;
    }
}