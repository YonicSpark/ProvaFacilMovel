package exemplo.restcliente.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import exemplo.restcliente.R;

public class SimplesAdapterListView1 extends BaseAdapter {
    private ArrayList<String> lista = new ArrayList<String>();
    private Context context;

    public void setLista(ArrayList<String> lista){
        this.lista = lista;
    }

    public SimplesAdapterListView1(Context context) {
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
        String itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_simples, parent, false);
        TextView t = (TextView) view.findViewById(R.id.tvNomeItem);
        t.setText(itemLista);
        return view;
    }
}
