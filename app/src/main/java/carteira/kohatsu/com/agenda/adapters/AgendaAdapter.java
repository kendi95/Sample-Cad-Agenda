package carteira.kohatsu.com.agenda.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import carteira.kohatsu.com.agenda.ActCadAgenda;
import carteira.kohatsu.com.agenda.R;
import carteira.kohatsu.com.agenda.entities.Agenda;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolderAgenda> {

    private List<Agenda> list = new ArrayList<>();

    public AgendaAdapter(List<Agenda> list){
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolderAgenda onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.lista_agenda, viewGroup, false);
        ViewHolderAgenda holderAgenda = new ViewHolderAgenda(view, viewGroup.getContext());

        return holderAgenda;
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaAdapter.ViewHolderAgenda viewHolder, int i) {
        if((list != null) && (list.size() > 0) && (!list.isEmpty())){
            Agenda agenda = list.get(i);

            viewHolder.txtViewId.setText(agenda.getId().toString());
            viewHolder.txtViewTitulo.setText(agenda.getTitulo());
            viewHolder.txtViewDataHoras.setText("Data: "+agenda.getData()+"     Horas: "+agenda.getHoras());
            viewHolder.txtViewDescricao.setText(agenda.getDescricao());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolderAgenda extends RecyclerView.ViewHolder{

        private TextView txtViewTitulo, txtViewDataHoras, txtViewDescricao, txtViewId;
        private LinearLayoutManager linearLayout_linha_agenda;

        public ViewHolderAgenda(@NonNull final View itemView, final Context context) {
            super(itemView);

            txtViewId = (TextView) itemView.findViewById(R.id.txtViewId);
            txtViewTitulo = (TextView) itemView.findViewById(R.id.txtViewTitulo);
            txtViewDataHoras = (TextView) itemView.findViewById(R.id.txtViewDataHoras);
            txtViewDescricao = (TextView) itemView.findViewById(R.id.txtViewDescricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.size() > 0){
                        Agenda agenda = list.get(getLayoutPosition());

                        Intent intent = new Intent(context, ActCadAgenda.class);
                        intent.putExtra("agenda", agenda);
                        ((AppCompatActivity)context).startActivityForResult(intent, 0);
                    }
                }

            });
        }


        public TextView getTxtViewTitulo() {
            return txtViewTitulo;
        }

        public void setTxtViewTitulo(TextView txtViewTitulo) {
            this.txtViewTitulo = txtViewTitulo;
        }

        public TextView getTxtViewDataHoras() {
            return txtViewDataHoras;
        }

        public void setTxtViewDataHoras(TextView txtViewDataHoras) {
            this.txtViewDataHoras = txtViewDataHoras;
        }

        public TextView getTxtViewDescricao() {
            return txtViewDescricao;
        }

        public void setTxtViewDescricao(TextView txtViewDescricao) {
            this.txtViewDescricao = txtViewDescricao;
        }
    }

}
