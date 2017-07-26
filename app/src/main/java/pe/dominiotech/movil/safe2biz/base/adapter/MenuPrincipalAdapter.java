package pe.dominiotech.movil.safe2biz.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.base.model.MenuPrincipalItem;

public class MenuPrincipalAdapter extends RecyclerView.Adapter<MenuPrincipalAdapter.ViewHolder>{

    private List<MenuPrincipalItem> menuPrincipalItemsList;
    View.OnClickListener onClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        ImageView icono;
        public ViewHolder(View v) {
            super(v);
            tvTitulo = (TextView) v.findViewById(R.id.tvTituloGridViewSubMenuItemPrincipal);
            icono = (ImageView) v.findViewById(R.id.imgViewIconoGridViewSubMenuItemPrincipal);
            v.setOnClickListener(onClickListener);
        }
    }

    public MenuPrincipalAdapter(List<MenuPrincipalItem> menuPrincipalItemList, View.OnClickListener onClickListener) {
        super();
        this.menuPrincipalItemsList = menuPrincipalItemList;
        this.onClickListener = onClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_principal_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.icono.setImageResource(menuPrincipalItemsList.get(position).getIcono());
        holder.tvTitulo.setText(menuPrincipalItemsList.get(position).getTitulo());
        holder.itemView.setTag(menuPrincipalItemsList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return menuPrincipalItemsList.size();
    }

    public void setList(List<MenuPrincipalItem> list){
        this.menuPrincipalItemsList = list;
        notifyDataSetChanged();
    }


}
