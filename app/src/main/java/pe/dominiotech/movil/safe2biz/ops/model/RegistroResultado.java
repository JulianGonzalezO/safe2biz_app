package pe.dominiotech.movil.safe2biz.ops.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_OPS_REGISTRO_RESULTADO)
public class RegistroResultado implements Serializable{

    @DatabaseField(columnName = "ops_registro_resultado_id", generatedId = true)
    private int opsRegistroResultadoId;
    @DatabaseField(columnName = "ops_registro_generales_id")
    private int opsRegistroGeneralesId;
    @DatabaseField(columnName = "ops_lista_verif_pregunta_id")
    private Long opsListaVerifPreguntaId;
    @DatabaseField(columnName = "ops_lista_verif_seccion_id")
    private Long opsListaVerifSeccionId;
    @DatabaseField(columnName = "ops_lista_verif_categoria_id")
    private Long opsListaVerifCategoriaId;
    @DatabaseField(columnName = "ops_lista_verif_resultado_id")
    private Long opsListaVerifResultadoId;
    @DatabaseField(columnName = "observacion")
    private String observacion;
    @DatabaseField(columnName = "ruta")
    private String ruta;
    @DatabaseField(columnName = "nombre_imagen")
    private String nombreImagen;
    @DatabaseField(columnName = "id_generado_syncronizacion")
    private String idGeneradoSyncronizacion;

    public int getOpsRegistroResultadoId() {
        return opsRegistroResultadoId;
    }

    public void setOpsRegistroResultadoId(int opsRegistroResultadoId) {
        this.opsRegistroResultadoId = opsRegistroResultadoId;
    }

    public int getOpsRegistroGeneralesId() {
        return opsRegistroGeneralesId;
    }

    public void setOpsRegistroGeneralesId(int opsRegistroGeneralesId) {
        this.opsRegistroGeneralesId = opsRegistroGeneralesId;
    }

    public Long getOpsListaVerifPreguntaId() {
        return opsListaVerifPreguntaId;
    }

    public void setOpsListaVerifPreguntaId(Long opsListaVerifPreguntaId) {
        this.opsListaVerifPreguntaId = opsListaVerifPreguntaId;
    }

    public Long getOpsListaVerifSeccionId() {
        return opsListaVerifSeccionId;
    }

    public void setOpsListaVerifSeccionId(Long opsListaVerifSeccionId) {
        this.opsListaVerifSeccionId = opsListaVerifSeccionId;
    }

    public Long getOpsListaVerifCategoriaId() {
        return opsListaVerifCategoriaId;
    }

    public void setOpsListaVerifCategoriaId(Long opsListaVerifCategoriaId) {
        this.opsListaVerifCategoriaId = opsListaVerifCategoriaId;
    }

    public Long getOpsListaVerifResultadoId() {
        return opsListaVerifResultadoId;
    }

    public void setOpsListaVerifResultadoId(Long opsListaVerifResultadoId) {
        this.opsListaVerifResultadoId = opsListaVerifResultadoId;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getIdGeneradoSyncronizacion() {
        return idGeneradoSyncronizacion;
    }

    public void setIdGeneradoSyncronizacion(String idGeneradoSyncronizacion) {
        this.idGeneradoSyncronizacion = idGeneradoSyncronizacion;
    }
}