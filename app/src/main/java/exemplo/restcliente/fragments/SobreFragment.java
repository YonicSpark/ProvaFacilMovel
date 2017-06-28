package exemplo.restcliente.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import exemplo.restcliente.MenuProvaActivity;
import exemplo.restcliente.R;
import exemplo.restcliente.adapters.AdapterListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_opcao2, container, false);
        ((MenuProvaActivity) getActivity())
                .setActionBarTitle("Sobre");

        return view;
    }


    private AlertDialog alerta;


}
