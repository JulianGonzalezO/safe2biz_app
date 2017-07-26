
package pe.dominiotech.movil.safe2biz.utils;


public interface AppConstants {
	String DB_NAME = "safe2biz1.db";
	int DB_VERSION = 1;
	String APP_DIRECTORY_NAME = "safe2biz1";
	String ruta_log_safe2biz = "/SAFE2BIZ/SAFE2BIZ";
	String ARCHIVO_LOG_SAFE2BIZ = "Safe2BizLog.txt";

	String metodo_post = "POST";
	String valor_ultima_fecha_sync_lista_verificacion = "ULTIMA_FECHA_SYNC_LISTA_VERIFICACION";

	String procesar_usuario = "VALIDAR_USUARIO";
	String procesar_tipo_resultado = "OBTENER_LISTA_TIPO_RESULTADO";
	String procesar_lista_verificacion = "OBTENER_LISTA_VERIFICACION";
	String procesar_lista_verificacion_categoria = "OBTENER_LISTA_VERIFICACION_CATEGORIA";
	String procesar_lista_verificacion_seccion = "OBTENER_LISTA_VERIFICACION_SECCION";
	String procesar_lista_verificacion_pregunta = "OBTENER_LISTA_VERIFICACION_PREGUNTA";
	String procesar_lista_verificacion_resultado = "OBTENER_LISTA_VERIFICACION_RESULTADO";
	String procesar_lista_empresa_especializada = "OBTENER_LISTA_EMPRESA_ESPECIALIZADA";
	String procesar_lista_area = "OBTENER_LISTA_AREA";
	String procesar_lista_turno = "OBTENER_LISTA_TURNO";

	String procesar_registro_generales = "ENVIAR_REGISTRO_GENERALES";
	String procesar_registro_resultado = "ENVIAR_REGISTRO_RESULTADO";

	String cadena_vacia = "";
	String SYSTEM_ROOT = "safe2biz";
	String ruta_log_lista_verificacion = "/SAFE2BIZ/LISTA_VERIFICACION";
	String ruta_log_actos_and_condiciones = "/SAFE2BIZ/ACTOS_Y_CONDICIONES";
	String ruta_log_ejecucion_sac = "/SAFE2BIZ/EJECUCION_SAC";
	String ruta_log_verificacion_sac = "/SAFE2BIZ/VERIFICACION_SAC";
	String ARCHIVO_LOG_LISTA_VERIFICACION = "Safe2biz_ListaVerificacion.txt";
	String ARCHIVO_LOG_ACTOS_Y_CONDICIONES = "Safe2biz_Actos_y_Condiciones.txt";
	String ARCHIVO_LOG_EJECUCION_SAC = "Safe2biz_Ejecucion_Sac.txt";
	String ARCHIVO_LOG_VERIFICACION_SAC = "Safe2biz_Verificacion_Sac.txt";

	String TABLA_PARAMETRO = "T_PARAMETRO";
	String TABLA_USUARIO = "T_SC_USER";
	String TABLA_UNIDAD = "FB_UEA_PE";
	String TABLA_TIPO_RESULTADO = "OPS_TIPO_RESULTADO";
	String TABLA_LISTA_VERIF_RESULTADO = "OPS_LISTA_VERIF_RESULTADO";
	String TABLA_OPS_LISTA_VERIFICACION = "OPS_LISTA_VERIFICACION";
	String TABLA_OPS_REGISTRO_GENERALES = "OPS_REGISTRO_GENERALES";
	String TABLA_TURNO = "T_TURNO";
	String TABLA_AREA = "FB_AREA";
	String TABLA_EMPRESA_ESPECIALIZADA = "FB_EMPRESA_ESPECIALIZADA";
	String TABLA_LISTA_VERIF_CATEGORIA = "OPS_LISTA_VERIF_CATEGORIA";
	String TABLA_LISTA_VERIF_SECCION = "OPS_LISTA_VERIF_SECCION";
	String TABLA_LISTA_VERIF_PREGUNTA = "OPS_LISTA_VERIF_PREGUNTA";
	String TABLA_OPS_REGISTRO_RESULTADO = "OPS_REGISTRO_RESULTADO";
	String TABLA_SECCION_COMPLETA = "T_SECCION_COMPLETA";
	String TABLA_SAC = "SAC_ACCION_CORRECTIVA";

	//String RESPUESTA - VALORES
	String VALOR_NC = "6";
	String VALOR_CP = "4";
	String VALOR_C = "5";
	String VALOR_NA = "7";

	//Suncronize Information
	String estado_error = "-1";

	String tipo_mensaje_informacion = "INFO";
	String tipo_mensaje_alerta = "ALERT";
	String tipo_mensaje_confirmacion = "CONFIRM";
	String datos_descarga_activo = "ACTIVO_1VEZ";

	//Parametro_Movil
	String PARMOV_N_PARAMETRO = "n_parametro";
	String PARMOV_CODIGO = "X_CODIGO";
	String PARMOV_VALOR = "X_VALOR";
	String PARMOV_DESCRIPCION = "X_DESCRIPCION";

	//Upload
	String IS_LISTA_VERIFICACION = "1";

}
