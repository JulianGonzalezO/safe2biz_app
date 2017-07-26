package pe.dominiotech.movil.safe2biz.base.model;


import android.graphics.Bitmap;

import java.io.Serializable;

public class Imagen implements Serializable {

    private Bitmap image_bitmap;

    public Bitmap getImage_bitmap() {
        return image_bitmap;
    }

    public void setImage_bitmap(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }

}
