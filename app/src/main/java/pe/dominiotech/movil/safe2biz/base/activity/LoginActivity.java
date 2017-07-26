package pe.dominiotech.movil.safe2biz.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.stream.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;
import pe.dominiotech.movil.safe2biz.service.UsuarioService;
import pe.dominiotech.movil.safe2biz.utils.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends Activity {
    static final int CONFIG_COMPLETA_REQUEST = 1;  // The request code
    String ipOrDominioServidor;
    String usuarioServidor;
    String passwordServidor;
    String idDispositivo;
    @Bind(R.id.input_name) EditText emailText;
    @Bind(R.id.input_layout_name) TextInputLayout inputLayoutName;
    @Bind(R.id.input_pass) EditText passwordText;
    private MainApplication app;
    private UsuarioService usuarioService;
    private UsuarioBean usuario = new UsuarioBean();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONFIG_COMPLETA_REQUEST) {
            if (resultCode == RESULT_OK) {
                ipOrDominioServidor = data.getExtras().getString("ip_or_dominio_servidor");
                usuarioServidor = data.getExtras().getString("user_login_servidor");
                passwordServidor = data.getExtras().getString("password_servidor");
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, " No se guardó la información... ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, " No se guardó la información... ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private boolean validateName() {
        if (emailText.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Usuario vacío");
            requestFocus(emailText);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        setTheme(R.style.MyMaterialTheme);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        app = (MainApplication) getApplication();
        usuarioService = app.getUsuarioService();
        idDispositivo = Util.getImei(LoginActivity.this);
        emailText.addTextChangedListener(new MyTextWatcher(emailText));
        usuario.setUsuario_id(1);

        if (usuarioService.getBean(usuario) != null) {
            usuario = usuarioService.getBean(usuario);
            ipOrDominioServidor = usuario.getIpOrDominioServidor();
            if (usuario.getUser_login() != null && usuario.getPassword() != null) {
                emailText.setText(usuario.getUser_login());
                passwordText.setText(usuario.getPassword());
            }
        }
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClick() {
        validateName();

        final String emailStr = emailText.getText().toString();
        final String passwordStr = passwordText.getText().toString();

        if (emailStr.equals("") || passwordStr.equals("")) {
            Toast.makeText(app, "Hay campos vacíos. Por favor, llenar todos los campos.", Toast.LENGTH_SHORT).show();
        } else {
            if (ipOrDominioServidor != null) {
                if (emailStr.equals(usuario.getUser_login()) && passwordStr.equals(usuario.getPassword()) && ipOrDominioServidor.equals(usuario.getIpOrDominioServidor())){
                    usuario.setIdDispositivo(idDispositivo);
                    app.setUsuarioEnSesion(usuario);
                    Intent i = new Intent(getApplicationContext(), ListaUnidadesActivity.class);
                    startActivity(i);
                }else{
                    if (isOnline()) {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("userLogin", emailStr);
                        headers.put("userPassword", passwordStr);
                        headers.put("systemRoot", "safe2biz");
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("user_login", emailStr.split("@")[0]);
                        final ProgressDialog dialog;
                        dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setMessage("Validando...");
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .build();

                        AndroidNetworking.post(ipOrDominioServidor + getResources().getString(R.string.SERVICIO_SC_USER))
                                .addBodyParameter(parameters)
                                .addHeaders(headers)
                                .setTag("login")
                                .setPriority(Priority.HIGH)
                                .setOkHttpClient(okHttpClient)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONArray data = response.getJSONArray("data");
                                            for (int i = 0; i < data.length(); i++) {
                                                JSONObject object = data.getJSONObject(i);
                                                usuario.setUser_login(emailStr);
                                                usuario.setPassword(passwordStr);
                                                usuario.setSc_user_id(object.getLong("SC_USER_ID"));
                                                usuario.setUsuario(object.getString("USER_LOGIN"));
//                                                usuario.setDni(object.getString("DNI"));
                                                usuario.setFb_empleado_id(object.getLong("fb_empleado_id"));
//                                                usuario.setNombre_empleado(object.getString("nombre_empleado"));
                                                usuario.setUrl_ext(object.getString("URL_EXT"));
                                                usuario.setIpOrDominioServidor(ipOrDominioServidor);
                                            }
                                            int isSaveUser = usuarioService.save(usuario);
                                            if (1 == isSaveUser) {
                                                dialog.dismiss();
                                                usuario.setIdDispositivo("1");
                                                app.setUsuarioEnSesion(usuario);
                                                Intent intent = new Intent(getApplicationContext(), ListaUnidadesActivity.class);
                                                startActivity(intent);
                                            } else {
                                                mostrarMensaje("Error al guardar el usuario...");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError error) {
                                        dialog.dismiss();
                                        Log.e("Error", Integer.toString(error.getErrorCode()));
                                        if (Integer.toString(error.getErrorCode()).equals("410")){
                                            try {
                                                JSONObject jsonObject = new JSONObject(error.getErrorBody());
                                                String mensaje =  jsonObject.getJSONArray("errors").get(0).toString();
                                                Toast.makeText(app, mensaje, Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else{
                                            Toast.makeText(app, "Ha ocurrido un error en la conexión, revisa tu conexión a internet o la ip/dominio del servidor...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(app, "No existe el usuario. Necesita conexión a internet...", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(app, "Presione la tuerca para configurar el servidor...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void validacionLogin(UsuarioBean uBean) {
        String emailStr = emailText.getText().toString();
        String passwordStr = passwordText.getText().toString();
        String msj = verificarCamposObligatorios(emailStr, passwordStr);
        if (msj.length() != 0) {
            mostrarMensaje(msj);
        } else if (isOnline()) {
            new AsyncLoginValidator().execute(emailStr, passwordStr, uBean.getIpOrDominioServidor());
        } else {
            UsuarioBean usuarioBean = usuarioService.validarLogin(emailStr, passwordStr);
            if (usuarioBean != null) {
                usuarioBean.setIdDispositivo(idDispositivo);
                app.setUsuarioEnSesion(usuarioBean);
                Intent i = new Intent(getApplicationContext(), ListaUnidadesActivity.class);
                startActivity(i);
            } else {
                mostrarMensaje("No Existe Usuario");
            }
        }
    }

    public String verificarCamposObligatorios(String emailStr, String passwordStr) {
        String mensaje = "";
        if (emailStr.equals("") || emailStr.length() == 0) {
            mensaje = "Ingresar Usuario";
        } else if (passwordStr.equals("") || passwordStr.length() == 0) {
            mensaje = "Ingresar Contraseña";
        }
        return mensaje;
    }

    public boolean isOnline() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable() && conMgr
                .getActiveNetworkInfo().isConnected());
    }

    @OnClick(R.id.imgViewConfiguracion)
    public void onConfiguracionImageViewClick() {
        Intent i = new Intent(getApplicationContext(), ConfiguracionActivity.class);
        startActivityForResult(i, CONFIG_COMPLETA_REQUEST);
    }

    public void mostrarMensaje(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
            }
        }
    }

    private class AsyncLoginValidator extends AsyncTask<String, Void, UsuarioBean> {
        private ProgressDialog dialog;

        public AsyncLoginValidator() {
            dialog = new ProgressDialog(LoginActivity.this);
        }

        @Override
        protected void onPostExecute(UsuarioBean usuarioBean) {
            dialog.dismiss();

            if (usuarioBean != null) {
                usuarioBean.setUsuario_id(1);
                usuarioBean.setUser_login(emailText.getText().toString());
                usuarioBean.setPassword(passwordText.getText().toString());
                int isSaveUser = usuarioService.save(usuarioBean);
                if (1 == isSaveUser) {
                    usuarioBean.setIdDispositivo(idDispositivo);
                    app.setUsuarioEnSesion(usuarioBean);
                    Intent i = new Intent(getApplicationContext(), ListaUnidadesActivity.class);
                    startActivity(i);
                } else {
                    mostrarMensaje("Error al guardar el usuario...");
                }
            } else {
                app.setUsuarioEnSesion(null);
                mostrarMensaje("Usuario o Contrasena incorrecto");
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Validando...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected UsuarioBean doInBackground(String... params) {
            UsuarioBean usuarioBean = null;
            try {
                final String URL_REST = params[2] + getResources().getString(R.string.SERVICIO_SC_USER);
                URL url = new URL(URL_REST);

                HttpClient client = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(URL_REST);

// add header
                post.setHeader("userLogin", params[0]);
                post.setHeader("userPassword", params[1]);
                post.setHeader("systemRoot", "safe2biz");

                String user = params[0].substring(0, params[0].indexOf("@"));

                List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
                urlParameters.add(new BasicNameValuePair("user_login", user));

                post.setEntity(new UrlEncodedFormEntity(urlParameters));

                HttpResponse response = client.execute(post);
//                String json = IOUtils.toString(response.getEntity().getContent());
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                JsonReader reader = new JsonReader(rd);
                reader.beginObject();

                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("data")) {
                        usuarioBean = new UsuarioBean();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                name = reader.nextName();
                                if (name.equals("SC_USER_ID")) {
                                    usuarioBean.setSc_user_id(reader.nextLong());
                                } else if (name.equals("USER_LOGIN")) {
                                    usuarioBean.setUsuario(reader.nextString());
                                } else if (name.equals("DNI")) {
                                    usuarioBean.setDni(reader.nextString());
                                } else if (name.equals("fb_empleado_id")) {
                                    usuarioBean.setFb_empleado_id(reader.nextLong());
                                } else if (name.equals("nombre_empleado")) {
                                    usuarioBean.setNombre_empleado(reader.nextString());
                                } else {
                                    reader.skipValue();
                                }
                                usuarioBean.setIpOrDominioServidor(params[2]);
                            }
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                reader.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return usuarioBean;
        }


    }

}


