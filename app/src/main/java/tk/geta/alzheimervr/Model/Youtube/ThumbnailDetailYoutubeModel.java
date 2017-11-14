package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class ThumbnailDetailYoutubeModel extends SugarRecord {

    String url;
    long width;
    long height;

    public ThumbnailDetailYoutubeModel() {
    }

    public ThumbnailDetailYoutubeModel(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public ThumbnailDetailYoutubeModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public long getWidth() {
        return width;
    }

    public ThumbnailDetailYoutubeModel setWidth(long width) {
        this.width = width;
        return this;
    }

    public long getHeight() {
        return height;
    }

    public ThumbnailDetailYoutubeModel setHeight(long height) {
        this.height = height;
        return this;
    }
}
