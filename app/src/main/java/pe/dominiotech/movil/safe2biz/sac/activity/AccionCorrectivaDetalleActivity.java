package pe.dominiotech.movil.safe2biz.sac.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

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

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.base.adapter.ImagenViewAdapter;
import pe.dominiotech.movil.safe2biz.sac.dao.AccionCorrectivaDao;
import pe.dominiotech.movil.safe2biz.sac.dao.AccionCorrectivaEvidenciaDao;
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectiva;
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectivaEvidencia;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AccionCorrectivaDetalleActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener{

    Toolbar toolbar;

    private MainApplication app;
    int onStartCount = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final int SELECT_PICTURE = 300;
    final String rutaImagenes = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ImagenesSac/";

    private  EditText edFecha;
    Uri outputFileUri = null;
    private String codigoImagen;
    private String rutaImagen;
    private RecyclerView visorImagenes;
    private AccionCorrectiva accionCorrectiva;
    private ImagenViewAdapter imagenViewAdapter;
    private List<Object> listEvidencias = new ArrayList<>();
    private Button btnGuardar;
    private Calendar calendar;
    private String usuario;
    private String password;
    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        setContentView(R.layout.sac_detalle_activity);


        visorImagenes = (RecyclerView)findViewById(R.id.imagegallery);
        visorImagenes.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        visorImagenes.setLayoutManager(layoutManager);
        imagenViewAdapter = new ImagenViewAdapter(getApplicationContext(), listEvidencias, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMenu(view);
            }
        });
        visorImagenes.setAdapter(imagenViewAdapter);

        //Crea el directorio para las imagenes
        File newdir = new File(rutaImagenes);
        newdir.mkdirs();
        app = (MainApplication) getApplication();
        usuario = app.getUsuarioEnSesion().getUser_login();
        password = app.getUsuarioEnSesion().getPassword();
        accionCorrectiva = (AccionCorrectiva)getIntent().getExtras().getSerializable("accionCorrectiva");
        inicializarComponentes();
    }


    private void inicializarComponentes() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        AndroidNetworking.initialize(app);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if(null != accionCorrectiva.getCodigo_accion_correctiva()){
                actionBar.setTitle("Acción Correctiva");
            }else {
                actionBar.setTitle("Codigo");
            }
            actionBar.setHomeButtonEnabled(true);
        }

        TextView tvSubTituloSac = (TextView) findViewById(R.id.tvSubTituloSac);
        tvSubTituloSac.setTypeface(null, Typeface.BOLD);
        TextView tvCodigoSac = (TextView) findViewById(R.id.tvCodigoSac);
        tvCodigoSac.setTypeface(null, Typeface.BOLD);
        TextView tvDescripcionSac = (TextView) findViewById(R.id.tvDescripcionSac);
        tvDescripcionSac.setTypeface(null, Typeface.BOLD);
        TextView tvOrigenSac = (TextView) findViewById(R.id.tvOrigenSac);
        tvOrigenSac.setTypeface(null, Typeface.BOLD);
        TextView tvFechaSac = (TextView) findViewById(R.id.tvFechaSac);
        tvFechaSac.setTypeface(null, Typeface.BOLD);
        TextView tvResponsableSac = (TextView) findViewById(R.id.tvResponsableSac);
        tvResponsableSac.setTypeface(null, Typeface.BOLD);
        TextView tvCodigoCarViewSac = (TextView) findViewById(R.id.tvCodigoCarViewSac);
        tvCodigoCarViewSac.setText(accionCorrectiva.getCodigo_accion_correctiva());
        TextView tvDescripcionCarViewSac = (TextView) findViewById(R.id.tvDescripcionCarViewSac);
        tvDescripcionCarViewSac.setText(accionCorrectiva.getAccion_correctiva_detalle());
        TextView tvFechaCarViewSac = (TextView) findViewById(R.id.tvFechaCarViewSac);
        tvFechaCarViewSac.setText(accionCorrectiva.getFecha_acordada_ejecucion());
        TextView tvOrigenCarViewSac = (TextView) findViewById(R.id.tvOrigenCarViewSac);
        tvOrigenCarViewSac.setText(accionCorrectiva.getOrigen());
        TextView tvResponsableCarViewSac = (TextView) findViewById(R.id.tvResponsableCarViewSac);
        tvResponsableCarViewSac.setText(accionCorrectiva.getNombre_responsable_correccion());
        edFecha = (EditText) findViewById(R.id.edFecha);
        if(null == accionCorrectiva.getFecha_ejecucion()){
            edFecha.setText(Util.obtenerFechayHoraActual().split("-")[0]);
        }else{
            edFecha.setText(accionCorrectiva.getFecha_ejecucion());
        }
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setTransformationMethod(null);
        btnGuardar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AccionCorrectivaDao accionCorrectivaDao = new AccionCorrectivaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
                AccionCorrectivaEvidenciaDao accionCorrectivaEvidenciaDao = new AccionCorrectivaEvidenciaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
                accionCorrectiva.setFecha_ejecucion(edFecha.getText().toString());

                for (int i = 0; i < listEvidencias.size(); i++){
                    try {
                        accionCorrectivaEvidenciaDao.createOrUpdate((AccionCorrectivaEvidencia)listEvidencias.get(i));
                        Log.d("lol",((AccionCorrectivaEvidencia)listEvidencias.get(i)).getNombre());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    accionCorrectivaDao.createOrUpdate(accionCorrectiva);
                    Toast.makeText(getApplicationContext(),"Se guardó correctamente",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        });

        ImageButton btnCamara = (ImageButton) findViewById(R.id.btnCamara);
        btnCamara.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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

        });
        ImageButton btnGaleria = (ImageButton) findViewById(R.id.btnGaleria);
        btnGaleria.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
            }

        });
        cargarImagenes();

    }
    private void cargarImagenes() {
        AccionCorrectivaEvidenciaDao accionCorrectivaEvidenciaDao = new AccionCorrectivaEvidenciaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
        AccionCorrectivaEvidencia accionCorrectivaEvidencia = new AccionCorrectivaEvidencia();
        accionCorrectivaEvidencia.setSac_accion_correctiva_id(accionCorrectiva.getSac_accion_correctiva_id());
        List<AccionCorrectivaEvidencia> evidencias = accionCorrectivaEvidenciaDao.getEvidenciaBeanList(accionCorrectivaEvidencia);
        if(evidencias != null){
            listEvidencias = new ArrayList<Object>(accionCorrectivaEvidenciaDao.getEvidenciaBeanList(accionCorrectivaEvidencia));
            if (listEvidencias.size() > 0){
                visorImagenes.setVisibility(View.VISIBLE);
                btnGuardar.setEnabled(true);
                imagenViewAdapter.setList(listEvidencias);
            }
        }
    }

    public void onItemClickMenu(View item) {
        final AccionCorrectivaEvidencia evideciaEliminada = (AccionCorrectivaEvidencia) ((ImagenViewAdapter.ViewHolder)item.getTag()).getImagenModel();

        switch(item.getId()){
            case R.id.close : {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("ELIMINAR IMAGEN ");
                dialog.setMessage("¿Desea ELIMINAR la imagen?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarImagen(evideciaEliminada);
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


    private void eliminarImagen(AccionCorrectivaEvidencia evideciaEliminada) {

        AccionCorrectivaEvidenciaDao accionCorrectivaEvidenciaDao = new AccionCorrectivaEvidenciaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
        listEvidencias.remove(evideciaEliminada);
        if (listEvidencias.size()==0){
            btnGuardar.setEnabled(false);
            visorImagenes.setVisibility(View.GONE);
        }
        imagenViewAdapter.setList(listEvidencias);
        imagenViewAdapter.notifyDataSetChanged();
        try {
            if (evideciaEliminada != null){
                accionCorrectivaEvidenciaDao.deleteById(AccionCorrectivaEvidencia.class, evideciaEliminada.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(getApplicationContext(),"Imagen eliminada correctamente",Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * Metodo privado que genera un codigo unico segun la hora y fecha del sistema
     * @return codigoImagen
     * */
    private String getCodigoImagen()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", Locale.US);
        String date = dateFormat.format(new Date());
        codigoImagen = "pic_" + date + ".jpg";
        return codigoImagen;
    }

    public String getRutaImagen() {
        rutaImagen = rutaImagenes + getCodigoImagen();
        return rutaImagen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            visorImagenes.setVisibility(View.VISIBLE);
            btnGuardar.setEnabled(true);

            AccionCorrectivaEvidencia img = new AccionCorrectivaEvidencia();
            img.setRuta(rutaImagen);
            img.setNombre(codigoImagen);
            img.setSac_accion_correctiva_id(accionCorrectiva.getSac_accion_correctiva_id());
            listEvidencias.add(img);
            imagenViewAdapter.setList(listEvidencias);


        }else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            visorImagenes.setVisibility(View.VISIBLE);
            btnGuardar.setEnabled(true);

            File destFile = new File(getRutaImagen());
            try {
                copyFile(new File(getImagenPath(data.getData())), destFile);
                AccionCorrectivaEvidencia img = new AccionCorrectivaEvidencia();
                img.setRuta(rutaImagen);
                img.setNombre(codigoImagen);
                img.setSac_accion_correctiva_id(accionCorrectiva.getSac_accion_correctiva_id());
                listEvidencias.add(img);
                imagenViewAdapter.setList(listEvidencias);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if ( resultCode == RESULT_CANCELED) {
            Toast.makeText(this, " Imagen no fue tomada... ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " Imagen no fue tomada... ", Toast.LENGTH_SHORT).show();
        }

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

    public void showDatePickerDialog(View v) {
        int anho = obtenerAnho();
        int mes = obtenerMes();
        int dia = obtenerDia();
        DialogFragment dialogFragment = pe.dominiotech.movil.safe2biz.utils.DatePicker.nuevaInstancia(anho, mes, dia,edFecha,Calendar.getInstance().getTimeInMillis(),null);
        dialogFragment.show(getSupportFragmentManager(), "DatePicker");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnSubir){
            String URL_EXT = app.getUsuarioEnSesion().getIpOrDominioServidor();
            AccionCorrectivaEvidenciaDao accionCorrectivaEvidenciaDao = new AccionCorrectivaEvidenciaDao(getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

            AccionCorrectivaEvidencia accionCorrectivaEvidencia = new AccionCorrectivaEvidencia();
            accionCorrectivaEvidencia.setSac_accion_correctiva_id(accionCorrectiva.getSac_accion_correctiva_id());
            final List<AccionCorrectivaEvidencia> ListaEvidencias = accionCorrectivaEvidenciaDao.getEvidenciaBeanList(accionCorrectivaEvidencia);
            if (ListaEvidencias != null){

                Map <String,String> headers = new HashMap<>();
                headers.put("userLogin",usuario);
                headers.put("userPassword",password);
                headers.put("systemRoot","safe2biz");

                String Id = Integer.toString(accionCorrectiva.getSac_accion_correctiva_id());

                Map <String,String> parameters = new HashMap<>();
                parameters.put("sac_id",Id);
                parameters.put("fecha_eje", edFecha.getText().toString());
                parameters.put("user_id", app.getUsuarioEnSesion().getSc_user_id().toString());

                final ProgressDialog Dialog1 = new ProgressDialog(AccionCorrectivaDetalleActivity.this);
                Dialog1.setMessage("Enviando datos al servidor...");
                Dialog1.show();
                final int[] count = {0};

                AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_sac_update")
                        .addBodyParameter(parameters)
                        .addHeaders(headers)
                        .setTag("accion_correctiva")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("lol","entro");
                                count[0] = count[0] + 1;
                                if (count[0] == ListaEvidencias.size()+1){
                                    Dialog1.dismiss();
                                }
                                // do anything with response
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                count[0] = count[0] + 1;
                                if (count[0] == ListaEvidencias.size()+1){
                                    Dialog1.dismiss();
                                }
                                Log.e("Error",error.toString());
                                Toast.makeText(app, "No se completó la sincronización", Toast.LENGTH_SHORT).show();
                            }
                        });

                for (int i = 0; i < ListaEvidencias.size(); i++){
                    String Ruta = ((AccionCorrectivaEvidencia) listEvidencias.get(i)).getRuta();
                    String nombre = ((AccionCorrectivaEvidencia) listEvidencias.get(i)).getNombre();

                    Bitmap bitmap = BitmapFactory.decodeFile(Ruta);
                    String imageStream = Util.encodeToBase64(Util.resize(bitmap,800,800), Bitmap.CompressFormat.JPEG, 100);

                    Map <String,String> parameters1 = new HashMap<>();
                    parameters1.put("sac_id",Id);
                    parameters1.put("imagen", nombre+";"+imageStream);
                    parameters1.put("user_id", app.getUsuarioEnSesion().getSc_user_id().toString());
                    Log.d("ruta",imageStream);

                    AndroidNetworking.post(URL_EXT + "/ws/null/pr_ws_sac_insert_evidencia")
                            .addBodyParameter(parameters1)
                            .addHeaders(headers)
                            .setTag("tag"+i)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("lol","entro");
                                    count[0] = count[0] + 1;
                                    if (count[0] == ListaEvidencias.size()+1){
                                        Dialog1.dismiss();
                                    }
                                    // do anything with response
                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    count[0] = count[0] + 1;
                                    if (count[0] == ListaEvidencias.size()+1){
                                        Dialog1.dismiss();
                                    }
                                    Log.e("Error",error.toString());
                                    Toast.makeText(app, "No se completó la sincronización", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }


}
