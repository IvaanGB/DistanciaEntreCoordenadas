package gpsdistance.com.gpsdistance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;

public class Metodos {
    private static int ok=0;


    public static boolean validar(EditText latSt, EditText lonSt){
        if (latSt.getText().toString().isEmpty() || latSt.getText().toString().toCharArray().length<2
                || latSt.getText().toString()=="."){
            latSt.setError("Ingrese latitud");
            return false;
        }

        if (lonSt.getText().toString().isEmpty() || lonSt.getText().toString().toCharArray().length<2
                || lonSt.getText().toString()=="."){
            lonSt.setError("Ingrese longitud");
            return false;
        }

        return true;
    }

    public static void mayorA50(Context main, TextView metros, EditText latSt, EditText lonSt, TextView latEnd, TextView lonEnd){
        if (Calcular(latSt, lonSt, latEnd, lonEnd)>=50){
            metros.setText(""+Calcular(latSt, lonSt, latEnd, lonEnd)+ " metros");
            if(ok==0) {
                alert(main);
            }
        }
        else{
            metros.setText(""+Calcular(latSt, lonSt, latEnd, lonEnd)+ " metros");
            ok=1-1;
        }
    }

    private static double Calcular(EditText latSt, EditText lonSt, TextView latEnd, TextView lonEnd){
        double medida=0.00;
        if (validar(latSt, lonSt)) {
            float latA = Float.parseFloat(latSt.getText().toString());
            float lonA = Float.parseFloat(lonSt.getText().toString());
            float latB = Float.parseFloat(latEnd.getText().toString());
            float lonB = Float.parseFloat(lonEnd.getText().toString());

            medida = meterDistanceBetweenPoints(latA, lonA, latB, lonB);
            medida= (double)Math.round(medida*100d)/100d;
        }
        return medida;
    }




    private static void alert(final Context main){
        AlertDialog alertDialog = new AlertDialog.Builder(main).create();
        alertDialog.setTitle("Superior a 50 metros");
        alertDialog.setMessage("Usted ha superado los 50 metros.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ok=0+1;
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private static double meterDistanceBetweenPoints(float lat_a, float lng_a, float lat_b, float lng_b) {
        float pk = (float) (180.f / Math.PI);
        float a1 = lat_a / pk;
        float a2 = lng_a / pk;
        float b1 = lat_b / pk;
        float b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        double meters = 6366000 * tt;

        return meters;
    }

}
