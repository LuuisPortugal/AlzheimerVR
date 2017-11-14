package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class ResourceIdYoutubeModel extends SugarRecord {

    String kind;
    String videoId;

    public ResourceIdYoutubeModel() {
    }

    public ResourceIdYoutubeModel(String kind, String videoId) {
        this.kind = kind;
        this.videoId = videoId;
    }

    public String getKind() {
        return kind;
    }

    public ResourceIdYoutubeModel setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getVideoId() {
        return videoId;
    }

    public ResourceIdYoutubeModel setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }
}
