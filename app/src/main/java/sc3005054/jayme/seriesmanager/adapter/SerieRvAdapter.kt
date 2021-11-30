package sc3005054.jayme.seriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sc3005054.jayme.seriesmanager.view.utils.OnSerieClickListener
import sc3005054.jayme.seriesmanager.R
import sc3005054.jayme.seriesmanager.databinding.LayoutSerieBinding
import sc3005054.jayme.seriesmanager.domain.Serie

class SerieRvAdapter(
    private val onSerieClickListener: OnSerieClickListener,
    private val serieList: MutableList<Serie>
): RecyclerView.Adapter<SerieRvAdapter.SerieLayoutHolder>() {

    var posicao: Int = -1

    // ViewHolder
    inner class SerieLayoutHolder(layoutSerieBinding: LayoutSerieBinding): RecyclerView.ViewHolder(layoutSerieBinding.root), View.OnCreateContextMenuListener {
        val nomeTv: TextView = layoutSerieBinding.nomeTv
        val anoLancamentoTv: TextView = layoutSerieBinding.anoLancamentoTv
        val emissoraTv: TextView = layoutSerieBinding.emissoraTv
        val generoTv: TextView = layoutSerieBinding.generoTv

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_serie, menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieLayoutHolder {
        // Criar uma nova célula
        val layoutSerieBinding = LayoutSerieBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        // Criar um viewHolder associado a nova célula
        return SerieLayoutHolder(layoutSerieBinding)
    }

    override fun onBindViewHolder(holder: SerieLayoutHolder, position: Int) {
        // Buscar a serie
        val serie = serieList[position]

        // Atualizar os valores do viewHolder
        with(holder) {
            nomeTv.text = serie.nome
            anoLancamentoTv.text = serie.anoLancamento
            emissoraTv.text = serie.emissora
            generoTv.text = serie.genero
            itemView.setOnClickListener {
                onSerieClickListener.onSerieClick(position)
            }
            itemView.setOnLongClickListener{
                posicao = position
                false
            }
        }
    }

    override fun getItemCount(): Int = serieList.size
}
