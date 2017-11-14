package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class ChannelYoutubeModel extends SugarRecord {
    public static final String KIND = "youtube#channel";

    String kind;
    String etag;
    String idChannel;
    SnippetYoutubeModel snippet;
    StatisticYoutubeModel statistic;

    public ChannelYoutubeModel() {
    }

    public ChannelYoutubeModel(String kind, String etag, String id, SnippetYoutubeModel snippet, StatisticYoutubeModel statistic) {
        this.kind = kind;
        this.etag = etag;
        this.idChannel = id;
        this.snippet = snippet;
        this.statistic = statistic;
    }

    public String getKind() {
        return kind;
    }

    public ChannelYoutubeModel setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public ChannelYoutubeModel setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public String getIdChannel() {
        return idChannel;
    }

    public ChannelYoutubeModel setIdChannel(String id) {
        this.idChannel = id;
        return this;
    }

    public SnippetYoutubeModel getSnippet() {
        return snippet;
    }

    public ChannelYoutubeModel setSnippet(SnippetYoutubeModel snippet) {
        this.snippet = snippet;
        return this;
    }

    public StatisticYoutubeModel getStatistic() {
        return statistic;
    }

    public ChannelYoutubeModel setStatistic(StatisticYoutubeModel statistic) {
        this.statistic = statistic;
        return this;
    }
}
