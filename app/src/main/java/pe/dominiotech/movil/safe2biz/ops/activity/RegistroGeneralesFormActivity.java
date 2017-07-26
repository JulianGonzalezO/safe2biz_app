package pe.dominiotech.movil.safe2biz.ops.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ops.fragment.OpsDetalleCuestionario;
import pe.dominiotech.movil.safe2biz.ops.fragment.OpsDetalleDatos;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RegistroGeneralesFormActivity extends AppCompatActivity implements OpsDetalleDatos.habilitar_tabs {

    Toolbar toolbar;
    private MainApplication app;
    private ListaVerificacion listaVerificacionSeleccionada;
    int onStartCount = 0;
    private TabLayout tabLayout;

    private RegistroGenerales registroGenerales;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);

        app = (MainApplication) getApplication();

/*        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {             @Override             public void uncaughtException(Thread paramThread, Throwable paramThrowable) {                 app.setUsuarioEnSesion(null);                 Intent intent = new Intent(getApplicationContext(), LoginActivity.class);                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                 startActivity(intent);                 System.exit(2);             }         });*/

        onStartCount = 1;
        if (savedInstanceState == null) {
            this.overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
        } else {
            onStartCount = 2;
        }

        setContentView(R.layout.nuevo_checklist_activity);
        listaVerificacionSeleccionada = (ListaVerificacion)getIntent().getExtras().getSerializable("ListaVerificacion");
        registroGenerales = (RegistroGenerales) getIntent().getExtras().getSerializable("registroGenerales");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.Tabs);
        tabLayout.setupWithViewPager(viewPager);
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        if (registroGenerales.getOps_registro_generales_id() == 0) {
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(1).setBackgroundColor(getResources().getColor(R.color.tw__light_gray));
                tabStrip.getChildAt(i).setEnabled(false);
            }
        }
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(listaVerificacionSeleccionada.getNombre());
            actionBar.setHomeButtonEnabled(true);
        }

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
            this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment Detalle = new OpsDetalleDatos();
        Fragment Cuestionario = new OpsDetalleCuestionario();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listaVerificacionSeleccionada",listaVerificacionSeleccionada);
        bundle.putSerializable("registroGenerales",registroGenerales);
        Detalle.setArguments(bundle);
        Cuestionario.setArguments(bundle);
        adapter.addFragment(Detalle, "Datos Generales");
        adapter.addFragment(Cuestionario, "Cuestionario");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void habilitar_tabs(boolean bool) {
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(1).setBackgroundColor(getResources().getColor(R.color.safe2biz_colorprimary));
            tabStrip.getChildAt(i).setEnabled(bool);
        }

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
