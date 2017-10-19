package tk.geta.alzheimervr.Models.Youtube;

public class Playlist {
    public static final String KIND = "youtube#playlist";

    public String kind;
    public String etag;
    public String id;
    public Snippet snippet;

    public class PlaylistItem {
        public static final String KIND = "youtube#playlistItem";

        public String kind;
        public String etag;
        public String id;
        public Snippet snippet;
        public ContentDetail contentDetails;
    }
}
