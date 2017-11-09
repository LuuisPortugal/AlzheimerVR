package tk.geta.alzheimervr.Model;

import android.content.Context;

import com.google.gson.Gson;
import com.orm.SugarRecord;

import tk.geta.alzheimervr.Util.Error;


public class Generic extends SugarRecord {
    String sequence;
    String data;
    String className;

    public Generic() {
        super();
    }

    public Generic(String sequence, String data, String className) {
        this.sequence = sequence;
        this.data = data;
        this.className = className;
    }

    public String getSequence() {
        return sequence;
    }

    public Generic setSequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Generic setClassName(Class className) {
        this.className = className.getName();
        return this;
    }

    public Object getData(Context context) {
        try {
            return new Gson().fromJson(data, (Class<Object>) Class.forName(className).newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Error.execute(context, e);
            e.printStackTrace();
        }

        return null;
    }

    public Generic setData(Object data) {
        Gson gson = new Gson();
        this.data = gson.toJson(data);
        return this;
    }
}
