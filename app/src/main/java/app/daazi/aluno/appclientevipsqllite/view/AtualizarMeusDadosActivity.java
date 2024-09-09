package app.daazi.aluno.appclientevipsqllite.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.daazi.aluno.appclientevipsqllite.R;

public class AtualizarMeusDadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_meus_dados);
    }



    public void voltar(View view) {

        Intent intent = new Intent(AtualizarMeusDadosActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}