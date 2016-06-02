package br.com.criptografia;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private EditText etSenha;
    private EditText etTextoDesprotegido;
    private EditText etTextoProtegido;
    private Button btProteger;
    private Button btDesproteger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        loadComponents();
        loadActions();
    }

    private void loadComponents() {
        etSenha = (EditText) findViewById(R.id.etPassword);
        etTextoProtegido = (EditText) findViewById(R.id.etTextoProtegido);
        etTextoDesprotegido = (EditText) findViewById(R.id.etTextoDesprotegido);

        btProteger = (Button) findViewById(R.id.btProteger);
        btDesproteger = (Button) findViewById(R.id.btDesproteger);
    }

    private void loadActions() {
        btProteger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSenha.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Digitar senha", Toast.LENGTH_LONG).show();
                } else if (etTextoDesprotegido.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Digitar o text para proteger", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        etTextoProtegido.setText(CryptoUtil.Proteger(etTextoDesprotegido.getText().toString(), etSenha.getText().toString()));
                    } catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btDesproteger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSenha.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Digitar senha", Toast.LENGTH_LONG).show();
                } else if (etTextoProtegido.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Digitar o texto protegido", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        etTextoDesprotegido.setText(CryptoUtil.Desproteger(etTextoProtegido.getText().toString(), etSenha.getText().toString()));
                    } catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
