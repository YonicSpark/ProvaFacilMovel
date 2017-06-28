package exemplo.restcliente.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import exemplo.restcliente.MenuProvaActivity;
import exemplo.restcliente.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinhasProvasFragment extends Fragment {


    Button b1,b2,b3,b4,b5,b6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_opcao1, container, false);
        ((MenuProvaActivity) getActivity())
                .setActionBarTitle("Minhas Provas");
        b1 = (Button) view.findViewById(R.id.b1);
        b2 = (Button) view.findViewById(R.id.b2);
        b3 = (Button) view.findViewById(R.id.b3);
        b4 = (Button) view.findViewById(R.id.b4);
        b5 = (Button) view.findViewById(R.id.b5);
        b6 = (Button) view.findViewById(R.id.b6);

        return view;
    }
    public void onResume() {
        super.onResume();

    }





}
