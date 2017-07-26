package pe.dominiotech.movil.safe2biz.task;

import android.util.Base64;
import android.util.Log;

import com.google.gson.stream.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.LogApp;
import pe.dominiotech.movil.safe2biz.utils.Util;

public class ConexionWS {

    private MainApplication app;
    public ConexionWS(){
        //	idSession = "";
    }

    public ConexionWS(MainApplication apps) {
        // TODO Auto-generated constructor stub
        this.app = apps;
        LogApp.log(AppConstants.ruta_log_safe2biz, AppConstants.ruta_log_safe2biz,"ConexionWS " + app.getUsuarioEnSesion().getNombre_empleado());
    }

    public JsonReader conexionServidorPost(String userLogin, String userPassword, String systemRoot, ParametrosWS parametro){
        JsonReader rspta = null;
        String paramString = URLEncodedUtils.format(parametro.getParametros(), HTTP.UTF_8);

        String ruta = parametro.getRuta();

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
        HttpConnectionParams.setSoTimeout(httpParameters, 6000000);
        HttpClient httpClient = new DefaultHttpClient(httpParameters);

        HttpPost httpPost = new HttpPost(ruta);

        httpPost.setHeader("userLogin", userLogin);
        httpPost.setHeader("userPassword", userPassword);
        httpPost.setHeader("systemRoot", systemRoot);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parametro.getParametros()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        LogApp.log(AppConstants.ruta_log_safe2biz, AppConstants.ARCHIVO_LOG_SAFE2BIZ,"conexionWS POST INI "+ Util.obtenerFechaHora());
        try{
            HttpResponse httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                Reader streamReader = new InputStreamReader(httpResponse.getEntity().getContent());
                rspta = new JsonReader(streamReader);
                LogApp.log(AppConstants.ruta_log_safe2biz, AppConstants.ARCHIVO_LOG_SAFE2BIZ,"conexionWS POST FIN "+Util.obtenerFechaHora());
            }else{
                LogApp.log(AppConstants.ruta_log_safe2biz, AppConstants.ARCHIVO_LOG_SAFE2BIZ,"ConexionWS POST res "+EntityUtils.toString(httpResponse.getEntity()));
            }
        }catch (Exception ex){
            LogApp.log(AppConstants.ruta_log_safe2biz, AppConstants.ARCHIVO_LOG_SAFE2BIZ,"error conexion POST " + ex.getLocalizedMessage());
        }
        return rspta;
    }

    public String conexion(String usuario, String clave, ParametrosWS parametro){
        String rspta = "";
        String credentials = usuario + ":" + clave;
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        LogApp.log("Conexion credenciales " + usuario + " - " + clave );
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
        HttpConnectionParams.setSoTimeout(httpParameters, 300000);
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        String paramString = URLEncodedUtils.format(parametro.getParametros(), "utf-8");
        String ruta = parametro.getRuta() + "?" + paramString;
        LogApp.log("Conexion "+parametro.getMetodo()+" ruta "+ruta );
        Log.i("VCC","ConexionWS "+parametro.getMetodo()+" ruta "+ruta);
        HttpGet httpGet = new HttpGet(ruta);
        httpGet.setHeader( "Content-Type", "application/json");
        httpGet.addHeader("Authorization", "Basic " + base64EncodedCredentials);
        Log.i("VCC", "conexion ws INI "+Util.obtenerFechaHora());
        LogApp.log("conexion GET INI "+Util.obtenerFechaHora());
        try{
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                rspta = EntityUtils.toString(httpResponse.getEntity());
                LogApp.log("conexion GET FIN "+Util.obtenerFechaHora());
                LogApp.log("Conexion GET res "+rspta);
                Log.i("VCC", "conexion ws FIN "+Util.obtenerFechaHora());
            }
        }catch (Exception ex){
            Log.i("VCC","error conexion " + ex.getLocalizedMessage());
            LogApp.log("error conexion GET " + ex.getLocalizedMessage());
        }
        return rspta;
    }
}