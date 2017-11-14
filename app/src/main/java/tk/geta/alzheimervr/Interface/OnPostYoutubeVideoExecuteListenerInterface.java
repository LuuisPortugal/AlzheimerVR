package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.VideoListResponse;

public interface OnPostYoutubeVideoExecuteListenerInterface {
    void onPostYoutubeVideoExecuteListener(String videoMethodType, VideoListResponse videoListResponse);
}
