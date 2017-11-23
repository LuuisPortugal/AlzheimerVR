package tk.geta.alzheimervr.Model.YtFile;

import com.orm.SugarRecord;

public class FormatYtFileModel extends SugarRecord {
    public String ext;
    public int height;
    public int fps;
    public int audioBitrate;
    public boolean isDashContainer;
    public boolean isHlsContent;

    public FormatYtFileModel() {
    }

    public FormatYtFileModel(String ext, int height, int fps, int audioBitrate, boolean isDashContainer, boolean isHlsContent) {
        this.ext = ext;
        this.height = height;
        this.fps = fps;
        this.audioBitrate = audioBitrate;
        this.isDashContainer = isDashContainer;
        this.isHlsContent = isHlsContent;
    }

    public String getExt() {
        return ext;
    }

    public FormatYtFileModel setExt(String ext) {
        this.ext = ext;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public FormatYtFileModel setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getFps() {
        return fps;
    }

    public FormatYtFileModel setFps(int fps) {
        this.fps = fps;
        return this;
    }

    public int getAudioBitrate() {
        return audioBitrate;
    }

    public FormatYtFileModel setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
        return this;
    }

    public boolean isDashContainer() {
        return isDashContainer;
    }

    public FormatYtFileModel setDashContainer(boolean dashContainer) {
        isDashContainer = dashContainer;
        return this;
    }

    public boolean isHlsContent() {
        return isHlsContent;
    }

    public FormatYtFileModel setHlsContent(boolean hlsContent) {
        isHlsContent = hlsContent;
        return this;
    }
}
