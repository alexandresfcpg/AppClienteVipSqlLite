package app.daazi.aluno.appclientevipsqllite.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;

import java.util.ArrayList;
import java.util.List;

import app.daazi.aluno.appclientevipsqllite.R;
import app.daazi.aluno.appclientevipsqllite.api.AppUtil;
import app.daazi.aluno.appclientevipsqllite.controller.ClienteController;
import app.daazi.aluno.appclientevipsqllite.controller.ClientePFController;
import app.daazi.aluno.appclientevipsqllite.controller.ClientePJController;
import app.daazi.aluno.appclientevipsqllite.model.Cliente;
import app.daazi.aluno.appclientevipsqllite.model.ClientePF;
import app.daazi.aluno.appclientevipsqllite.model.ClientePJ;

public class MainActivity extends AppCompatActivity {

    Cliente cliente;
    ClienteController controller;
    ClientePFController controllerPF;
    ClientePJController controllerPJ;

    TextView txtNomeCliente;

    private SharedPreferences preferences;

    List<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFormulario();
    }

    private void buscarListaDeClientes() {

        clientes = new ArrayList<>();
        clientes.add(cliente);

        Cliente novoCliente01 = new Cliente();
        novoCliente01.setPrimeiroNome("Novo Cliente 01");
        clientes.add(novoCliente01);

        Cliente novoCliente02 = new Cliente();
        novoCliente02.setPrimeiroNome("Novo Cliente 02");
        clientes.add(novoCliente02);

        for (Cliente obj : clientes) {

            Log.i(AppUtil.LOG_APP, "Obj: " + obj.getPrimeiroNome());

        }

    }

    private void initFormulario() {

        cliente = new Cliente();
        controller = new ClienteController(this);
        controllerPF = new ClientePFController(this);
        controllerPJ = new ClientePJController(this);

        txtNomeCliente = findViewById(R.id.txtNomeCliente);

        restaurarSharedPreferences();

        txtNomeCliente.setText("Bem vindo, " + cliente.getPrimeiroNome());

    }

    private void salvarSharedPreferences() {

        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();
    }

    private void restaurarSharedPreferences() {

        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        cliente.setPrimeiroNome(preferences.getString("primeiroNome", "NULO"));
        cliente.setSobreNome(preferences.getString("sobreNome", "NULO"));
        cliente.setEmail(preferences.getString("email", "NULO"));
        cliente.setSenha(preferences.getString("senha", "NULO"));
        cliente.setPessoaFisica(preferences.getBoolean("pessoaFisica", true));
        cliente.setId(preferences.getInt("clienteID", -1));
    }

    public void meusDados(View view) {

        Intent intent = new Intent(MainActivity.this, MeusDadosActivity.class);
        startActivity(intent);

    }

    public void atualizarMeusDados(View view) {

        Intent intent = new Intent(MainActivity.this, AtualizarMeusDadosActivity.class);
        startActivity(intent);

    }

    public void excluirMinhaConta(View view) {

        FancyAlertDialog.Builder
                .with(MainActivity.this)
                .setTitle("EXCLUIR SUA CONTA")
                .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                .setMessage("Confirma a exclusão definitiva da sua conta ?")
                .setNegativeBtnText("RETORNAR")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                .setPositiveBtnText("SIM")
                .setNegativeBtnBackground(Color.parseColor("#4ECA25"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                .isCancellable(true)
                .setAnimation(Animation.POP)
                .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                .onPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void onClick(Dialog dialog) {

                        cliente.setClientePF(controllerPF.getClientePFByFK(cliente.getId()));

                        if (!cliente.isPessoaFisica()) {
                            cliente.setClientePJ(controllerPJ.getClientePJByFK(cliente.getClientePF().getId()));
                            controllerPJ.deletar(cliente.getClientePJ());
                        }

                        controllerPF.deletar(cliente.getClientePF());
                        controller.deletar(cliente);


                        Toast.makeText(MainActivity.this, cliente.getPrimeiroNome() + " , a sua conta foi excluída!", Toast.LENGTH_SHORT).show();


                    }
                })
                .onNegativeClicked(dialog -> Toast.makeText(MainActivity.this, cliente.getPrimeiroNome() + " , obrigado por continuar a usar o nosso aplicativo!", Toast.LENGTH_SHORT).show())
                .build()
                .show();

    }

    public void consultarClientesVip(View view) {

        Intent intent = new Intent(MainActivity.this, ConsultarClientesActivity.class);
        startActivity(intent);
        finish();
    }

    public void sairDoAplicativo(View view) {

        FancyAlertDialog.Builder
                .with(MainActivity.this)
                .setTitle("SAIR DO APLICATIVO")
                .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                .setMessage("Confirma a sua saída do aplicativo ?")
                .setNegativeBtnText("RETORNAR")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                .setPositiveBtnText("SIM")
                .setNegativeBtnBackground(Color.parseColor("#4ECA25"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                .isCancellable(true)
                .setAnimation(Animation.POP)
                .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                .onPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        Toast.makeText(MainActivity.this, cliente.getPrimeiroNome() + " , volte sempre e obrigado!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                })
                .onNegativeClicked(dialog -> Toast.makeText(MainActivity.this, cliente.getPrimeiroNome() + " , obrigado por continuar a usar o nosso aplicativo!", Toast.LENGTH_SHORT).show())
                .build()
                .show();
    }
}