package tk.geta.alzheimervr.Dao.SqLite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

import tk.geta.alzheimervr.Interface.OnPostSqLiteListVideoExecuteListener;
import tk.geta.alzheimervr.Interface.OnPostSqLiteVideoExecuteListener;
import tk.geta.alzheimervr.Model.Generic;
import tk.geta.alzheimervr.Util.Error;

public class Video extends AsyncTask<Void, Integer, Object> {
    private String sequence;
    private Context context;
    private ProgressDialog progressDialog;
    private OnPostSqLiteVideoExecuteListener onPostSqLiteVideoExecuteListener;
    private OnPostSqLiteListVideoExecuteListener onPostSqLiteListVideoExecuteListener;

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
    protected Object doInBackground(Void... params) {
        if(this.onPostSqLiteVideoExecuteListener != null)
            return Generic.find(Generic.class, "sequence = ?", this.sequence).get(0);

        if(this.onPostSqLiteListVideoExecuteListener != null)
            return Generic.listAll(Generic.class);

        return null;
    }

    @Override
    protected void onPostExecute(Object response) {
        Gson gson = new Gson();

        try{
            if(this.onPostSqLiteVideoExecuteListener != null) {
                com.google.api.services.youtube.model.Video data = (com.google.api.services.youtube.model.Video) ((Generic) response).getData(com.google.api.services.youtube.model.Video.class);
                this.onPostSqLiteVideoExecuteListener
                        .onPostSqLiteVideoExecute(gson.fromJson(data.toString(), com.google.api.services.youtube.model.Video.class));
            }

            if(this.onPostSqLiteListVideoExecuteListener != null) {
                ArrayList<com.google.api.services.youtube.model.Video> videoArrayList = new ArrayList<>();
                for (Generic obj : (ArrayList<Generic>) response) {
                    videoArrayList.add((com.google.api.services.youtube.model.Video) obj.getData(com.google.api.services.youtube.model.Video.class));
                }

                this.onPostSqLiteListVideoExecuteListener
                        .onPostSqLiteListVideoExecute(videoArrayList);
            }
        } catch (Exception e){
            Video.this.cancel(true);
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

    public Video setOnPostSqLiteVideoExecuteListener(OnPostSqLiteVideoExecuteListener onPostExecuteListener) {
        this.onPostSqLiteVideoExecuteListener = onPostExecuteListener;
        return this;
    }

    public Video setOnPostSqLiteListVideoExecuteListener(OnPostSqLiteListVideoExecuteListener onPostExecuteListener) {
        this.onPostSqLiteListVideoExecuteListener = onPostExecuteListener;
        return this;
    }

    public Video setSequence(String sequence){
        this.sequence = sequence;
        return this;
    }
}