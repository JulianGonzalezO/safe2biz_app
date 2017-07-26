package pe.dominiotech.movil.safe2biz.task;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.stream.JsonReader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.service.ListaVerificacionService;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.LogApp;

public class AsyncConexionUploadWSTask extends AsyncTask<ListaRutasWS, String, List<RespuestaWS>> {
    public interface AsyncUploadTaskListener{
        void onCompletadoTask(List<RespuestaWS> resultado);
    }

    private AsyncUploadTaskListener asyncTaskListener;
    private MainApplication app;
    private TextView messageBox;
    private ListaVerificacionService listaVerificacionService;

    public AsyncConexionUploadWSTask(MainApplication app, AsyncUploadTaskListener asyncTaskListener, TextView messageBox){
        this.asyncTaskListener = asyncTaskListener;
        this.app = app;
        this.messageBox = messageBox;
    }

    public AsyncConexionUploadWSTask(AsyncUploadTaskListener asyncTaskListener){
        this.asyncTaskListener = asyncTaskListener;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        String mensaje = values[0];
        messageBox.setText(mensaje);
    }

    @Override
    protected List<RespuestaWS> doInBackground(ListaRutasWS... params) {
        LogApp.log("[AsyncConexionTaskWS] doInBackground");
        listaVerificacionService = app.getListaVerificacionService();
        List<RespuestaWS> resultado = new ArrayList<RespuestaWS>();
        ListaRutasWS lista = params[0];
        int contador = 1;
        int totalEnviar = lista.getParametros().size();
        for (ParametrosWS parametrows : lista.getParametros()) {
            JsonReader reader = null;
            ConexionWS conexion = new ConexionWS(app);
            RespuestaWS respuesta = new RespuestaWS();
            if (parametrows.getMetodo().equals(AppConstants.metodo_post)){
                int actual = contador++;
                this.publishProgress(parametrows.getMensaje()+ " "+actual+" de "+ totalEnviar);
                reader = conexion.conexionServidorPost(lista.getUserLogin(), lista.getUserPassword(), lista.getSystemRoot(), parametrows);
                respuesta = resultadoOperacion(reader, parametrows.getNombreMetodo());
                String opsRegistroGeneralesId = respuesta.getIdentificador();
                List<ParametrosWS> parametrosResultadoWSList = obtenerParametrosResultadoWsList(parametrows.getParamWsList(), opsRegistroGeneralesId);
                boolean isSaveResultadoAll = false;
                for (ParametrosWS parametroResultadoWS : parametrosResultadoWSList){
                    JsonReader readerResultado = null;
                    ConexionWS conexionResultado = new ConexionWS(app);
                    RespuestaWS respuestaResultado = new RespuestaWS();

                    if (parametroResultadoWS.getMetodo().equals(AppConstants.metodo_post)){
                        readerResultado = conexionResultado.conexionServidorPost(lista.getUserLogin(), lista.getUserPassword(), lista.getSystemRoot(), parametroResultadoWS);
                        respuestaResultado = resultadoOperacion(readerResultado, parametroResultadoWS.getNombreMetodo());
                        String opsRegistroResultadoId;
                        if(null != respuestaResultado.getIdentificador()){
                            opsRegistroResultadoId = respuestaResultado.getIdentificador();
                            listaVerificacionService.updateCheckListResultado2(parametroResultadoWS.getIdentificador(), opsRegistroResultadoId);
                            isSaveResultadoAll = true;
                        }else{
                            resultado.add(respuestaResultado);
                            isSaveResultadoAll = false;
                            break;
                        }
                    }
                }
                if(isSaveResultadoAll){
                    listaVerificacionService.updateCheckListCabecera2(parametrows.getIdentificador(), opsRegistroGeneralesId);
                }else {
                    break;
                }
            }
            resultado.add(respuesta);
        }
        LogApp.log("[AsyncConexionTaskWS] respuesta "+resultado.size());
        return resultado;
    }

    private RespuestaWS resultadoOperacion(String rspta, String identificador, String tipoEnvio) {
        // TODO Auto-generated method stub
        RespuestaWS respuesta = app.getSynchronizeService().procesarMensajeEnvio(rspta);
        respuesta.setIdentificador(identificador);
        respuesta.setTipoEnvio(tipoEnvio);
        return respuesta;
    }

    private RespuestaWS resultadoOperacion(JsonReader reader, String accion) {
        // TODO Auto-generated method stub
        RespuestaWS respuesta = new RespuestaWS();
        if (accion.equals(AppConstants.procesar_tipo_resultado)){
            respuesta = app.getSynchronizeService().procesarTipoResultado(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_verificacion)){
            respuesta = app.getSynchronizeService().procesarListaVerificacion(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_verificacion_categoria)){
            respuesta = app.getSynchronizeService().procesarListaVerificacionCategoria(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_verificacion_seccion)){
            respuesta = app.getSynchronizeService().procesarListaVerificacionSeccion(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_verificacion_pregunta)){
            respuesta = app.getSynchronizeService().procesarListaVerificacionPregunta(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_verificacion_resultado)){
            respuesta = app.getSynchronizeService().procesarListaVerificacionResultado(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_empresa_especializada)){
            respuesta = app.getSynchronizeService().procesarListaEmpresaEspecializada(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_area)){
            respuesta = app.getSynchronizeService().procesarListaArea(reader, app);
        }else if (accion.equals(AppConstants.procesar_lista_turno)){
            respuesta = app.getSynchronizeService().procesarListaTurno(reader, app);
        }else if (accion.equals(AppConstants.procesar_registro_generales)){
            respuesta = app.getSynchronizeService().procesarRegistroGenerales(reader, app);
        }else if (accion.equals(AppConstants.procesar_registro_resultado)){
            respuesta = app.getSynchronizeService().procesarRegistroResultado(reader, app);
        }

        return respuesta;
    }

    @Override
    protected void onPostExecute(List<RespuestaWS> result) {
        asyncTaskListener.onCompletadoTask(result);
    }

    private List<ParametrosWS> obtenerParametrosResultadoWsList(List<ParametrosWS> paramWsList, String opsRegistroGeneralesId){
        List<ParametrosWS> newParametroWsList = new ArrayList<>();
        for (ParametrosWS parametrows : paramWsList) {
            NameValuePair nameValuePair = new BasicNameValuePair("ops_registro_generales_id", opsRegistroGeneralesId);
            parametrows.getParametros().add(4, nameValuePair);

            newParametroWsList.add(parametrows);
        }
        return newParametroWsList;
    }
}