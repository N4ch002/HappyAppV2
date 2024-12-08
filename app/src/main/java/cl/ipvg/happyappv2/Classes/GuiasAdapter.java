package cl.ipvg.happyappv2.Classes;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import cl.ipvg.happyappv2.R;

public class GuiasAdapter extends RecyclerView.Adapter<GuiasAdapter.GuiasViewHolder> {

    private List<Guia> guias;
    private boolean[] expandedStates;

    public GuiasAdapter(List<Guia> guias) {
        this.guias = guias;
        this.expandedStates = new boolean[guias.size()]; // Controla el estado expandido/colapsado
    }

    @NonNull
    @Override
    public GuiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_guia_expandable, parent, false);
        return new GuiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuiasViewHolder holder, int position) {
        Guia guia = guias.get(position);
        holder.titulo.setText(guia.getTitulo());
        holder.descripcion.setText(guia.getDescripcion());


        // mostrar u ocultar la descripción basado en el estado expandido
        boolean expandido = expandedStates[position];
        holder.descripcion.setVisibility(expandido ? View.VISIBLE : View.GONE);

        // cambios de estado al hacer clic en el ítem
        holder.itemView.setOnClickListener(v -> {
            expandedStates[position] = !expandido;
            notifyItemChanged(position); // refresca el ítem
        });

        //permite links
        holder.descripcion.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public int getItemCount() {
        return guias.size();
    }

    public static class GuiasViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descripcion;

        public GuiasViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textTitulo);
            descripcion = itemView.findViewById(R.id.textDescripcion);
        }
    }


}
