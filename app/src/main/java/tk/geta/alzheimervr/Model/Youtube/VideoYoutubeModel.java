package tk.geta.alzheimervr.Model.Youtube;

import com.google.api.services.youtube.model.Video;
import com.orm.SugarRecord;

import at.huber.youtubeExtractor.Format;
import tk.geta.alzheimervr.Model.YtFile.FormatYtFileModel;

public class VideoYoutubeModel extends SugarRecord {
    public static final String KIND = "youtube#video";

    String kind;
    String etag;
    String idVideo;
    SnippetYoutubeModel snippet;
    ContentDetailYoutubeModel contentDetails;
    StatisticYoutubeModel statistics;
    FormatYtFileModel format;

    public VideoYoutubeModel() {
    }

    public VideoYoutubeModel(Video video, Format formatYtFile) {
        if(video != null) {
            if (video.getId() != null)
                this.idVideo = video.getId();
            if (video.getSnippet() != null) {
                this.snippet = new SnippetYoutubeModel();
                if (video.getSnippet().getTitle() != null)
                    this.snippet.title = video.getSnippet().getTitle();
                if (video.getSnippet().getChannelTitle() != null)
                    this.snippet.channelTitle = video.getSnippet().getChannelTitle();
                if (video.getSnippet().getCategoryId() != null)
                    this.snippet.categoryId = video.getSnippet().getCategoryId();
                if (video.getSnippet().getChannelId() != null)
                    this.snippet.channelId = video.getSnippet().getChannelId();
                if (video.getSnippet().getDescription() != null)
                    this.snippet.description = video.getSnippet().getDescription();
                if (video.getSnippet().getPublishedAt() != null)
                    this.snippet.publishedAt = video.getSnippet().getPublishedAt();
                if (video.getSnippet().getTags() != null)
                    this.snippet.tags = video.getSnippet().getTags();
                if (video.getSnippet().getThumbnails() != null) {
                    this.snippet.thumbnails = new ThumbnailsYoutubeModel();
                    if (video.getSnippet().getThumbnails().getDefault() != null) {
                        this.snippet.thumbnails.defaultThumbnail =
                                new ThumbnailDetailYoutubeModel()
                                        .setHeight(video.getSnippet().getThumbnails().getDefault().getHeight())
                                        .setUrl(video.getSnippet().getThumbnails().getDefault().getUrl())
                                        .setWidth(video.getSnippet().getThumbnails().getDefault().getWidth());
                        this.snippet.thumbnails.defaultThumbnail.save();
                    }

                    if (video.getSnippet().getThumbnails().getStandard() != null) {
                        this.snippet.thumbnails.standard =
                                new ThumbnailDetailYoutubeModel()
                                        .setHeight(video.getSnippet().getThumbnails().getStandard().getHeight())
                                        .setUrl(video.getSnippet().getThumbnails().getStandard().getUrl())
                                        .setWidth(video.getSnippet().getThumbnails().getStandard().getWidth());
                        this.snippet.thumbnails.standard.save();
                    }

                    if (video.getSnippet().getThumbnails().getHigh() != null) {
                        this.snippet.thumbnails.high =
                                new ThumbnailDetailYoutubeModel()
                                        .setHeight(video.getSnippet().getThumbnails().getHigh().getHeight())
                                        .setUrl(video.getSnippet().getThumbnails().getHigh().getUrl())
                                        .setWidth(video.getSnippet().getThumbnails().getHigh().getWidth());
                        this.snippet.thumbnails.high.save();
                    }

                    if (video.getSnippet().getThumbnails().getMedium() != null) {
                        this.snippet.thumbnails.medium =
                                new ThumbnailDetailYoutubeModel()
                                        .setHeight(video.getSnippet().getThumbnails().getMedium().getHeight())
                                        .setUrl(video.getSnippet().getThumbnails().getMedium().getUrl())
                                        .setWidth(video.getSnippet().getThumbnails().getMedium().getWidth());
                        this.snippet.thumbnails.medium.save();
                    }

                    if (video.getSnippet().getThumbnails().getMaxres() != null) {
                        this.snippet.thumbnails.maxres =
                                new ThumbnailDetailYoutubeModel()
                                        .setHeight(video.getSnippet().getThumbnails().getMaxres().getHeight())
                                        .setUrl(video.getSnippet().getThumbnails().getMaxres().getUrl())
                                        .setWidth(video.getSnippet().getThumbnails().getMaxres().getWidth());
                        this.snippet.thumbnails.maxres.save();
                    }
                    this.snippet.thumbnails.save();
                }
                this.snippet.save();
            }

            if (video.getContentDetails() != null) {
                this.contentDetails = new ContentDetailYoutubeModel();
                if (video.getContentDetails().getLicensedContent() != null)
                    this.contentDetails.licensedContent = video.getContentDetails().getLicensedContent();
                if (video.getContentDetails().getCaption() != null)
                    this.contentDetails.caption = video.getContentDetails().getCaption();
                if (video.getContentDetails().getDefinition() != null)
                    this.contentDetails.definition = video.getContentDetails().getDefinition();
                if (video.getContentDetails().getDimension() != null)
                    this.contentDetails.dimension = video.getContentDetails().getDimension();
                if (video.getContentDetails().getDuration() != null)
                    this.contentDetails.duration = video.getContentDetails().getDuration();
                this.contentDetails.save();
            }

            if(video.getStatistics() != null){
                this.statistics = new StatisticYoutubeModel();
                if(video.getStatistics().getCommentCount() != null)
                    this.statistics.commentCount = video.getStatistics().getCommentCount().longValue();
                if(video.getStatistics().getDislikeCount() != null)
                    this.statistics.dislikeCount = video.getStatistics().getDislikeCount().longValue();
                if(video.getStatistics().getFavoriteCount() != null)
                    this.statistics.favoriteCount = video.getStatistics().getFavoriteCount().longValue();
                if(video.getStatistics().getLikeCount() != null)
                    this.statistics.likeCount = video.getStatistics().getLikeCount().longValue();
                if(video.getStatistics().getViewCount() != null)
                    this.statistics.viewCount = video.getStatistics().getViewCount().longValue();
                this.contentDetails.save();
            }

            if (formatYtFile != null){
                this.format = new FormatYtFileModel();
                if(formatYtFile.getExt() != null)
                    this.format.ext = formatYtFile.getExt();
                if(formatYtFile.getHeight() != 0)
                    this.format.height = formatYtFile.getHeight();
                if(formatYtFile.getFps() != 0)
                    this.format.fps = formatYtFile.getFps();
                if(formatYtFile.getAudioBitrate() != 0)
                    this.format.audioBitrate = formatYtFile.getAudioBitrate();
                if(formatYtFile.isDashContainer())
                    this.format.isDashContainer = formatYtFile.isDashContainer();
                if(formatYtFile.isHlsContent())
                    this.format.isHlsContent = formatYtFile.isHlsContent();
                this.format.save();
            }

            this.save();
        }
    }

