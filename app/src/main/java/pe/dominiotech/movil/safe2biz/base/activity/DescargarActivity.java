package pe.dominiotech.movil.safe2biz.base.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ayc.dao.RegistroDao;
import pe.dominiotech.movil.safe2biz.ayc.model.Origen;
import pe.dominiotech.movil.safe2biz.ayc.model.TipoRiesgo;
import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.base.model.Turno;
import pe.dominiotech.movil.safe2biz.model.FiltroDescargaModuloBean;
import pe.dominiotech.movil.safe2biz.model.ResultadoBean;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifCategoria;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifPregunta;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifSeccion;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.TipoResultadoBean;
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectiva;
import pe.dominiotech.movil.safe2biz.task.AsyncConexionWSTask;
import pe.dominiotech.movil.safe2biz.task.AsyncConexionWSTask.AsyncTaskListener;
import pe.dominiotech.movil.safe2biz.task.ListaRutasWS;
import pe.dominiotech.movil.safe2biz.task.ParametrosWS;
import pe.dominiotech.movil.safe2biz.task.RespuestaWS;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.LogApp;
import pe.dominiotech.movil.safe2biz.utils.Mensajes;
import pe.dominiotech.movil.safe2biz.utils.Util;

public class DescargarActivity extends AppCompatActivity implements OnClickListener{

    Toolbar toolbar;
    private Button btnDescargar;
    private CheckedTextView chkOps;
    private CheckedTextView chkAyc;
    private CheckedTextView chkSac;

