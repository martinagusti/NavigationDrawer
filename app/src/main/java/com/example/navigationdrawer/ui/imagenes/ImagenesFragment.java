package com.example.navigationdrawer.ui.imagenes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ImagenesFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {


    EditText campodocumento;
    TextView campocancha, txtdia, campofecha;
    EditText a930, a11, a1230, a14, a1530, a17, a1830, a20, a2130;
    ProgressDialog progreso, pDIalog;
    RadioButton cancha1, cancha2;
    Button btnactualizar, btnfecha, btnagendar;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    String data;

    StringRequest stringRequest;
    private int dia, mes, ano, diadelasemana;


    private ImagenesViewModel imagenesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        imagenesViewModel =
                ViewModelProviders.of(this).get(ImagenesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_imagenes, container, false);




        campofecha = (TextView) root.findViewById(R.id.txtfecha);
        a930 = (EditText) root.findViewById(R.id.a930);
        a11 = (EditText) root.findViewById(R.id.a11);
        a1230 = (EditText) root.findViewById(R.id.a1230);
        a14 = (EditText) root.findViewById(R.id.a14);
        a1530 = (EditText) root.findViewById(R.id.a1530);
        a17 = (EditText) root.findViewById(R.id.a17);
        a1830 = (EditText) root.findViewById(R.id.a1830);
        a20 = (EditText) root.findViewById(R.id.a20);
        a2130 = (EditText) root.findViewById(R.id.a2130);
        cancha1 = (RadioButton) root.findViewById(R.id.radiocancha1);
        cancha2 = (RadioButton) root.findViewById(R.id.radiocancha2);
        campocancha = (TextView) root.findViewById(R.id.txtcancha);
        btnactualizar = (Button) root.findViewById(R.id.btnactualizar);
        btnfecha = (Button) root.findViewById(R.id.btnfecha);
        txtdia = (TextView) root.findViewById(R.id.txtdia);






        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
                        campofecha.setText(dayofmonth + "/" + (monthofyear + 1) + "/" + year);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, monthofyear);
                        calendar1.set(Calendar.DAY_OF_MONTH, dayofmonth);
                        diadelasemana = calendar1.get(calendar1.DAY_OF_WEEK);


                        txtdia.setText("" + diadelasemana);


                    }
                }
                        , ano, mes, dia);
                datePickerDialog.show();

                cancha1.setChecked(false);
                cancha2.setChecked(false);

                campocancha.setText("");

                a930.setText("");
                a11.setText("");
                a1230.setText("");
                a14.setText("");
                a1530.setText("");
                a17.setText("");
                a1830.setText("");
                a20.setText("");
                a2130.setText("");


            }


        });


        cancha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campocancha.setText("1");
                if (campofecha.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                } else {
                    cargarWebService();

                }
                cancha1.setChecked(true);
                cancha2.setChecked(false);


            }
        });

        cancha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campocancha.setText("2");
                if (campofecha.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                } else {
                    cargarWebService();

                }
                cancha2.setChecked(true);
                cancha1.setChecked(false);


            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (campofecha.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                }else if(campocancha.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Seleccione una cancha", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setMessage("Seguro desea modificar los cambios?");

                    dialog.setCancelable(false);
                    dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            actualizar();


                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alerta = dialog.create();
                    alerta.setTitle("Actualizar datos");
                    alerta.show();

                }
            }


        });


        request = Volley.newRequestQueue(getContext());


        final TextView textView = root.findViewById(R.id.text_gallery);
        imagenesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void actualizar() {


            pDIalog = new ProgressDialog(getContext());
            pDIalog.setMessage("Cargando...");
            pDIalog.show();

            String url = "https://comprehensive-games.000webhostapp.com/login/registroturno.php?";

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
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String fecha = campofecha.getText().toString();
                    String cancha = campocancha.getText().toString();
                    String aa930 = a930.getText().toString();
                    String aa11 = a11.getText().toString();
                    String aa1230 = a1230.getText().toString();
                    String aa14 = a14.getText().toString();
                    String aa1530 = a1530.getText().toString();
                    String aa17 = a17.getText().toString();
                    String aa1830 = a1830.getText().toString();
                    String aa20 = a20.getText().toString();
                    String aa2130 = a2130.getText().toString();
                    String diassemana = txtdia.getText().toString();



                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("fecha", fecha);
                    parametros.put("cancha", cancha);
                    parametros.put("a930", aa930);
                    parametros.put("a11", aa11);
                    parametros.put("a1230", aa1230);
                    parametros.put("a14", aa14);
                    parametros.put("a1530", aa1530);
                    parametros.put("a17", aa17);
                    parametros.put("a1830", aa1830);
                    parametros.put("a20", aa20);
                    parametros.put("a2130", aa2130);
                    parametros.put("diasemana", diassemana);

                    return parametros;

                }
            };
            request.add(stringRequest);




    }

    public void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();

        String ip = "https://comprehensive-games.000webhostapp.com";
        String url = ip + "/login/consulta.php?fecha=" + campofecha.getText().toString() + "&&cancha=" + campocancha.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        progreso.hide();
        Toast.makeText(getContext(), "No hay registros para cancha " + campocancha.getText().toString() + " el dia " + campofecha.getText().toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

        a930.setText("CREAR REGISTRO");
        a11.setText("CREAR REGISTRO");
        a1230.setText("CREAR REGISTRO");
        a14.setText("CREAR REGISTRO");
        a1530.setText("CREAR REGISTRO");
        a17.setText("CREAR REGISTRO");
        a1830.setText("CREAR REGISTRO");
        a20.setText("CREAR REGISTRO");
        a2130.setText("CREAR REGISTRO");


    }

    @Override
    public void onResponse(JSONObject response) {

        progreso.hide();


        Usuario miUsuario = new Usuario();
        JSONArray json = response.optJSONArray("consultaturnos");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            miUsuario.setFecha(jsonObject.optString("fecha"));

            miUsuario.setA930(jsonObject.optString("a930"));
            miUsuario.setA11(jsonObject.optString("a11"));
            miUsuario.setA1230(jsonObject.optString("a1230"));
            miUsuario.setA14(jsonObject.optString("a14"));
            miUsuario.setA1530(jsonObject.optString("a1530"));
            miUsuario.setA17(jsonObject.optString("a17"));
            miUsuario.setA1830(jsonObject.optString("a1830"));
            miUsuario.setA20(jsonObject.optString("a20"));
            miUsuario.setA2130(jsonObject.optString("a2130"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        campofecha.setText(miUsuario.getFecha());

        a930.setText(miUsuario.getA930());
        a11.setText(miUsuario.getA11());
        a1230.setText(miUsuario.getA1230());
        a14.setText(miUsuario.getA14());
        a1530.setText(miUsuario.getA1530());
        a17.setText(miUsuario.getA17());
        a1830.setText(miUsuario.getA1830());
        a20.setText(miUsuario.getA20());
        a2130.setText(miUsuario.getA2130());






    }

}




