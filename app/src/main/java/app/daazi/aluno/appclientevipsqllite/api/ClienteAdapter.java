package app.daazi.aluno.appclientevipsqllite.api;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.daazi.aluno.appclientevipsqllite.R;
import app.daazi.aluno.appclientevipsqllite.model.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {

    private List<Cliente> aClientes;
    private Context aContext;

    public ClienteAdapter(List<Cliente> aClientes, Context aContext) {
        this.aClientes = aClientes;
        this.aContext = aContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Monta o layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View linhaView = inflater.inflate(R.layout.activity_linha_detalhe, parent, false);

        ViewHolder viewHolder = new ViewHolder(linhaView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Monta as informações linha por linha
        Cliente objDaLinha = aClientes.get(position);

        TextView txtPrimeiroNome = holder.rvPrimeiroNome;
        txtPrimeiroNome.setText(objDaLinha.getPrimeiroNome());

        TextView rvEmail = holder.rvEmail;
        rvEmail.setText(objDaLinha.getEmail());

        Button btnPessoaFisica = holder.rvPessoaFisica;

        try {
            btnPessoaFisica.setText(objDaLinha.isPessoaFisica() ? "CPF: " + objDaLinha.getClientePF().getCpf() : "CNPJ: " + objDaLinha.getClientePJ().getCnpj());
        }catch (Exception e){
            btnPessoaFisica.setText(objDaLinha.isPessoaFisica() ? "CPF" : "CNPJ");
        }
    }

    @Override
    public int getItemCount() {
        return aClientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // As informações que serão apresentadas na consulta
        public TextView rvPrimeiroNome, rvEmail;
        public Button rvPessoaFisica;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rvPrimeiroNome = itemView.findViewById(R.id.rvPrimeiroNome);
            rvEmail = itemView.findViewById(R.id.rvEmail);
            rvPessoaFisica = itemView.findViewById(R.id.rvPessoaFisica);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // Evento do clique do botão
            int position = getAdapterPosition();

            Cliente clienteSelecionado = aClientes.get(position);

            if (position != RecyclerView.NO_POSITION){

                Log.i(AppUtil.LOG_APP, "Cliente ID "+position+" Primeiro Nome: "+clienteSelecionado.getPrimeiroNome());

                Toast.makeText(aContext, "Cliente ID "+ position +" Primeiro Nome: "+ clienteSelecionado.getPrimeiroNome(), Toast.LENGTH_LONG).show();

            }

        }
    }
}
