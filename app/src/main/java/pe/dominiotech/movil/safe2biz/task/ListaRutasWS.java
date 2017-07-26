package pe.dominiotech.movil.safe2biz.task;

import java.util.ArrayList;
import java.util.List;

public class ListaRutasWS {

    private List<ParametrosWS> parametros;
    private String userLogin;
    private String userPassword;
    private String systemRoot;
    private String tipoRspta;
    private String idDispositivo;

    public ListaRutasWS(){
        this.parametros = new ArrayList<ParametrosWS>();
        this.userLogin = "";
        this.userPassword = "";
        this.systemRoot = "";
        this.tipoRspta = "0";
        this.idDispositivo = "";
    }

    public List<ParametrosWS> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametrosWS> parametros) {
        this.parametros = parametros;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSystemRoot() {
        return systemRoot;
    }

    public void setSystemRoot(String systemRoot) {
        this.systemRoot = systemRoot;
    }

    public String getTipoRspta() {
        return tipoRspta;
    }

    public void setTipoRspta(String tipoRspta) {
        this.tipoRspta = tipoRspta;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }
}