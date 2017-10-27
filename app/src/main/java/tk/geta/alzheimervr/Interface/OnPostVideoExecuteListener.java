package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.VideoListResponse;

public interface OnPostVideoExecuteListener {
    void onPostVideoExecute(String videoMethodType, VideoListResponse videoListResponse);
}
