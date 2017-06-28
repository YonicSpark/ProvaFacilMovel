package exemplo.restcliente.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import exemplo.restcliente.R;

public class Opcao3Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.activity_manipulacao_prova, container, false);


        return view;
    }

    public void onResume() {
        super.onResume();

    }

}
