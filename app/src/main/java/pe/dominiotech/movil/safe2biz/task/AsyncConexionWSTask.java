package pe.dominiotech.movil.safe2biz.task;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.stream.JsonReader;

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;
import pe.dominiotech.movil.safe2biz.utils.LogApp;

public class AsyncConexionWSTask extends AsyncTask<ListaRutasWS, String, List<RespuestaWS>> {
    public interface AsyncTaskListener{
        void onCompletadoTask(List<RespuestaWS> resultado);
    }

    private AsyncTaskListener asyncTaskListener;
    private MainApplication app;
    private TextView messageBox;
    public AsyncConexionWSTask(MainApplication app, AsyncTaskListener asyncTaskListener, TextView messageBox){
        this.asyncTaskListener = asyncTaskListener;
        this.app = app;
        this.messageBox = messageBox;
    }

    public AsyncConexionWSTask(AsyncTaskListener asyncTaskListener){
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
        List<RespuestaWS> resultado = new ArrayList<RespuestaWS>();
        ListaRutasWS lista = params[0];
        for (ParametrosWS parametrows : lista.getParametros()) {
            JsonReader reader = null;
            ConexionWS conexion = new ConexionWS(app);
            RespuestaWS respuesta = new RespuestaWS();
            if (parametrows.getMetodo().equals(AppConstants.metodo_post)){
                this.publishProgress(parametrows.getMensaje());
                reader = conexion.conexionServidorPost(lista.getUserLogin(), lista.getUserPassword(), lista.getSystemRoot(), parametrows);
                respuesta = resultadoOperacion(reader, parametrows.getNombreMetodo());
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
        }

        return respuesta;
    }

    @Override
    protected void onPostExecute(List<RespuestaWS> result) {
        asyncTaskListener.onCompletadoTask(result);
    }
}