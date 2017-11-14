package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class ContentDetailYoutubeModel extends SugarRecord {

    String videoId;
    String startAt;
    String endAt;
    String note;
    String duration;
    String dimension;
    String definition;
    String caption;
    boolean licensedContent;

    public ContentDetailYoutubeModel() {
    }

    public ContentDetailYoutubeModel(String videoId, String startAt, String endAt, String note, String duration, String dimension, String definition, String caption, boolean licensedContent) {
        this.videoId = videoId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.note = note;
        this.duration = duration;
        this.dimension = dimension;
        this.definition = definition;
        this.caption = caption;
        this.licensedContent = licensedContent;
    }

    public String getVideoId() {
        return videoId;
    }

    public ContentDetailYoutubeModel setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public String getStartAt() {
        return startAt;
    }

    public ContentDetailYoutubeModel setStartAt(String startAt) {
        this.startAt = startAt;
        return this;
    }

    public String getEndAt() {
        return endAt;
    }

    public ContentDetailYoutubeModel setEndAt(String endAt) {
        this.endAt = endAt;
        return this;
    }

    public String getNote() {
        return note;
    }

    public ContentDetailYoutubeModel setNote(String note) {
        this.note = note;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public ContentDetailYoutubeModel setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getDimension() {
        return dimension;
    }

    public ContentDetailYoutubeModel setDimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public String getDefinition() {
        return definition;
    }

    public ContentDetailYoutubeModel setDefinition(String definition) {
        this.definition = definition;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public ContentDetailYoutubeModel setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public boolean isLicensedContent() {
        return licensedContent;
    }

    public ContentDetailYoutubeModel setLicensedContent(boolean licensedContent) {
        this.licensedContent = licensedContent;
        return this;
    }
}