    private MainApplication app;
    private UsuarioBean usuario;
    private FiltroDescargaModuloBean filtro;
    ProgressDialog progressDialog = null;
    RegistroDao registroDao;
    private int totalCount = 0 ;
    int count = 0;
    ProgressDialog Dialog1;
    private boolean pvez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"[DescargarActivity] entro ");
        setupTheme();
        super.onCreate(savedInstanceState);
        registroDao = new RegistroDao(this, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
        Dialog1 = new ProgressDialog(this);
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
//                app.setUsuarioEnSesion(null);
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                System.exit(2);
//            }
//        });

        setContentView(R.layout.descargar_modulos_activity);
        pvez = getIntent().getBooleanExtra(AppConstants.datos_descarga_activo, false);
        mostrarBarraAcciones();
        inicializarComponentes();
    }

    public void setupTheme() {
        setTheme(R.style.MyMaterialTheme);
    }

    private void mostrarBarraAcciones(){

         app = (MainApplication) getApplication();
        usuario = app.getUsuarioEnSesion();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.lb_titulo_descarga);
        }

    }

    private void inicializarComponentes() {
        chkOps = (CheckedTextView)findViewById(R.id.chkOps);
        chkAyc = (CheckedTextView)findViewById(R.id.chkAyc);
        chkSac = (CheckedTextView)findViewById(R.id.chkSac);

        chkOps.setOnClickListener(this);
        chkAyc.setOnClickListener(this);
        chkSac.setOnClickListener(this);

        btnDescargar = (Button)findViewById(R.id.btnDescargar);
        btnDescargar.setOnClickListener(this);

        filtro = new FiltroDescargaModuloBean();

        chkOps.setChecked(false);
        chkOps.setEnabled(true);
        chkAyc.setChecked(false);
        chkAyc.setEnabled(true);
        chkSac.setChecked(false);
        chkSac.setEnabled(true);

        filtro.setListaVerificacion(true);
        filtro.setActosAndCondiciones(true);
        filtro.setEjecucionSac(true);
        filtro.setVerificacionSac(true);
    }

    @Override
    public void onClick(View v) {

        if(v == btnDescargar){

            String URL_EXT = app.getUsuarioEnSesion().getIpOrDominioServidor();
//            String URL_EXT = "http://192.168.1.54:7777/safe2biz";
            Map<String,String> headers = new HashMap<>();
            headers.put("userLogin",usuario.getUser_login());
            headers.put("userPassword",usuario.getPassword());
            headers.put("systemRoot","safe2biz");
            Map <String,String> parameters = new HashMap<>();

            if (totalCount > 0){
                Dialog1.setMessage("Descargando datos del servidor...");
                Dialog1.show();
            }

            if(chkAyc.isChecked()) {
                parameters.put("code","origen_ayc");
                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_master_table")
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("master_table")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Origen origen = new Origen();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        origen.setCode(object.getString("code"));
                                        origen.setName(object.getString("name"));

                                        if (registroDao.getById(Origen.class,origen.getCode()) != null) {
                                            System.out.println("Ya está");
                                        } else {
                                            registroDao.createOrUpdateGeneric(origen);
                                        }
                                    }
                                    addCount();


                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                parameters.clear();
                parameters.put("code","tipo_riesgo_ayc");
                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_master_table")
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("master_table")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    TipoRiesgo tipoRiesgo = new TipoRiesgo();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        tipoRiesgo.setCode(object.getString("code"));
                                        tipoRiesgo.setName(object.getString("name"));
                                        if (registroDao.getById(TipoRiesgo.class,tipoRiesgo.getCode()) != null) {
                                            System.out.println("Ya está");
                                        } else {
                                            registroDao.createOrUpdateGeneric(tipoRiesgo);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                parameters.clear();
                parameters.put("user_login",usuario.getUser_login());
                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_fb_area")
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Area")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Area area = new Area();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        area.setFb_area_id(object.getLong("fb_area_id"));
                                        area.setCodigo(object.getString("codigo"));
                                        area.setNombre(object.getString("nombre"));
                                        if (registroDao.getById(Area.class,area.getFb_area_id()) != null) {
                                            Log.d("Area","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(area);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                parameters.clear();
                parameters.put("user_login",usuario.getUser_login());
                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_fb_empresa_especializada")
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Empresa")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    EmpresaEspecializada empresaEspecializada = new EmpresaEspecializada();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        empresaEspecializada.setFb_empresa_especializada_id(object.getLong("fb_empresa_especializada_id"));
                                        empresaEspecializada.setRazon_social(object.getString("razon_social"));
                                        empresaEspecializada.setRuc_empresa(object.getString("ruc_empresa"));
                                        empresaEspecializada.setG_rol_empresa_id(object.getLong("g_rol_empresa_id"));
                                        if (registroDao.getById(EmpresaEspecializada.class,empresaEspecializada.getFb_empresa_especializada_id()) != null) {
                                            Log.d("Empresa","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(empresaEspecializada);
                                        }
                                    }

                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
            }
            if(chkOps.isChecked()){
                parameters.clear();
                parameters.put("user_login",usuario.getUser_login());
                AndroidNetworking.post(URL_EXT + getResources().getString(R.string.SERVICIO_OPS_TIPO_RESULTADO))
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Ops")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    TipoResultadoBean tipoResultadoBean = new TipoResultadoBean();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        tipoResultadoBean.setOpsTipoResultadoId(object.getLong("ops_tipo_resultado_id"));
                                        tipoResultadoBean.setCodigo(object.getString("codigo"));
                                        tipoResultadoBean.setNombre(object.getString("nombre"));
                                        if (registroDao.getById(TipoResultadoBean.class,tipoResultadoBean.getOpsTipoResultadoId()) != null) {
                                            Log.d("Ops","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(tipoResultadoBean);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                AndroidNetworking.post(URL_EXT + getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION))
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Ops")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ListaVerificacion listaVerificacion = new ListaVerificacion();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        listaVerificacion.setOps_lista_verificacion_id(object.getLong("ops_lista_verificacion_id"));
                                        listaVerificacion.setCodigo(object.getString("codigo"));
                                        listaVerificacion.setNombre(object.getString("nombre"));
                                        listaVerificacion.setOpsTipoResultadoId(object.getLong("ops_tipo_resultado_id"));
                                        if (registroDao.getById(ListaVerificacion.class,listaVerificacion.getOps_lista_verificacion_id()) != null) {
                                            Log.d("Ops","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(listaVerificacion);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                AndroidNetworking.post(URL_EXT + getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_SECCION))
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Ops")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ListaVerifSeccion listaVerifSeccion = new ListaVerifSeccion();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        listaVerifSeccion.setOps_lista_verif_seccion_id(object.getLong("ops_lista_verif_seccion_id"));
                                        listaVerifSeccion.setOps_lista_verif_categoria_id(object.getLong("ops_lista_verif_categoria_id"));
                                        listaVerifSeccion.setOps_lista_verificacion_id(object.getLong("ops_lista_verificacion_id"));
                                        listaVerifSeccion.setNombre(object.getString("nombre"));
                                        if (registroDao.getById(ListaVerifSeccion.class,listaVerifSeccion.getOps_lista_verif_seccion_id()) != null) {
                                            Log.d("Ops","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(listaVerifSeccion);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                AndroidNetworking.post(URL_EXT + getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_CATEGORIA))
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Ops")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ListaVerifCategoria listaVerifCategoria = new ListaVerifCategoria();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        listaVerifCategoria.setOps_lista_verif_categoria_id(object.getLong("ops_lista_verif_categoria_id"));
                                        listaVerifCategoria.setOps_lista_verificacion_id(object.getLong("ops_lista_verificacion_id"));
                                        listaVerifCategoria.setNombre(object.getString("nombre"));
                                        if (registroDao.getById(ListaVerifCategoria.class,listaVerifCategoria.getOps_lista_verif_categoria_id()) != null) {
                                            Log.d("Ops","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(listaVerifCategoria);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                AndroidNetworking.post(URL_EXT + getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_PREGUNTA))
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Ops")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ListaVerifPregunta listaVerifPregunta = new ListaVerifPregunta();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        listaVerifPregunta.setOps_lista_verif_pregunta_id(object.getLong("ops_lista_verif_pregunta_id"));
                                        listaVerifPregunta.setOps_lista_verif_seccion_id(object.getLong("ops_lista_verif_seccion_id"));
                                        listaVerifPregunta.setOps_lista_verif_categoria_id(object.getLong("ops_lista_verif_categoria_id"));
                                        listaVerifPregunta.setOps_lista_verificacion_id(object.getLong("ops_lista_verificacion_id"));
                                        listaVerifPregunta.setNombre(object.getString("nombre"));
                                        listaVerifPregunta.setFlag_pregunta(object.getInt("flag_pregunta"));
                                        if (registroDao.getById(ListaVerifPregunta.class,listaVerifPregunta.getOps_lista_verif_pregunta_id()) != null) {
                                            Log.d("Ops","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(listaVerifPregunta);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                AndroidNetworking.post(URL_EXT + getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_RESULTADO))
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Ops")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ResultadoBean listaVerifCategoria = new ResultadoBean();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        listaVerifCategoria.setOps_lista_verif_resultado_id(object.getLong("ops_lista_verif_resultado_id"));
                                        listaVerifCategoria.setOps_tipo_resultado_id(object.getLong("ops_tipo_resultado_id"));
                                        listaVerifCategoria.setNombre(object.getString("nombre"));
                                        listaVerifCategoria.setCodigo(object.getString("codigo"));
                                        if (registroDao.getById(ResultadoBean.class,listaVerifCategoria.getOps_lista_verif_resultado_id()) != null) {
                                            Log.d("Ops","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(listaVerifCategoria);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                parameters.clear();
                parameters.put("code","turno");
                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_master_table")
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("master_table")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Turno turno = new Turno();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        turno.setCode(object.getString("code"));
                                        turno.setName(object.getString("name"));
                                        if (registroDao.getById(Turno.class,turno.getCode()) != null) {
                                            System.out.println("Ya está");
                                        } else {
                                            registroDao.createOrUpdateGeneric(turno);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                parameters.clear();
                parameters.put("user_login",usuario.getUser_login());
                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_fb_area")
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Area")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Area area = new Area();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        area.setFb_area_id(object.getLong("fb_area_id"));
                                        area.setCodigo(object.getString("codigo"));
                                        area.setNombre(object.getString("nombre"));
                                        if (registroDao.getById(Area.class,area.getFb_area_id()) != null) {
                                            Log.d("Area","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(area);
                                        }
                                    }
                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
                parameters.clear();
                parameters.put("user_login",usuario.getUser_login());
                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_fb_empresa_especializada")
                        .addHeaders(headers)
                        .addBodyParameter(parameters)
                        .setTag("Empresa")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    EmpresaEspecializada empresaEspecializada = new EmpresaEspecializada();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        empresaEspecializada.setFb_empresa_especializada_id(object.getLong("fb_empresa_especializada_id"));
                                        empresaEspecializada.setRazon_social(object.getString("razon_social"));
                                        empresaEspecializada.setRuc_empresa(object.getString("ruc_empresa"));
                                        empresaEspecializada.setG_rol_empresa_id(object.getLong("g_rol_empresa_id"));
                                        if (registroDao.getById(EmpresaEspecializada.class,empresaEspecializada.getFb_empresa_especializada_id()) != null) {
                                            Log.d("Empresa","Ya existe");
                                        } else {
                                            registroDao.createOrUpdateGeneric(empresaEspecializada);
                                        }
                                    }

                                    addCount();
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                cancelRequest(error);
                            }
                        });
            }

            if(chkSac.isChecked()){
                AndroidNetworking.get(URL_EXT + "/ws/"+usuario.getFb_uea_pe_id()+"/pr_ws_sac_consulta")
                        .addHeaders(headers)
                        .setTag("accion_correctiva")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    AccionCorrectiva accionCorrectiva = new AccionCorrectiva();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++){
                                        JSONObject sacObject = data.getJSONObject(i);
                                        accionCorrectiva.setSac_accion_correctiva_id(sacObject.getInt("sac_accion_correctiva_id"));
                                        accionCorrectiva.setCodigo_accion_correctiva(sacObject.getString("codigo_accion_correctiva"));
                                        accionCorrectiva.setAccion_correctiva_detalle(sacObject.getString("accion_correctiva_detalle"));
                                        accionCorrectiva.setFecha_acordada_ejecucion(sacObject.getString("fecha_acordada_ejecucion"));
                                        accionCorrectiva.setNombre_responsable_correccion(sacObject.getString("nombre_responsable_correccion"));
                                        accionCorrectiva.setOrigen(sacObject.getString("origen"));
                                        if (registroDao.getById(AccionCorrectiva.class,accionCorrectiva.getSac_accion_correctiva_id())!=null){
                                            Log.d("Sac","Ya existe");
                                        }else{
                                            registroDao.createOrUpdateGeneric(accionCorrectiva);
                                        }
                                    }
                                } catch (JSONException | SQLException e) {
                                    e.printStackTrace();
                                }
                                addCount();
                            }
                            @Override
                            public void onError(ANError error) {
                               cancelRequest(error);
                            }
                        });
            }


//			LogApp.log(AppConstants.ruta_log_safe2biz, AppConstants.ARCHIVO_LOG_SAFE2BIZ,"[DescargarActivity] Verificando Checked... ");

			/*try{
				if (chkOps.isChecked() || chkAyc.isChecked() || chkSac.isChecked() ){
					String res = "";
					String msj = "";
					if (chkOps.isChecked()){
					}
					if (chkAyc.isChecked()){
					}
					if (chkSac.isChecked()){
					}
					LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"[DescargarActivity] msj "+msj+" -");
					if (msj.equals(AppConstants.cadena_vacia)){
						if (Util.VerificarConexionInternet(getApplicationContext())){
							descargarFormatos();
						}else{
							Util.mostrarMensaje(getApplicationContext(), Mensajes.mensaje_no_hay_internet);
						}
					}else if (res.equals(AppConstants.estado_error)){
						Util.mostrarMensaje(getApplicationContext(), Mensajes.error_procesar);
					}else {
						Util.mostrarMensaje(getApplicationContext(), msj);
					}
				}else{
					Util.mostrarMensaje(getApplicationContext(), Mensajes.mensaje_seleccionar_modulo);
				}
			}catch(Exception e){
				LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"[DescargarActivity] error "+e.getLocalizedMessage());
			}*/
        }else if (v == chkOps){
            if(chkOps.isChecked()){
                totalCount = totalCount - 9;
                chkOps.setChecked(false);
                filtro.setListaVerificacion(false);
            } else{
                totalCount = totalCount + 9;

                chkOps.setChecked(true);
                filtro.setListaVerificacion(true);
            }
            LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"[DescargarActivity] chkOps "+ chkOps.isChecked()+ " - " +filtro.isListaVerificacion());
        }else if (v == chkAyc){
            if(chkAyc.isChecked()){
                totalCount = totalCount - 4;
                chkAyc.setChecked(false);
                filtro.setActosAndCondiciones(false);
            } else{
                totalCount = totalCount + 4;

                chkAyc.setChecked(true);
                filtro.setActosAndCondiciones(true);
            }
            LogApp.log(AppConstants.ruta_log_actos_and_condiciones, AppConstants.ARCHIVO_LOG_ACTOS_Y_CONDICIONES,"[DescargaActivity] chkAyc "+ chkAyc.isChecked()+ " - " +filtro.isActosAndCondiciones());
        }else if (v == chkSac){
            if(chkSac.isChecked()){
                totalCount = totalCount - 1;
                chkSac.setChecked(false);
                filtro.setEjecucionSac(false);
            } else{
                totalCount = totalCount + 1;
                chkSac.setChecked(true);
                filtro.setEjecucionSac(true);
            }
            LogApp.log(AppConstants.ruta_log_ejecucion_sac, AppConstants.ARCHIVO_LOG_EJECUCION_SAC,"[DescargaActivity] chkSac "+ chkSac.isChecked()+ " - " +filtro.isEjecucionSac());
        }

        if(chkOps.isChecked() || chkSac.isChecked() || chkAyc.isChecked()){
            btnDescargar.setEnabled(true);

        }else{
            btnDescargar.setEnabled(false);
        }
    }

    private void addCount() {
        count++;
        if (count == totalCount){
            Dialog1.dismiss();
            Dialog1.hide();
            Toast.makeText(app, "Se completó la sincronización", Toast.LENGTH_SHORT).show();
            count = 0;
        }
    }

    private void cancelRequest(ANError error) {
        Log.e("Error", error.toString());
        Dialog1.dismiss();
        Dialog1.hide();
        Toast.makeText(app, "No se completó la sincronización", Toast.LENGTH_SHORT).show();
        count = 0;
    }

    private void descargarFormatos() {
        // TODO Auto-generated method stub
        LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"[DescargarActivity] descargarFormatos entro");

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        View pDialogView = getLayoutInflater().inflate(R.layout.pantalla_espera, null);
        progressDialog.setContentView(pDialogView);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        TextView mensaje = (TextView)pDialogView.findViewById(R.id.lbMensajeEspera);
        mensaje.setText(Mensajes.pb_mensaje_iniciando_descarga);
        LogApp.log(AppConstants.ruta_log_lista_verificacion, AppConstants.ARCHIVO_LOG_LISTA_VERIFICACION,"[DescargarActivity] filtro "+filtro.isListaVerificacion()+" - "+filtro.isActosAndCondiciones()+" - "+filtro.isEjecucionSac());

        ListaRutasWS rutas = new ListaRutasWS();
        rutas.setUserLogin(app.getUsuarioEnSesion().getUser_login());
        rutas.setUserPassword(app.getUsuarioEnSesion().getPassword());
        rutas.setSystemRoot(AppConstants.SYSTEM_ROOT);

        if (filtro.isListaVerificacion()){
            ParametrosWS param1 = new ParametrosWS();
            param1.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_TIPO_RESULTADO));
            param1.setMetodo(AppConstants.metodo_post);
            param1.setNombreMetodo(AppConstants.procesar_tipo_resultado);
            param1.setMensaje(Mensajes.pb_mensaje_descargando_tipo_resultado);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param1.setParametros(nameValuePairs);
            rutas.getParametros().add(param1);

            ParametrosWS param2 = new ParametrosWS();
            param2.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION));
            param2.setMetodo(AppConstants.metodo_post);
            param2.setNombreMetodo(AppConstants.procesar_lista_verificacion);
            param2.setMensaje(Mensajes.pb_mensaje_descargando_lista_verificacion_2);
            List<NameValuePair> nameValuePairs2 = new ArrayList<NameValuePair>();
            nameValuePairs2.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param2.setParametros(nameValuePairs2);
            rutas.getParametros().add(param2);

            ParametrosWS param3 = new ParametrosWS();
            param3.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_CATEGORIA));
            param3.setMetodo(AppConstants.metodo_post);
            param3.setNombreMetodo(AppConstants.procesar_lista_verificacion_categoria);
            param3.setMensaje(Mensajes.pb_mensaje_descargando_categoria);
            List<NameValuePair> nameValuePairs3 = new ArrayList<NameValuePair>();
            nameValuePairs3.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param3.setParametros(nameValuePairs3);
            rutas.getParametros().add(param3);

            ParametrosWS param4 = new ParametrosWS();
            param4.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_SECCION));
            param4.setMetodo(AppConstants.metodo_post);
            param4.setNombreMetodo(AppConstants.procesar_lista_verificacion_seccion);
            param4.setMensaje(Mensajes.pb_mensaje_descargando_seccion);
            List<NameValuePair> nameValuePairs4 = new ArrayList<NameValuePair>();
            nameValuePairs4.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param4.setParametros(nameValuePairs4);
            rutas.getParametros().add(param4);

            ParametrosWS param5 = new ParametrosWS();
            param5.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_PREGUNTA));
            param5.setMetodo(AppConstants.metodo_post);
            param5.setNombreMetodo(AppConstants.procesar_lista_verificacion_pregunta);
            param5.setMensaje(Mensajes.pb_mensaje_descargando_pregunta);
            List<NameValuePair> nameValuePairs5 = new ArrayList<NameValuePair>();
            nameValuePairs5.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param5.setParametros(nameValuePairs5);
            rutas.getParametros().add(param5);

            ParametrosWS param6 = new ParametrosWS();
            param6.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_VERIFICACION_RESULTADO));
            param6.setMetodo(AppConstants.metodo_post);
            param6.setNombreMetodo(AppConstants.procesar_lista_verificacion_resultado);
            param6.setMensaje(Mensajes.pb_mensaje_descargando_resultado);
            List<NameValuePair> nameValuePairs6 = new ArrayList<NameValuePair>();
            nameValuePairs6.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param6.setParametros(nameValuePairs6);
            rutas.getParametros().add(param6);

            ParametrosWS param7 = new ParametrosWS();
            param7.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_EMPRESA_ESPECIALIZADA));
            param7.setMetodo(AppConstants.metodo_post);
            param7.setNombreMetodo(AppConstants.procesar_lista_empresa_especializada);
            param7.setMensaje(Mensajes.pb_mensaje_descargando_empresa_especializada);
            List<NameValuePair> nameValuePairs7 = new ArrayList<NameValuePair>();
            nameValuePairs7.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param7.setParametros(nameValuePairs7);
            rutas.getParametros().add(param7);

            ParametrosWS param8 = new ParametrosWS();
            param8.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_AREA));
            param8.setMetodo(AppConstants.metodo_post);
            param8.setNombreMetodo(AppConstants.procesar_lista_area);
            param8.setMensaje(Mensajes.pb_mensaje_descargando_area);
            List<NameValuePair> nameValuePairs8 = new ArrayList<NameValuePair>();
            nameValuePairs8.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param8.setParametros(nameValuePairs8);
            rutas.getParametros().add(param8);

            ParametrosWS param9 = new ParametrosWS();
            param9.setRuta(app.getUsuarioEnSesion().getIpOrDominioServidor()+getResources().getString(R.string.SERVICIO_OPS_LISTA_TURNO));
            param9.setMetodo(AppConstants.metodo_post);
            param9.setNombreMetodo(AppConstants.procesar_lista_turno);
            param9.setMensaje(Mensajes.pb_mensaje_descargando_turno);
            List<NameValuePair> nameValuePairs9 = new ArrayList<NameValuePair>();
            nameValuePairs9.add(new BasicNameValuePair("user_login", app.getUsuarioEnSesion().getUsuario()));
            param9.setParametros(nameValuePairs9);
            rutas.getParametros().add(param9);

            LogApp.log(AppConstants.ruta_log_safe2biz, AppConstants.ARCHIVO_LOG_SAFE2BIZ,"[DescargarActivity] parametros lista verificación "+rutas.getParametros().size());
        }

        AsyncConexionWSTask ejecutar = new AsyncConexionWSTask(app, new AsyncTaskListener() {

            @Override
            public void onCompletadoTask(List<RespuestaWS> resultado) {
                // TODO Auto-generated method stub
                String cad = "";
                String mensaje = Mensajes.mensaje_cant_modulos_cab;
                for (RespuestaWS respuesta : resultado) {
                    if (!respuesta.isProcesado()){
                        cad += respuesta.getMensaje() + "\n";
                    }else
                        mensaje += respuesta.getMensaje();
                }
                progressDialog.dismiss();
                TextView tvMensaje = (TextView)findViewById(R.id.tvNumeroFormatos);
                if (cad.equals(AppConstants.cadena_vacia)){
                    if(filtro.isListaVerificacion()){
                        app.getParametroService().modificarParametroMovil(AppConstants.valor_ultima_fecha_sync_lista_verificacion, Util.obtenerFechaHora());
                    }
                    Util.mostrarMensaje(DescargarActivity.this, Mensajes.mensaje_exito_descarga_modulos);
                    tvMensaje.setText(mensaje);
                }else{
                    Util.mostrarMensaje(DescargarActivity.this, Mensajes.mensaje_error_descarga_modulos);
                    tvMensaje.setText(AppConstants.cadena_vacia);
                }
            }
        }, mensaje);
        ejecutar.execute(rutas);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            this.overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
