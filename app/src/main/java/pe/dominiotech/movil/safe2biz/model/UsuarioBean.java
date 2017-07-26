package pe.dominiotech.movil.safe2biz.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_USUARIO)
public class UsuarioBean implements Serializable{

    @DatabaseField(columnName = "usuario_id", generatedId = true)
    private int usuario_id;                 // Id local
    @DatabaseField(columnName = "sc_user_id")
    private Long sc_user_id;
    @DatabaseField(columnName = "user_login")
    private String user_login;                 // Usuario login
    @DatabaseField(columnName = "password")
    private String password;               // Contrasenia
    @DatabaseField(columnName = "usuario")
    private String usuario;
    @DatabaseField(columnName = "dni")
    private String dni;                        // Dni
    @DatabaseField(columnName = "fb_empleado_id")
    private Long fb_empleado_id;               // Codigo empleado
    @DatabaseField(columnName = "nombre_empleado")
    private String nombre_empleado;            // Nombre completo empleado
    @DatabaseField(columnName = "fb_uea_pe_id")
    private Long fb_uea_pe_id;                 // Codigo unidad
    @DatabaseField(columnName = "fb_uea_pe_abr")
    private String fb_uea_pe_abr;              // Abreviatura nombre unidad
    @DatabaseField(columnName = "ip_o_dominio_servidor")
    private String ipOrDominioServidor;        // Ip o dominio del servidor
    @DatabaseField(columnName = "user_login_servidor")
    private String user_login_servidor;                 // Usuario login servidor
    @DatabaseField(columnName = "password_servidor")
    private String password_servidor;                   // Contrasenia servidor
    @DatabaseField(columnName = "url_ext")
    private String url_ext;                   // URL_Ext

    private String idDispositivo;
    private String urlProfilePicture;

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Long getFb_empleado_id() {
        return fb_empleado_id;
    }

    public void setFb_empleado_id(Long fb_empleado_id) {
        this.fb_empleado_id = fb_empleado_id;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public Long getFb_uea_pe_id() {
        return fb_uea_pe_id;
    }

    public void setFb_uea_pe_id(Long fb_uea_pe_id) {
        this.fb_uea_pe_id = fb_uea_pe_id;
    }

    public String getFb_uea_pe_abr() {
        return fb_uea_pe_abr;
    }

    public void setFb_uea_pe_abr(String fb_uea_pe_abr) {
        this.fb_uea_pe_abr = fb_uea_pe_abr;
    }

    public String getUrlProfilePicture() {
        return urlProfilePicture;
    }

    public void setUrlProfilePicture(String urlProfilePicture) {
        this.urlProfilePicture = urlProfilePicture;
    }

    public String getIpOrDominioServidor() {
        return ipOrDominioServidor;
    }

    public void setIpOrDominioServidor(String ipOrDominioServidor) {
        this.ipOrDominioServidor = ipOrDominioServidor;
    }

    public String getUser_login_servidor() {
        return user_login_servidor;
    }

    public void setUser_login_servidor(String user_login_servidor) {
        this.user_login_servidor = user_login_servidor;
    }

    public String getPassword_servidor() {
        return password_servidor;
    }

    public void setPassword_servidor(String password_servidor) {
        this.password_servidor = password_servidor;
    }

    public Long getSc_user_id() {
        return sc_user_id;
    }

    public void setSc_user_id(Long sc_user_id) {
        this.sc_user_id = sc_user_id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getUrl_ext() {
        return url_ext;
    }

    public void setUrl_ext(String url_ext) {
        this.url_ext = url_ext;
    }
}
