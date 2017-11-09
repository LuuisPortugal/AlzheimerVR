package tk.geta.alzheimervr.Util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class Keyboard {
    public static void hide(Context context){
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
