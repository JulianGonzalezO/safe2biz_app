package pe.dominiotech.movil.safe2biz.sac.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.sac.adapter.AccionCorrectivaAdapter;
import pe.dominiotech.movil.safe2biz.sac.dao.AccionCorrectivaDao;
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectiva;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AccionCorrectivaActivity extends AppCompatActivity {

    Toolbar toolbar;
    private MainApplication app;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private AccionCorrectivaAdapter cardViewAdapter;
    int onStartCount = 0;
    private List<AccionCorrectiva> accionCorrectivaList = new ArrayList<>();
    AccionCorrectivaDao accionCorrectivaDao = new AccionCorrectivaDao(this, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);
        app = (MainApplication)getApplication();
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        setContentView(R.layout.sac_activity);
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Acciones Correctivas");
            actionBar.setHomeButtonEnabled(true);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.sacRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        cardViewAdapter = new AccionCorrectivaAdapter(accionCorrectivaList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMenu(view);
            }
        },getApplicationContext());
        mRecyclerView.setAdapter(cardViewAdapter);

        cargarListaSac();
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
    public void onRestart() {
        super.onRestart();
        cargarListaSac();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClickMenu(View item) {
        AccionCorrectiva accionCorrectiva = ((AccionCorrectivaAdapter.ViewHolder)item.getTag()).getCardViewModelSac();

        switch(item.getId()){
            case R.id.card_view : {
                final Intent i = new Intent(getApplicationContext(), AccionCorrectivaDetalleActivity.class);
                i.putExtra("accionCorrectiva", accionCorrectiva);
                startActivity(i);
                break;
            }
        }
    }

    public void cargarListaSac(){
        if ((accionCorrectivaList = accionCorrectivaDao.getSacBeanList()) != null) {
            cardViewAdapter.setList(accionCorrectivaList);
        }else{
            Toast.makeText(app, "No hay registro para mostrar", Toast.LENGTH_SHORT).show();
        }
    }



}
