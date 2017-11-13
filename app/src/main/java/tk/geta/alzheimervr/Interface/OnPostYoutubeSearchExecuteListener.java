package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.SearchListResponse;

public interface OnPostYoutubeSearchExecuteListener {
    void onPostYoutubeSearchExecute(String searchMethodType, SearchListResponse searchListResponse);
}
