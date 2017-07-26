package pe.dominiotech.movil.safe2biz.task;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    private int Id;
    private int codigo;
    private String tipo;
    private String titulo;
    private boolean exito;
    private String atributo;
    private String mensaje;
    public ErrorMessage(){
        this.atributo = "";
        this.mensaje = "";
        this.tipo = "";
    }
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getAtributo() {
        return atributo;
    }
    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public boolean isExito() {
        return exito;
    }
    public void setExito(boolean exito) {
        this.exito = exito;
    }
}