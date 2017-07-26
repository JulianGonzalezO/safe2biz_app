package pe.dominiotech.movil.safe2biz.ayc.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.springframework.util.StringUtils;

import java.util.List;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ayc.dao.RegistroDao;
import pe.dominiotech.movil.safe2biz.ayc.dao.RegistroEvidenciaDao;
import pe.dominiotech.movil.safe2biz.ayc.model.Registro;
import pe.dominiotech.movil.safe2biz.ayc.model.RegistroEvidencia;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.ViewHolder>{

    List<Registro> mItems;
    View.OnClickListener onClickListener;
    Context context;

    public RegistroAdapter(List<Registro> drawerItems, View.OnClickListener onClickListener, Context contex) {
        this.mItems = drawerItems;
        this.onClickListener = onClickListener;
        context = contex;
    }


    public boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    @Override
    public RegistroAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ayc_registro_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(  ViewHolder viewHolder,   int i) {
        Registro carViewModel = mItems.get(i);
        RegistroDao registroDao = new RegistroDao(context, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
        ListaVerificacionDao listaVerificacionDao = new ListaVerificacionDao(context, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

        switch (carViewModel.getOrigen_ayc().getCode()) {
            case "A":
                viewHolder.tvOrigen.setText("ACTO");
                break;
            case "C":
                viewHolder.tvOrigen.setText("COND");
                break;
        }
        viewHolder.tvOrigen.setTypeface(null,Typeface.BOLD);

        viewHolder.tvNivelRiesgo.setText(carViewModel.getTipo_riesgo_ayc().getName());
        switch (carViewModel.getTipo_riesgo_ayc().getCode()) {
            case "RA":
                viewHolder.tvNivelRiesgo.setBackgroundResource(R.drawable.border_rojo);
                break;
            case "RM":
                viewHolder.tvNivelRiesgo.setBackgroundResource(R.drawable.border_naranja);
                break;
            case "RB":
                viewHolder.tvNivelRiesgo.setBackgroundResource(R.drawable.border_verde);
                break;
        }

        if (StringUtils.hasText(carViewModel.getDescripcion())) {
            viewHolder.tvDescripcion.setText(carViewModel.getDescripcion());
        } else {
            viewHolder.tvDescripcion.setVisibility(View.GONE);
        }
        if (StringUtils.hasText(carViewModel.getFecha())) {
            viewHolder.tvFecha.setText(carViewModel.getFecha());
        } else {
            viewHolder.tvFecha.setVisibility(View.GONE);
        }
        if (StringUtils.hasText(carViewModel.getHora())) {
            viewHolder.tvHora.setText(carViewModel.getHora());
        } else {
            viewHolder.tvHora.setVisibility(View.GONE);
        }

        if (carViewModel.getArea() != null) {
            listaVerificacionDao.refreshArea(carViewModel.getArea());
            viewHolder.tvArea.setText(carViewModel.getArea().getNombre());
            viewHolder.tvArea.setTypeface(null,Typeface.BOLD);
        }
        if (carViewModel.getEmpresa_especializada()!= null) {
            listaVerificacionDao.refreshEmpresa(carViewModel.getEmpresa_especializada());
            viewHolder.tvEmpresaReportante.setText(carViewModel.getEmpresa_especializada().getRazon_social());
        } else {
            viewHolder.tvEmpresaReportante.setVisibility(View.GONE);
        }

        RegistroEvidenciaDao evidenciaDao = new RegistroEvidenciaDao(context, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

        List<RegistroEvidencia> ListaEvidencias;
        RegistroEvidencia evidenciaBean = new RegistroEvidencia();
        evidenciaBean.setAyc_registro_id(carViewModel.getAyc_registro_id());
        ListaEvidencias = evidenciaDao.getEvidenciaAyCBeanList(evidenciaBean);

        if (ListaEvidencias != null){
            viewHolder.checkBox.setChecked(true);
        }else{
            viewHolder.checkBox.setChecked(false);
        }
        LinearLayout lnlyAycRegistro = (LinearLayout) viewHolder.itemView.findViewById(R.id.lnlyAycRegistro);
        lnlyAycRegistro.setTag(viewHolder);
        viewHolder.setRegistroModel(carViewModel);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout lnlyAycRegistro;
        public TextView tvOrigen;
        public TextView tvDescripcion;
        public TextView tvFecha;
        public TextView tvHora;
        public TextView tvNivelRiesgo;
        public TextView tvArea;
        public TextView tvEmpresaReportante;
        public CheckBox checkBox;

        private Registro RegistroModel;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlyAycRegistro = (LinearLayout) itemView.findViewById(R.id.lnlyAycRegistro);
            tvOrigen = (TextView) itemView.findViewById(R.id.tvOrigen);
            tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            tvHora = (TextView) itemView.findViewById(R.id.tvHora);
            tvNivelRiesgo = (TextView) itemView.findViewById(R.id.tvNivelRiesgo);
            tvArea = (TextView) itemView.findViewById(R.id.tvArea);
            tvEmpresaReportante = (TextView) itemView.findViewById(R.id.tvEmpresaReportante);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

            lnlyAycRegistro.setOnClickListener(onClickListener);
        }

        public Registro getRegistroModel() {
            return RegistroModel;
        }

        public void setRegistroModel(Registro registroModel) {
            RegistroModel = registroModel;
        }

    }

    public void setList(List<Registro> list){
        this.mItems = list;
        notifyDataSetChanged();
    }
}