package pe.dominiotech.movil.safe2biz.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.model.UnidadBean;

public class ListaUnidadesAdapter extends RecyclerView.Adapter<ListaUnidadesAdapter.ViewHolder>{

    List<UnidadBean> mItems;
    View.OnClickListener onClickListener;
    Context context;
    private ProgressBar progressBar;

    public ListaUnidadesAdapter(List<UnidadBean> drawerItems, View.OnClickListener onClickListener, Context contex) {
        this.mItems = drawerItems;
        this.onClickListener = onClickListener;
        context = contex;
    }


    public boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    @Override
    public ListaUnidadesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.unidad_card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(  ViewHolder viewHolder,   int i) {
        UnidadBean carViewModel = mItems.get(i);
        viewHolder.tvNombreCarViewUnidad.setText(carViewModel.getNombre());

        LinearLayout lnlyCarViewUnidad = (LinearLayout) viewHolder.itemView.findViewById(R.id.lnlyCarViewUnidad);
        lnlyCarViewUnidad.setTag(viewHolder);
        viewHolder.setUnidadBean(carViewModel);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout lnlyCardViewUnidad;
        public TextView tvNombreCarViewUnidad;

        private UnidadBean unidadBean;

        public ViewHolder(View itemView) {
            super(itemView);
            lnlyCardViewUnidad = (LinearLayout) itemView.findViewById(R.id.lnlyCarViewUnidad);
            tvNombreCarViewUnidad = (TextView) itemView.findViewById(R.id.tvNombreCarViewUnidad);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarPrincipal);

            lnlyCardViewUnidad.setOnClickListener(onClickListener);
        }

        public UnidadBean getUnidadBean() {
            return unidadBean;
        }

        public void setUnidadBean(UnidadBean unidadBean) {
            this.unidadBean = unidadBean;
        }
    }

    public void setList(List<UnidadBean> list){
        this.mItems = list;
    }
}