package tk.geta.alzheimervr.Model.Youtube;

import com.orm.SugarRecord;

public class ThumbnailsYoutubeModel extends SugarRecord {

    ThumbnailDetailYoutubeModel defaultThumbnail;
    ThumbnailDetailYoutubeModel high;
    ThumbnailDetailYoutubeModel maxres;
    ThumbnailDetailYoutubeModel medium;
    ThumbnailDetailYoutubeModel standard;

    public ThumbnailsYoutubeModel() {
    }

    public ThumbnailsYoutubeModel(ThumbnailDetailYoutubeModel default__, ThumbnailDetailYoutubeModel high, ThumbnailDetailYoutubeModel maxres, ThumbnailDetailYoutubeModel medium, ThumbnailDetailYoutubeModel standard) {
        this.defaultThumbnail = default__;
        this.high = high;
        this.maxres = maxres;
        this.medium = medium;
        this.standard = standard;
    }

    public ThumbnailDetailYoutubeModel getDefault() {
        return defaultThumbnail;
    }

    public ThumbnailsYoutubeModel setDefault(ThumbnailDetailYoutubeModel default__) {
        this.defaultThumbnail = default__;
        return this;
    }

    public ThumbnailDetailYoutubeModel getHigh() {
        return high;
    }

    public ThumbnailsYoutubeModel setHigh(ThumbnailDetailYoutubeModel high) {
        this.high = high;
        return this;
    }

    public ThumbnailDetailYoutubeModel getMaxres() {
        return maxres;
    }

    public ThumbnailsYoutubeModel setMaxres(ThumbnailDetailYoutubeModel maxres) {
        this.maxres = maxres;
        return this;
    }

    public ThumbnailDetailYoutubeModel getMedium() {
        return medium;
    }

    public ThumbnailsYoutubeModel setMedium(ThumbnailDetailYoutubeModel medium) {
        this.medium = medium;
        return this;
    }

    public ThumbnailDetailYoutubeModel getStandard() {
        return standard;
    }

    public ThumbnailsYoutubeModel setStandard(ThumbnailDetailYoutubeModel standard) {
        this.standard = standard;
        return this;
    }

    public static ThumbnailDetailYoutubeModel getMaxResolution(ThumbnailsYoutubeModel thumbnailDetails) {
        if (thumbnailDetails.getMaxres() != null)
            return thumbnailDetails.getMaxres();
        else if (thumbnailDetails.getHigh() != null)
            return thumbnailDetails.getHigh();
        else if (thumbnailDetails.getMedium() != null)
            return thumbnailDetails.getMedium();
        else if (thumbnailDetails.getStandard() != null)
            return thumbnailDetails.getStandard();
        else
            return thumbnailDetails.getDefault();
    }
}