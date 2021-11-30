package sc3005054.jayme.seriesmanager.adapter

import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sc3005054.jayme.seriesmanager.view.utils.OnEpisodioClickListener
import sc3005054.jayme.seriesmanager.R
import sc3005054.jayme.seriesmanager.databinding.LayoutEpisodioBinding
import sc3005054.jayme.seriesmanager.domain.Episodio

class EpisodioRvAdapter (
    private val onEpisodioClickListener: OnEpisodioClickListener,
    private val episodioList: MutableList<Episodio>
): RecyclerView.Adapter<EpisodioRvAdapter.EpisodioLayoutHolder>() {

    var posicao: Int = -1

    //View Holder
    inner class EpisodioLayoutHolder(layoutEpisodioBinding: LayoutEpisodioBinding): RecyclerView.ViewHolder(layoutEpisodioBinding.root), View.OnCreateContextMenuListener {
        val nomeEpisodioTv: TextView = layoutEpisodioBinding.nomeEpisodioTv
        val numeroSequencialEpisodioTv: TextView = layoutEpisodioBinding.numeroSequencialEpisodioTv
        val duracaoEpisodioTv: TextView = layoutEpisodioBinding.duracaoEpisodioTv
        val foiVistoCb: CheckBox = layoutEpisodioBinding.foiVistoCb

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_episodio, menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodioLayoutHolder {
        val layoutEpisodioBinding = LayoutEpisodioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodioLayoutHolder(layoutEpisodioBinding)
    }

    override fun onBindViewHolder(holder: EpisodioLayoutHolder, position: Int) {
        val episodio = episodioList[position]

        with(holder) {
            nomeEpisodioTv.text = episodio.nome
            numeroSequencialEpisodioTv.text = episodio.numeroSequencial.toString()
            duracaoEpisodioTv.text = episodio.duracao.toString()
            foiVistoCb.isChecked = episodio.foiVisto
            itemView.setOnClickListener {
                onEpisodioClickListener.onEpisodioClick(position)
            }
            itemView.setOnLongClickListener{
                posicao = position
                false
            }
        }
    }

    override fun getItemCount(): Int = episodioList.size
}
