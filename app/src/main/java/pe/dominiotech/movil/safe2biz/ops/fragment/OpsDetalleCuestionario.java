package pe.dominiotech.movil.safe2biz.ops.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ops.activity.ListaVerificacionPreguntaActivity;
import pe.dominiotech.movil.safe2biz.ops.adapter.ExpandableListCategoriaAdapter;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifCategoria;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;

import static android.app.Activity.RESULT_CANCELED;


public class OpsDetalleCuestionario extends Fragment {

    private static final int PREGUNTA_COMPLETA = 1 ;
    private MainApplication app;
    private RegistroGenerales registroGenerales;
    private ListaVerificacion listaVerificacionSeleccionada;
    private ExpandableListCategoriaAdapter listAdapter;

    public OpsDetalleCuestionario() {
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
        return inflater.inflate(R.layout.ops_detalle_cuestionario, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listaVerificacionSeleccionada = (ListaVerificacion) bundle.getSerializable("listaVerificacionSeleccionada");
            registroGenerales = (RegistroGenerales) bundle.getSerializable("registroGenerales");
        }
        inicializarComponentes(view);
    }
    private void inicializarComponentes(View view) {
        app = (MainApplication)getActivity().getApplication();
        ListaVerificacionDao listaVerificacionDao = new ListaVerificacionDao(app.getApplicationContext(), AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

        List<ListaVerifCategoria> categoriaItemList = listaVerificacionDao.getListaVerifCategorias(listaVerificacionSeleccionada.getOps_lista_verificacion_id());
        ExpandableListView expListView = (ExpandableListView) view.findViewById(R.id.expandableListCategorias);

        final List<ListaVerifCategoria> finalCategoriaItemList = categoriaItemList;
        ExpandableListView.OnChildClickListener childLst = new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView eListView, View view, int groupPosition,
                                        int childPosition, long id) {
                Intent i = new Intent(getContext(), ListaVerificacionPreguntaActivity.class);
                i.putExtra("registroGenerales", registroGenerales);
                i.putExtra("listaVerificacionSeleccionada", listaVerificacionSeleccionada);
                i.putExtra("listaVerificacionPregunta", finalCategoriaItemList.get(0).getSeccionList().get(groupPosition).getListaPreguntas().get(childPosition));
                i.putExtra("listaVerificacionSeccion", finalCategoriaItemList.get(0).getSeccionList().get(groupPosition));
                startActivityForResult(i, PREGUNTA_COMPLETA);
                return true/* or false depending on what you need */;
            }
        };

        listAdapter = new ExpandableListCategoriaAdapter(getContext(),categoriaItemList, childLst, registroGenerales.getOps_registro_generales_id());

        expListView.setAdapter(listAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PREGUNTA_COMPLETA) {
            if ( resultCode == Activity.RESULT_OK) {
                Log.d("Id", Integer.toString(registroGenerales.getOps_registro_generales_id()));
                listAdapter.setOps_registro_generales_id(registroGenerales.getOps_registro_generales_id());
//                listAdapter.notifyDataSetChanged();
            } else if ( resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), " No se guardó la información... ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), " No se guardó la información... ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
//        listAdapter.notifyDataSetChanged();
        super.onResume();
    }
}