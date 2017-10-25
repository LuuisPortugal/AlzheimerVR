package tk.geta.alzheimervr.Interface;

import com.google.api.services.youtube.model.SearchListResponse;

public interface OnPostSearchExecute {
    void onPostSearchExecuteListener(String searchMethodType, SearchListResponse searchListResponse);
}
