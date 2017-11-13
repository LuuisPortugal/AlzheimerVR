package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.VideoListResponse;

public interface OnPostYoutubeVideoExecuteListener {
    void onPostYoutubeVideoExecute(String videoMethodType, VideoListResponse videoListResponse);
}
