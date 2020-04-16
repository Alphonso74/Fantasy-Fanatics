package psu.ajm6684.myapplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }
    public void startDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_dialog_loading,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }
    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
