package pe.dominiotech.movil.safe2biz.ops.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.base.model.MenuPrincipalItem;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.adapter.ListaVerificacionAdapter;
import pe.dominiotech.movil.safe2biz.service.ListaVerificacionService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ListaVerificacionActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout statusBar;
    private MainApplication app;
    private MenuPrincipalItem menuPrincipalItem;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private ListaVerificacionAdapter cardViewAdapter;
    int onStartCount = 0;
    private ListaVerificacionService listaVerificacionService;

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
        menuPrincipalItem = (MenuPrincipalItem)getIntent().getExtras().getSerializable("menuPrincipalItem");
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        app = (MainApplication) getApplication();
        listaVerificacionService = app.getListaVerificacionService();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(menuPrincipalItem.getTitulo());
            actionBar.setHomeButtonEnabled(true);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.listaVerificacionRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList menuList = new ArrayList<>();
        cardViewAdapter = new ListaVerificacionAdapter(menuList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMenu(view);
            }
        },getApplicationContext());
        mRecyclerView.setAdapter(cardViewAdapter);

        cargarListaVerificacion();
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
        ListaVerificacion listaVerificacion = ((ListaVerificacionAdapter.ViewHolder)item.getTag()).getCardViewModelEncuestaAutorizada();
        switch(item.getId()){
            case R.id.lnlyCarViewEncuestaAutorizada : {
                Intent i = new Intent(ListaVerificacionActivity.this, RegistroGeneralesActivity.class);
                i.putExtra("ListaVerificacion", listaVerificacion);
                startActivity(i);
                break;
            }
        }
    }

    public void cargarListaVerificacion(){
        List<ListaVerificacion> listaVerificacionList = new ArrayList<>();
        listaVerificacionList = listaVerificacionService.getListaVerificacionList();
        if(null != listaVerificacionList){
            cardViewAdapter.setList(listaVerificacionList);
            cardViewAdapter.notifyDataSetChanged();
        }else {
            mostrarMensaje("No existen registros para mostrar!");
        }
    }

    public void mostrarMensaje(String mensaje){
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

}
