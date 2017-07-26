package pe.dominiotech.movil.safe2biz.base.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ayc.activity.RegistroActivity;
import pe.dominiotech.movil.safe2biz.ayc.activity.RegistroDetalleActivity;
import pe.dominiotech.movil.safe2biz.base.adapter.MenuPrincipalAdapter;
import pe.dominiotech.movil.safe2biz.base.model.MenuPrincipalItem;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;
import pe.dominiotech.movil.safe2biz.ops.activity.ListaVerificacionActivity;
import pe.dominiotech.movil.safe2biz.sac.activity.AccionCorrectivaActivity;
import pe.dominiotech.movil.safe2biz.utils.CircleTransform;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MenuActivity extends AppCompatActivity  {
    private MainApplication app;
    ImageView imageViewPictureMain, imageViewCoverMain;
    TextView tvNombreLogueo;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    List<MenuPrincipalItem> menuList = new ArrayList<>();
    MenuPrincipalAdapter menuPrincipalAdapter;

    @Bind(R.id.sincronizacion) LinearLayout lnlSincronizacion;
    @Bind(R.id.sinc_arrow) ImageView sinc_arrow;
    @Bind(R.id.descarga) LinearLayout lnlDescarga;
    @Bind(R.id.envio) LinearLayout lnlEnvio;
    @Bind(R.id.cambiar_sede) LinearLayout lnlCambiarSede;
    @Bind(R.id.cerrar_sesion) LinearLayout lnlCerrarSesion;
    @Bind(R.id.menu_principal) RecyclerView menu_principal;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);

/*        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {             @Override             public void uncaughtException(Thread paramThread, Throwable paramThrowable) {                 app.setUsuarioEnSesion(null);                 Intent intent = new Intent(getApplicationContext(), LoginActivity.class);                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                 startActivity(intent);                 System.exit(2);             }         });*/

        setContentView(R.layout.menu_activity);
        app = (MainApplication)getApplication();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        setupNavigationDrawer();
        ButterKnife.bind(this);
        crearMenuPrincipal();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        menuPrincipalAdapter = new MenuPrincipalAdapter(menuList, new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MenuPrincipalItem menuPrincipalItem = (MenuPrincipalItem) view.getTag();
                if(1 == menuPrincipalItem.getOrden()){
                    Intent i = new Intent(getApplicationContext(), ListaVerificacionActivity.class);
                    i.putExtra("menuPrincipalItem", menuPrincipalItem);
                    startActivity(i);
                }else if (2 == menuPrincipalItem.getOrden()){
                    Intent i = new Intent(getApplicationContext(), RegistroActivity.class);
                    i.putExtra("menuPrincipalItem", menuPrincipalItem);
                    startActivity(i);
                }else if (3 == menuPrincipalItem.getOrden()){
                    Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                    i.putExtra("menuPrincipalItem", menuPrincipalItem);
                    startActivity(i);
                }else if (4 == menuPrincipalItem.getOrden()){
                    Intent i = new Intent(getApplicationContext(), RegistroDetalleActivity.class);
                    i.putExtra("menuPrincipalItem", menuPrincipalItem);
                    startActivity(i);
                }else if (5 == menuPrincipalItem.getOrden()){
                    Intent i = new Intent(getApplicationContext(), MapaActivity.class);
                    i.putExtra("menuPrincipalItem", menuPrincipalItem);
                    startActivity(i);
                }else if (6 == menuPrincipalItem.getOrden()){
                    Intent i = new Intent(getApplicationContext(), AccionCorrectivaActivity.class);
                    i.putExtra("menuPrincipalItem", menuPrincipalItem);
                    startActivity(i);
                }
            }
        });
        menu_principal.setHasFixedSize(true);
        menu_principal.setLayoutManager(mLayoutManager);
        menu_principal.setAdapter(menuPrincipalAdapter);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Menú");
            actionBar.setHomeButtonEnabled(true);
        }
        inicializar();
        app.setActionBarPrincipal(actionBar);
    }

    protected void inicializar(){
        lnlSincronizacion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnlDescarga.getVisibility()==View.GONE){
                    lnlDescarga.setVisibility(View.VISIBLE);
                    lnlEnvio.setVisibility(View.VISIBLE);
                    sinc_arrow.setImageResource(R.drawable.ic_arrow_up_black_24dp);
                }else{
                    lnlDescarga.setVisibility(View.GONE);
                    lnlEnvio.setVisibility(View.GONE);
                    sinc_arrow.setImageResource(R.drawable.ic_arrow_down_black_24dp);
                }
            }
        });
        lnlDescarga.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DescargarActivity.class);
                startActivity(intent);
            }
        });

        lnlEnvio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CargarActivity.class);
                startActivity(intent);
            }
        });

        lnlCambiarSede.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.super.onBackPressed();
            }
        });

        lnlCerrarSesion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle(R.string.confirmar);
                builder.setMessage(R.string.cerrarsesion2);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        app.setUsuarioEnSesion(null);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    protected UsuarioBean getUsuarioEnSesion(){
        return  app.getUsuarioEnSesion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void setupTheme() {
        setTheme(R.style.MyMaterialTheme);
    }

    public void setupNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.guion, R.string.guion);
        drawerLayout.setDrawerListener(drawerToggle);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        drawerToggle.syncState();

        imageViewPictureMain = (ImageView) findViewById(R.id.imageViewPictureMain);
        imageViewCoverMain = (ImageView) findViewById(R.id.imageViewCover);
        tvNombreLogueo = (TextView) findViewById(R.id.textViewName);
        tvNombreLogueo.setText(getUsuarioEnSesion().getNombre_empleado());

        Picasso.with(getApplicationContext()).load(R.mipmap.ic_launcher).transform(new CircleTransform()).into(imageViewPictureMain);
        Picasso.with(getApplicationContext()).load(R.drawable.img_cover_main).into(imageViewCoverMain);

        TypedValue typedValue = new TypedValue();
        MenuActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        final int color = typedValue.data;
        drawerLayout.setStatusBarBackgroundColor(color);

    }

    private void crearMenuPrincipal(){
        MenuPrincipalItem menuPrincipalItem = new MenuPrincipalItem();

        menuPrincipalItem.setOrden(1);
        menuPrincipalItem.setTitulo("Observaciones Preventivas");
        menuPrincipalItem.setIcono(R.drawable.ic_encuestas);
        menuList.add(menuPrincipalItem);

        menuPrincipalItem = new MenuPrincipalItem();
        menuPrincipalItem.setOrden(2);
        menuPrincipalItem.setTitulo("Actos y Condiciones");
        menuPrincipalItem.setIcono(R.drawable.ic_gestion_a_y_c);
        menuList.add(menuPrincipalItem);

        menuPrincipalItem = new MenuPrincipalItem();
        menuPrincipalItem.setOrden(6);
        menuPrincipalItem.setTitulo("Acciones Correctivas");
        menuPrincipalItem.setIcono(R.drawable.ic_encuestas);
        menuList.add(menuPrincipalItem);

        menuPrincipalItem = new MenuPrincipalItem();
        menuPrincipalItem.setOrden(5);
        menuPrincipalItem.setTitulo("Mapa");
        menuPrincipalItem.setIcono(R.drawable.ic_mapa_03);
        menuList.add(menuPrincipalItem);

        menuPrincipalItem = new MenuPrincipalItem();
        menuPrincipalItem.setOrden(3);
        menuPrincipalItem.setTitulo("Tablero Gerencial");
        menuPrincipalItem.setIcono(R.drawable.ic_reportes_graficos);
        menuList.add(menuPrincipalItem);

    }
}
