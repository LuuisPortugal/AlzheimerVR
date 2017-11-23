package tk.geta.alzheimervr.Model.Youtube;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;

public class ThumbnailDetailYoutubeModel extends SugarRecord {

    String url;
    long width;
    long height;
    String base64;

    public ThumbnailDetailYoutubeModel() {
    }

    public ThumbnailDetailYoutubeModel(String url, int width, int height, String base64) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.base64 = base64;
    }

    public String getUrl() {
        return url;
    }

    public ThumbnailDetailYoutubeModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public long getWidth() {
        return width;
    }

    public ThumbnailDetailYoutubeModel setWidth(long width) {
        this.width = width;
        return this;
    }

    public long getHeight() {
        return height;
    }

    public ThumbnailDetailYoutubeModel setHeight(long height) {
        this.height = height;
        return this;
    }

    public String getBase64() {
        return base64;
    }

    public ThumbnailDetailYoutubeModel setBase64(String base64) {
        this.base64 = base64;
        return this;
    }

    public ImageView getBase64(Context context) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);

        return imageView;
    }

    public ThumbnailDetailYoutubeModel setBase64(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        this.base64 = Base64.encodeToString(stream.toByteArray(), 0);;
        return this;
    }
}
