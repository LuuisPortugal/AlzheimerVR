package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class SearchYoutubeModel extends SugarRecord {
    public static final String KIND = "youtube#searchResult";

    String kind;
    String etag;
    IdYoutubeModel id;
    SnippetYoutubeModel snippet;

    public SearchYoutubeModel() {
    }

    public SearchYoutubeModel(String kind, String etag, IdYoutubeModel id, SnippetYoutubeModel snippet) {
        this.kind = kind;
        this.etag = etag;
        this.id = id;
        this.snippet = snippet;
    }

    public String getKind() {
        return kind;
    }

    public SearchYoutubeModel setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public SearchYoutubeModel setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public IdYoutubeModel getIdYoutubeModel() {
        return id;
    }

    public SearchYoutubeModel setIdYoutubeModel(IdYoutubeModel id) {
        this.id = id;
        return this;
    }

    public SnippetYoutubeModel getSnippet() {
        return snippet;
    }

    public SearchYoutubeModel setSnippet(SnippetYoutubeModel snippet) {
        this.snippet = snippet;
        return this;
    }
}
