package app.daazi.aluno.appclientevipsqllite.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancydialoglib.FancyAlertDialog;

import app.daazi.aluno.appclientevipsqllite.R;
import app.daazi.aluno.appclientevipsqllite.api.AppUtil;
import app.daazi.aluno.appclientevipsqllite.controller.ClienteController;
import app.daazi.aluno.appclientevipsqllite.datamodel.ClienteDataModel;
import app.daazi.aluno.appclientevipsqllite.model.Cliente;

public class CredencialDeAcessoActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    Button btnCadastro;
    EditText editNome, editEmail, editSenhaA, editSenhaB;
    CheckBox ckTermo;
    boolean isFormularioOK, isPessoaFisica;

    Cliente cliente;
    ClienteController controller;
    int clienteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario_card);

        initFormulario();

        btnCadastro.setOnClickListener((v -> {

            isFormularioOK = true;

            if (TextUtils.isEmpty(editNome.getText().toString())) {
                editNome.setError("*");
                editNome.requestFocus();
                isFormularioOK = false;
            }
            if (TextUtils.isEmpty(editEmail.getText().toString())) {
                editEmail.setError("*");
                editEmail.requestFocus();
                isFormularioOK = false;
            }
            if (TextUtils.isEmpty(editSenhaA.getText().toString())) {
                editSenhaA.setError("*");
                editSenhaA.requestFocus();
                isFormularioOK = false;
            }
            if (TextUtils.isEmpty(editSenhaB.getText().toString())) {
                editSenhaB.setError("*");
                editSenhaB.requestFocus();
                isFormularioOK = false;
            }
            if (!ckTermo.isChecked()) {
                isFormularioOK = false;
            }

            if (isFormularioOK) {

                if (!validarSenha()) {

                    editSenhaA.setError(("*"));
                    editSenhaB.setError(("*"));
                    editSenhaA.requestFocus();

                    FancyAlertDialog.Builder
                            .with(CredencialDeAcessoActivity.this)
                            .setTitle("ATENÇÃO!!!!!")
                            .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                            .setMessage("As senhas digitadas não conferem, por favor, tente novamente!")
                            .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                            .setPositiveBtnText("CONTINUAR")
                            .isCancellable(true)
                            .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                            .onPositiveClicked(dialog -> Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show())
                            .build()
                            .show();
                } else {

                    cliente.setEmail(editEmail.getText().toString());
                    cliente.setSenha(AppUtil.gerarMD5Hash(editSenhaA.getText().toString()));

                    controller.alterar(cliente);

                    salvarSharedPreferences();

                    Intent iMenuPrincipal = new Intent(CredencialDeAcessoActivity.this, LoginActivity.class);
                    startActivity(iMenuPrincipal);
                    finish();
                    return;
                }
            }

        }));
    }

    private boolean validarSenha() {

        boolean retorno = false;

        int senhaA, senhaB;

        senhaA = Integer.parseInt(editSenhaA.getText().toString());
        senhaB = Integer.parseInt(editSenhaB.getText().toString());

        retorno = (senhaA == senhaB);

        return retorno;
    }

    public void validarTermo(View view) {

    }

    private void initFormulario() {

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenhaA = findViewById(R.id.editSenhaA);
        editSenhaB = findViewById(R.id.editSenhaB);
        ckTermo = findViewById(R.id.ckTermo);
        btnCadastro = findViewById(R.id.btnCadastro);

        isFormularioOK = false;

        cliente = new Cliente();
        controller = new ClienteController(this);

        restaurarSharedPreferences();
    }

    private void salvarSharedPreferences() {

        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();

        dados.putString("email", editEmail.getText().toString());
        dados.putString("senha", AppUtil.gerarMD5Hash(editSenhaA.getText().toString()));
        dados.apply();

    }

    private void restaurarSharedPreferences() {

        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        clienteID = preferences.getInt("clienteID", -1);
        String primeiroNome = preferences.getString("primeiroNome", "");
        String sobreNome = preferences.getString("sobreNome", "");
        isPessoaFisica = preferences.getBoolean("pessoaFisica", true);

        cliente.setId(clienteID);
        cliente.setPrimeiroNome(primeiroNome);
        cliente.setSobreNome(sobreNome);
        cliente.setPessoaFisica(isPessoaFisica);

        if (isPessoaFisica)
            editNome.setText(preferences.getString("nomeCompleto", "Verifique os dados!"));
        else
            editNome.setText(preferences.getString("razaoSocial", "Verifique os dados!"));

    }
}