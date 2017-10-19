package tk.geta.alzheimervr.Models.Youtube;

import java.util.Date;
import java.util.HashMap;

public class Snippet {
    public Date publishedAt;
    public String channelId;
    public String title;
    public String description;
    public String channelTitle;
    public String[] tags;
    public String playlistId;
    public int position;
    public ResourceId resourceId;
    public String categoryId;
    public HashMap<String, Thumbnail> thumbnails;
}
