package pe.dominiotech.movil.safe2biz.ops.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.dominiotech.movil.safe2biz.base.dao.SQLiteHelper;
import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.base.model.Turno;
import pe.dominiotech.movil.safe2biz.exception.LoginException;
import pe.dominiotech.movil.safe2biz.model.ResultadoBean;
import pe.dominiotech.movil.safe2biz.model.SeccionCompletaBean;
import pe.dominiotech.movil.safe2biz.model.UnidadBean;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifCategoria;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifPregunta;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifSeccion;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroResultado;
import pe.dominiotech.movil.safe2biz.ops.model.TipoResultadoBean;
import pe.dominiotech.movil.safe2biz.utils.LogApp;

public class ListaVerificacionDao extends SQLiteHelper {

	public ListaVerificacionDao(Context context, String name, CursorFactory factory,
								int version) {
		super(context, name, factory, version);
	}
	
	public List<UnidadBean> getUnidadBeanList() {
		List<UnidadBean> unidadBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<UnidadBean, String> unidadDao = createDao(UnidadBean.class);
				List<UnidadBean> unidadList = unidadDao.queryForAll();
				if(unidadList != null && unidadList.size() > 0){
					unidadBeanList = unidadList;
				}else{
					return null;
				}

				return unidadBeanList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		}
	}

	public Area refreshArea(Area area){

        Dao areaDao;
        try {
            areaDao = getDao(area.getClass());
            areaDao.refresh(area);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return area;
    }

    public EmpresaEspecializada refreshEmpresa(EmpresaEspecializada empresa){

        Dao empresaDao = null;
        EmpresaEspecializada empresa1 = new EmpresaEspecializada();
        try {
            empresaDao = getDao(empresa.getClass());
            empresa1 = (EmpresaEspecializada) empresaDao.queryForId(empresa.getFb_empresa_especializada_id());
            empresaDao.refresh(empresa1);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return empresa1;
    }

	public List<ListaVerificacion> getListaVerificacionList() {
		List<ListaVerificacion> listaVerificacionList = new ArrayList<>();
		try {
			RuntimeExceptionDao<ListaVerificacion, String> listaVerificacionDao = createDao(ListaVerificacion.class);
			List<ListaVerificacion> listVerificacionList = listaVerificacionDao.queryForAll();
			if(listVerificacionList != null && listVerificacionList.size() > 0){
				listaVerificacionList = listVerificacionList;
			}else{
				return null;
			}

			return listaVerificacionList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		}
	}

	public List<Turno> getTurnoBeanList() {
		List<Turno> turnoBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<Turno, String> turnoDao = createDao(Turno.class);
			List<Turno> turnoList = turnoDao.queryForAll();
			if(turnoList != null && turnoList.size() > 0){
				turnoBeanList = turnoList;
			}else{
				return null;
			}

			return turnoBeanList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		}
	}

	public List<Area> getAreaBeanList() {
		List<Area> areaBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<Area, String> areaDao = createDao(Area.class);
			List<Area> areaList = areaDao.queryForAll();
			if(areaList != null && areaList.size() > 0){
				areaBeanList = areaList;
			}else{
				return null;
			}

			return areaBeanList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		}
	}

	public EmpresaEspecializada getEmpresaEspecializada(String ruc) {
		EmpresaEspecializada empresaEspecializada = new EmpresaEspecializada();
		try {
			RuntimeExceptionDao<EmpresaEspecializada, String> empresaEspecializadaDao = createDao(EmpresaEspecializada.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ruc_empresa", ruc);
			List<EmpresaEspecializada> empresaEspecializadaList = empresaEspecializadaDao.queryForFieldValues(fields);
			if(empresaEspecializadaList != null && empresaEspecializadaList.size() > 0){
				empresaEspecializada = empresaEspecializadaList.get(0);
			}else{
				return null;
			}
			return empresaEspecializada;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("getEmpresaEspecializadaDao.error");
		}
	}

	public List<EmpresaEspecializada> getEmpresaEspecializadaList() {
		List<EmpresaEspecializada> empresaEspecializadaList;
		try {
			RuntimeExceptionDao<EmpresaEspecializada, String> empresaDao = createDao(EmpresaEspecializada.class);
			List<EmpresaEspecializada> empresaList = empresaDao.queryForAll();
			if(empresaList != null && empresaList.size() > 0){
				empresaEspecializadaList = empresaList;
			}else{
				return null;
			}

			return empresaEspecializadaList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		}
	}

	public void guardarRegistroGenerales(RegistroGenerales registroGenerales) {
		try {
			RuntimeExceptionDao<RegistroGenerales, String> checkListCabeceraDao = createDao(RegistroGenerales.class);
			checkListCabeceraDao.createOrUpdate(registroGenerales);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("guardarCheckListCabeceraDao.error");
		}
	}

	public RegistroGenerales getCheckListCabecera(String codCheckListCabecera){
		RegistroGenerales cabeceraBean = new RegistroGenerales();
		try {
			RuntimeExceptionDao<RegistroGenerales, String> cabeceraDao = createDao(RegistroGenerales.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_registro_generales_id", codCheckListCabecera);
			List<RegistroGenerales> cabeceraList = cabeceraDao.queryForFieldValues(fields);
			if(cabeceraList != null && cabeceraList.size() > 0){
				cabeceraBean = cabeceraList.get(0);
			}else{
				return null;
			}
			return cabeceraBean;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCheckListCabecera.error");
		}
	}

	public List<RegistroGenerales> getCheckListCabeceraList(String codEncuestaAutorizada) {
		List<RegistroGenerales> cabeceraBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<RegistroGenerales, String> cabeceraDao = createDao(RegistroGenerales.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verificacion_id", codEncuestaAutorizada);
			List<RegistroGenerales> cabeceraList = cabeceraDao.queryForFieldValues(fields);
			if(cabeceraList != null && cabeceraList.size() > 0){
				cabeceraBeanList = cabeceraList;
			}else{
				return null;
			}

			return cabeceraBeanList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCheckListCabeceraList.error");
		}
	}

    public List<RegistroGenerales> getRegistrosGeneralesList() {
        List<RegistroGenerales> cabeceraBeanList = new ArrayList<>();
        try {
            RuntimeExceptionDao<RegistroGenerales, String> cabeceraDao = createDao(RegistroGenerales.class);
            Map<String, Object> fields = new HashMap<String, Object>();
            List<RegistroGenerales> cabeceraList = cabeceraDao.queryForAll();
            if(cabeceraList != null && cabeceraList.size() > 0){
                cabeceraBeanList = cabeceraList;
            }else{
                return null;
            }

            return cabeceraBeanList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new LoginException("ListaVerificacionDao-getCheckListCabeceraList.error");
        }
    }

	public List<ListaVerifCategoria> getCategoriaList(String codListaVerificacion, int codCabecera) {
		List<ListaVerifCategoria> categoriaBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<ListaVerifCategoria, String> categoriaDao = createDao(ListaVerifCategoria.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verificacion_id", codListaVerificacion);
			List<ListaVerifCategoria> categoriaList = categoriaDao.queryForFieldValues(fields);
			if(categoriaList != null && categoriaList.size() > 0){
				for (int i =0; i<categoriaList.size(); i++){
					ListaVerifCategoria listaVerifCategoria = new ListaVerifCategoria();
					listaVerifCategoria = categoriaList.get(i);
					List<ListaVerifSeccion> listaVerifSeccionList = getSeccionList(listaVerifCategoria.getOps_lista_verif_categoria_id()+"", listaVerifCategoria.getOps_lista_verificacion_id()+"");
					List<ListaVerifSeccion> seccionList = new ArrayList<>();
					for (int j = 0; j< listaVerifSeccionList.size(); j++){
						ListaVerifSeccion listaVerifSeccion = listaVerifSeccionList.get(j);
						SeccionCompletaBean seccionCompletaBean = getSeccionCompletaBean(codCabecera, listaVerifSeccion.getOps_lista_verif_seccion_id(), listaVerifSeccion.getOps_lista_verif_categoria_id());
						if (null != seccionCompletaBean){
							if(1 == seccionCompletaBean.getFlagCompleto()){
								listaVerifSeccion.setFlagSeccionCompleta(1);
							}
						}
						seccionList.add(listaVerifSeccion);
					}
					listaVerifCategoria.setSeccionList(seccionList);
					categoriaBeanList.add(listaVerifCategoria);
				}
			}else{
				return null;
			}
			return categoriaBeanList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCategoriaAndSeccion.error");
		}
	}

	public List<ListaVerifCategoria> getListaVerifCategorias(Long codListaVerificacion) {
		List<ListaVerifCategoria> categoriaBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<ListaVerifCategoria, String> categoriaDao = createDao(ListaVerifCategoria.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verificacion_id", codListaVerificacion);
			List<ListaVerifCategoria> categoriaList = categoriaDao.queryForFieldValues(fields);
			if(categoriaList != null && categoriaList.size() > 0){
				for (int i =0; i<categoriaList.size(); i++){
					ListaVerifCategoria listaVerifCategoria = new ListaVerifCategoria();
					listaVerifCategoria = categoriaList.get(i);
					List<ListaVerifSeccion> listaVerifSeccionList = getSeccionList(listaVerifCategoria.getOps_lista_verif_categoria_id()+"", listaVerifCategoria.getOps_lista_verificacion_id()+"");
                    List<ListaVerifSeccion> seccionList = new ArrayList<>();
					for (int j = 0; j< listaVerifSeccionList.size(); j++){
						ListaVerifSeccion listaVerifSeccion = listaVerifSeccionList.get(j);
                        List<ListaVerifPregunta> listaVerifPreguntaList = getPreguntaList(listaVerifSeccion);
                        listaVerifSeccion.setListaPreguntas(listaVerifPreguntaList);

						seccionList.add(listaVerifSeccion);
					}
					listaVerifCategoria.setSeccionList(seccionList);
					categoriaBeanList.add(listaVerifCategoria);
				}
			}else{
				return null;
			}
			return categoriaBeanList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCategoriaAndSeccion.error");
		}
	}

	private List<ListaVerifSeccion> getSeccionList(String codListaCategoriaId, String codListaVerificacion) {
		List<ListaVerifSeccion> seccionBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<ListaVerifSeccion, String> seccionDao = createDao(ListaVerifSeccion.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verif_categoria_id", codListaCategoriaId);
			fields.put("ops_lista_verificacion_id", codListaVerificacion);
			List<ListaVerifSeccion> seccionList = seccionDao.queryForFieldValues(fields);
			if(seccionList != null && seccionList.size() > 0){
				seccionBeanList = seccionList;
			}else{
				return null;
			}
			return seccionBeanList;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCategoriaAndSeccion.error");
		}
	}

	public List<ListaVerifPregunta> getPreguntaList(ListaVerifSeccion listaVerifSeccion) {
		List<ListaVerifPregunta> encuestaBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<ListaVerifPregunta, String> encuestaDao = createDao(ListaVerifPregunta.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verif_seccion_id", listaVerifSeccion.getOps_lista_verif_seccion_id());
			fields.put("ops_lista_verif_categoria_id", listaVerifSeccion.getOps_lista_verif_categoria_id());
			fields.put("ops_lista_verificacion_id", listaVerifSeccion.getOps_lista_verificacion_id());
			List<ListaVerifPregunta> encuestaList = encuestaDao.queryForFieldValues(fields);
			if(encuestaList != null && encuestaList.size() > 0){
				encuestaBeanList = encuestaList;
			}else{
				return null;
			}
			return encuestaBeanList;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getPreguntaList.error");
		}
	}

	public RegistroResultado getRegistroResultado(int opsRegistroGeneralesId, Long opsListaVerifPreguntaId){
		RegistroResultado detalleBean;
		try {
			RuntimeExceptionDao<RegistroResultado, String> detalleDao = createDao(RegistroResultado.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_registro_generales_id", opsRegistroGeneralesId);
			fields.put("ops_lista_verif_pregunta_id", opsListaVerifPreguntaId);
			List<RegistroResultado> detalleList = detalleDao.queryForFieldValues(fields);
			if(detalleList != null && detalleList.size() > 0){
				detalleBean = detalleList.get(0);
			}else{
                RegistroResultado registroResultado = new RegistroResultado();
                registroResultado.setOpsRegistroGeneralesId(opsRegistroGeneralesId);
                registroResultado.setOpsListaVerifPreguntaId(opsListaVerifPreguntaId);
				return registroResultado;
			}
			return detalleBean;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCheckListCabecera.error");
		}
	}

	public void guardarRegistroResultado(RegistroResultado detalle) {
		try {
			RuntimeExceptionDao<RegistroResultado, String> checkListDetalleDao = createDao(RegistroResultado.class);
			checkListDetalleDao.createOrUpdate(detalle);
            RegistroGenerales registroGenerales = getRegistroByResultado(detalle).get(0);
            Log.d("Igual", Integer.toString(getListaPreguntasByRegistro(registroGenerales).size() ));
            Log.d("Igual", Integer.toString(getListaResultadosByRegistro(registroGenerales).size() ));

            if (getListaPreguntasByRegistro(registroGenerales).size() == getListaResultadosByRegistro(registroGenerales).size()){
                Log.d("Igual", "iguales");
                registroGenerales.setFlag("1");
                guardarRegistroGenerales(registroGenerales);
            }
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ListaVerificacionDao-guardarRegistroResultado.error");
		}
	}
	public List<RegistroGenerales> getRegistroByResultado(RegistroResultado registroResultado){
        List<RegistroGenerales> encuestaBeanList;
        try {
            RuntimeExceptionDao<RegistroGenerales, String> encuestaDao = createDao(RegistroGenerales.class);
            Map<String, Object> fields = new HashMap<>();
            fields.put("ops_registro_generales_id", registroResultado.getOpsRegistroGeneralesId());
            List<RegistroGenerales> encuestaList = encuestaDao.queryForFieldValues(fields);
            if(encuestaList != null && encuestaList.size() > 0){
                encuestaBeanList = encuestaList;
            }else{
                return null;
            }
            return encuestaBeanList;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new LoginException("ListaVerificacionDao-getPreguntaList.error");
        }
    }

    public List<RegistroResultado> getListaResultadosByRegistro(RegistroGenerales registro){
        List<RegistroResultado> encuestaBeanList;
        try {
            RuntimeExceptionDao<RegistroResultado, String> encuestaDao = createDao(RegistroResultado.class);
            Map<String, Object> fields = new HashMap<>();
            fields.put("ops_registro_generales_id", registro.getOps_registro_generales_id());
            List<RegistroResultado> encuestaList = encuestaDao.queryForFieldValues(fields);
            if(encuestaList != null && encuestaList.size() > 0){
                encuestaBeanList = encuestaList;
            }else{
                return null;
            }
            return encuestaBeanList;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new LoginException("ListaVerificacionDao-getPreguntaList.error");
        }
    }

	public List<ListaVerifPregunta> getListaPreguntasByRegistro(RegistroGenerales registro){
        List<ListaVerifPregunta> encuestaBeanList;
        try {
            RuntimeExceptionDao<ListaVerifPregunta, String> encuestaDao = createDao(ListaVerifPregunta.class);
            Map<String, Object> fields = new HashMap<>();
            fields.put("ops_lista_verificacion_id", registro.getOpsListaVerificacionId());
            List<ListaVerifPregunta> encuestaList = encuestaDao.queryForFieldValues(fields);
            if(encuestaList != null && encuestaList.size() > 0){
                encuestaBeanList = encuestaList;
            }else{
                return null;
            }
            return encuestaBeanList;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new LoginException("ListaVerificacionDao-getPreguntaList.error");
        }
    }

	public int updateCheckListDetalle(RegistroResultado detalle) {
		try {
			RuntimeExceptionDao<RegistroResultado, String> detalleDao = createDao(RegistroResultado.class);
			int row = detalleDao.update(detalle);
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ListaVerificacionDao-updateCheckListDetalle.error");
		}
	}

	public List<RegistroResultado> getDetalleXSeccionList(int codCabecera, Long seccionId) {
		List<RegistroResultado> detalleBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<RegistroResultado, String> detalleDao = createDao(RegistroResultado.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_registro_generales_id", codCabecera);
			fields.put("ops_lista_verif_seccion_id", seccionId);
			List<RegistroResultado> detalleList = detalleDao.queryForFieldValues(fields);
			if(detalleList != null && detalleList.size() > 0){
				detalleBeanList = detalleList;
			}else{
				return null;
			}
			return detalleBeanList;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getDetalleXSeccionList.error");
		}
	}

	public List<ListaVerifPregunta> getPreguntaAndRespuestasList(ListaVerifSeccion listaVerifSeccion, int codCabecera) {
		List<ListaVerifPregunta> encuestaBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<ListaVerifPregunta, String> encuestaDao = createDao(ListaVerifPregunta.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verif_seccion_id", listaVerifSeccion.getOps_lista_verif_seccion_id());
			fields.put("ops_lista_verif_categoria_id", listaVerifSeccion.getOps_lista_verif_categoria_id());
			fields.put("ops_lista_verificacion_id", listaVerifSeccion.getOps_lista_verificacion_id());
			List<ListaVerifPregunta> encuestaList = encuestaDao.queryForFieldValues(fields);
			if(encuestaList != null && encuestaList.size() > 0){
				for (int i =0; i<encuestaList.size(); i++){
					ListaVerifPregunta listaVerifPregunta = new ListaVerifPregunta();
					listaVerifPregunta = encuestaList.get(i);
					RegistroResultado detalleBean = getRegistroResultado(codCabecera, listaVerifPregunta.getOps_lista_verif_pregunta_id());
					if(null != detalleBean){
						listaVerifPregunta.setOpsListaVerifResultadoId(detalleBean.getOpsListaVerifResultadoId());
						if(!"".equals(detalleBean.getObservacion()) && null != detalleBean.getObservacion()){
							listaVerifPregunta.setFlagObservacion(1);
						}else {
							listaVerifPregunta.setFlagObservacion(0);
						}
						if(!"".equals(detalleBean.getRuta()) && null != detalleBean.getRuta()){
							listaVerifPregunta.setFlagFoto(1);
						}else{
							listaVerifPregunta.setFlagFoto(0);
						}
					}
					encuestaBeanList.add(listaVerifPregunta);
				}
			}else{
				return null;
			}
			return encuestaBeanList;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getPreguntaAndRespuestasList.error");
		}
	}

	public int getTotalPreguntasXSeccion(ListaVerifSeccion listaVerifSeccion) {
		int totalPreguntasXSeccion = -1;
		try {
			RuntimeExceptionDao<ListaVerifPregunta, String> encuestaDao = createDao(ListaVerifPregunta.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verif_seccion_id", listaVerifSeccion.getOps_lista_verif_seccion_id());
			fields.put("ops_lista_verif_categoria_id", listaVerifSeccion.getOps_lista_verif_categoria_id());
			fields.put("ops_lista_verificacion_id", listaVerifSeccion.getOps_lista_verificacion_id());
			List<ListaVerifPregunta> encuestaList = encuestaDao.queryForFieldValues(fields);
			if(encuestaList != null && encuestaList.size() > 0){
				totalPreguntasXSeccion = encuestaList.size();
			}else{
				return totalPreguntasXSeccion;
			}
			return totalPreguntasXSeccion;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getTotalPreguntasXSeccion.error");
		}
	}

	public int getTotalRespuestasXSeccion(ListaVerifSeccion listaVerifSeccion, int codCabecera) {
		int totalRespuestasXSeccion = -1;
		List<ListaVerifPregunta> encuestaBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<ListaVerifPregunta, String> encuestaDao = createDao(ListaVerifPregunta.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_lista_verif_seccion_id", listaVerifSeccion.getOps_lista_verif_seccion_id());
			fields.put("ops_lista_verif_categoria_id", listaVerifSeccion.getOps_lista_verif_categoria_id());
			fields.put("ops_lista_verificacion_id", listaVerifSeccion.getOps_lista_verificacion_id());
			List<ListaVerifPregunta> encuestaList = encuestaDao.queryForFieldValues(fields);
			if(encuestaList != null && encuestaList.size() > 0){
				for (int i =0; i<encuestaList.size(); i++){
					ListaVerifPregunta listaVerifPregunta = new ListaVerifPregunta();
					listaVerifPregunta = encuestaList.get(i);
					RegistroResultado detalleBean = getRegistroResultado(codCabecera, listaVerifPregunta.getOps_lista_verif_pregunta_id());
					if(null != detalleBean){
						encuestaBeanList.add(listaVerifPregunta);
					}
				}
				totalRespuestasXSeccion = encuestaBeanList.size();
			}else{
				return totalRespuestasXSeccion;
			}
			return totalRespuestasXSeccion;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getTotalRespuestasXSeccion.error");
		}
	}

	public int guardarSeccionCompleta(SeccionCompletaBean seccionCompletaBean) {
		try {
			int isSavaSeccionCompleta = -1;
			RuntimeExceptionDao<SeccionCompletaBean, String> seccionCompletaDao = createDao(SeccionCompletaBean.class);

			SeccionCompletaBean secCompletBean = getSeccionCompletaBean(seccionCompletaBean.getOpsRegistroGeneralesId(), seccionCompletaBean.getOpsListaVerifSeccionId(), seccionCompletaBean.getOpsListaVerifCategoriaId());
			if(null == secCompletBean){
				SeccionCompletaBean secCompletaBean = seccionCompletaDao.createIfNotExists(seccionCompletaBean);
				if (null != secCompletaBean){
					isSavaSeccionCompleta = 1;
				}
			}else {
				isSavaSeccionCompleta = 1;
			}
			return isSavaSeccionCompleta;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ListaVerificacionDao-guardarSeccionCompleta.error");
		}
	}

	public SeccionCompletaBean getSeccionCompletaBean(int codCabecera, Long opsListaVerifSeccionId, Long opsListaVerifCategoriaId){
		SeccionCompletaBean seccionCompletaBean = new SeccionCompletaBean();
		try {
			RuntimeExceptionDao<SeccionCompletaBean, String> seccionCompletaDao = createDao(SeccionCompletaBean.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_registro_generales_id", codCabecera);
			fields.put("ops_lista_verif_seccion_id", opsListaVerifSeccionId);
			fields.put("ops_lista_verif_categoria_id", opsListaVerifCategoriaId);
			List<SeccionCompletaBean> secCompletaList = seccionCompletaDao.queryForFieldValues(fields);
			if(secCompletaList != null && secCompletaList.size() > 0){
				seccionCompletaBean = secCompletaList.get(0);
			}else{
				return null;
			}
			return seccionCompletaBean;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getSeccionCompletaBean.error");
		}
	}

	public int borrarUnidades() {
		int isDeleteUnidadesCompleta = -1;
		try {
			RuntimeExceptionDao<UnidadBean, String> unidadBeanDao = createDao(UnidadBean.class);
			DeleteBuilder<UnidadBean, String> deleteUnidadBuilder = unidadBeanDao.deleteBuilder();
			isDeleteUnidadesCompleta = deleteUnidadBuilder.delete();
			return  isDeleteUnidadesCompleta;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-borrarUnidades.error");
		}
	}

	public int guardarListaUnidades(List<UnidadBean> unidadBeanList) {
		int isSaveListaUni = 1;
		try {
			if (unidadBeanList != null) {
				RuntimeExceptionDao<UnidadBean, String> unidadDao = createDao(UnidadBean.class);
				for (UnidadBean unidadBean : unidadBeanList) {
					unidadDao.createOrUpdate(unidadBean);
				}
			}
			return isSaveListaUni;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ListaVerificacionDao-guardarListaUnidades.error");
		}
	}

	public int guardarListaTipoResultado (List<TipoResultadoBean> lista) {
		int isSaveListaTipoResultado = 0;
		try{
			if (lista != null) {
				RuntimeExceptionDao<TipoResultadoBean, String> tipoResultadoDao = createDao(TipoResultadoBean.class);
				for (TipoResultadoBean tipoResultadoBean : lista) {
					tipoResultadoDao.createOrUpdate(tipoResultadoBean);
				}
			}
			return isSaveListaTipoResultado;
		}catch (SQLException e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaTipoResultado sqlException "+e.getLocalizedMessage());
		}catch (Exception e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaTipoResultado "+e.getLocalizedMessage());
		}
		return isSaveListaTipoResultado;
	}

    public int guardarListaVerificacion (List<ListaVerificacion> lista) {
        int isSaveListaVerificacion = 0;
        try{
            if (lista != null) {
                RuntimeExceptionDao<ListaVerificacion, String> listaVerificacionDao = createDao(ListaVerificacion.class);
                for (ListaVerificacion listaVerificacionBean : lista) {
                    listaVerificacionDao.createOrUpdate(listaVerificacionBean);
                }
            }
            return isSaveListaVerificacion;
        }catch (SQLException e) {
            LogApp.log("[ListaVerificacionDao] error guardarListaVerificacion sqlException "+e.getLocalizedMessage());
        }catch (Exception e) {
            LogApp.log("[ListaVerificacionDao] error guardarListaVerificacion "+e.getLocalizedMessage());
        }
        return isSaveListaVerificacion;
    }

	public int guardarListaVerificacionCategoria (List<ListaVerifCategoria> lista) {
		int isSaveListaVerificacionCategoria = 0;
		try{
			if (lista != null) {
				RuntimeExceptionDao<ListaVerifCategoria, String> listaVerificacionCategoriaDao = createDao(ListaVerifCategoria.class);
				for (ListaVerifCategoria listaVerificacionCategoriaBean : lista) {
					listaVerificacionCategoriaDao.createOrUpdate(listaVerificacionCategoriaBean);
				}
			}
			return isSaveListaVerificacionCategoria;
		}catch (SQLException e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionCategoria sqlException "+e.getLocalizedMessage());
		}catch (Exception e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionCategoria "+e.getLocalizedMessage());
		}
		return isSaveListaVerificacionCategoria;
	}

	public int guardarListaVerificacionSeccion (List<ListaVerifSeccion> lista) {
		int isSaveListaVerificacionSeccion = 0;
		try{
			if (lista != null) {
				RuntimeExceptionDao<ListaVerifSeccion, String> listaVerificacionSeccionDao = createDao(ListaVerifSeccion.class);
				for (ListaVerifSeccion listaVerificacionSeccionBean : lista) {
					listaVerificacionSeccionDao.createOrUpdate(listaVerificacionSeccionBean);
				}
			}
			return isSaveListaVerificacionSeccion;
		}catch (SQLException e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionSeccion sqlException "+e.getLocalizedMessage());
		}catch (Exception e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionSeccion "+e.getLocalizedMessage());
		}
		return isSaveListaVerificacionSeccion;
	}

	public int guardarListaVerificacionPregunta (List<ListaVerifPregunta> lista) {
		int isSaveListaVerificacionPregunta = 0;
		try{
			if (lista != null) {
				RuntimeExceptionDao<ListaVerifPregunta, String> listaVerificacionPreguntaDao = createDao(ListaVerifPregunta.class);
				for (ListaVerifPregunta listaVerificacionPreguntaBean : lista) {
					listaVerificacionPreguntaDao.createOrUpdate(listaVerificacionPreguntaBean);
				}
			}
			return isSaveListaVerificacionPregunta;
		}catch (SQLException e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionPregunta sqlException "+e.getLocalizedMessage());
		}catch (Exception e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionPregunta "+e.getLocalizedMessage());
		}
		return isSaveListaVerificacionPregunta;
	}

	public int guardarListaVerificacionResultado (List<ResultadoBean> lista) {
		int isSaveListaVerificacionResultado = 0;
		try{
			if (lista != null) {
				RuntimeExceptionDao<ResultadoBean, String> listaVerificacionResultadoDao = createDao(ResultadoBean.class);
				for (ResultadoBean listaVerificacionResultadoaBean : lista) {
					listaVerificacionResultadoDao.createOrUpdate(listaVerificacionResultadoaBean);
				}
			}
			return isSaveListaVerificacionResultado;
		}catch (SQLException e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionResultado sqlException "+e.getLocalizedMessage());
		}catch (Exception e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaVerificacionResultado "+e.getLocalizedMessage());
		}
		return isSaveListaVerificacionResultado;
	}

	public int guardarListaEmpresaEspecializada (List<EmpresaEspecializada> lista) {
		int isSaveListaEmpresaEspecializada = 0;
		try{
			if (lista != null) {
				RuntimeExceptionDao<EmpresaEspecializada, String> listaEmpresaEspecializadaDao = createDao(EmpresaEspecializada.class);
				for (EmpresaEspecializada listaEmpresaEspecializada : lista) {
					listaEmpresaEspecializadaDao.createOrUpdate(listaEmpresaEspecializada);
				}
			}
			return isSaveListaEmpresaEspecializada;
		}catch (SQLException e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaEmpresaEspecializada sqlException "+e.getLocalizedMessage());
		}catch (Exception e) {
			LogApp.log("[ListaVerificacionDao] error guardarListaEmpresaEspecializada "+e.getLocalizedMessage());
		}
		return isSaveListaEmpresaEspecializada;
	}

    public int guardarListaArea (List<Area> lista) {
        int isSaveListaArea = 0;
        try{
            if (lista != null) {
                RuntimeExceptionDao<Area, String> listaAreaDao = createDao(Area.class);
                for (Area area : lista) {
                    listaAreaDao.createOrUpdate(area);
                }
            }
            return isSaveListaArea;
        }catch (SQLException e) {
            LogApp.log("[ListaVerificacionDao] error guardarListaArea sqlException "+e.getLocalizedMessage());
        }catch (Exception e) {
            LogApp.log("[ListaVerificacionDao] error guardarListaArea "+e.getLocalizedMessage());
        }
        return isSaveListaArea;
    }

    public int guardarListaTurno (List<Turno> lista) {
        int isSaveListaTurno = 0;
        try{
            if (lista != null) {
                RuntimeExceptionDao<Turno, String> listaTurnoDao = createDao(Turno.class);
                for (Turno turno : lista) {
                    listaTurnoDao.createOrUpdate(turno);
                }
            }
            return isSaveListaTurno;
        }catch (SQLException e) {
            LogApp.log("[ListaVerificacionDao] error guardarListaTurno sqlException "+e.getLocalizedMessage());
        }catch (Exception e) {
            LogApp.log("[ListaVerificacionDao] error guardarListaTurno "+e.getLocalizedMessage());
        }
        return isSaveListaTurno;
    }

	public int updateCheckListCabecera(RegistroGenerales cabecera) {
		try {
			RuntimeExceptionDao<RegistroGenerales, String> cabeceraDao = createDao(RegistroGenerales.class);
			int row = cabeceraDao.update(cabecera);
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ListaVerificacionDao-updateCheckListCabecera.error");
		}
	}

	public int getTotalCabeceraPorEnviar() {
		int totalCabecerasPorEnviar = -1;
		try {
			RuntimeExceptionDao<RegistroGenerales, String> cabeceraDao = createDao(RegistroGenerales.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("estado", 1);
			List<RegistroGenerales> cabeceraList = cabeceraDao.queryForFieldValues(fields);
			if(cabeceraList != null && cabeceraList.size() > 0){
				totalCabecerasPorEnviar = cabeceraList.size();
			}else{
				return totalCabecerasPorEnviar;
			}

			return totalCabecerasPorEnviar;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getTotalCabeceraPorEnviar.error");
		}
	}

	public List<RegistroGenerales> getCabeceraListPorEnviar() {
		List<RegistroGenerales> cabeceraList = new ArrayList<>();
		try {
			RuntimeExceptionDao<RegistroGenerales, String> cabeceraDao = createDao(RegistroGenerales.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("estado", 1);
			List<RegistroGenerales> cabecList = cabeceraDao.queryForFieldValues(fields);
			if(cabecList != null && cabecList.size() > 0){
				for (int i =0; i<cabecList.size(); i++){
					RegistroGenerales cabeceraBean = new RegistroGenerales();
					cabeceraBean = cabecList.get(i);
					List<RegistroResultado> detalleList = getDetalleListPorEnviar(cabeceraBean.getOps_registro_generales_id());
					if(detalleList != null && detalleList.size() > 0){
						cabeceraBean.setResultadoList(detalleList);
					}
					cabeceraList.add(cabeceraBean);
				}
			}else{
				return null;
			}

			return cabeceraList;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCabeceraListPorEnviar.error");
		}
	}

	public List<RegistroResultado> getDetalleListPorEnviar(int codCabecera){
		List<RegistroResultado> detalleBeanList = new ArrayList<>();
		try {
			RuntimeExceptionDao<RegistroResultado, String> detalleDao = createDao(RegistroResultado.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_registro_generales_id", codCabecera);
			List<RegistroResultado> detalleList = detalleDao.queryForFieldValues(fields);
			if(detalleList != null && detalleList.size() > 0){
				detalleBeanList = detalleList;
			}else{
				return null;
			}
			return detalleBeanList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getDetalleListPorEnviar.error");
		}
	}

	public int updateCheckListResultado2(String codResultado,  String idResultadoGenerado) {
		try {
			RegistroResultado detalleBean = getCheckListDetalle2(codResultado);
			detalleBean.setIdGeneradoSyncronizacion(idResultadoGenerado);
			RuntimeExceptionDao<RegistroResultado, String> detalleDao = createDao(RegistroResultado.class);
			int row = detalleDao.update(detalleBean);
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ListaVerificacionDao-updateCheckListResultado2.error");
		}
	}

	public RegistroResultado getCheckListDetalle2(String codResultado){
		RegistroResultado detalleBean = new RegistroResultado();
		try {
			RuntimeExceptionDao<RegistroResultado, String> detalleDao = createDao(RegistroResultado.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ops_registro_resultado_id", codResultado);
			List<RegistroResultado> detalleList = detalleDao.queryForFieldValues(fields);
			if(detalleList != null && detalleList.size() > 0){
				detalleBean = detalleList.get(0);
			}else{
				return null;
			}
			return detalleBean;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao-getCheckListDetalle2.error");
		}
	}

	public int updateCheckListCabecera2(String codCabecera,  String idResultadoGenerado) {
		try {
			RegistroGenerales cabeceraBean = getCheckListCabecera(codCabecera);
			cabeceraBean.setId_generado_syncronizacion(idResultadoGenerado);
			cabeceraBean.setEstado(2L);
			RuntimeExceptionDao<RegistroGenerales, String> cabeceraDao = createDao(RegistroGenerales.class);
			int row = cabeceraDao.update(cabeceraBean);
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ListaVerificacionDao-updateCheckListCabecera2.error");
		}
	}

}
