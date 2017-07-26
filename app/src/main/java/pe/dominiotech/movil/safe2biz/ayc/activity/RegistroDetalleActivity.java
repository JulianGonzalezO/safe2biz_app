package pe.dominiotech.movil.safe2biz.ayc.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ayc.dao.RegistroDao;
import pe.dominiotech.movil.safe2biz.ayc.dao.RegistroEvidenciaDao;
import pe.dominiotech.movil.safe2biz.ayc.model.Origen;
import pe.dominiotech.movil.safe2biz.ayc.model.Registro;
import pe.dominiotech.movil.safe2biz.ayc.model.RegistroEvidencia;
import pe.dominiotech.movil.safe2biz.ayc.model.TipoRiesgo;
import pe.dominiotech.movil.safe2biz.base.adapter.AdaptadorSpinner;
import pe.dominiotech.movil.safe2biz.base.adapter.ImagenViewAdapter;
import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.DatePicker;
import pe.dominiotech.movil.safe2biz.utils.GPSTracker;
import pe.dominiotech.movil.safe2biz.utils.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RegistroDetalleActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final String rutaImagenes = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ImagenesAyc/";
    private final int SELECT_PICTURE = 300;
    int onStartCount = 0;
    Uri outputFileUri = null;
    List<Object> listaAreaOcurrencia = new ArrayList<>();
    List<Object> listaEmpresaOcurrencia = new ArrayList<>();
    List<Object> listaTipoRiesgo = new ArrayList<>();
    /** Bindeo de todos los elementos del layout **/
    @Bind(R.id.app_bar) Toolbar toolbar;
    @Bind(R.id.rGroup) RadioGroup radioGroup;
    @Bind(R.id.rdBtnActo) RadioButton rdBtnActo;
    @Bind(R.id.rdBtnCondicion) RadioButton rdBtnCondicion;
    @Bind(R.id.Fecha) EditText edFecha;
    @Bind(R.id.Hora) EditText edHora;
    @Bind(R.id.Descripcion) EditText edDescripcion;
    @Bind(R.id.Accion) EditText edAccion;
    @Bind(R.id.Area) MaterialBetterSpinner spnArea;
    @Bind(R.id.TipoRiesgo) MaterialBetterSpinner spnTipoRiesgo;
    @Bind(R.id.Empresa) AutoCompleteTextView autEmpresa;
    @Bind(R.id.imagegallery) RecyclerView visorImagenes;
    @Bind(R.id.mapDetalle1) MapView mapView;
    @Bind(R.id.btnGuardar) Button btnGuardar;
    private MainApplication app;
    private ListaVerificacionDao listaVerificacionDao;
    private RegistroDao registroDao;
    private ImagenViewAdapter imagenViewAdapter;
    private List<Object> listEvidencias = new ArrayList<>();
    private GoogleMap map;
    private LocationManager locationManager;
    private String rutaImagen;
    private String codigoImagen;
    private Calendar calendar;
    private Registro registroModel;
    private ProgressDialog Dialog1;
    private int count = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);
        app = (MainApplication) getApplication();
        setContentView(R.layout.ayc_registro_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        registroModel = (Registro)getIntent().getExtras().getSerializable("Registro");
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (registroModel.getOrigen_ayc() != null){
                actionBar.setTitle("Actos y Condiciones");
            }else{
                actionBar.setTitle("Nuevo Registro");
            }
            actionBar.setHomeButtonEnabled(true);
        }


        onStartCount = 1;
        if (savedInstanceState == null){
            this.overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
        } else {
            onStartCount = 2;
        }

        listaVerificacionDao = new ListaVerificacionDao(this, AppConstants.DB_NAME, null,AppConstants.DB_VERSION);
        registroDao = new RegistroDao(this, AppConstants.DB_NAME, null,AppConstants.DB_VERSION);

        autEmpresa.setThreshold(1); //Start searching from 1 character

        inicializarComponentes(savedInstanceState);
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        visorImagenes.setHasFixedSize(true);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                onCheckFields();

            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        visorImagenes.setLayoutManager(layoutManager);
        imagenViewAdapter = new ImagenViewAdapter(getApplicationContext(), listEvidencias, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMenu(view);
            }
        });
        visorImagenes.setAdapter(imagenViewAdapter);

        String[] fechaYhora = Util.obtenerFechayHoraActual().split("-");
        edFecha.setText(fechaYhora[0]);
        edHora.setText(fechaYhora[1]);
        //Crea el directorio para las imagenes
        File newdir = new File(rutaImagenes);
        newdir.mkdirs();

        cargarImagenes();
    }

    /**Click Listeners**/
    @OnClick(R.id.btnCamara)
    public void camara() {
        File mi_foto = new File(getRutaImagen());
        try {
            mi_foto.createNewFile();
        } catch (IOException ex) {
            Log.e("ERROR ", "Error:" + ex);
        }
        outputFileUri = Uri.fromFile( mi_foto );

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @OnClick(R.id.btnGaleria)
    public void galeria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
    }

    @OnClick(R.id.btnGuardar)
    public void guardar(){
        if(guardarRegistro()){
            Toast toast = Toast.makeText(getApplicationContext(),"Se guardó correctamente",Toast.LENGTH_SHORT);
            toast.show();
            onBackPressed();
            this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Hay campos sin llenar",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @OnClick(R.id.btnFecha)
    public void cargarDatePicker() {
        int anho = obtenerAnho();
        int mes = obtenerMes();
        int dia = obtenerDia();
        DialogFragment dialogFragment = DatePicker.nuevaInstancia(anho, mes, dia,edFecha,Calendar.getInstance().getTimeInMillis(),null);
        dialogFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    @OnClick(R.id.btnHora)
    public void cargarTimePicker() {
        int h = obtenerHora();
        int min = obtenerMinuto();
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String hourText = hourOfDay+"";
                        String minText = minute+"";
                        if(hourText.length() == 1){
                            hourText = "0"+hourText;
                        }
                        if(minText.length() == 1){
                            minText = "0"+minText;
                        }
                        edHora.setText(hourText + ":" + minText);
                    }
                }, h, min, false);
        tpd.setTitle("Seleccionar Hora Inicio");
        tpd.show();
    }

    @OnClick({R.id.rdBtnActo, R.id.rdBtnCondicion})
    void onCheckedChanged(){
        onCheckFields();
    }

    /** Text Watcher **/
    @OnTextChanged(value = { R.id.Area, R.id.Empresa, R.id.Descripcion, R.id.TipoRiesgo, R.id.Accion },
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void textChange() {
        onCheckFields();
    }

    /** Metodos para el manejo de Imagenes **/
    public String getRutaImagen() {
        rutaImagen = rutaImagenes + getCodigoImagen();
        return rutaImagen;
    }

    private String getCodigoImagen()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", Locale.US);
        String date = dateFormat.format(new Date());
        codigoImagen = "pic_" + date + ".jpg";
        return codigoImagen;
    }

    public String getImagenPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source;
        FileChannel destination;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        destination.close();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            onImageTaken();
        }else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            File destFile = new File(getRutaImagen());
            try {
                copyFile(new File(getImagenPath(data.getData())), destFile);
                onImageTaken();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if ( resultCode == RESULT_CANCELED) {
            Toast.makeText(this, " Imagen no fue tomada... ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " Imagen no fue tomada... ", Toast.LENGTH_SHORT).show();
        }

    }

    public void onImageTaken(){
        visorImagenes.setVisibility(View.VISIBLE);
        RegistroEvidencia img = new RegistroEvidencia();
        img.setRuta(rutaImagen);
        img.setNombre(codigoImagen);
        img.setAyc_registro_id(registroModel.getAyc_registro_id());
        listEvidencias.add(img);
        imagenViewAdapter.setList(listEvidencias);
        onCheckFields();
    }

    /** Método para verificar campos obligatorios **/
    private void onCheckFields() {
        if ( !spnArea.getText().toString().equals("") &&
                !autEmpresa.getText().toString().equals("") &&
                !edDescripcion.getText().toString().equals("") &&
                !spnTipoRiesgo.getText().toString().equals("") &&
                !edAccion.getText().toString().equals("") &&
                listEvidencias.size()>0 &&
                radioGroup.getCheckedRadioButtonId() != -1
                ){
            boolean flag = false;
            for (Object empresa : listaEmpresaOcurrencia){
                if (((EmpresaEspecializada)empresa).getRazon_social().equals(autEmpresa.getText().toString())){
                    btnGuardar.setEnabled(true);
                    flag = true;
                    break;
                }
            }
            if (!flag ){
                btnGuardar.setEnabled(false);
            }
        }else{
            btnGuardar.setEnabled(false);
        }
    }

    private void inicializarComponentes(Bundle savedInstanceState) {

        if (registroModel != null ){
            if (registroModel.getOrigen_ayc() != null){
                if (registroModel.getOrigen_ayc().getName().equals("Acto")){
                    rdBtnActo.setChecked(true);
                }else {
                    rdBtnCondicion.setChecked(true);
                }
            }
            if (registroModel.getArea() != null){
                spnArea.setText(registroModel.getArea().getNombre());
            }
            if (registroModel.getEmpresa_especializada()!=null){
                autEmpresa.setText(registroModel.getEmpresa_especializada().getRazon_social());
            }
            if (registroModel.getFecha() != null){
                edFecha.setText(registroModel.getFecha());
            }
            if (registroModel.getHora() != null){
                edHora.setText(registroModel.getHora());
            }
            if (registroModel.getDescripcion() != null){
                edDescripcion.setText(registroModel.getDescripcion());
            }
            if (registroModel.getTipo_riesgo_ayc() != null){
                spnTipoRiesgo.setText(registroModel.getTipo_riesgo_ayc().getName());
            }
            if (registroModel.getAccion_inmediata() != null){
                edAccion.setText(registroModel.getAccion_inmediata());
            }

        }
        cargarCombos();
        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        map = mapView.getMap();
        map.setMyLocationEnabled(false);
//        map.getUiSettings().setAllGesturesEnabled(false);
        mapView.setClickable(false);
        mapView.setFocusable(false);

        try {
            MapsInitializer.initialize(RegistroDetalleActivity.this);
        } catch ( Exception e) {
            e.printStackTrace();
        }
        map.setOnMapLoadedCallback(this);
        onMapReady(map);
        cargarMapa();
    }


    public void onItemClickMenu(View item) {
        final RegistroEvidencia Lista = (RegistroEvidencia) ((ImagenViewAdapter.ViewHolder)item.getTag()).getImagenModel();

        switch(item.getId()){
            case R.id.close : {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("ELIMINAR IMAGEN ");
                dialog.setMessage("¿Desea ELIMINAR la imagen?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarImagen(Lista);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
            }
        }
    }

    private void eliminarImagen(RegistroEvidencia imagenEliminada) {
        RegistroEvidenciaDao evidenciaDao = new RegistroEvidenciaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

        listEvidencias.remove(imagenEliminada);
        if (listEvidencias.size()==0){
            btnGuardar.setEnabled(false);
            visorImagenes.setVisibility(View.GONE);
        }
        imagenViewAdapter.setList(listEvidencias);
        imagenViewAdapter.notifyDataSetChanged();
        try {
            if (imagenEliminada != null){
                evidenciaDao.deleteById(RegistroEvidencia.class, imagenEliminada.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(getApplicationContext(),"Imagen eliminada correctamente",Toast.LENGTH_SHORT);
        toast.show();

    }

    private void cargarImagenes() {
        RegistroEvidenciaDao evidenciaDao = new RegistroEvidenciaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
        RegistroEvidencia evidenciaBean = new RegistroEvidencia();
        evidenciaBean.setAyc_registro_id(registroModel.getAyc_registro_id());
        if (evidenciaDao.getEvidenciaAyCBeanList(evidenciaBean) != null){
            listEvidencias = new ArrayList<Object>(evidenciaDao.getEvidenciaAyCBeanList(evidenciaBean));
            if(listEvidencias.size() > 0){
                visorImagenes.setVisibility(View.VISIBLE);
                btnGuardar.setEnabled(true);
                imagenViewAdapter.setList(listEvidencias);
            }
        }

    }

    private void cargarMapa() {
        activarGps();
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Map<String, Object> hasMapUbi = notificar();
            registroModel.setLatitud(hasMapUbi.get("latitud").toString());
            registroModel.setLongitud(hasMapUbi.get("longitud").toString());
            showMarkers(hasMapUbi);
        }
    }
    private void showMarkers(Map<String,Object> hasMapUbi) {
        double latitud = (double)hasMapUbi.get("latitud");
        double longitud = (double)hasMapUbi.get("longitud");
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setIndoorLevelPickerEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);


        final LatLng markeLugares = new LatLng(latitud,longitud);
        Marker showMarker = map.addMarker(new MarkerOptions()
                .title("Ubicación Actual")
                .position(markeLugares)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_google_marker)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(markeLugares, 17));
        showMarker.showInfoWindow();
    }
    private void activarGps() {
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("ADVERTENCIA");
            dialog.setMessage("Ubicación (GPS) desactivada, para usar ésta aplicación necesita activar la (Ubicación de Google)... ¿Desea Activarla Ahora?");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }

    private Map<String,Object> notificar() {
        Map<String,Object> hasMapUbicacion = new HashMap<>();
        GPSTracker mGPS = new GPSTracker(this);
        if (mGPS.canGetLocation()) {
            double mLat = mGPS.getLatitude();
            double mLong = mGPS.getLongitude();
            hasMapUbicacion.put("latitud", mLat);
            hasMapUbicacion.put("longitud", mLong);
        }else{
            hasMapUbicacion.put("error", 0);
        }

        return hasMapUbicacion;
    }

    public void setupTheme() {
        setTheme(R.style.MyMaterialTheme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        app.getUsuarioEnSesion();
        getMenuInflater().inflate(R.menu.menu_sac_detalle, menu);
        return true;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        map.setMyLocationEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnSubir){
            String URL_EXT = app.getUsuarioEnSesion().getIpOrDominioServidor();

            if (listEvidencias.size() > 0 && registroModel.getAyc_registro_id() > 0){

                Map <String,String> headers = new HashMap<>();
                headers.put("userLogin",app.getUsuarioEnSesion().getUser_login());
                headers.put("userPassword",app.getUsuarioEnSesion().getPassword());
                headers.put("systemRoot","safe2biz");

                Map <String,String> parameters = new HashMap<>();
                parameters.put("sc_user_id",app.getUsuarioEnSesion().getSc_user_id().toString());
                parameters.put("origen_ayc",registroModel.getOrigen_ayc().getCode());
                parameters.put("fb_area_id", registroModel.getArea().getFb_area_id().toString());
                parameters.put("fb_empresa_especializada_id", registroModel.getEmpresa_especializada().getFb_empresa_especializada_id().toString());
                parameters.put("fecha", registroModel.getFecha());
                parameters.put("hora", registroModel.getHora());
                parameters.put("descripcion", registroModel.getDescripcion());
                parameters.put("tipo_riesgo_ayc",registroModel.getTipo_riesgo_ayc().getCode());
                parameters.put("accion_inmediata", registroModel.getAccion_inmediata());
                parameters.put("latitud", registroModel.getLatitud());
                parameters.put("longitud", registroModel.getLongitud());
                parameters.put("fb_uea_pe_id", registroModel.getUea_pe());
                parameters.put("fb_empleado_id", registroModel.getEmpleado());
                parameters.put("ayc_registro_id", Integer.toString(registroModel.getAyc_registro_id_server()));

                Dialog1 = new ProgressDialog(this);
                Dialog1.setMessage("Enviando datos al servidor...");
                Dialog1.show();

                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_ayc_registro_upload")
                        .addBodyParameter(parameters)
                        .addHeaders(headers)
                        .setTag("ayc")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject object = data.getJSONObject(i);
                                        registroModel.setAyc_registro_id_server(object.getInt("id_generado"));
                                        registroDao.createOrUpdateGeneric(registroModel);
                                        uploadEvidencias(object.getLong("id_generado"));
                                    }
                                } catch (JSONException  | SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(ANError error) {
                                Dialog1.hide();
                                Dialog1.dismiss();
                                Log.e("Error",error.toString());
                                Toast.makeText(app, "No se completó la sincronización", Toast.LENGTH_SHORT).show();
                            }
                        });

//

            }else{
                Toast.makeText(app, "No se pudo sincronizar, no hay imágenes guardadas para esta acción correctiva. ", Toast.LENGTH_SHORT).show();
            }


            return true;
        }else if (id == android.R.id.home){
            onBackPressed();
            this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void uploadEvidencias(Long identity){
        String URL_EXT = app.getUsuarioEnSesion().getIpOrDominioServidor();

            Map <String,String> headers = new HashMap<>();
            headers.put("userLogin",app.getUsuarioEnSesion().getUser_login());
            headers.put("userPassword",app.getUsuarioEnSesion().getPassword());
            headers.put("systemRoot","safe2biz");

            for (int i = 0; i < listEvidencias.size(); i++){
                    Bitmap bitmap = BitmapFactory.decodeFile(((RegistroEvidencia) listEvidencias.get(i)).getRuta());
                    String imageStream = Util.encodeToBase64(Util.resize(bitmap,800,800), Bitmap.CompressFormat.JPEG, 100);

                    Map <String,String> parameters1 = new HashMap<>();
                    parameters1.put("ayc_registro_id",identity.toString());
                    parameters1.put("imagen", ((RegistroEvidencia) listEvidencias.get(i)).getNombre()+";"+imageStream);
                    parameters1.put("user_id", app.getUsuarioEnSesion().getSc_user_id().toString());
                    Log.d("ruta",imageStream);

                    AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_ayc_registro_insert_evidencia")
                            .addBodyParameter(parameters1)
                            .addHeaders(headers)
                            .setTag("tag"+i)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    addCount();
                                }
                                @Override
                                public void onError(ANError error) {
                                    count = 0;
                                    Dialog1.hide();
                                    Dialog1.dismiss();
                                    Log.e("Error",error.toString());
                                    Toast.makeText(app, "No se completó la sincronización", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
    }

    private void addCount() {
        Log.d("Succes","Completó");
        count++;
        if (count == listEvidencias.size()){
            count = 0 ;
            Dialog1.hide();
            Dialog1.dismiss();
            Toast.makeText(app, "Se completó la sincronización", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean guardarRegistro() {
        if (rdBtnActo.isChecked() || rdBtnCondicion.isChecked()){
            Origen origen = new Origen();
            if (rdBtnActo.isChecked()){
                origen.setCode("A");
                registroModel.setOrigen_ayc(registroDao.refreshOrigen(origen));
            }
            if (rdBtnCondicion.isChecked()){
                origen.setCode("C");
                registroModel.setOrigen_ayc(registroDao.refreshOrigen(origen));
            }
        }else {
            return false;
        }
        if (spnArea.getText() != null){
            List<Area> areaOcurrenciaLista = listaVerificacionDao.getAreaBeanList();
            for (Area area : areaOcurrenciaLista){
                if (area.getNombre().equals(spnArea.getText().toString())){
                    registroModel.setArea(area);
                }
            }
        }else {
            return false;
        }
        if (!spnTipoRiesgo.getText().toString().equals("")){
            List<TipoRiesgo> tipoRiesgoList = registroDao.getTipoRiesgoList();

            for (TipoRiesgo tipoRiesgo : tipoRiesgoList){

                if (tipoRiesgo.getName().equals(spnTipoRiesgo.getText().toString())){
                    registroModel.setTipo_riesgo_ayc(tipoRiesgo);
                }
            }
        }else {
            return false;
        }
        if (!autEmpresa.getText().toString().equals("")){
            List<EmpresaEspecializada> empresaEspecializadaList = listaVerificacionDao.getEmpresaEspecializadaList();

            for (EmpresaEspecializada empresa : empresaEspecializadaList){

                if (empresa.getRazon_social().equals(autEmpresa.getText().toString())){
                    registroModel.setEmpresa_especializada(empresa);
                }
            }
        }else {
            return false;
        }
        if (!edFecha.getText().toString().equals("")){
            registroModel.setFecha(edFecha.getText().toString());
        }else {
            return false;
        }
        if (!edHora.getText().toString().equals("")){
            registroModel.setHora(edHora.getText().toString());
        }else {
            return false;
        }
        if (!edDescripcion.getText().toString().equals("")){
            registroModel.setDescripcion(edDescripcion.getText().toString());
        }else {
            return false;
        }
        if (!edAccion.getText().toString().equals("")){
            registroModel.setAccion_inmediata(edAccion.getText().toString());
        }else {
            return false;
        }
        if (listEvidencias.size()<1){
            Toast toast = Toast.makeText(getApplicationContext(),"No hay evidencia del registro",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }else{
            try {
                registroModel.setEmpleado(app.getUsuarioEnSesion().getFb_empleado_id().toString());
                registroModel.setUea_pe(app.getUsuarioEnSesion().getFb_uea_pe_id().toString());
                registroDao.createOrUpdate(registroModel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            RegistroEvidenciaDao evidenciaDao = new RegistroEvidenciaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

            for (Object evidencia: listEvidencias) {
                try {
                    ((RegistroEvidencia) evidencia).setAyc_registro_id(registroModel.getAyc_registro_id());
                    evidenciaDao.createOrUpdate((RegistroEvidencia) evidencia);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    private void cargarCombos() {
        if (listaVerificacionDao.getAreaBeanList() != null){
            listaAreaOcurrencia = new ArrayList<Object>(listaVerificacionDao.getAreaBeanList());
        }
        AdaptadorSpinner adaptadorAreaOcurrencia = new AdaptadorSpinner(getApplicationContext(), R.layout.spinner_item,listaAreaOcurrencia,"Area");
        spnArea.setAdapter(adaptadorAreaOcurrencia);

        if (listaVerificacionDao.getEmpresaEspecializadaList() != null){
            listaEmpresaOcurrencia = new ArrayList<Object>(listaVerificacionDao.getEmpresaEspecializadaList());
        }
        AdaptadorSpinner adaptadorEmpresaOcurrencia = new AdaptadorSpinner(getApplicationContext(), R.layout.spinner_item,listaEmpresaOcurrencia,"Empresa");
        autEmpresa.setAdapter(adaptadorEmpresaOcurrencia);

        if (registroDao.getTipoRiesgoList() != null){
            listaTipoRiesgo = new ArrayList<Object>(registroDao.getTipoRiesgoList());
        }
        AdaptadorSpinner adaptadorTipoRiesgo = new AdaptadorSpinner(getApplicationContext(), R.layout.spinner_item,listaTipoRiesgo,"TipoRiesgo");
        spnTipoRiesgo.setAdapter(adaptadorTipoRiesgo);

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private int obtenerAnho() {
        int anho;
        String fecha = edFecha.getText().toString();
        if(fecha.equals("")){
            calendar = Calendar.getInstance();
            anho = calendar.get(Calendar.YEAR);
        }else {
            String anhoText = fecha.substring(6,10);
            anho = Integer.parseInt(anhoText);
        }
        return anho;
    }

    private int obtenerMes() {
        int mes;
        String fecha = edFecha.getText().toString();
        if(fecha.equals("")){
            calendar = Calendar.getInstance();
            mes = calendar.get(Calendar.MONTH) + 1;
        }else {
            String mesText = fecha.substring(3,5);
            mes = Integer.parseInt(mesText);
        }
        return mes;
    }

    private int obtenerDia() {
        int dia;
        String fecha = edFecha.getText().toString();
        if(fecha.equals("")){
            calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
        }else {
            String diaText = fecha.substring(0,2);
            dia = Integer.parseInt(diaText);
        }
        return dia;
    }



    private int obtenerHora() {
        int hor;
        String horaInicio = edHora.getText().toString();
        if(horaInicio.equals("")){
            calendar = Calendar.getInstance();
            hor = calendar.get(Calendar.HOUR);
        }else {
            String horText = horaInicio.substring(0,2);
            hor = Integer.parseInt(horText);
        }
        return hor;
    }

    private int obtenerMinuto() {
        int minu;
        String horaInicio = edHora.getText().toString();
        if(horaInicio.equals("")){
            calendar = Calendar.getInstance();
            minu = calendar.get(Calendar.MINUTE);
        }else {
            String minuText = horaInicio.substring(3,5);
            minu = Integer.parseInt(minuText);
        }
        return minu;
    }
}