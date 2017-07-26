package pe.dominiotech.movil.safe2biz.base.model;

import java.io.Serializable;

public class MenuPrincipalItem implements Serializable{

    private String titulo;
    private int icono;
    private int orden;

    public int getEsActividad() {
        return esActividad;
    }

    public void setEsActividad(int esActividad) {
        this.esActividad = esActividad;
    }

    private int numDia;
    private int esActividad;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

}