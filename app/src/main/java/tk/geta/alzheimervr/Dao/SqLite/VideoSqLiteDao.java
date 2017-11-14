package tk.geta.alzheimervr.Dao.SqLite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import tk.geta.alzheimervr.Interface.OnPostSqLiteListVideoExecuteListenerInterface;
import tk.geta.alzheimervr.Interface.OnPostSqLiteVideoExecuteListenerInterface;
import tk.geta.alzheimervr.Model.Youtube.VideoYoutubeModel;
import tk.geta.alzheimervr.Util.Error;

public class VideoSqLiteDao extends AsyncTask<Void, Integer, List<VideoYoutubeModel>> {
    private String idVideo;
    private Context context;
    private ProgressDialog progressDialog;
    private OnPostSqLiteVideoExecuteListenerInterface onPostSqLiteVideoExecuteListenerInterface;
    private OnPostSqLiteListVideoExecuteListenerInterface onPostSqLiteListVideoExecuteListenerInterface;

    public VideoSqLiteDao(@NonNull Context ctx) {
        context = ctx;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                VideoSqLiteDao.this.cancel(true);
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
    protected List<VideoYoutubeModel> doInBackground(Void... params) {
        try {
            if (this.onPostSqLiteVideoExecuteListenerInterface != null)
                return VideoYoutubeModel.find(VideoYoutubeModel.class, "idVideo = ?", this.idVideo);

            if (this.onPostSqLiteListVideoExecuteListenerInterface != null)
                return VideoYoutubeModel.listAll(VideoYoutubeModel.class);
        } catch (Exception e) {
            Error.execute(context, e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<VideoYoutubeModel> response) {
        try {
            if (this.onPostSqLiteVideoExecuteListenerInterface != null)
                this.onPostSqLiteVideoExecuteListenerInterface
                    .onPostSqLiteVideoExecuteListener(response);


            if (this.onPostSqLiteListVideoExecuteListenerInterface != null)
                this.onPostSqLiteListVideoExecuteListenerInterface
                    .onPostSqLiteListVideoExecuteListener(response);
        } catch (Exception e){
            Error.execute(context, e);
        }
        progressDialog.dismiss();
        super.onPostExecute(response);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(values[0]);
        super.onProgressUpdate(values);
    }

    public VideoSqLiteDao setOnPostSqLiteVideoExecuteListenerInterface(OnPostSqLiteVideoExecuteListenerInterface onPostExecuteListener) {
        this.onPostSqLiteVideoExecuteListenerInterface = onPostExecuteListener;
        return this;
    }

    public VideoSqLiteDao setOnPostSqLiteListVideoExecuteListenerInterface(OnPostSqLiteListVideoExecuteListenerInterface onPostExecuteListener) {
        this.onPostSqLiteListVideoExecuteListenerInterface = onPostExecuteListener;
        return this;
    }

    public VideoSqLiteDao setIdVideo(String idVideo) {
        this.idVideo = idVideo;
        return this;
    }
}