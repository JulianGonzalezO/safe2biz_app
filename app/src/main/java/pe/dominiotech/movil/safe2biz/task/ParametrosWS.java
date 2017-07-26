package pe.dominiotech.movil.safe2biz.task;

import org.apache.http.NameValuePair;

import java.util.List;

public class ParametrosWS {

    private String ruta;
    private String metodo;
    private List<NameValuePair> parametros;
    private String nombreMetodo;
    private String identificador;
    private String tipoEnvio;
    private String mensaje;

    private List<ParametrosWS> paramWsList;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public List<NameValuePair> getParametros() {
        return parametros;
    }

    public void setParametros(List<NameValuePair> parametros) {
        this.parametros = parametros;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<ParametrosWS> getParamWsList() {
        return paramWsList;
    }

    public void setParamWsList(List<ParametrosWS> paramWsList) {
        this.paramWsList = paramWsList;
    }
}