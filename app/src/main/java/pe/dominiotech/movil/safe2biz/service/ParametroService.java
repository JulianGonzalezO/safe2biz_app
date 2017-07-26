package pe.dominiotech.movil.safe2biz.service;

import pe.dominiotech.movil.safe2biz.base.dao.ParametroDao;

public class ParametroService extends AppService {
	protected static final String TAG = ParametroService.class.getSimpleName();
	
	private ParametroDao parametroDao;
	
	public void setParametroDao(ParametroDao parametroDao) {
		this.parametroDao = parametroDao;
	}

	public int modificarParametroMovil(String codigo, String valor) {
		return parametroDao.modificarParMovil(codigo, valor);
	}
}
