package pe.dominiotech.movil.safe2biz.ops.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.springframework.util.StringUtils;

import java.util.List;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;

public class RegistroGeneralesAdapter extends RecyclerView.Adapter<RegistroGeneralesAdapter.ViewHolder>{

    List<RegistroGenerales> mItems;
    View.OnClickListener onClickListener;
    Context context;
    private ProgressBar progressBar;

    public RegistroGeneralesAdapter(List<RegistroGenerales> drawerItems, View.OnClickListener onClickListener, Context contex) {
        this.mItems = drawerItems;
        this.onClickListener = onClickListener;
        context = contex;
    }


    public boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    @Override
    public RegistroGeneralesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ops_registro_generales, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(  ViewHolder viewHolder,   int i) {
        RegistroGenerales registroGenerales = mItems.get(i);
        viewHolder.tvFecha.setText(registroGenerales.getFechaOps());
        viewHolder.tvHora.setText(registroGenerales.getHoraOps());
        if (StringUtils.hasText(registroGenerales.getEmpresaEspecializada().getRazon_social())) {
            viewHolder.tvEmpresa.setText(registroGenerales.getEmpresaEspecializada().getRazon_social());
        } else {
            viewHolder.tvEmpresa.setVisibility(View.GONE);
        }
        if (StringUtils.hasText(registroGenerales.getTurno().getName())) {
            viewHolder.tvTurno.setText(registroGenerales.getTurno().getName());
        } else {
            viewHolder.tvTurno.setVisibility(View.GONE);
        }
        if (StringUtils.hasText(registroGenerales.getArea().getNombre())) {
            viewHolder.tvArea.setText(registroGenerales.getArea().getNombre());
        } else {
            viewHolder.tvArea.setVisibility(View.GONE);
        }

        LinearLayout lnlyCarViewCheckList = (LinearLayout) viewHolder.itemView.findViewById(R.id.lnlyCarViewCheckList);
        lnlyCarViewCheckList.setTag(viewHolder);
        viewHolder.setRegistroGenerales(registroGenerales);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout lnlyCardViewCheckList;
        TextView tvFecha;
        TextView tvHora;
        TextView tvEmpresa;
        TextView tvArea;
        TextView tvTurno;

        private RegistroGenerales registroGenerales;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlyCardViewCheckList = (LinearLayout) itemView.findViewById(R.id.lnlyCarViewCheckList);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            tvHora = (TextView) itemView.findViewById(R.id.tvHora);
            tvEmpresa = (TextView) itemView.findViewById(R.id.tvEmpresa);
            tvArea = (TextView) itemView.findViewById(R.id.tvArea);
            tvTurno = (TextView) itemView.findViewById(R.id.tvTurno);

            lnlyCardViewCheckList.setOnClickListener(onClickListener);
        }

        public void setRegistroGenerales(RegistroGenerales registroGenerales) {
            this.registroGenerales = registroGenerales;
        }

        public RegistroGenerales getRegistroGenerales() {
            return registroGenerales;
        }
    }

    public void setList(List<RegistroGenerales> list){
        this.mItems = list;
        notifyDataSetChanged();
    }
}