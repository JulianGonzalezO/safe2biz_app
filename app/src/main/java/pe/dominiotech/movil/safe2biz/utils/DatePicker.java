package pe.dominiotech.movil.safe2biz.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;



public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int anhoElegido;
    private int mesElegido;
    private int diaElegido;
    private EditText Recurso;
    private Long max;
    private Long min;

    public static DatePicker nuevaInstancia(int anhoElegido, int mesElegido, int diaElegido, EditText recurso, Long max, Long min) {
        DatePicker dialogo = new DatePicker();
        dialogo.anhoElegido = anhoElegido;
        dialogo.mesElegido = mesElegido;
        dialogo.diaElegido = diaElegido;
        dialogo.Recurso = recurso;
        dialogo.max = max;
        dialogo.min = min;
        return dialogo;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, anhoElegido, mesElegido - 1, diaElegido);
        android.widget.DatePicker datePicker = datePickerDialog.getDatePicker();
        if (min != null){
            datePicker.setMinDate(min);
        }
        if (max != null){
            datePicker.setMaxDate(max);
        }

        return datePickerDialog;
    }

    public void onDateSet(android.widget.DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm+1, dd);
    }
    public void populateSetDate(int year, int month, int day) {
        String dayText = day+"";
        String monthText = month+"";
        if(dayText.length() == 1){
            dayText = "0"+dayText;
        }
        if(monthText.length() == 1){
            monthText = "0"+monthText;
        }
        Recurso.setText(dayText + "/" + monthText + "/" + year);
    }


}