package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class StatisticYoutubeModel extends SugarRecord {

    long viewCount;
    long commentCount;
    long subscriberCount;
    boolean hiddenSubscriberCount;
    long videoCount;
    long likeCount;
    long dislikeCount;
    long favoriteCount;

    public StatisticYoutubeModel() {
    }

    public StatisticYoutubeModel(long viewCount, long commentCount, long subscriberCount, boolean hiddenSubscriberCount, long videoCount, long likeCount, long dislikeCount, long favoriteCount) {
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.subscriberCount = subscriberCount;
        this.hiddenSubscriberCount = hiddenSubscriberCount;
        this.videoCount = videoCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.favoriteCount = favoriteCount;
    }

    public long getViewCount() {
        return viewCount;
    }

    public StatisticYoutubeModel setViewCount(long viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public StatisticYoutubeModel setCommentCount(long commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public long getSubscriberCount() {
        return subscriberCount;
    }

    public StatisticYoutubeModel setSubscriberCount(long subscriberCount) {
        this.subscriberCount = subscriberCount;
        return this;
    }

    public boolean isHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }

    public StatisticYoutubeModel setHiddenSubscriberCount(boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
        return this;
    }

    public long getVideoCount() {
        return videoCount;
    }

    public StatisticYoutubeModel setVideoCount(long videoCount) {
        this.videoCount = videoCount;
        return this;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public StatisticYoutubeModel setLikeCount(long likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public StatisticYoutubeModel setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
        return this;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public StatisticYoutubeModel setFavoriteCount(long favoriteCount) {
        this.favoriteCount = favoriteCount;
        return this;
    }
}
