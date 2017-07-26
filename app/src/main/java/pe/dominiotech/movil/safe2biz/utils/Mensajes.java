package pe.dominiotech.movil.safe2biz.utils;

public interface Mensajes {

	String pb_mensaje_validar_usuario = "Validando Usuario ........";
	String pb_mensaje_descargando_tablas_maestras = "Descargando Datos Generales ........";
	String pb_mensaje_iniciando_descarga = "Iniciando descarga...";
	String pb_mensaje_descargando_tipo_resultado = "Descargando: Tipo Resultado...";
	String pb_mensaje_descargando_lista_verificacion_2 = "Descargando: Lista Verificación...";
	String pb_mensaje_descargando_categoria = "Descargando: Lista de Verificación Categoría...";
	String pb_mensaje_descargando_seccion = "Descargando: Lista Verificación Sección...";
	String pb_mensaje_descargando_pregunta = "Descargando: Lista Verificación Pregunta...";
	String pb_mensaje_descargando_resultado = "Descargando: Lista Verificación Resultado...";
	String pb_mensaje_descargando_empresa_especializada = "Descargando: Empresa Especializada...";
	String pb_mensaje_descargando_area = "Descargando: Área...";
	String pb_mensaje_descargando_turno = "Descargando: Turno...";
	String pb_mensaje_descargando_modulos = "Descargando Módulo(os) seleccionado(os) ........";
	String pb_mensaje_descargando_lista_verificacion = "Descargando Módulo Lista de Verificación...";
	String pb_mensaje_descargando_actos_and_condiciones = "Descargando Módulo Actos y Condiciones...";
	String pb_mensaje_descargando_ejecucion_sac = "Descargando Módulo Ejecución Sac";
	String pb_mensaje_descargando_verificacion_sac = "Descargando Módulo Verificacion Sac";

	String pb_mensaje_iniciando_subida = "Iniciando envío...";
	
	String error_ws = "Se produjo un error al conectarse al servidor";
	String error_procesar = "Se produjo un error al procesar la información";
	String error_validar_usuario = "El usuario y/o contraseña no son correctos";
	String error_procesar_lista_verificacion = "Se produjo un error al procesar LISTA DE VERIFICACIÓN";
	String error_procesar_lista_verificacion_categoria = "Se produjo un error al procesar LISTA DE VERIFICACIÓN CATEGORÍA";
	String error_procesar_lista_verificacion_seccion = "Se produjo un error al procesar LISTA DE VERIFICACIÓN SECCION";
	String error_procesar_lista_verificacion_pregunta = "Se produjo un error al procesar LISTA DE VERIFICACIÓN PREGUNTA";
	String error_procesar_lista_verificacion_resultado = "Se produjo un error al procesar LISTA DE VERIFICACIÓN RESULTADO";
	String error_procesar_lista_empresa_especializada = "Se produjo un error al procesar LISTA EMPRESA ESPECIALIZADA";
	String error_procesar_lista_area = "Se produjo un error al procesar LISTA AREA";
	String error_procesar_lista_turno = "Se produjo un error al procesar LISTA TURNO";
	String error_procesar_actos_and_condiciones = "Se produjo un error al procesar módulo actos y condiciones";
	String error_procesar_modulo_lista_verificacion_cab = "Se produjo un error al procesar los módulos lista de verificacion cab";
	String error_procesar_modulo_lista_verificacion_det = "Se produjo un error al procesar los módulos lista de verificacion det";
	String error_procesar_tipo_resultado = "Se produjo un error al procesar la lista TIPO RESULTADO";
	
	String mensaje_seleccionar_modulo = "Debe seleccionar un módulo";
	String mensaje_exito_descarga_modulos =  "La descarga de los módulos se proceso correctamente";
	String mensaje_error_descarga_modulos =  "Error al descargar los módulos";
	
	String pb_mensaje_numero_registros = "Nro de registros a enviar: ";

	String mensaje_nro_archivos= "Cantidad de archivos a procesar:";
    String mensaje_nohay_archivos= "No hay archivos para procesar";
    String mensaje_cantidad_datos_procesados= "datos procesados";
    String mensaje_error_datos_procesados= "error al procesar los datos";
    String mensaje_mensaje_espera ="Espere mientras se procesa el archivo ......";

	String mensaje_guardar_informacion_exito = "Se guardo los datos con éxito...";
	String mensaje_informacion_sinvarientes = "La información no presenta cambios...";

	String mensaje_no_modulos_enviar = "No existe módulos para enviar al servidor.";
	String mensaje_existe_modulos_por_enviar = "Existen módulos que faltan enviar al servidor.";
	String mensaje_no_existe_módulos ="No existen módulos almacenados.";

	String mensaje_cant_modulos_cab = "Cantidad de Módulos Descargados \n";
	String mensaje_modulo_lista_verificacion = 	 "Lista de Verificación     : ";
	String mensaje_modulo_actos_and_condiciones = "Actos y Condiciones   : ";
	String mensaje_modulo_ejecucion_sac = "Ejecución Sac: ";
	String mensaje_modulo_verificacion_sac = "Verificación Sac: ";

	String mensaje_no_hay_internet = "En estos momentos no hay conexion a Internet, vuelva a intentarlo...";
	String mensaje_datos_incompletos = "Debe ingresar el usuario y/o la contraseña";

	String mensaje_no_hay_informacion = "No hay información para mostrar.";
	String mensaje_borrando_modulos = "Espere mientras se borra los datos.....";
	String mensaje_borrado_completada = "Los datos han sido borrados con éxito.";

	String mensaje_actualizar_tm= "Se actualizaron las tablas maestras seleccionadas con éxito.";
	String mensaje_guardar_gps = "Se obtuvo la ubicación del registro.";

	String mensaje_exito_enviar_servidor = "Toda la información enviada al servidor se guardó con éxito";
	String mensaje_error_enviar_servidor = "Toda la información enviada al servidor no se guardó con éxito";

	String pb_mensaje_enviando_lista_verificacion = "Enviando:  ";
	String mensaje_exito_subida_modulos =  "El envío de los registros se proceso correctamente";
	String error_enviar_resgistro_generales = "Se produjo un error al enviar Registro Generales";
	String error_enviar_resgistro_resultado = "Se produjo un error al enviar Registro Resultado";
	String mensaje_error_enviar_modulos =  "Error al enviar los módulos";
}
