package pe.dominiotech.movil.safe2biz.ops.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;

public class ListaVerificacionAdapter extends RecyclerView.Adapter<ListaVerificacionAdapter.ViewHolder>{

    List<ListaVerificacion> mItems;
    View.OnClickListener onClickListener;
    Context context;
    private ProgressBar progressBar;

    public ListaVerificacionAdapter(List<ListaVerificacion> drawerItems, View.OnClickListener onClickListener, Context contex) {
        this.mItems = drawerItems;
        this.onClickListener = onClickListener;
        context = contex;
    }

    public boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    @Override
    public ListaVerificacionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.encuesta_autorizada_card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(  ViewHolder viewHolder,   int i) {
        ListaVerificacion carViewModel = mItems.get(i);
        viewHolder.tvTituloCarViewEncuestaAutorizada.setText(carViewModel.getCodigo());
        if (StringUtils.hasText(carViewModel.getNombre())) {
            viewHolder.tvDescripcionCarViewEncuestaAutorizada.setText(carViewModel.getNombre());
            viewHolder.tvDescripcionCarViewEncuestaAutorizada.setTypeface(null, Typeface.BOLD);
        } else {
            viewHolder.tvDescripcionCarViewEncuestaAutorizada.setVisibility(View.GONE);
        }

        LinearLayout lnlyCarViewEncuestaAutorizada = (LinearLayout) viewHolder.itemView.findViewById(R.id.lnlyCarViewEncuestaAutorizada);
        lnlyCarViewEncuestaAutorizada.setTag(viewHolder);
        viewHolder.setCardViewModelEncuestaAutorizada(carViewModel);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout lnlyCardViewEncuestaAutorizada;
        public TextView tvTituloCarViewEncuestaAutorizada;
        public TextView tvDescripcionCarViewEncuestaAutorizada;

        private ListaVerificacion cardViewModelEncuestaAutorizada;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlyCardViewEncuestaAutorizada = (LinearLayout) itemView.findViewById(R.id.lnlyCarViewEncuestaAutorizada);
            tvTituloCarViewEncuestaAutorizada = (TextView) itemView.findViewById(R.id.tvTituloCarViewEncuestaAutorizada);
            tvDescripcionCarViewEncuestaAutorizada = (TextView) itemView.findViewById(R.id.tvDescripcionCarViewEncuestaAutorizada);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarPrincipal);

            lnlyCardViewEncuestaAutorizada.setOnClickListener(onClickListener);
        }

        public void setCardViewModelEncuestaAutorizada(ListaVerificacion cardViewModelEncuestaAutorizada) {
            this.cardViewModelEncuestaAutorizada = cardViewModelEncuestaAutorizada;
        }

        public ListaVerificacion getCardViewModelEncuestaAutorizada() {
            return cardViewModelEncuestaAutorizada;
        }
    }

    public void setList(List<ListaVerificacion> list){
        this.mItems = list;
    }
}