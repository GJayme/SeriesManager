package sc3005054.jayme.seriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sc3005054.jayme.seriesmanager.OnTemporadaClickListener
import sc3005054.jayme.seriesmanager.R
import sc3005054.jayme.seriesmanager.databinding.LayoutTemporadaBinding
import sc3005054.jayme.seriesmanager.domain.Temporada

class TemporadaRvAdapter(
    private val onTemporadaClickListener: OnTemporadaClickListener,
    private val temporadaList: MutableList<Temporada>
): RecyclerView.Adapter<TemporadaRvAdapter.TemporadaLayoutHolder>() {

    var posicao: Int = -1

    //ViewHolder
    inner class TemporadaLayoutHolder(layoutTemporadaBinding: LayoutTemporadaBinding): RecyclerView.ViewHolder(layoutTemporadaBinding.root), View.OnCreateContextMenuListener {
        val numeroSequencialTv: TextView = layoutTemporadaBinding.numeroSequencialTv
        val anoLancamentoTv: TextView = layoutTemporadaBinding.anoLancamentoTv

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_temporada, menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemporadaLayoutHolder {
        // Criar uma nova célula
        val layoutTemporadaBinding = LayoutTemporadaBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        // Criar um viewHolder associado a nova célula
        return TemporadaLayoutHolder(layoutTemporadaBinding)
    }

    override fun onBindViewHolder(holder: TemporadaLayoutHolder, position: Int) {
        // Buscar a temporada
        val temporada = temporadaList[position]

        // Atualizar os valores do viewHolder
        with(holder) {
            numeroSequencialTv.text = temporada.numeroSequencial.toString()
            anoLancamentoTv.text = temporada.anoLancamento
            itemView.setOnClickListener {
                onTemporadaClickListener.onTemporadaClick(position)
            }
            itemView.setOnLongClickListener{
                posicao = position
                false
            }
        }
    }

    override fun getItemCount(): Int = temporadaList.size
}
