package pe.dominiotech.movil.safe2biz.ops.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ops.adapter.RegistroGeneralesAdapter;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;
import pe.dominiotech.movil.safe2biz.service.ListaVerificacionService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RegistroGeneralesActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ListaVerificacion listaVerificacionSeleccionada;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private RegistroGeneralesAdapter cardViewAdapter;
    int onStartCount = 0;
    List<RegistroGenerales> menuList = new ArrayList<>();
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
        if (savedInstanceState == null) {
            this.overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
        } else {
            onStartCount = 2;
        }

        setContentView(R.layout.registro_generales_activity);
        listaVerificacionSeleccionada = (ListaVerificacion)getIntent().getExtras().getSerializable("ListaVerificacion");
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        MainApplication app = (MainApplication) getApplication();
        listaVerificacionService = app.getListaVerificacionService();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(listaVerificacionSeleccionada.getNombre());
            actionBar.setHomeButtonEnabled(true);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.listaVerificacionRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        cardViewAdapter = new RegistroGeneralesAdapter(menuList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMenu(view);
            }
        },getApplicationContext());
        mRecyclerView.setAdapter(cardViewAdapter);

        cargarListaVerificacion(listaVerificacionSeleccionada.getOps_lista_verificacion_id());
        ButterKnife.bind(this);
    }

    public void setupTheme() {
        setTheme(R.style.MyMaterialTheme);
    }


    @OnClick(R.id.btnAgregar)
    void agregar(){
        RegistroGenerales registroGenerales = new RegistroGenerales();
        Intent i = new Intent(getApplicationContext(), RegistroGeneralesFormActivity.class);
        i.putExtra("ListaVerificacion", listaVerificacionSeleccionada);
        i.putExtra("registroGenerales",registroGenerales);
        startActivity(i);
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

    public void onItemClickMenu(View item) {
        RegistroGenerales registroGeneralSeleccionado = ((RegistroGeneralesAdapter.ViewHolder)item.getTag()).getRegistroGenerales();
        switch(item.getId()){
            case R.id.lnlyCarViewCheckList : {
                Intent i = new Intent(getApplicationContext(), RegistroGeneralesFormActivity.class);
                i.putExtra("ListaVerificacion", listaVerificacionSeleccionada);
                i.putExtra("registroGenerales",registroGeneralSeleccionado);
                startActivity(i);
                break;
            }
        }
    }

    public void cargarListaVerificacion(Long codEncuestaAutorizada){
        List<RegistroGenerales> listaVerificacionList = listaVerificacionService.getCheckListCabeceraList(codEncuestaAutorizada+"");

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

    @Override
    public void onRestart() {
        super.onRestart();
        cargarListaVerificacion(listaVerificacionSeleccionada.getOps_lista_verificacion_id());
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }
}
