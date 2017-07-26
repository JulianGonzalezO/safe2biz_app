package pe.dominiotech.movil.safe2biz.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_SECCION_COMPLETA)
public class SeccionCompletaBean implements Serializable{

    @DatabaseField(columnName = "seccion_completa_id", generatedId = true)
    private int seccionCompletaId;                   // Id Seccion Completa
    @DatabaseField(columnName = "ops_registro_generales_id")
    private int opsRegistroGeneralesId;                      // codigo Cabecera (Registros Generales)
    @DatabaseField(columnName = "ops_lista_verif_seccion_id")
    private Long opsListaVerifSeccionId;                      // codigo Seccion
    @DatabaseField(columnName = "ops_lista_verif_categoria_id")
    private Long opsListaVerifCategoriaId;
    @DatabaseField(columnName = "flagCompleto")               // 1 si se completo la respuesta a todas las preguntas de una seccion
    public int flagCompleto;

    public int getSeccionCompletaId() {
        return seccionCompletaId;
    }

    public void setSeccionCompletaId(int seccionCompletaId) {
        this.seccionCompletaId = seccionCompletaId;
    }

    public int getOpsRegistroGeneralesId() {
        return opsRegistroGeneralesId;
    }

    public void setOpsRegistroGeneralesId(int opsRegistroGeneralesId) {
        this.opsRegistroGeneralesId = opsRegistroGeneralesId;
    }

    public Long getOpsListaVerifSeccionId() {
        return opsListaVerifSeccionId;
    }

    public void setOpsListaVerifSeccionId(Long opsListaVerifSeccionId) {
        this.opsListaVerifSeccionId = opsListaVerifSeccionId;
    }

    public int getFlagCompleto() {
        return flagCompleto;
    }

    public void setFlagCompleto(int flagCompleto) {
        this.flagCompleto = flagCompleto;
    }

    public Long getOpsListaVerifCategoriaId() {
        return opsListaVerifCategoriaId;
    }

    public void setOpsListaVerifCategoriaId(Long opsListaVerifCategoriaId) {
        this.opsListaVerifCategoriaId = opsListaVerifCategoriaId;
    }
}
