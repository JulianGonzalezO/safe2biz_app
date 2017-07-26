package pe.dominiotech.movil.safe2biz.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import pe.dominiotech.movil.safe2biz.R;

public class Util {
    enum MESES {ENE,FEB, MAR, ABR, MAY, JUN, JUL, AGO, SET,OCT, NOV, DIC}
    public static boolean contains(String[] array , String itemToSearch){
        for (String item: array) {
            if(item.contains(itemToSearch) || itemToSearch.contains(item)){
                return true;
            }
        }
        return false;
    }

    public static String normalizeURL(String urlInput){
        int i = urlInput.lastIndexOf("/");
        String imageName = urlInput.substring(i + 1);
        String urlPreffix =  urlInput.substring(0 , i + 1);
        try {
            return (urlPreffix + URLEncoder.encode(imageName, "UTF-8").replaceAll("\\+", "%20"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNombreMes(int numeroMes){
        int numeroMesIteracion = 1;
        for(MESES MES : MESES.values()){
            if(numeroMesIteracion == numeroMes){
                return MES.toString();
            }
            numeroMesIteracion++;
        }
        return "";
    }

    public static void displayPromptForEnablingGPS(
            final Activity activity)
    {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = activity.getString(R.string.confirmargps);

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    public static Bitmap compressBitmap(Bitmap photo){
        double width = Double.valueOf(photo.getWidth());
        if(width>=600){
            width = 600/width;
        }else{
            width = 1;
        }
        Bitmap newPhoto = Bitmap.createScaledBitmap(photo,(int) (photo.getWidth()*width),(int) (photo.getHeight()*width), true);
        return newPhoto;
    }

    public static String obtenerFechaLog(){
        String fecha = "";
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        df.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        fecha = df.format(date);
        return fecha;
    }

    public static File validarDirectorio(String path){
        String ruta = Environment.getExternalStorageDirectory().getAbsolutePath() + path;
        File dir = new File(ruta);
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    public static String obtenerHora(){
        String fecha = "";
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        fecha = df.format(date);
        return fecha;
    }

    public static boolean VerificarConexionInternet(Context contexto){
        boolean isConexion = false;
        ConnectivityManager connMgr = (ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"conexion wifi " + isWifiConn);
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = false;
        if(null != networkInfo){
            isMobileConn = networkInfo.isConnected();
        }
        LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"conexion Mobile " + isMobileConn);
        isConexion = isWifiConn || isMobileConn;
        LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"conexion " + isConexion);
        return isConexion;
    }

    public static void MostrarMensajeOperacion(Activity actividad, String mensaje, String tipo) {
        int color = 0;
        if (tipo.equals(AppConstants.tipo_mensaje_alerta)) color = R.color.md_red_900;
        else if (tipo.equals(AppConstants.tipo_mensaje_confirmacion)) color = R.color.md_green_900;
        else if (tipo.equals(AppConstants.tipo_mensaje_informacion)) color = R.color.md_blue_900;
        Style estilo = new Style.Builder()
                .setBackgroundColor(color)
                .setGravity(Gravity.CENTER)
                .setTextSize(18)
                .setConfiguration(new Configuration.Builder().setDuration(5000).build())
                .setTextColor(android.R.color.black)
                .build();
        Crouton.makeText(actividad, mensaje, estilo).show();

    }

    public static void cambiarTheme(Activity activity, View lnlyPrincipal, int flag) {
        // TODO Auto-generated method stub
        if (flag == 0){
            activity.setTheme(android.R.style.Theme_Translucent);
            lnlyPrincipal.setBackgroundColor(activity.getResources().getColor(R.color.fondo_transparente));
        }else if (flag == 1){
            activity.setTheme(R.style.AppThemeRedLight);
            lnlyPrincipal.setBackgroundColor(activity.getResources().getColor(R.color.md_black_1000_50));
        }
    }

    public static void mostrarMensaje(Context context, String mensaje){
        Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }

    public static String obtenerFechaHora(){
        String fecha = "";
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        fecha = df.format(date);
        return fecha;
    }

    public static String getImei(Context context) {
        String idDispositivo = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(null != telephonyManager){
            idDispositivo = telephonyManager.getDeviceId();
        }
        if(null == idDispositivo || 0 == idDispositivo.length()){
            idDispositivo = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return idDispositivo;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0 ) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                if (ratioBitmap >= 1){
                    finalHeight = (int) ((float)maxHeight / ratioBitmap);
                }else{
                    finalWidth = (int) ((float)maxWidth * ratioBitmap);
                }
            }
            if (maxHeight < height || maxWidth < width){
                image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            }
            Log.d("alto",Integer.toString(finalHeight));
            Log.d("largo",Integer.toString(finalWidth));
            return image;
        } else {
            return image;
        }
    }

    public static String obtenerFechayHoraActual() {
        String fechayHora;
        Calendar calendar;
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayText = day+"";
        String mesText = month+"";
        if(dayText.length() == 1) {
            dayText = "0" + dayText;
        }
        if(mesText.length() == 1){
            mesText = "0"+mesText;
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String hourText = hour+"";
        String minuteText = minute+"";
        if(hourText.length() == 1) {
            hourText = "0" + hourText;
        }
        if(minuteText.length() == 1){
            minuteText = "0"+minuteText;
        }
        fechayHora = dayText+"/"+mesText+"/"+year+"-"+hourText+":"+minuteText;
        return fechayHora;
    }
}
