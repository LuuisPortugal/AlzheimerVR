package tk.geta.alzheimervr.Libraries;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MyAppCompatActivity extends AppCompatActivity {
    protected ProgressDialog ProgressDialogService;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ProgressDialogService = new ProgressDialog(this);
        ProgressDialogService.setMessage("Carregando");
        ProgressDialogService.setIndeterminate(true);
        ProgressDialogService.setMax(100);
    }
}
