package tk.geta.alzheimervr.Model.Youtube;

import com.google.api.client.util.DateTime;
import com.orm.SugarRecord;

import java.util.List;

public class SnippetYoutubeModel extends SugarRecord {

    DateTime publishedAt;
    String channelId;
    String title;
    String description;
    String channelTitle;
    List<String> tags;
    String playlistId;
    int position;
    ResourceIdYoutubeModel resourceId;
    String categoryId;
    ThumbnailsYoutubeModel thumbnails;

    public SnippetYoutubeModel() {
    }

    public SnippetYoutubeModel(DateTime publishedAt, String channelId, String title, String description, String channelTitle, List<String> tags, String playlistId, int position, ResourceIdYoutubeModel resourceId, String categoryId, ThumbnailsYoutubeModel thumbnails) {
        this.publishedAt = publishedAt;
        this.channelId = channelId;
        this.title = title;
        this.description = description;
        this.channelTitle = channelTitle;
        this.tags = tags;
        this.playlistId = playlistId;
        this.position = position;
        this.resourceId = resourceId;
        this.categoryId = categoryId;
        this.thumbnails = thumbnails;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public SnippetYoutubeModel setPublishedAt(DateTime publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }

    public SnippetYoutubeModel setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SnippetYoutubeModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SnippetYoutubeModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public SnippetYoutubeModel setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public SnippetYoutubeModel setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public SnippetYoutubeModel setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public SnippetYoutubeModel setPosition(int position) {
        this.position = position;
        return this;
    }

    public ResourceIdYoutubeModel getResourceId() {
        return resourceId;
    }

    public SnippetYoutubeModel setResourceId(ResourceIdYoutubeModel resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public SnippetYoutubeModel setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ThumbnailsYoutubeModel getThumbnails() {
        return thumbnails;
    }

    public SnippetYoutubeModel setThumbnails(ThumbnailsYoutubeModel thumbnails) {
        this.thumbnails = thumbnails;
        return this;
    }
}
