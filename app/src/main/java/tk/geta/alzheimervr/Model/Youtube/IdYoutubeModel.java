package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class IdYoutubeModel extends SugarRecord {

    String kind;
    String videoId;
    String channelId;
    String playlistId;

    public IdYoutubeModel() {
    }

    public IdYoutubeModel(String kind, String videoId, String channelId, String playlistId) {
        this.kind = kind;
        this.videoId = videoId;
        this.channelId = channelId;
        this.playlistId = playlistId;
    }

    public String getKind() {
        return kind;
    }

    public IdYoutubeModel setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getVideoId() {
        return videoId;
    }

    public IdYoutubeModel setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }

    public IdYoutubeModel setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public IdYoutubeModel setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
        return this;
    }
}
