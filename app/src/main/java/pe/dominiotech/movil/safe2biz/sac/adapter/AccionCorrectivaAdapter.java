package pe.dominiotech.movil.safe2biz.sac.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.sac.dao.AccionCorrectivaEvidenciaDao;
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectiva;
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectivaEvidencia;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;

public class AccionCorrectivaAdapter extends RecyclerView.Adapter<AccionCorrectivaAdapter.ViewHolder>{

    private List<AccionCorrectiva> mItems;
    View.OnClickListener onClickListener;
    Context context;

    public AccionCorrectivaAdapter(List<AccionCorrectiva> drawerItems, View.OnClickListener onClickListener, Context contex) {
        this.mItems = drawerItems;
        this.onClickListener = onClickListener;
        context = contex;
    }


    public boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    @Override
    public AccionCorrectivaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sac_card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(  ViewHolder viewHolder,   int i) {
        AccionCorrectiva carViewModel = mItems.get(i);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date today = new Date();
        Date fecha_sac;
        viewHolder.tvCodigoCarViewSac.setText(carViewModel.getCodigo_accion_correctiva());
        if (StringUtils.hasText(carViewModel.getAccion_correctiva_detalle())) {
            viewHolder.tvDescripcionCarViewSac.setText(carViewModel.getAccion_correctiva_detalle());
        } else {
            viewHolder.tvDescripcionCarViewSac.setVisibility(View.GONE);
        }
        if (StringUtils.hasText(carViewModel.getOrigen())) {
            viewHolder.tvOrigenCarViewSac.setText(carViewModel.getOrigen());
        } else {
            viewHolder.tvOrigenCarViewSac.setVisibility(View.GONE);
        }
        if (StringUtils.hasText(carViewModel.getNombre_responsable_correccion())) {
            viewHolder.tvPersonaReportanteCarViewSAC.setText(carViewModel.getNombre_responsable_correccion());
        } else {
            viewHolder.tvPersonaReportanteCarViewSAC.setVisibility(View.GONE);
        }
        if (StringUtils.hasText(carViewModel.getFecha_acordada_ejecucion())) {
            try {
                fecha_sac = dateFormat.parse(carViewModel.getFecha_acordada_ejecucion());
                long diff =( today.getTime() - fecha_sac.getTime())/ (1000 * 60 * 60 * 24);
                System.out.print(diff);
                viewHolder.tvFechaCarViewSac.setText(carViewModel.getFecha_acordada_ejecucion());
                if (Math.abs(diff) == 1){
                    viewHolder.tvDiasCarViewSac.setText(Math.abs(diff) + " día.");
                }else{
                    viewHolder.tvDiasCarViewSac.setText(Math.abs(diff) + " días.");
                }

                if (diff<1) {
                    viewHolder.tvFechaCarViewSac.setTextColor(ContextCompat.getColor(context, R.color.md_blue_600));
                    viewHolder.tvDiasCarViewSac.setBackgroundResource(R.drawable.border_sac_dias_blue);

                }else{
                    viewHolder.tvFechaCarViewSac.setTextColor(ContextCompat.getColor(context, R.color.md_red_A700));
                    viewHolder.tvDiasCarViewSac.setBackgroundResource(R.drawable.border_sac_dias_red);

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            viewHolder.tvFechaCarViewSac.setVisibility(View.GONE);
            viewHolder.tvDiasCarViewSac.setVisibility(View.GONE);

        }

        AccionCorrectivaEvidenciaDao accionCorrectivaEvidenciaDao = new AccionCorrectivaEvidenciaDao(context, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);

        List<AccionCorrectivaEvidencia> ListaEvidencias;
        AccionCorrectivaEvidencia accionCorrectivaEvidencia = new AccionCorrectivaEvidencia();
        accionCorrectivaEvidencia.setSac_accion_correctiva_id(carViewModel.getSac_accion_correctiva_id());
        ListaEvidencias = accionCorrectivaEvidenciaDao.getEvidenciaBeanList(accionCorrectivaEvidencia);

        if (ListaEvidencias != null){
            viewHolder.checkbox.setChecked(true);
        }else{
            viewHolder.checkbox.setChecked(false);
        }
        CardView  cardView = (CardView) viewHolder.itemView.findViewById(R.id.card_view);
        cardView.setTag(viewHolder);
        viewHolder.setCardViewModelSac(carViewModel);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        LinearLayout lnlyCarViewSac;
        TextView tvCodigoCarViewSac;
        TextView tvOrigenCarViewSac;
        TextView tvDescripcionCarViewSac;
        TextView tvPersonaReportanteCarViewSAC;
        TextView tvFechaCarViewSac;
        TextView tvDiasCarViewSac;
        public CheckBox checkbox;

        private AccionCorrectiva cardViewModelSac;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            lnlyCarViewSac = (LinearLayout) itemView.findViewById(R.id.lnlyCarViewSac);
            tvCodigoCarViewSac = (TextView) itemView.findViewById(R.id.tvCodigoCarViewSac);
            tvOrigenCarViewSac = (TextView) itemView.findViewById(R.id.tvOrigenCarViewSac);
            tvDescripcionCarViewSac = (TextView) itemView.findViewById(R.id.tvDescripcionCarViewSac);
            tvPersonaReportanteCarViewSAC = (TextView) itemView.findViewById(R.id.tvPersonaReportanteCarViewSAC);
            tvFechaCarViewSac = (TextView) itemView.findViewById(R.id.tvFechaCarViewSac);
            tvDiasCarViewSac = (TextView) itemView.findViewById(R.id.tvDiasCarViewSac);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkBox);

            cardView.setOnClickListener(onClickListener);
        }

        void setCardViewModelSac(AccionCorrectiva cardViewModelSac) {
            this.cardViewModelSac = cardViewModelSac;
        }

        public AccionCorrectiva getCardViewModelSac() {
            return cardViewModelSac;
        }
    }

    public void setList(List<AccionCorrectiva> list){
        this.mItems = list;
        notifyDataSetChanged();
    }
}