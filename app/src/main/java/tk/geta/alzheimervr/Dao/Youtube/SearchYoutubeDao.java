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
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;

import tk.geta.alzheimervr.Inicio;
import tk.geta.alzheimervr.Interface.OnPostYoutubeSearchExecuteListenerInterface;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.Error;

public class SearchYoutubeDao extends AsyncTask<Void, Integer, SearchListResponse> {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    public static final String BY_QUERY_SEARCH_METHOD_TYPE = "byQuery";
    public static final String BY_CHANNEL_ID_SEARCH_METHOD_TYPE = "byChannelId";

    private Context context;
    private YouTube.Search.List search;
    private ProgressDialog progressDialog;
    private OnPostYoutubeSearchExecuteListenerInterface onPostYoutubeSearchExecuteListenerListenerInterface;
    private String searchMethodType;
    private long maxResult = 25;
    private String nextPageToken;

    public SearchYoutubeDao setNextPageToken(String nextPageTokenParam) {
        nextPageToken = nextPageTokenParam;
        return this;
    }

    public SearchYoutubeDao setMaxResult(long maxResultParam) {
        maxResult = maxResultParam;
        return this;
    }

    public SearchYoutubeDao(@NonNull Context ctx) {
        context = ctx;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                SearchYoutubeDao.this.cancel(true);
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
    protected SearchListResponse doInBackground(Void... params) {
        try {
            if (nextPageToken != null && !nextPageToken.isEmpty())
                search.setPageToken(nextPageToken);

            search.setMaxResults(maxResult);
            search.setKey(Inicio.YOUTUBE_API_KEY);
            return search.execute();
        } catch (GoogleJsonResponseException e) {
            SearchYoutubeDao.this.cancel(true);
            Error.execute(context, e, "There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            SearchYoutubeDao.this.cancel(true);
            Error.execute(context, e, "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            SearchYoutubeDao.this.cancel(true);
            Error.execute(context, t);
        }

        return null;
    }

    public SearchYoutubeDao byQuery(String param) {
        try {
            searchMethodType = BY_QUERY_SEARCH_METHOD_TYPE;

            YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName(context.getString(R.string.app_name)).build();

            search = youtube.search().list("id,snippet");
            search.setQ(param);
            search.setType("video");

        } catch (IOException e) {
            Error.execute(context, e, "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }

        return this;
    }

    public SearchYoutubeDao byChannelId(String param) {
        try {
            searchMethodType = BY_CHANNEL_ID_SEARCH_METHOD_TYPE;

            YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName(context.getString(R.string.app_name)).build();

            search = youtube.search().list("id,snippet");
            search.setChannelId(param);
            search.setType("playlist");
            search.setFields("items(id/playlistId,snippet/title)");
        } catch (IOException e) {
            Error.execute(context, e, "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }

        return this;
    }

    @Override
    protected void onPostExecute(SearchListResponse searchListResponse) {
        if(onPostYoutubeSearchExecuteListenerListenerInterface != null)
            onPostYoutubeSearchExecuteListenerListenerInterface.onPostYoutubeSearchExecuteListener(searchMethodType, searchListResponse);

        progressDialog.dismiss();
        super.onPostExecute(searchListResponse);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(values[0]);
        super.onProgressUpdate(values);
    }

    public SearchYoutubeDao setOnPostExecuteListener(OnPostYoutubeSearchExecuteListenerInterface onPostYoutubeSearchExecuteListenerListenerInterface) {
        this.onPostYoutubeSearchExecuteListenerListenerInterface = onPostYoutubeSearchExecuteListenerListenerInterface;
        return this;
    }
}