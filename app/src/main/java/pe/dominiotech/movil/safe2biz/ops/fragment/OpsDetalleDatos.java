package pe.dominiotech.movil.safe2biz.ops.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.base.adapter.AdaptadorSpinner;
import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.base.model.Turno;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;
import pe.dominiotech.movil.safe2biz.service.ListaVerificacionService;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.GPSTracker;



public class OpsDetalleDatos extends Fragment implements TextWatcher, LocationListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, View.OnClickListener {

    private ListaVerificacionDao listaVerificacionDao;
    private MainApplication app;
    private ListaVerificacion listaVerificacionSeleccionada;
    private EditText edtFechayHora;
    private AutoCompleteTextView autEmpresa;
    private MaterialBetterSpinner spnTurno, spnArea;
    private LocationManager locationManager;
    double mLat, mLong;
    private GoogleMap map;
    MapView mapView;
    private int codCheckListCabecera;
    private Long codListaVerificacion;
    private String nombreTitulo;
    private Button btnGuardar;
    private RegistroGenerales registroGenerales;
    private ListaVerificacionService listaVerificacionService;

    List<Object> listaAreaOcurrencia;
    List<Object> listaEmpresaOcurrencia;
    List<Object> listaTurno;
    List<EmpresaEspecializada> empresaEspecializadaList;
    List<Area> areaOcurrenciaLista;
    List<Turno> turnoLista;

