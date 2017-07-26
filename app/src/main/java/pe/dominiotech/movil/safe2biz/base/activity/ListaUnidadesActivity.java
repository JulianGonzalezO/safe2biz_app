package pe.dominiotech.movil.safe2biz.base.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.base.adapter.ListaUnidadesAdapter;
import pe.dominiotech.movil.safe2biz.model.UnidadBean;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;
import pe.dominiotech.movil.safe2biz.service.ListaVerificacionService;
import pe.dominiotech.movil.safe2biz.service.UsuarioService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ListaUnidadesActivity extends AppCompatActivity {

    Toolbar toolbar;
    private MainApplication app;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private ListaUnidadesAdapter cardViewAdapter;
    int onStartCount = 0;
    private ListaVerificacionService listaVerificacionService;
    private UsuarioService usuarioService;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);

/*        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {             @Override             public void uncaughtException(Thread paramThread, Throwable paramThrowable) {                 app.setUsuarioEnSesion(null);                 Intent intent = new Intent(getApplicationContext(), LoginActivity.class);                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                 startActivity(intent);                 System.exit(2);             }         });*/

        onStartCount = 1;
        if (savedInstanceState == null){
            this.overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
        } else {
            onStartCount = 2;
        }

        setContentView(R.layout.lista_verificacion_activity);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        app = (MainApplication) getApplication();
        listaVerificacionService = app.getListaVerificacionService();
        usuarioService = app.getUsuarioService();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Sedes");
            actionBar.setHomeButtonEnabled(true);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.listaVerificacionRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList menuList = new ArrayList<>();
        cardViewAdapter = new ListaUnidadesAdapter(menuList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMenu(view);
            }
        },getApplicationContext());
        mRecyclerView.setAdapter(cardViewAdapter);

        if(isOnline()){
            descargarListaUnidad(app.getUsuarioEnSesion().getUser_login(), app.getUsuarioEnSesion().getPassword(), app.getUsuarioEnSesion().getUsuario(), app.getUsuarioEnSesion().getSc_user_id()+"", app.getUsuarioEnSesion().getIpOrDominioServidor());
        }else{
            cargarListaUnidad();
        }
    }

    public void setupTheme() {
        setTheme(R.style.MyMaterialTheme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        app.getUsuarioEnSesion();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {

        }else if (id == android.R.id.home){
            onBackPressed();
            this.overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClickMenu(View item) {
        UnidadBean unidadBean = ((ListaUnidadesAdapter.ViewHolder)item.getTag()).getUnidadBean();
        switch(item.getId()){
            case R.id.lnlyCarViewUnidad : {
                UsuarioBean usuarioBean = app.getUsuarioEnSesion();
                usuarioBean.setFb_uea_pe_id(unidadBean.getFb_uea_pe_id());
                usuarioBean.setFb_uea_pe_abr(unidadBean.getCodigo());
                int isUpdate = usuarioService.update(usuarioBean);
                if(isUpdate == 1){
                    app.setUsuarioEnSesion(usuarioBean);
                    Intent i = new Intent(ListaUnidadesActivity.this, MenuActivity.class);
                    startActivity(i);
                }else {
                    app.setUsuarioEnSesion(null);
                    Intent i = new Intent(ListaUnidadesActivity.this, MenuActivity.class);
                    startActivity(i);
                }
                break;
            }
        }
    }

    public void cargarListaUnidad(){
        List<UnidadBean> unidades = new ArrayList<>();
        unidades = listaVerificacionService.getUnidadBeanList();

        if(null != unidades){
            cardViewAdapter.setList(unidades);
            cardViewAdapter.notifyDataSetChanged();
        }else {
            mostrarMensaje("No existen unidades para mostrar...!");
        }
    }

    public void descargarListaUnidad(String userLogin, String password, String user, String scUserId, String ipOrDominioServidor){
        new ListaUnidadesActivity.AsyncTraerUnidades().execute(userLogin, password, user, scUserId, ipOrDominioServidor);
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable() && conMgr
                .getActiveNetworkInfo().isConnected());
    }

    public void mostrarMensaje(String mensaje){
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    private class AsyncTraerUnidades extends AsyncTask<String, Void, List<UnidadBean>> {
        private ProgressDialog dialog ;

        public AsyncTraerUnidades(){
            dialog = new ProgressDialog(ListaUnidadesActivity.this);
        }

        @Override
        protected void onPostExecute(List<UnidadBean> unidadList) {
            dialog.dismiss();

            if(0 != unidadList.size()){
                List<UnidadBean> unidades = listaVerificacionService.getUnidadBeanList();
                if(null != unidades){
                    int isClearUnidades = listaVerificacionService.borrarUnidades();
                    if(-1 != isClearUnidades){
                        int isSaveListUnid = listaVerificacionService.guardarListaUnidades(unidadList);
                        cargarListaUnidad();
                    }else{
                        mostrarMensaje("Error al guardar las unidades...!");
                    }
                }else {
                    int isSaveListUnid = listaVerificacionService.guardarListaUnidades(unidadList);
                    cargarListaUnidad();
                }

            }else{
                app.setUsuarioEnSesion(null);
                mostrarMensaje("No existen unidades para mostrar...!");
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Cargando unidades...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected List<UnidadBean> doInBackground(String... params) {
            List<UnidadBean> unidadLista = new ArrayList<>();
            try {
                final String URL_REST = params[4]+getResources().getString(R.string.SERVICIO_FB_UEA);
                URL url=new URL(URL_REST);

                HttpClient client = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(URL_REST);

                post.setHeader("userLogin", params[0]);
                post.setHeader("userPassword", params[1]);
                post.setHeader("systemRoot", "safe2biz");

                List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
                urlParameters.add(new BasicNameValuePair("sc_user_id", params[3]));

                post.setEntity(new UrlEncodedFormEntity(urlParameters));

                HttpResponse response = client.execute(post);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                JsonReader reader = new JsonReader(rd);
                reader.beginObject();
                UnidadBean unidadBean = new UnidadBean();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("data")) {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            unidadBean = new UnidadBean();
                            while (reader.hasNext()) {
                                name = reader.nextName();
                                if(name.equals("fb_uea_pe_id")){
                                    unidadBean.setFb_uea_pe_id(reader.nextLong());
                                }else if(name.equals("codigo")){
                                    unidadBean.setCodigo(reader.nextString());
                                }else if(name.equals("nombre")){
                                    unidadBean.setNombre(reader.nextString());
                                }else if(name.equals("sc_user_id")){
                                    unidadBean.setScUserId(reader.nextLong());
                                }else {
                                    reader.skipValue();
                                }
                            }
                            unidadLista.add(unidadBean);
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                reader.close();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return unidadLista;
        }
    }

}
