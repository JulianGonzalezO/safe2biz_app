package pe.dominiotech.movil.safe2biz.ayc.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;

@DatabaseTable(tableName = "ayc_registro")
public class Registro implements Serializable {

    @DatabaseField(columnName = "ayc_registro_id", generatedId = true)
    private int ayc_registro_id;

    @DatabaseField(canBeNull = false, columnName = "origen_ayc", foreign = true, foreignAutoRefresh = true)
    private	Origen origen_ayc;

    @DatabaseField(canBeNull = false, columnName = "fb_area_id", foreign = true, foreignAutoRefresh = true)
    private Area area;

    @DatabaseField(canBeNull = false, columnName = "fb_empresa_especializada_id", foreign = true, foreignAutoRefresh = true)
    private EmpresaEspecializada empresa_especializada;

    @DatabaseField(columnName = "fecha")
    private String fecha;

    @DatabaseField(columnName = "hora")
    private String hora;

    @DatabaseField(columnName = "descripcion")
    private String descripcion;

    @DatabaseField(canBeNull = false, columnName = "tipo_riesgo_ayc", foreign = true, foreignAutoRefresh = true)
    private TipoRiesgo tipo_riesgo_ayc;

    @DatabaseField(columnName = "accion_inmediata")
    private String accion_inmediata;

    @DatabaseField(columnName = "latitud")
    private String latitud;

    @DatabaseField(columnName = "longitud")
    private String longitud;

    @DatabaseField(columnName = "evidencia")
    private String evidencia;

    @DatabaseField(columnName = "fb_uea_pe_id")
    private String uea_pe;

    @DatabaseField(columnName = "fb_empleado_id")
    private String empleado;

    @DatabaseField(columnName = "ayc_registro_id_server")
    private int ayc_registro_id_server;

    public int getAyc_registro_id_server() {
        return ayc_registro_id_server;
    }

    public void setAyc_registro_id_server(int ayc_registro_id_server) {
        this.ayc_registro_id_server = ayc_registro_id_server;
    }

    public int getAyc_registro_id() {
        return ayc_registro_id;
    }

    public void setAyc_registro_id(int ayc_registro_id) {
        this.ayc_registro_id = ayc_registro_id;
    }

    public Origen getOrigen_ayc() {
        return origen_ayc;
    }

    public void setOrigen_ayc(Origen origen_ayc) {
        this.origen_ayc = origen_ayc;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public EmpresaEspecializada getEmpresa_especializada() {
        return empresa_especializada;
    }

    public void setEmpresa_especializada(EmpresaEspecializada empresa_especializada) {
        this.empresa_especializada = empresa_especializada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoRiesgo getTipo_riesgo_ayc() {
        return tipo_riesgo_ayc;
    }

    public void setTipo_riesgo_ayc(TipoRiesgo tipo_riesgo_ayc) {
        this.tipo_riesgo_ayc = tipo_riesgo_ayc;
    }

    public String getAccion_inmediata() {
        return accion_inmediata;
    }

    public void setAccion_inmediata(String accion_inmediata) {
        this.accion_inmediata = accion_inmediata;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getUea_pe() {
        return uea_pe;
    }

    public void setUea_pe(String uea_pe) {
        this.uea_pe = uea_pe;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
}
