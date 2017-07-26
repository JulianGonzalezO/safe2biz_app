package pe.dominiotech.movil.safe2biz.ops.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifCategoria;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifPregunta;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifSeccion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroResultado;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;

public class ExpandableListCategoriaAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ListaVerifCategoria> _listDataHeader;
    private ExpandableListView.OnChildClickListener childClickListener;
    private int ops_registro_generales_id;
    private List<SecondLevelAdapter> adapters = new ArrayList<>();

    public ExpandableListCategoriaAdapter(Context context, List<ListaVerifCategoria> listaVerifCategorias, ExpandableListView.OnChildClickListener childClickListener, int ops_registro_generales_id) {
        this._listDataHeader = listaVerifCategorias;
        this.context = context;
        this.childClickListener = childClickListener;
        this.ops_registro_generales_id = ops_registro_generales_id;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return  childPosititon;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        SecondLevelAdapter adapter = new SecondLevelAdapter(context, _listDataHeader.get(groupPosition).getSeccionList(), this.ops_registro_generales_id);
        adapters.add(groupPosition,adapter);
        secondLevelELV.setAdapter(adapter);
        secondLevelELV.setOnChildClickListener(this.childClickListener);
        return secondLevelELV;
    }

    public void setOps_registro_generales_id(int ops_registro_generales_id) {
        this.ops_registro_generales_id = ops_registro_generales_id;
        for (SecondLevelAdapter adapter : adapters){
            adapter.setOps_registro_generales_id(ops_registro_generales_id);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ListaVerifCategoria header = (ListaVerifCategoria) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ops_categoria_item, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.nombreCategoria);
        text.setText(header.getNombre());
        text.setTypeface(null, Typeface.BOLD);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
class SecondLevelExpandableListView extends ExpandableListView {

    public SecondLevelExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

class SecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ListaVerifSeccion> _listDataHeader;
    private int ops_registro_generales_id;

    SecondLevelAdapter(Context context, List<ListaVerifSeccion> listaVerifSeccions, int ops_registro_generales_id) {
        this.context = context;
        this._listDataHeader = listaVerifSeccions;
        this.ops_registro_generales_id = ops_registro_generales_id;
    }

    public void setOps_registro_generales_id(int ops_registro_generales_id) {
        this.ops_registro_generales_id = ops_registro_generales_id;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ListaVerifSeccion header = (ListaVerifSeccion) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ops_seccion_item, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.nombreSeccion);
        text.setText(header.getNombre());
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataHeader.get(groupPosition).getListaPreguntas().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition+groupPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ListaVerifPregunta child = (ListaVerifPregunta) getChild(groupPosition, childPosition);
        ListaVerificacionDao listaVerificacionDao = new ListaVerificacionDao(context, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ops_pregunta_item, null);
        }
        RegistroResultado registroResultado = listaVerificacionDao.getRegistroResultado(ops_registro_generales_id,child.getOps_lista_verif_pregunta_id());
        TextView text = (TextView) convertView.findViewById(R.id.nombrePregunta);
        text.setText(child.getNombre());
        ImageView semaforo = (ImageView) convertView.findViewById(R.id.semaforo);

        if (Objects.equals(registroResultado.getOpsListaVerifResultadoId(), Long.valueOf(AppConstants.VALOR_C))){
            semaforo.setColorFilter(ContextCompat.getColor(context,R.color.md_green_700));
        }else if (Objects.equals(registroResultado.getOpsListaVerifResultadoId(), Long.valueOf(AppConstants.VALOR_CP))){
            semaforo.setColorFilter(ContextCompat.getColor(context,R.color.md_yellow_900));
        }else if (Objects.equals(registroResultado.getOpsListaVerifResultadoId(), Long.valueOf(AppConstants.VALOR_NC))){
            semaforo.setColorFilter(ContextCompat.getColor(context,R.color.md_red_A700));
        }else if (Objects.equals(registroResultado.getOpsListaVerifResultadoId(), Long.valueOf(AppConstants.VALOR_NA))){
            semaforo.setColorFilter(ContextCompat.getColor(context,R.color.md_black_1000));
        }else{
            semaforo.setColorFilter(ContextCompat.getColor(context,R.color.md_white_1000));
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getListaPreguntas().size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
