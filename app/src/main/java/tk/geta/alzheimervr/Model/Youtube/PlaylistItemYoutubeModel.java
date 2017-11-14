package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class PlaylistItemYoutubeModel extends SugarRecord {
    public static final String KIND = "youtube#playlistItem";

    String kind;
    String etag;
    String idPlaylistItem;
    SnippetYoutubeModel snippet;
    ContentDetailYoutubeModel contentDetails;

    public PlaylistItemYoutubeModel() {
    }

    public PlaylistItemYoutubeModel(String kind, String etag, String id, SnippetYoutubeModel snippet, ContentDetailYoutubeModel contentDetails) {
        this.kind = kind;
        this.etag = etag;
        this.idPlaylistItem = id;
        this.snippet = snippet;
        this.contentDetails = contentDetails;
    }

    public String getKind() {
        return kind;
    }

    public PlaylistItemYoutubeModel setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public PlaylistItemYoutubeModel setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public String getIdPlaylistItem() {
        return idPlaylistItem;
    }

    public PlaylistItemYoutubeModel setIdPlaylistItem(String id) {
        this.idPlaylistItem = id;
        return this;
    }

    public SnippetYoutubeModel getSnippet() {
        return snippet;
    }

    public PlaylistItemYoutubeModel setSnippet(SnippetYoutubeModel snippet) {
        this.snippet = snippet;
        return this;
    }

    public ContentDetailYoutubeModel getContentDetails() {
        return contentDetails;
    }

    public PlaylistItemYoutubeModel setContentDetails(ContentDetailYoutubeModel contentDetails) {
        this.contentDetails = contentDetails;
        return this;
    }
}