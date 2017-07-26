package pe.dominiotech.movil.safe2biz.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.utils.Util;

public class ImagenViewAdapter extends RecyclerView.Adapter<ImagenViewAdapter.ViewHolder> {
    private List<Object> galleryList;
    private Context context;
    View.OnClickListener onClickListener;

    public ImagenViewAdapter(Context context, List<Object> galleryList, View.OnClickListener onClickListener ) {
        this.galleryList = galleryList;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @Override
    public ImagenViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.imagen_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagenViewAdapter.ViewHolder viewHolder, int i) {
        Object Imagen = galleryList.get(i);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(Imagen.toString(), options);
        viewHolder.img.setImageBitmap(Util.resize(bitmap,400,400));
        ImageView close = (ImageView) viewHolder.itemView.findViewById(R.id.close);
        close.setTag(viewHolder);
        viewHolder.setImagenModel(Imagen);

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView close;
        private ImageView img;
        private Object ImagenModel;
        public ViewHolder(View view) {
            super(view);

            close = (ImageView) view.findViewById(R.id.close);
            img = (ImageView) view.findViewById(R.id.img);

            close.setOnClickListener(onClickListener);
        }

        public Object getImagenModel() {
            return ImagenModel;
        }

        void setImagenModel(Object imagenModel) {
            ImagenModel = imagenModel;
        }
    }

    public void setList(List<Object> galleryList){
        this.galleryList = galleryList;
        notifyDataSetChanged();
    }



}