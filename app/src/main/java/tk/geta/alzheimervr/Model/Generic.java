package tk.geta.alzheimervr.Model;

import com.google.gson.Gson;
import com.orm.SugarRecord;


public class Generic extends SugarRecord {
    String sequence;
    String data;

    public Generic() {
        super();
    }

    public Generic(String sequence, String data, String className) {
        this.sequence = sequence;
        this.data = data;
    }

    public String getSequence() {
        return sequence;
    }

    public Generic setSequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public Object getData(Class aClass) {
        return new Gson().fromJson(data, aClass);
    }

    public Generic setData(Object data) {
        Gson gson = new Gson();
        this.data = gson.toJson(data);
        return this;
    }
}
