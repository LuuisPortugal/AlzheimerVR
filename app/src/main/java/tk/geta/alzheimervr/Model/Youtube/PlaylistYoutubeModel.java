package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class PlaylistYoutubeModel extends SugarRecord {
    public static final String KIND = "youtube#playlist";

    String kind;
    String etag;
    String idPlaylist;
    SnippetYoutubeModel snippet;

    public PlaylistYoutubeModel() {
    }

    public PlaylistYoutubeModel(String kind, String etag, String id, SnippetYoutubeModel snippet) {
        this.kind = kind;
        this.etag = etag;
        this.idPlaylist = id;
        this.snippet = snippet;
    }

    public String getKind() {
        return kind;
    }

    public PlaylistYoutubeModel setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public PlaylistYoutubeModel setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public PlaylistYoutubeModel setIdPlaylist(String id) {
        this.idPlaylist = id;
        return this;
    }

    public SnippetYoutubeModel getSnippet() {
        return snippet;
    }

    public PlaylistYoutubeModel setSnippet(SnippetYoutubeModel snippet) {
        this.snippet = snippet;
        return this;
    }
}
