package exemplo.restcliente.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import exemplo.restcliente.R;
import exemplo.restcliente.beans.Questao;

public class SimplesAdapterListView2 extends BaseAdapter {
    private List<Questao> lista;
    private Context context;

    public void setLista(List<Questao> lista){
        this.lista = lista;
    }

    public SimplesAdapterListView2(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String itemLista = lista.get(position).getQueEnunciado();
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_simples, parent, false);
        TextView t = (TextView) view.findViewById(R.id.tvNomeItem);
        t.setText(itemLista);
        return view;
    }
}
