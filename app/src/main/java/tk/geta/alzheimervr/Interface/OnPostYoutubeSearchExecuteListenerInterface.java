package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.SearchListResponse;

public interface OnPostYoutubeSearchExecuteListenerInterface {
    void onPostYoutubeSearchExecuteListener(String searchMethodType, SearchListResponse searchListResponse);
}
