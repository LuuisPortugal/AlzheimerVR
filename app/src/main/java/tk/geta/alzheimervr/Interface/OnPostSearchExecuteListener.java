package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.SearchListResponse;

public interface OnPostSearchExecuteListener {
    void onPostSearchExecute(String searchMethodType, SearchListResponse searchListResponse);
}
