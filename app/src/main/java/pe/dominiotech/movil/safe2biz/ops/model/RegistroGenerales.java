package pe.dominiotech.movil.safe2biz.ops.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.base.model.Turno;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_OPS_REGISTRO_GENERALES)
public class RegistroGenerales implements Serializable{

    @DatabaseField(columnName = "ops_registro_generales_id", generatedId = true)
    private int ops_registro_generales_id;
    @DatabaseField(columnName = "fb_uea_pe_id")
    private Long fbUeaPeId;
    @DatabaseField(columnName = "codigo")
    private String codigo;
    @DatabaseField(columnName = "g_tipo_origen_id")
    private int gTipoOrigenId;
    @DatabaseField(columnName = "fecha_ops")
    private String fechaOps;
    @DatabaseField(columnName = "hora_ops")
    private String horaOps;
    @DatabaseField(columnName = "turno", foreign = true, foreignAutoRefresh = true)
    private Turno turno;
    @DatabaseField(columnName = "fb_area_id" , foreign = true, foreignAutoRefresh = true)
    private Area area;
    @DatabaseField(columnName = "g_rol_empresa_id")
    private Long gRolEmpresaId;
    @DatabaseField(columnName = "fb_empresa_especializada_id", foreign = true, foreignAutoRefresh = true)
    private EmpresaEspecializada empresaEspecializada;
    @DatabaseField(columnName = "fb_empleado_id")
    private Long fbEmpleadoId;
    @DatabaseField(columnName = "ops_lista_verificacion_id")
    private Long opsListaVerificacionId;
    @DatabaseField(columnName = "ops_tipo_resultado_id")
    private Long opsTipoResultadoId;
    @DatabaseField(columnName = "latitud")
    private double latitud;
    @DatabaseField(columnName = "longitud")
    private double longitud;
    @DatabaseField(columnName = "fb_area_nombre")
    private String fbAreaNombre;
    @DatabaseField(columnName = "turno_nombre")
    private String turnoNombre;
    @DatabaseField(columnName = "fb_empresa_especializada_nombre")
    private String fbEmpresaEspecializadaNombre;
    @DatabaseField(columnName = "fb_empleado_nombre_completo")
    private String fbEmpleadoNombreCompleto;
    @DatabaseField(columnName = "estado")
    private Long estado;
    @DatabaseField(columnName = "id_generado_syncronizacion")
    private String id_generado_syncronizacion;
    @DatabaseField(columnName = "flag")
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private List<RegistroResultado> resultadoList;

    public int getOps_registro_generales_id() {
        return ops_registro_generales_id;
    }

    public void setOps_registro_generales_id(int ops_registro_generales_id) {
        this.ops_registro_generales_id = ops_registro_generales_id;
    }

    public Long getFbUeaPeId() {
        return fbUeaPeId;
    }

    public void setFbUeaPeId(Long fbUeaPeId) {
        this.fbUeaPeId = fbUeaPeId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getgTipoOrigenId() {
        return gTipoOrigenId;
    }

    public void setgTipoOrigenId(int gTipoOrigenId) {
        this.gTipoOrigenId = gTipoOrigenId;
    }

    public String getFechaOps() {
        return fechaOps;
    }

    public void setFechaOps(String fechaOps) {
        this.fechaOps = fechaOps;
    }

    public String getHoraOps() {
        return horaOps;
    }

    public void setHoraOps(String horaOps) {
        this.horaOps = horaOps;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Long getgRolEmpresaId() {
        return gRolEmpresaId;
    }

    public void setgRolEmpresaId(Long gRolEmpresaId) {
        this.gRolEmpresaId = gRolEmpresaId;
    }

    public EmpresaEspecializada getEmpresaEspecializada() {
        return empresaEspecializada;
    }

    public void setEmpresaEspecializada(EmpresaEspecializada empresaEspecializada) {
        this.empresaEspecializada = empresaEspecializada;
    }

    public Long getFbEmpleadoId() {
        return fbEmpleadoId;
    }

    public void setFbEmpleadoId(Long fbEmpleadoId) {
        this.fbEmpleadoId = fbEmpleadoId;
    }

    public Long getOpsListaVerificacionId() {
        return opsListaVerificacionId;
    }

    public void setOpsListaVerificacionId(Long opsListaVerificacionId) {
        this.opsListaVerificacionId = opsListaVerificacionId;
    }

    public Long getOpsTipoResultadoId() {
        return opsTipoResultadoId;
    }

    public void setOpsTipoResultadoId(Long opsTipoResultadoId) {
        this.opsTipoResultadoId = opsTipoResultadoId;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getFbAreaNombre() {
        return fbAreaNombre;
    }

    public void setFbAreaNombre(String fbAreaNombre) {
        this.fbAreaNombre = fbAreaNombre;
    }

    public String getTurnoNombre() {
        return turnoNombre;
    }

    public void setTurnoNombre(String turnoNombre) {
        this.turnoNombre = turnoNombre;
    }

    public String getFbEmpresaEspecializadaNombre() {
        return fbEmpresaEspecializadaNombre;
    }

    public void setFbEmpresaEspecializadaNombre(String fbEmpresaEspecializadaNombre) {
        this.fbEmpresaEspecializadaNombre = fbEmpresaEspecializadaNombre;
    }

    public String getFbEmpleadoNombreCompleto() {
        return fbEmpleadoNombreCompleto;
    }

    public void setFbEmpleadoNombreCompleto(String fbEmpleadoNombreCompleto) {
        this.fbEmpleadoNombreCompleto = fbEmpleadoNombreCompleto;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getId_generado_syncronizacion() {
        return id_generado_syncronizacion;
    }

    public void setId_generado_syncronizacion(String id_generado_syncronizacion) {
        this.id_generado_syncronizacion = id_generado_syncronizacion;
    }

    public List<RegistroResultado> getResultadoList() {
        return resultadoList;
    }

    public void setResultadoList(List<RegistroResultado> resultadoList) {
        this.resultadoList = resultadoList;
    }
}