package com.example.navigationdrawer.ui.slideshow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationdrawer.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    RadioButton lunes, martes, miercoles, jueves, viernes, sabado, domingo;
    RadioButton a930, a11, a1230, a14, a1530, a17, a1830, a20, a2130;
    RadioButton cancha1, cancha2;
    TextView dia, hora, cancha, txtconsulta;
    EditText nombre;

    Button agendarfijo;

    StringRequest stringRequest;

    ProgressDialog progreso, pDIalog;

    RequestQueue request;

    String sql;


    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        agendarfijo=(Button)root.findViewById(R.id.btnagendarfijo);

        lunes=(RadioButton)root.findViewById(R.id.radiolunes);
        martes=(RadioButton)root.findViewById(R.id.radiomartes);
        miercoles=(RadioButton)root.findViewById(R.id.radiomiercoles);
        jueves=(RadioButton)root.findViewById(R.id.radiojueves);
        viernes=(RadioButton)root.findViewById(R.id.radioviernes);
        sabado=(RadioButton)root.findViewById(R.id.radiosabado);
        domingo=(RadioButton)root.findViewById(R.id.radiodomingo);

        a930=(RadioButton)root.findViewById(R.id.a930);
        a11=(RadioButton)root.findViewById(R.id.a11);
        a1230=(RadioButton)root.findViewById(R.id.a1230);
        a14=(RadioButton)root.findViewById(R.id.a14);
        a1530=(RadioButton)root.findViewById(R.id.a1530);
        a17=(RadioButton)root.findViewById(R.id.a17);
        a1830=(RadioButton)root.findViewById(R.id.a1830);
        a20=(RadioButton)root.findViewById(R.id.a20);
        a2130=(RadioButton)root.findViewById(R.id.a2130);

        cancha1=(RadioButton)root.findViewById(R.id.radiocancha1);
        cancha2=(RadioButton)root.findViewById(R.id.radiocancha2);

        nombre = (EditText)root.findViewById(R.id.txtnombre);

        dia=(TextView)root.findViewById(R.id.txtdia);
        hora=(TextView)root.findViewById(R.id.txthora);
        cancha=(TextView)root.findViewById(R.id.txtcancha);

        txtconsulta=(TextView)root.findViewById(R.id.txtconsulta);



        lunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.setText("2");
            }
        });

        martes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.setText("3");
            }
        });

        miercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.setText("4");
            }
        });

        jueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.setText("5");
            }
        });

        viernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.setText("6");
            }
        });

        sabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.setText("7");
            }
        });
        domingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.setText("1");
            }
        });


        a930.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a930");
            }
        });

        a11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a11");
            }
        });

        a1230.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a1230");
            }
        });

        a14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a14");
            }
        });

        a1530.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a1530");
            }
        });

        a17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a17");
            }
        });
        a1830.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a1830");
            }
        });
        a20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a20");
            }
        });
        a2130.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora.setText("a2130");
            }
        });


        cancha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancha.setText("1");


            }
        });
        cancha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancha.setText("2");
            }
        });

        agendarfijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sql="UPDATE turnos SET "+hora.getText().toString()+"= '"+nombre.getText().toString()+"' WHERE cancha= "+cancha.getText().toString()+" AND diasemana = "+dia.getText().toString()+"";
                txtconsulta.setText(sql);


                if(!lunes.isChecked()&&!martes.isChecked()&&!miercoles.isChecked()&&!jueves.isChecked()&&!viernes.isChecked()&&!sabado.isChecked()&&!domingo.isChecked()){
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }else if(!a930.isChecked()&&!a11.isChecked()&&!a1230.isChecked()&&!a14.isChecked()&&!a1530.isChecked()&&!a17.isChecked()&&!a1830.isChecked()&&!a20.isChecked()&&!a2130.isChecked()) {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();

                }else if(!cancha1.isChecked()&&!cancha2.isChecked()) {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();

                } else if(nombre.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }else{
                    actualizar();
                }



            }
        });


        request= Volley.newRequestQueue(getContext());





        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;



    }




    private void actualizar() {
        pDIalog= new ProgressDialog(getContext());
        pDIalog.setMessage("Cargando...");
        pDIalog.show();

        String url ="https://comprehensive-games.000webhostapp.com/login/fijo"+hora.getText().toString()+".php?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDIalog.hide();

                Toast.makeText(getContext(), "Se ha actualizado con exito", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                pDIalog.hide();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String horas =  nombre.getText().toString();
                String canchas =  cancha.getText().toString();
                String diasemanas =  dia.getText().toString();



                Map<String, String> parametros = new HashMap<>();


                parametros.put(hora.getText().toString(), horas);
                parametros.put("cancha", canchas);
                parametros.put("diasemana", diasemanas);


                return parametros;

            }
        };
        request.add(stringRequest);

    }

}