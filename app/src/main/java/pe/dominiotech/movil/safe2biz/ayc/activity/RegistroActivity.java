package pe.dominiotech.movil.safe2biz.ayc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ayc.adapter.RegistroAdapter;
import pe.dominiotech.movil.safe2biz.ayc.dao.RegistroDao;
import pe.dominiotech.movil.safe2biz.ayc.model.Registro;
import pe.dominiotech.movil.safe2biz.base.model.MenuPrincipalItem;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RegistroActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout statusBar;
    private MainApplication app;
    private MenuPrincipalItem menuPrincipalItem;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private RegistroAdapter cardViewAdapter;
    int onStartCount = 0;
    RegistroDao registroDao = new RegistroDao(this, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);

        onStartCount = 1;
        if (savedInstanceState == null){
            this.overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
        } else {
            onStartCount = 2;
        }

        setContentView(R.layout.ayc_list);
        menuPrincipalItem = (MenuPrincipalItem)getIntent().getExtras().getSerializable("menuPrincipalItem");
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        app = (MainApplication) getApplication();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Actos y Condiciones");
            actionBar.setHomeButtonEnabled(true);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.ayc_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList menuList = new ArrayList<>();
        cardViewAdapter = new RegistroAdapter(menuList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMenu(view);
            }
        },getApplicationContext());
        mRecyclerView.setAdapter(cardViewAdapter);
        FloatingActionButton btnAgregar = (FloatingActionButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro RegistroModel = new Registro();
                Intent i = new Intent(getApplicationContext(), RegistroDetalleActivity.class);
                i.putExtra("Registro", RegistroModel);
                startActivity(i);
            }
        });

        cargarListaRegistrosAyC();
    }

    public void setupTheme() {
        setTheme(R.style.MyMaterialTheme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        app.getUsuarioEnSesion();
        return true;
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
        Registro RegistroModel = ((RegistroAdapter.ViewHolder)item.getTag()).getRegistroModel();
        switch(item.getId()){
            case R.id.lnlyAycRegistro : {
                Intent i = new Intent(getApplicationContext(), RegistroDetalleActivity.class);
                i.putExtra("Registro", RegistroModel);
                i.putExtra("menuPrincipalItem", menuPrincipalItem);
                startActivity(i);
                break;
            }
        }
    }
    @Override
    public void onRestart() {
        super.onRestart();
        cargarListaRegistrosAyC();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }

    public void cargarListaRegistrosAyC(){
        List<Registro> registroList = registroDao.getRegistroList();
        if (registroList != null) {
            cardViewAdapter.setList(registroList);
        }

    }

}
