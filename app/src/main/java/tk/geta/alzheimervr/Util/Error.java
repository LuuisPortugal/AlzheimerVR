package tk.geta.alzheimervr.Util;

import android.content.Context;
import android.widget.Toast;

public class Error {
    public static void execute(Context context, Exception e, String msg){
        if(msg == null)
            msg = e.getMessage();

        e.printStackTrace();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void execute(Context context, Throwable t){
        t.printStackTrace();
        Toast.makeText(context, "There was an error: " + t.getCause() + " : " + t.getMessage(), Toast.LENGTH_LONG).show();
    }

    public static void execute(Context context, Exception e){
        e.printStackTrace();
        Toast.makeText(context, "There was an error: " + e.getCause() + " : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
