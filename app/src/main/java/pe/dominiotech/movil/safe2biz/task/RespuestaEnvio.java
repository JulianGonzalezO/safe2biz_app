package pe.dominiotech.movil.safe2biz.task;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RespuestaEnvio implements Serializable {

    private static final long serialVersionUID = 1L;
    @SerializedName("status")
    private String estado;
    @SerializedName("valido")
    private boolean valido;
    @SerializedName("transaccion")
    private ErrorMessage errores;

    @SerializedName("errorMessageList")
    private List<ErrorMessage> errorMessageList;

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public boolean isValido() {
        return valido;
    }
    public void setValido(boolean valido) {
        this.valido = valido;
    }
    public ErrorMessage getErrores() {
        return errores;
    }
    public void setErrores(ErrorMessage errores) {
        this.errores = errores;
    }
    public List<ErrorMessage> getErrorMessageList() {
        return errorMessageList;
    }
    public void setErrorMessageList(List<ErrorMessage> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }
}