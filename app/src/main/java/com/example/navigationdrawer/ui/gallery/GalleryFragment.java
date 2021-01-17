package com.example.navigationdrawer.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class GalleryFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private GalleryViewModel galleryViewModel;

    Button btnregistrar;
    EditText campofecha, campodieztreinta, campodoce, campotrecetreinta, campoquince, campodieciseistreinta, campodieciocho, campodiecinuevetreinta;
    ProgressDialog progreso;
    TextView cancha, diasemana;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    CalendarView calendar;

    RadioButton cancha1, cancha2;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        btnregistrar=(Button)root.findViewById(R.id.btnregistrar);
        campofecha=(EditText)root.findViewById(R.id.txtfecha);
        campodieztreinta=(EditText)root.findViewById(R.id.txtdieztreinta);
        campodoce=(EditText)root.findViewById(R.id.a11);
        campotrecetreinta=(EditText)root.findViewById(R.id.a1230);
        campoquince=(EditText)root.findViewById(R.id.a14);
        campodieciseistreinta=(EditText)root.findViewById(R.id.a1530);
        campodieciocho=(EditText)root.findViewById(R.id.a17);
        campodiecinuevetreinta=(EditText)root.findViewById(R.id.a1830);

        cancha1=(RadioButton)root.findViewById(R.id.radiocancha1);
        cancha2=(RadioButton)root.findViewById(R.id.radiocancha2);

        cancha=(TextView)root.findViewById(R.id.txtcancha);



        calendar = (CalendarView)root.findViewById(R.id.calendarView);



        request= Volley.newRequestQueue(getContext());


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

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campofecha.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                }else if(!cancha1.isChecked() &&  !cancha2.isChecked()){
                    Toast.makeText(getContext(), "Seleccione una cancha", Toast.LENGTH_SHORT).show();

                }else {
                    cargarWebService();
                }



            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "/" + (i1 + 1) + "/" + i;
                Log.d(TAG, "OnSelectedDayChange: mm/dd/yyyy " + date);

                campofecha.setText(date);



            }
        });




        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;



    }



    @Override
    public void onResponse(JSONObject response) {

        progreso.hide();


        Usuario miUsuario=new Usuario();
        JSONArray json = response.optJSONArray("turnos");
        JSONObject jsonObject= null;

        try {
            jsonObject=json.getJSONObject(0);






        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getContext(), "Turnos actualizados correctamente", Toast.LENGTH_SHORT).show();







    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No se pudo registrar "+error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());



    }



    public void cargarWebService() {



        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip2= "http://192.168.0.166";
        String ip ="https://comprehensive-games.000webhostapp.com";
        String url= ip+"/login/sesion.php?fecha="+campofecha.getText().toString()+"&cancha="+cancha.getText().toString()+"&dieztreinta="+campodieztreinta.getText().toString()+"&doce="+campodoce.getText().toString()+"&trecetreinta="+campotrecetreinta.getText().toString()+"&quince="+campoquince.getText().toString()+"&dieciseistreinta="+campodieciseistreinta.getText().toString()+"&dieciocho="+campodieciocho.getText().toString()+"&diecinuevetreinta="+campodiecinuevetreinta.getText().toString();

        url = url.replace(" ", "%20");

        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    
    
}