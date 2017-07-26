package pe.dominiotech.movil.safe2biz.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.dominiotech.movil.safe2biz.R;
import pe.dominiotech.movil.safe2biz.ayc.model.TipoRiesgo;
import pe.dominiotech.movil.safe2biz.base.model.Area;
import pe.dominiotech.movil.safe2biz.base.model.EmpresaEspecializada;
import pe.dominiotech.movil.safe2biz.base.model.Turno;

public class AdaptadorSpinner extends ArrayAdapter<Object> {

    private Context context;
    private int idCelda;
    private List<Object> datos;
    private String claseName;

    private ArrayList<EmpresaEspecializada> itemsAll;
    private ArrayList<EmpresaEspecializada> suggestions;

    public AdaptadorSpinner(Context context, int idCelda, List<Object> datos, String clase) {
        super(context, idCelda, datos);
        this.context = context;
        this.idCelda = idCelda;
        this.datos = datos;
        this.claseName = clase;
        if (clase.equals("Empresa")){
            this.itemsAll = new ArrayList<>();
            for (int i=0;i<datos.size();i++){
                System.out.println(datos.get(i));
                itemsAll.add((EmpresaEspecializada)datos.get(i));
            }
            this.suggestions = new ArrayList<>();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        try{
            opcionTexto texto = new opcionTexto();
            if(v == null){
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(idCelda,parent, false);
                texto.itemTexto = (TextView)v.findViewById(R.id.tvTexto);
                v.setTag(texto);
            }else {
                texto = (opcionTexto)v.getTag();
            }

            if (claseName.equals("RegistroGeneralesFormActivity")){
                String item = (String)datos.get(position);
                texto.itemTexto.setText(item);
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }else if (claseName.equals("TurnoClass")){
                Turno item = (Turno) datos.get(position);
                texto.itemTexto.setText(item.getName());
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }else if (claseName.equals("Area")){
                Area item = (Area) datos.get(position);
                texto.itemTexto.setText(item.getNombre());
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }else if (claseName.equals("Empresa")){
                EmpresaEspecializada item = (EmpresaEspecializada) datos.get(position);
                texto.itemTexto.setText(item.getRazon_social());
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }else if (claseName.equals("TipoRiesgo")){
                System.out.println("GetView");

                TipoRiesgo item = (TipoRiesgo) datos.get(position);
                texto.itemTexto.setText(item.getName());
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }
        }catch(Exception e){
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        try{
            opcionTexto texto = new opcionTexto();
            if(v == null){
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(idCelda, null);
                texto.itemTexto = (TextView)v.findViewById(R.id.tvTexto);
                v.setTag(texto);
            }else {
                texto = (opcionTexto)v.getTag();
            }

            if (claseName.equals("RegistroGeneralesFormActivity")){
                String item = (String)datos.get(position);
                texto.itemTexto.setText(item);
            }else if (claseName.equals("TurnoClass")){
                Turno item = (Turno) datos.get(position);
                texto.itemTexto.setText(item.getName());
            }else if (claseName.equals("Area")){
                Area item = (Area) datos.get(position);
                texto.itemTexto.setText(item.getNombre());
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }else if (claseName.equals("Empresa")){
                EmpresaEspecializada item = (EmpresaEspecializada) datos.get(position);
                texto.itemTexto.setText(item.getRazon_social());
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }else if (claseName.equals("TipoRiesgo")){
                System.out.println("Drop");
                TipoRiesgo item = (TipoRiesgo) datos.get(position);
                texto.itemTexto.setText(item.getName());
                texto.itemTexto.setTextColor(context.getResources().getColor(R.color.app_letra_tab));
            }
        }catch(Exception e){

        }
        return v;
    }

    private class opcionTexto{
        public TextView itemTexto;
    }

    @Override
    public Filter getFilter() {
        if (claseName.equals("Empresa")){

            return nameFilter;
        }
        return super.getFilter();
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((EmpresaEspecializada)(resultValue)).getRazon_social();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (EmpresaEspecializada customer : itemsAll) {
                    if(customer.getRazon_social().toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<EmpresaEspecializada> filteredList = (ArrayList<EmpresaEspecializada>) results.values;
            if(results.count > 0) {
                clear();
                for (EmpresaEspecializada c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
