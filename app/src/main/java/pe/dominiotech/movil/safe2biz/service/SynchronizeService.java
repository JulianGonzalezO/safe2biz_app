package pe.dominiotech.movil.safe2biz.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.base.dao.SynchronizeDao;
import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.base.model.Turno;
import pe.dominiotech.movil.safe2biz.model.ResultadoBean;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifCategoria;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifPregunta;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifSeccion;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.TipoResultadoBean;
import pe.dominiotech.movil.safe2biz.task.RespuestaEnvio;
import pe.dominiotech.movil.safe2biz.task.RespuestaWS;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.LogApp;
import pe.dominiotech.movil.safe2biz.utils.Mensajes;
import pe.dominiotech.movil.safe2biz.utils.Util;

public class SynchronizeService extends AppService{
	protected static final String TAG = SynchronizeService.class.getSimpleName();
	private SynchronizeDao synchronizeDao;
	private ParametroService parametroService;
	private ListaVerificacionService listaVerificacionService;
	private int timeout = 4;

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

	public void setListaVerificacionService(ListaVerificacionService listaVerificacionService) {
		this.listaVerificacionService = listaVerificacionService;
	}

	public void setSynchronizeSQLite(SynchronizeDao synchronizeDao) {
		this.synchronizeDao = synchronizeDao;
	}

