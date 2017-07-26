package pe.dominiotech.movil.safe2biz.ops.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifPregunta;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifSeccion;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroResultado;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListaVerificacionPreguntaActivity extends AppCompatActivity {

    Toolbar toolbar;
    final String rutaImagenes = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ImagenesOps/";

    private MainApplication app;
    private ListaVerificacion listaVerificacionSeleccionada;
    private ListaVerifPregunta listaVerificacionPregunta;
    private ListaVerifSeccion listaVerificacionSeccion;
    private ImageButton galeria, camara;
    private ImageView imagen;
    private EditText edComentario;
    private Button btnGuardar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final int SELECT_PICTURE = 300;

    int onStartCount = 0;
    private Uri outputFileUri;

    private int radioGroupFlag = 0;
    private int comentarioFlag = 0;
    private int imagenFlag = 0;
    private RegistroGenerales registroGenerales;
    private RegistroResultado registroResultado;
    private ListaVerificacionDao listaVerificacionDao;
    private String rutaImagen;
    private String codigoImagen;
    private RadioGroup radioGroup;

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

        setContentView(R.layout.ops_pregunta_activity);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        listaVerificacionSeleccionada = (ListaVerificacion)getIntent().getExtras().getSerializable("listaVerificacionSeleccionada");
        listaVerificacionPregunta = (ListaVerifPregunta) getIntent().getExtras().getSerializable("listaVerificacionPregunta");
        listaVerificacionSeccion = (ListaVerifSeccion) getIntent().getExtras().getSerializable("listaVerificacionSeccion");
        registroGenerales = (RegistroGenerales) getIntent().getExtras().getSerializable("registroGenerales");
        listaVerificacionDao = new ListaVerificacionDao(app.getApplicationContext(), AppConstants.DB_NAME, null,AppConstants.DB_VERSION);
        registroResultado =  listaVerificacionDao.getRegistroResultado(registroGenerales.getOps_registro_generales_id(),listaVerificacionPregunta.getOps_lista_verif_pregunta_id());
        registroResultado.setOpsListaVerifCategoriaId(listaVerificacionPregunta.getOps_lista_verif_categoria_id());
        registroResultado.setOpsListaVerifSeccionId(listaVerificacionPregunta.getOps_lista_verif_seccion_id());
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(listaVerificacionSeleccionada.getNombre());
            actionBar.setHomeButtonEnabled(true);
        }

        TextView nombrePregunta = (TextView) findViewById(R.id.nombrePregunta);
        nombrePregunta.setText(listaVerificacionPregunta.getNombre());
        TextView nombreSeccion = (TextView) findViewById(R.id.nombreSeccion);
        nombreSeccion.setText(listaVerificacionSeccion.getNombre());
        nombreSeccion.setTypeface(null, Typeface.BOLD);
        imagen = (ImageView) findViewById(R.id.imagen);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("id",Integer.toString(registroGenerales.getOps_registro_generales_id()));
                Log.d("id",Integer.toString(registroResultado.getOpsRegistroGeneralesId()));
                listaVerificacionDao.guardarRegistroResultado(registroResultado);
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        });

        edComentario = (EditText) findViewById(R.id.edComentario);
        edComentario.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edComentario.getText().toString().equals("")){
                    registroResultado.setObservacion(editable.toString());
                    comentarioFlag = 1;
                    if (radioGroupFlag == 1 && imagenFlag == 1){
                        btnGuardar.setClickable(true);
                        btnGuardar.setEnabled(true);
                    }
                }else {
                    comentarioFlag = 0;
                    btnGuardar.setClickable(false);
                    btnGuardar.setEnabled(false);
                }
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.rGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId != -1){
                    radioGroupFlag = 1;
                    if (comentarioFlag == 1 && imagenFlag == 1){
                        btnGuardar.setClickable(true);
                        btnGuardar.setEnabled(true);
                    }else{
                        btnGuardar.setClickable(false);
                        btnGuardar.setEnabled(false);
                    }
                    if(checkedId == R.id.rdBtnC){
                        btnGuardar.setClickable(true);
                        btnGuardar.setEnabled(true);
                        Long opsListaVerifResultadoId = Long.valueOf(AppConstants.VALOR_C);
                        registroResultado.setOpsListaVerifResultadoId(opsListaVerifResultadoId);
                    }else if (checkedId == R.id.rdBtnCP){
                        btnGuardar.setClickable(true);
                        btnGuardar.setEnabled(true);
                        Long opsListaVerifResultadoId = Long.valueOf(AppConstants.VALOR_CP);
                        registroResultado.setOpsListaVerifResultadoId(opsListaVerifResultadoId);
                    }else if (checkedId == R.id.rdBtnNC){
                        Long opsListaVerifResultadoId = Long.valueOf(AppConstants.VALOR_NC);
                        registroResultado.setOpsListaVerifResultadoId(opsListaVerifResultadoId);
                    }else if (checkedId == R.id.rdBtnNA){
                        btnGuardar.setClickable(true);
                        btnGuardar.setEnabled(true);
                        Long opsListaVerifResultadoId = Long.valueOf(AppConstants.VALOR_NA);
                        registroResultado.setOpsListaVerifResultadoId(opsListaVerifResultadoId);
                    }
                }else{
                    radioGroupFlag = 0;
                    btnGuardar.setClickable(false);
                    btnGuardar.setEnabled(false);
                }
            }
        });

        camara = (ImageButton) findViewById(R.id.camara);
        camara.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                File mi_foto = new File(getRutaImagen());
                try {
                    if (mi_foto.createNewFile()){
                        outputFileUri = Uri.fromFile( mi_foto );
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }

                } catch (IOException ex) {
                    Log.e("ERROR ", "Error:" + ex);
                }
            }

        });
        galeria = (ImageButton) findViewById(R.id.btnGaleria);
        galeria.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
            }

        });
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        if (registroResultado.getOpsRegistroResultadoId() > 0){
            if (Objects.equals(registroResultado.getOpsListaVerifResultadoId(), Long.valueOf(AppConstants.VALOR_C))){
                radioGroup.check(R.id.rdBtnC);
            }else if (Objects.equals(registroResultado.getOpsListaVerifResultadoId(), Long.valueOf(AppConstants.VALOR_CP))){
                radioGroup.check(R.id.rdBtnCP);
            }else if (Objects.equals(registroResultado.getOpsListaVerifResultadoId(), Long.valueOf(AppConstants.VALOR_NC))){
                radioGroup.check(R.id.rdBtnNC);
            }else{
                radioGroup.check(R.id.rdBtnNA);
            }
            if (registroResultado.getRuta() != null){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(registroResultado.getRuta(), options);
                imagen.setImageBitmap(Util.resize(bitmap,400,400));
                imagenFlag = 1;
            }
            if (registroResultado.getObservacion() != null){
                edComentario.setText(registroResultado.getObservacion());
                comentarioFlag = 1;
            }
            btnGuardar.setEnabled(true);
            btnGuardar.setClickable(true);
            radioGroupFlag = 1;
        }
        //Crea el directorio para las imagenes
        File newdir = new File(rutaImagenes);
        newdir.mkdirs();
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

    public void setupTheme() {
        setTheme(R.style.MyMaterialTheme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        app.getUsuarioEnSesion();
//        getMenuInflater().inflate(R.menu.menu_check_list_nuevo_cabecera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_check_list_cabecera) {
            return true;
        }else if (id == android.R.id.home){
            onBackPressed();
            this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            return false;
        }
        return super.onOptionsItemSelected(item);
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
        imagen.setVisibility(View.VISIBLE);
        registroResultado.setRuta(rutaImagen);
        registroResultado.setNombreImagen(codigoImagen);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen, options);
        imagen.setImageBitmap(Util.resize(bitmap,400,400));
        imagenFlag = 1;
        if (radioGroupFlag == 1 && comentarioFlag == 1){
            btnGuardar.setClickable(true);
            btnGuardar.setEnabled(true);
        }
    }

}
