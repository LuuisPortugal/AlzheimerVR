package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.Video;

import java.util.ArrayList;

public interface OnPostSqLiteListVideoExecuteListener {
    void onPostSqLiteListVideoExecute(ArrayList<Video> videoList);
}
