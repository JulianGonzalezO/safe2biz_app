package pe.dominiotech.movil.safe2biz.service;

import java.util.List;

import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.base.model.Turno;
import pe.dominiotech.movil.safe2biz.model.ResultadoBean;
import pe.dominiotech.movil.safe2biz.model.SeccionCompletaBean;
import pe.dominiotech.movil.safe2biz.model.UnidadBean;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifCategoria;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifPregunta;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerifSeccion;
import pe.dominiotech.movil.safe2biz.ops.model.ListaVerificacion;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroGenerales;
import pe.dominiotech.movil.safe2biz.ops.model.RegistroResultado;
import pe.dominiotech.movil.safe2biz.ops.model.TipoResultadoBean;

public class ListaVerificacionService extends AppService{
	
	private ListaVerificacionDao listaVerificacionDao;

	public void setListaVerificacionDao(ListaVerificacionDao listaVerificacionDao) {
		this.listaVerificacionDao = listaVerificacionDao;
	}

	public List<UnidadBean> getUnidadBeanList() {
		return listaVerificacionDao.getUnidadBeanList();
	}

	public List<ListaVerificacion> getListaVerificacionList() {
		return listaVerificacionDao.getListaVerificacionList();
	}

	public List<Turno> getTurnoBeanList() {
		return listaVerificacionDao.getTurnoBeanList();
	}

	public List<Area> getAreaBeanList() {
		return listaVerificacionDao.getAreaBeanList();
	}

	public EmpresaEspecializada getEmpresaEspecializada(String ruc) {
		return listaVerificacionDao.getEmpresaEspecializada(ruc);
	}

	public RegistroGenerales getCheckListCabecera(String codCheckListCabecera){
		return  listaVerificacionDao.getCheckListCabecera(codCheckListCabecera);
	}

	public List<RegistroGenerales> getCheckListCabeceraList(String codEncuestaAutorizada) {
		return listaVerificacionDao.getCheckListCabeceraList(codEncuestaAutorizada);
	}

	public List<ListaVerifCategoria> getCategoriaList(String codListaVerificacion, int codCabecera) {
		return listaVerificacionDao.getCategoriaList(codListaVerificacion, codCabecera);
	}

	public List<ListaVerifPregunta> getPreguntaList(ListaVerifSeccion listaVerifSeccion) {
		return listaVerificacionDao.getPreguntaList(listaVerifSeccion);
	}

	public RegistroResultado getCheckListDetalle(int opsRegistroGeneralesId, Long opsListaVerifPreguntaId){
		return  listaVerificacionDao.getRegistroResultado(opsRegistroGeneralesId, opsListaVerifPreguntaId);
	}

	public int updateCheckListDetalle(RegistroResultado detalle){
		return  listaVerificacionDao.updateCheckListDetalle(detalle);
	}

	public List<RegistroResultado> getDetalleXSeccionList(int codCabecera, Long seccionId) {
		return listaVerificacionDao.getDetalleXSeccionList(codCabecera, seccionId);
	}

	public List<ListaVerifPregunta> getPreguntaAndRespuestasList(ListaVerifSeccion listaVerifSeccion, int codCabecera) {
		return listaVerificacionDao.getPreguntaAndRespuestasList(listaVerifSeccion, codCabecera);
	}

	public int getTotalPreguntasXSeccion(ListaVerifSeccion listaVerifSeccion) {
		return listaVerificacionDao.getTotalPreguntasXSeccion(listaVerifSeccion);
	}

	public int getTotalRespuestasXSeccion(ListaVerifSeccion listaVerifSeccion, int codCabecera) {
		return listaVerificacionDao.getTotalRespuestasXSeccion(listaVerifSeccion, codCabecera);
	}

	public int guardarSeccionCompleta(SeccionCompletaBean seccionCompletaBean) {
		return listaVerificacionDao.guardarSeccionCompleta(seccionCompletaBean);
	}

	public int borrarUnidades() {
		return listaVerificacionDao.borrarUnidades();
	}

	public int guardarListaUnidades(List<UnidadBean> unidadBeanList) {
		return listaVerificacionDao.guardarListaUnidades(unidadBeanList);
	}

	public int guardarListaTipoResultado(List<TipoResultadoBean> lista) {
		return listaVerificacionDao.guardarListaTipoResultado(lista);
	}

	public int guardarListaVerificacion(List<ListaVerificacion> lista) {
		return listaVerificacionDao.guardarListaVerificacion(lista);
	}

	public int guardarListaVerificacionCategoria(List<ListaVerifCategoria> lista) {
		return listaVerificacionDao.guardarListaVerificacionCategoria(lista);
	}

	public int guardarListaVerificacionSeccion(List<ListaVerifSeccion> lista) {
		return listaVerificacionDao.guardarListaVerificacionSeccion(lista);
	}

	public int guardarListaVerificacionPregunta(List<ListaVerifPregunta> lista) {
		return listaVerificacionDao.guardarListaVerificacionPregunta(lista);
	}

	public int guardarListaVerificacionResultado(List<ResultadoBean> lista) {
		return listaVerificacionDao.guardarListaVerificacionResultado(lista);
	}

	public int guardarListaEmpresaEspecializada(List<EmpresaEspecializada> lista) {
		return listaVerificacionDao.guardarListaEmpresaEspecializada(lista);
	}

	public int guardarListaArea(List<Area> lista) {
		return listaVerificacionDao.guardarListaArea(lista);
	}

	public int guardarListaTurno(List<Turno> lista) {
		return listaVerificacionDao.guardarListaTurno(lista);
	}

	public int updateCheckListCabecera(RegistroGenerales registroGenerales){
		return listaVerificacionDao.updateCheckListCabecera(registroGenerales);
	}

	public int getTotalCabeceraPorEnviar() {
		return listaVerificacionDao.getTotalCabeceraPorEnviar();
	}

	public List<RegistroGenerales> getCabeceraListPorEnviar() {
		return listaVerificacionDao.getCabeceraListPorEnviar();
	}

	public int updateCheckListResultado2(String codResultado,  String idResultadoGenerado){
		return listaVerificacionDao.updateCheckListResultado2(codResultado, idResultadoGenerado);
	}

	public int updateCheckListCabecera2(String codCabecera,  String idResultadoGenerado){
		return listaVerificacionDao.updateCheckListCabecera2(codCabecera, idResultadoGenerado);
	}
}
