package pe.dominiotech.movil.safe2biz;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;

import com.androidnetworking.AndroidNetworking;

import java.util.Locale;

import pe.dominiotech.movil.safe2biz.base.dao.ParametroDao;
import pe.dominiotech.movil.safe2biz.base.dao.UsuarioDao;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;
import pe.dominiotech.movil.safe2biz.ops.dao.ListaVerificacionDao;
import pe.dominiotech.movil.safe2biz.service.ListaVerificacionService;
import pe.dominiotech.movil.safe2biz.service.ParametroService;
import pe.dominiotech.movil.safe2biz.service.SynchronizeService;
import pe.dominiotech.movil.safe2biz.service.UsuarioService;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.version.VersionService;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {

    private UsuarioBean usuarioBean;
    private String codigoDeIdioma;
    private Locale locale = null;
    private ActionBar actionBarPrincipal = null;

    private VersionService versionService;
    private UsuarioService usuarioService;
    private ListaVerificacionService listaVerificacionService;
    private SynchronizeService synchronizeService;
    private ParametroService parametroService;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        codigoDeIdioma = Locale.getDefault().getLanguage();
        String defaultLanguageCode = codigoDeIdioma;
        if ("es".equalsIgnoreCase(codigoDeIdioma)) {
            codigoDeIdioma = "";
        } else {
            codigoDeIdioma = "en";
            defaultLanguageCode = "en";
        }

        UsuarioDao usuarioDao = new UsuarioDao(this, AppConstants.DB_NAME, null, AppConstants.DB_VERSION);
        ListaVerificacionDao listaVerificacionDao = new ListaVerificacionDao(this, AppConstants.DB_NAME, null,AppConstants.DB_VERSION);
        ParametroDao parametroDao = new ParametroDao(this, AppConstants.DB_NAME, null,AppConstants.DB_VERSION);

        usuarioDao.updateVersion();
        versionService = new VersionService();
        usuarioService = new UsuarioService();
        listaVerificacionService = new ListaVerificacionService();
        synchronizeService = new SynchronizeService();
        parametroService = new ParametroService();

        versionService.setContext(this);
        usuarioService.setContext(this);
        listaVerificacionService.setContext(this);
        synchronizeService.setContext(this);
        parametroService.setContext(this);

        usuarioService.setUsuarioDao(usuarioDao);
        listaVerificacionService.setListaVerificacionDao(listaVerificacionDao);
        parametroService.setParametroDao(parametroDao);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();
        String lang = settings.getString(getString(R.string.pref_locale), defaultLanguageCode);
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }


    public UsuarioBean getUsuarioEnSesion() {
        return usuarioBean;
    }

    public void setUsuarioEnSesion(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public String getCodigoDeIdioma() {
        return codigoDeIdioma;
    }

    public void setCodigoDeIdioma(String codigoDeIdioma) {
        if ("es".equalsIgnoreCase(codigoDeIdioma)) {
            this.codigoDeIdioma = "";
        } else {
            this.codigoDeIdioma = codigoDeIdioma;
        }
    }

    public ActionBar getActionBarPrincipal() {
        return actionBarPrincipal;
    }

    public void setActionBarPrincipal(ActionBar actionBarPrincipal) {
        this.actionBarPrincipal = actionBarPrincipal;
    }


    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public VersionService getVersionService() {
        return versionService;
    }

    public ListaVerificacionService getListaVerificacionService() {
        return listaVerificacionService;
    }

    public SynchronizeService getSynchronizeService() {
        return synchronizeService;
    }

    public ParametroService getParametroService() {
        return parametroService;
    }
}