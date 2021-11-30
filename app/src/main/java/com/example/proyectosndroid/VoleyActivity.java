package com.example.proyectosndroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectosndroid.Adaptadores.PokemonAdapter;
import com.example.proyectosndroid.Modelos.Pokemon;
import com.example.proyectosndroid.Modelos.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VoleyActivity extends AppCompatActivity {

    RecyclerView pokeLista;
    TextView  texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voley);
        pokeLista = findViewById(R.id.poke_lista);
        texto = findViewById(R.id.textView);
        RequestQueue solicitud = Volley.newRequestQueue(this);
        String url = "https://pokeapi.co/api/v2/pokemon?limit=150";
        //List<Pokemon> pokemonList = new ArrayList<>();
        //PokemonAdapter pa = new PokemonAdapter(pokemonList);
        //pokemonList.add(new Pokemon("a"));

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            //texto.setText(response.toString());
                        final Gson gson = new Gson();
                        try {
                            List<Pokemon> pokemonList = new ArrayList<>();
                            JSONArray pokemonJson = response.getJSONArray("results");

                            //texto.setText(pokemonJson.length());


                            final Type tipoEnvoltorioEmpleado = new TypeToken<Envoltorio<Pokemon>>(){}.getType();

///https://www.adictosaltrabajo.com/2012/09/17/gson-java-json/
                            for(int i = 0; i<pokemonJson.length(); i++){
                                //JSONObject json = pokemonJson.getJSONObject(i);
                                Pokemon pok = gson.fromJson(pokemonJson.getJSONObject(i).toString(),Pokemon.class);
                                //pokemonList.add(new Pokemon());

                                //Pokemon pok = new Pokemon(json.getString("name"));

                                pokemonList.add(pok);
                                //texto.setText(json.getString("name"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

//        VolleySingleton.getMiInstancia(this).addToRequestQueue(stringRequest);
        VolleySingleton.getMiInstancia(this).addToRequestQueue(jsonRequest);

        pokeLista.setHasFixedSize(true);
        pokeLista.setLayoutManager(new LinearLayoutManager(this));
        pokeLista.setAdapter(pa);
    }

}