	public RespuestaWS procesarMensajeEnvio(String reader){
		LogApp.log("[SincronizarLogica] respuestaEnvio");
		RespuestaWS res = new RespuestaWS();
		if (!reader.equals(AppConstants.cadena_vacia)){
			try {
				Gson gson = new Gson();
				RespuestaEnvio resp = gson.fromJson(reader,RespuestaEnvio.class);
				res.setProcesado(true);
				res.setObjeto(resp);
			} catch (Exception e) {
				e.printStackTrace();
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar);

			}
			LogApp.log("[SincronizarLogica] validar usuario "+res.getObjeto().getClass().getSimpleName());
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarTipoResultado(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<TipoResultadoBean> listado = new ArrayList<TipoResultadoBean>(cantDatos);
				LogApp.log("[SincronizarLogica] TIPO_RESULTADO INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							TipoResultadoBean dir = gson.fromJson(reader, TipoResultadoBean.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaTipoResultado(listado);
								listado = new ArrayList<TipoResultadoBean>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaTipoResultado(listado);
				}
				LogApp.log("[SincronizarService] TIPO_RESULTADO cont "+cont);
				LogApp.log("[SincronizarService] TIPO_RESULTADO FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] TIPO_RESULTADO error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_tipo_resultado);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaVerificacion(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<ListaVerificacion> listado = new ArrayList<ListaVerificacion>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							ListaVerificacion dir = gson.fromJson(reader, ListaVerificacion.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaVerificacion(listado);
								listado = new ArrayList<ListaVerificacion>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaVerificacion(listado);
				}
				LogApp.log("[SincronizarService] LISTA_VERIFICACION cont "+cont);
				LogApp.log("[SincronizarService] LISTA_VERIFICACION FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_verificacion);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaVerificacionCategoria(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<ListaVerifCategoria> listado = new ArrayList<ListaVerifCategoria>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_CATEGORIA INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							ListaVerifCategoria dir = gson.fromJson(reader, ListaVerifCategoria.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaVerificacionCategoria(listado);
								listado = new ArrayList<ListaVerifCategoria>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaVerificacionCategoria(listado);
				}
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_CATEGORIA cont "+cont);
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_CATEGORIA FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_CATEGORIA error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_verificacion_categoria);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaVerificacionSeccion(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<ListaVerifSeccion> listado = new ArrayList<ListaVerifSeccion>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_SECCION INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							ListaVerifSeccion dir = gson.fromJson(reader, ListaVerifSeccion.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaVerificacionSeccion(listado);
								listado = new ArrayList<ListaVerifSeccion>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaVerificacionSeccion(listado);
				}
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_SECCION cont "+cont);
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_SECCION FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_SECCION error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_verificacion_seccion);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaVerificacionPregunta(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<ListaVerifPregunta> listado = new ArrayList<ListaVerifPregunta>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_PREGUNTA INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							ListaVerifPregunta dir = gson.fromJson(reader, ListaVerifPregunta.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaVerificacionPregunta(listado);
								listado = new ArrayList<ListaVerifPregunta>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaVerificacionPregunta(listado);
				}
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_PREGUNTA cont "+cont);
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_PREGUNTA FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_PREGUNTA error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_verificacion_pregunta);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaVerificacionResultado(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<ResultadoBean> listado = new ArrayList<ResultadoBean>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_RESULTADO INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							ResultadoBean dir = gson.fromJson(reader, ResultadoBean.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaVerificacionResultado(listado);
								listado = new ArrayList<ResultadoBean>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaVerificacionResultado(listado);
				}
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_RESULTADO cont "+cont);
				LogApp.log("[SincronizarService] LISTA_VERIFICACION_RESULTADO FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_VERIFICACION_RESULTADO error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_verificacion_resultado);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaEmpresaEspecializada(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<EmpresaEspecializada> listado = new ArrayList<EmpresaEspecializada>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_EMPRESA_ESPECIALIZADA INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							EmpresaEspecializada dir = gson.fromJson(reader, EmpresaEspecializada.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaEmpresaEspecializada(listado);
								listado = new ArrayList<EmpresaEspecializada>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaEmpresaEspecializada(listado);
				}
				LogApp.log("[SincronizarService] LISTA_EMPRESA_ESPECIALIZADA cont "+cont);
				LogApp.log("[SincronizarService] LISTA_EMPRESA_ESPECIALIZADA FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_EMPRESA_ESPECIALIZADA error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_empresa_especializada);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaArea(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<Area> listado = new ArrayList<Area>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_AREA INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							Area dir = gson.fromJson(reader, Area.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaArea(listado);
								listado = new ArrayList<Area>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaArea(listado);
				}
				LogApp.log("[SincronizarService] LISTA_AREA cont "+cont);
				LogApp.log("[SincronizarService] LISTA_AREA FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_AREA error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_area);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarListaTurno(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int cantDatos = 500;
		if (reader != null){
			Gson gson = new Gson();
			try {
				List<Turno> listado = new ArrayList<Turno>(cantDatos);
				LogApp.log("[SincronizarLogica] LISTA_TURNO INI "+ Util.obtenerFechaHora());
				int cont = 0;
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						while (reader.hasNext()) {
							Turno dir = gson.fromJson(reader, Turno.class);
							listado.add(dir);
							cont += 1;
							if (listado.size() == cantDatos) {
								app.getListaVerificacionService().guardarListaTurno(listado);
								listado = new ArrayList<Turno>(cantDatos);
							}
						}
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				if (listado.size()>0){
					app.getListaVerificacionService().guardarListaTurno(listado);
				}
				LogApp.log("[SincronizarService] LISTA_TURNO cont "+cont);
				LogApp.log("[SincronizarService] LISTA_TURNO FIN "+Util.obtenerFechaHora());
				res.setProcesado(true);
				res.setMensaje(AppConstants.cadena_vacia);
			} catch (Exception e) {
				LogApp.log("[SincronizarLogica] LISTA_TURNO error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_procesar_lista_turno);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarRegistroGenerales(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int opsRegistroGeneralesId = 0;
		if (reader != null){
			Gson gson = new Gson();
			try {
				LogApp.log("[SincronizarLogica] RESISTRO GENERALES ENVIO INI "+ Util.obtenerFechaHora());
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						reader.beginObject();
						while (reader.hasNext()) {
							name = reader.nextName();
							if (name.equals("ops_registro_generales_id")) {
								opsRegistroGeneralesId = reader.nextInt();
							}
						}
						reader.endObject();
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				LogApp.log("[SincronizarService] RESISTRO GENERALES ENVIO FIN "+Util.obtenerFechaHora());

				if(0 != opsRegistroGeneralesId){
					res.setProcesado(true);
					res.setMensaje(AppConstants.cadena_vacia);
					res.setIdentificador(opsRegistroGeneralesId+"");
				}else {
					res.setProcesado(false);
					res.setMensaje(Mensajes.error_enviar_resgistro_generales);
				}
			} catch (Exception e) {
				LogApp.log("[SynchronizeService] RESISTRO GENERALES ENVIO error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_enviar_resgistro_generales);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}

	public RespuestaWS procesarRegistroResultado(JsonReader reader, MainApplication app){
		RespuestaWS res = new RespuestaWS();
		int opsRegistroResultadoId = 0;
		if (reader != null){
			Gson gson = new Gson();
			try {
				LogApp.log("[SincronizarLogica] RESISTRO RESULTADO ENVIO INI "+ Util.obtenerFechaHora());
				reader.beginObject();
				if (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("data")){
						reader.beginArray();
						reader.beginObject();
						while (reader.hasNext()) {
							name = reader.nextName();
							if (name.equals("ops_registro_resultado_id")) {
								opsRegistroResultadoId = reader.nextInt();
							}
						}
						reader.endObject();
						reader.endArray();
					}
				}
				reader.endObject();
				reader.close();
				LogApp.log("[SincronizarService] RESISTRO RESULTADO ENVIO FIN "+Util.obtenerFechaHora());

				if(0 != opsRegistroResultadoId){
					res.setProcesado(true);
					res.setMensaje(AppConstants.cadena_vacia);
					res.setIdentificador(opsRegistroResultadoId+"");
				}else {
					res.setProcesado(false);
					res.setMensaje(Mensajes.error_enviar_resgistro_resultado);
				}
			} catch (Exception e) {
				LogApp.log("[SynchronizeService] RESISTRO RESULTADO ENVIO error al procesar la data "+e.getLocalizedMessage());
				res.setProcesado(false);
				res.setMensaje(Mensajes.error_enviar_resgistro_resultado);
			}
		}else{
			res.setProcesado(false);
			res.setMensaje(Mensajes.error_ws);
		}
		return res;
	}
}