    public VideoYoutubeModel(String kind, String etag, String id, SnippetYoutubeModel snippet, ContentDetailYoutubeModel contentDetails, StatisticYoutubeModel statistics) {
        this.kind = kind;
        this.etag = etag;
        this.idVideo = id;
        this.snippet = snippet;
        this.contentDetails = contentDetails;
        this.statistics = statistics;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public VideoYoutubeModel setIdVideo(String idVideo) {
        this.idVideo = idVideo;
        return this;
    }

    public String getKind() {
        return kind;
    }

    public VideoYoutubeModel setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public VideoYoutubeModel setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public SnippetYoutubeModel getSnippet() {
        return snippet;
    }

    public VideoYoutubeModel setSnippet(SnippetYoutubeModel snippet) {
        this.snippet = snippet;
        return this;
    }

    public ContentDetailYoutubeModel getContentDetails() {
        return contentDetails;
    }

    public VideoYoutubeModel setContentDetails(ContentDetailYoutubeModel contentDetails) {
        this.contentDetails = contentDetails;
        return this;
    }

    public StatisticYoutubeModel getStatistics() {
        return statistics;
    }

    public VideoYoutubeModel setStatistics(StatisticYoutubeModel statistics) {
        this.statistics = statistics;
        return this;
    }

    public FormatYtFileModel getFormat() {
        return format;
    }

    public VideoYoutubeModel setFormat(FormatYtFileModel format) {
        this.format = format;
        return this;
    }
}
