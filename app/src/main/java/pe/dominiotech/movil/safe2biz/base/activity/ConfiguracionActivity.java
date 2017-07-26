package pe.dominiotech.movil.safe2biz.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;
import pe.dominiotech.movil.safe2biz.service.UsuarioService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ConfiguracionActivity extends Activity {
    private MainApplication app;

    private UsuarioService usuarioService;

    @Bind(R.id.edtIpOrDominioConfiguracion)
    EditText edtIpOrDominioConfig;

    @Bind(R.id.edtUsuarioConfiguracion)
    EditText edtUsuarioConfig;

    @Bind(R.id.edtContraseniaConfiguracion)
    EditText edtContraseniaConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {             @Override             public void uncaughtException(Thread paramThread, Throwable paramThrowable) {                 app.setUsuarioEnSesion(null);                 Intent intent = new Intent(getApplicationContext(), LoginActivity.class);                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                 startActivity(intent);                 System.exit(2);             }         });*/

        setContentView(R.layout.configuracion_activity);
        ButterKnife.bind(this);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        app = (MainApplication)getApplication();
        usuarioService = app.getUsuarioService();
        Button btnGuardarConfiguracion = (Button) findViewById(R.id.btnGuardarConfiguracion);
        btnGuardarConfiguracion.setTransformationMethod(null);
        UsuarioBean usuarioBean = obtenerDatosUsuario();
        cargarInformacion(usuarioBean);
        TextView ip = (TextView) findViewById(R.id.ip);
        TextView user = (TextView) findViewById(R.id.user);
        TextView pass = (TextView) findViewById(R.id.contraseña);
        ip.setTypeface(null, Typeface.BOLD);
        user.setTypeface(null, Typeface.BOLD);
        pass.setTypeface(null, Typeface.BOLD);
    }


    @OnClick(R.id.btnGuardarConfiguracion)
    public void onLoginButtonClick(){
        String ipOrDominioConfigStr = edtIpOrDominioConfig.getText().toString();
        String usuarioConfiStr = edtUsuarioConfig.getText().toString();
        String contraseniaConfigStr = edtContraseniaConfig.getText().toString();
        String msj = verificarCamposObligatorios(ipOrDominioConfigStr, usuarioConfiStr, contraseniaConfigStr);
        if(msj.length() != 0){
            Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_SHORT);
            toast.show();
        }else {
            UsuarioBean usuarioBean = new UsuarioBean();
            usuarioBean.setUsuario_id(1);
            usuarioBean.setIpOrDominioServidor(ipOrDominioConfigStr);
            usuarioBean.setUser_login_servidor(usuarioConfiStr);
            usuarioBean.setPassword_servidor(contraseniaConfigStr);
            usuarioService.save(usuarioBean);

            Intent data = new Intent();
            data.putExtra("ip_or_dominio_servidor", ipOrDominioConfigStr);
            data.putExtra("user_login_servidor", usuarioConfiStr);
            data.putExtra("password_servidor", contraseniaConfigStr);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    public String verificarCamposObligatorios(String ipOrDominioConfigStr,String emailStr, String passwordStr){
        String mensaje = "";
        if(ipOrDominioConfigStr.equals("") || ipOrDominioConfigStr.length() == 0){
            mensaje = "Ingresar IP o dominio del servidor";
        }else if (emailStr.equals("") || emailStr.length() == 0){
            mensaje = "";
        }else if (passwordStr.equals("") || passwordStr.length() == 0){
            mensaje = "";
        }
        return mensaje;
    }

    public void cargarInformacion(UsuarioBean uBean){
        if(null != uBean){
            edtIpOrDominioConfig.setText(uBean.getIpOrDominioServidor());
            edtUsuarioConfig.setText(uBean.getUser_login_servidor());
            edtContraseniaConfig.setText(uBean.getPassword_servidor());
        }
    }

    public UsuarioBean obtenerDatosUsuario(){
        UsuarioBean usuarioBean = new UsuarioBean();
        usuarioBean.setUsuario_id(1);
        return usuarioService.getBean(usuarioBean);
    }

    @Override
    public void onBackPressed() {
        String ipOrDominioConfigStr = edtIpOrDominioConfig.getText().toString();
        String usuarioConfiStr = edtUsuarioConfig.getText().toString();
        String contraseniaConfigStr = edtContraseniaConfig.getText().toString();

        Intent data = new Intent();
        data.putExtra("ip_or_dominio_servidor", ipOrDominioConfigStr);
        data.putExtra("user_login_servidor", usuarioConfiStr);
        data.putExtra("password_servidor", contraseniaConfigStr);
        setResult(RESULT_OK, data);
        finish();
        super.onBackPressed();
    }
}