    public OpsDetalleDatos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ops_detalle_datos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listaVerificacionSeleccionada = (ListaVerificacion) bundle.getSerializable("listaVerificacionSeleccionada");
            registroGenerales = (RegistroGenerales) bundle.getSerializable("registroGenerales");
        }
        inicializarComponentes(savedInstanceState,view);
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        verificarCamposObligatorios();
    }

    @Override
    public void onClick(View view) {
        if (view == btnGuardar){
            guardarRegistroGenerales();
        }
    }

    public interface habilitar_tabs {
        void habilitar_tabs(boolean habilitar);
    }
    Activity activity;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    private void inicializarComponentes(Bundle savedInstanceState, View view) {
        app = (MainApplication)getActivity().getApplication();
        listaVerificacionService = app.getListaVerificacionService();
        listaVerificacionDao = new ListaVerificacionDao(app.getApplicationContext(), AppConstants.DB_NAME, null,AppConstants.DB_VERSION);

        edtFechayHora = (EditText)view.findViewById(R.id.Fecha);
        autEmpresa = (AutoCompleteTextView) view.findViewById(R.id.Empresa);
        autEmpresa.setThreshold(1);
        autEmpresa.addTextChangedListener(this);
        spnArea = (MaterialBetterSpinner) view.findViewById(R.id.Area);
        spnArea.addTextChangedListener(this);
        spnTurno = (MaterialBetterSpinner) view.findViewById(R.id.Turno);
        spnTurno.addTextChangedListener(this);
        mapView = (MapView) view.findViewById(R.id.mapDetalle);
        mapView.onCreate(savedInstanceState);

        btnGuardar = (Button)view.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        edtFechayHora.setText(obtenerFechayHoraActual());

        cargarCombos();
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.getUiSettings().setAllGesturesEnabled(false);
        mapView.setDuplicateParentStateEnabled(false);

        mapView.setClickable(false);
        mapView.setFocusable(false);

        try {
            MapsInitializer.initialize(app.getApplicationContext());
        } catch ( Exception e) {
            e.printStackTrace();
        }
        map.setOnMapLoadedCallback(this);
        onMapReady(map);
        cargarMapa();
        cargarDatos();
    }

    private void cargarDatos() {
        if (registroGenerales.getOps_registro_generales_id() > 0){
            spnArea.setText(registroGenerales.getArea().getNombre());
            spnTurno.setText(registroGenerales.getTurno().getName());
            autEmpresa.setText(registroGenerales.getEmpresaEspecializada().getRazon_social());
        }
    }

    private void cargarCombos() {
        areaOcurrenciaLista = cargarListaArea();
        listaAreaOcurrencia = new ArrayList<>();
        listaAreaOcurrencia.addAll(areaOcurrenciaLista);
        AdaptadorSpinner adaptadorAreaOcurrencia = new AdaptadorSpinner(app.getApplicationContext(), R.layout.spinner_item,listaAreaOcurrencia,"Area");
        spnArea.setAdapter(adaptadorAreaOcurrencia);

        turnoLista = cargarListaTurno();
        listaTurno = new ArrayList<>();
        listaTurno.addAll(turnoLista);
        AdaptadorSpinner adaptadorTurno = new AdaptadorSpinner(app.getApplicationContext(), R.layout.spinner_item,listaTurno,"TurnoClass");
        spnTurno.setAdapter(adaptadorTurno);

        empresaEspecializadaList = cargarListEmpresa();
        listaEmpresaOcurrencia = new ArrayList<>();
        listaEmpresaOcurrencia.addAll(empresaEspecializadaList);
        AdaptadorSpinner adaptadorEmpresaOcurrencia = new AdaptadorSpinner(app.getApplicationContext(), R.layout.spinner_item,listaEmpresaOcurrencia,"Empresa");
        autEmpresa.setAdapter(adaptadorEmpresaOcurrencia);

    }

    private List<EmpresaEspecializada> cargarListEmpresa() {
        List<EmpresaEspecializada> empresaBeanList;
        empresaBeanList = listaVerificacionDao.getEmpresaEspecializadaList();
        System.out.println(empresaBeanList.get(0).getRazon_social());
        return  empresaBeanList;
    }

    private List<Turno> cargarListaTurno() {
        List<Turno> turnoList;
        turnoList = listaVerificacionService.getTurnoBeanList();
        return turnoList;
    }

    private List<Area> cargarListaArea() {
        List<Area> areaList;
        areaList = listaVerificacionService.getAreaBeanList();
        return areaList;
    }

    private String obtenerFechayHoraActual() {
        String fechayHora;
        String fecha;
        String hora;
        Calendar calendar;
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayText = day+"";
        String mesText = month+"";
        if(dayText.length() == 1) {
            dayText = "0" + dayText;
        }
        if(mesText.length() == 1){
            mesText = "0"+mesText;
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String hourText = hour+"";
        String minuteText = minute+"";
        String secondText = second+"";
        if(hourText.length() == 1) {
            hourText = "0" + hourText;
        }
        if(minuteText.length() == 1){
            minuteText = "0"+minuteText;
        }
        if(secondText.length() == 1){
            secondText = "0"+secondText;
        }

        fecha = year+"-"+mesText+"-"+dayText;
        hora = hourText+":"+minuteText+":"+secondText;
        fechayHora = dayText+"/"+mesText+"/"+year+" "+hourText+":"+minuteText+":"+secondText;

        registroGenerales.setFechaOps(fecha);
        registroGenerales.setHoraOps(hora);

        return fechayHora;
    }


    public void cargarMapa(){
        activarGps();
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Map<String,Object> hasMapUbi = notificar();
            if (2 == hasMapUbi.size()){
                registroGenerales.setLatitud(Double.parseDouble(hasMapUbi.get("latitud").toString()));
                registroGenerales.setLongitud(Double.parseDouble(hasMapUbi.get("longitud").toString()));
                showMarkers(hasMapUbi);
            }
        }
    }

    private void activarGps() {
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
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
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

    }

    private Map<String,Object> notificar() {
        Map<String,Object> hasMapUbicacion = new HashMap<>();
        GPSTracker mGPS = new GPSTracker(getContext());
        if (mGPS.canGetLocation()) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();

            hasMapUbicacion.put("latitud", mLat);
            hasMapUbicacion.put("longitud", mLong);
        }else{
            hasMapUbicacion.put("error", 0);
        }

        return hasMapUbicacion;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
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
    public void onMapLoaded() {

    }

    private void showMarkers(Map<String,Object> hasMapUbi) {
        double latitud = (double)hasMapUbi.get("latitud");
        double longitud = (double)hasMapUbi.get("longitud");
        LatLng muni = new LatLng(latitud, longitud);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(muni, 13));
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

    private void verificarCamposObligatorios() {
        if (!autEmpresa.getText().toString().equals("") && !spnArea.getText().toString().equals("") &&
                !spnTurno.getText().toString().equals("")){
            boolean flag = false;
            for (EmpresaEspecializada empresa : empresaEspecializadaList){
                if (empresa.getRazon_social().equals(autEmpresa.getText().toString())){
                    btnGuardar.setEnabled(true);
                    flag = true;
                    break;
                }
            }
            if (!flag){
                btnGuardar.setEnabled(false);
            }
        }else{
            btnGuardar.setEnabled(false);
        }
    }


    private void guardarRegistroGenerales(){
        for (EmpresaEspecializada empresa : empresaEspecializadaList){
            if (empresa.getRazon_social().equals(autEmpresa.getText().toString())){
                registroGenerales.setEmpresaEspecializada(empresa);
            }
        }
        for (Area area : areaOcurrenciaLista){
            if (area.getNombre().equals(spnArea.getText().toString())){
                registroGenerales.setArea(area);
            }
        }
        for (Turno turno : turnoLista){
            if (turno.getName().equals(spnTurno.getText().toString())){
                registroGenerales.setTurno(turno);
            }
        }

        registroGenerales.setFbUeaPeId(app.getUsuarioEnSesion().getFb_uea_pe_id());
        registroGenerales.setCodigo(edtFechayHora.getText().toString());
        registroGenerales.setgTipoOrigenId(17);
        registroGenerales.setFbEmpleadoId(app.getUsuarioEnSesion().getFb_empleado_id());
        registroGenerales.setOpsListaVerificacionId(listaVerificacionSeleccionada.getOps_lista_verificacion_id());
        registroGenerales.setOpsTipoResultadoId(listaVerificacionSeleccionada.getOpsTipoResultadoId());
        registroGenerales.setFbEmpleadoNombreCompleto(app.getUsuarioEnSesion().getNombre_empleado());

        listaVerificacionDao.guardarRegistroGenerales(registroGenerales);
        ((habilitar_tabs) activity).habilitar_tabs(true);

    }
}