package com.maytemur.besinlerkitabiroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.maytemur.besinlerkitabiroom.R
import com.maytemur.besinlerkitabiroom.databinding.BesinRecyclerRowBinding
import com.maytemur.besinlerkitabiroom.model.Besin
import com.maytemur.besinlerkitabiroom.util.gorselIndir
import com.maytemur.besinlerkitabiroom.util.placeholderYapalim
import com.maytemur.besinlerkitabiroom.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*

class BesinRecyclerAdapter(val besinListesi: ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>(),BesinClickListener {
    class BesinViewHolder( var view : BesinRecyclerRowBinding) : RecyclerView.ViewHolder (view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.besin_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater,R.layout.besin_recycler_row,parent,false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.view.besin= besinListesi[position]
        holder.view.listener = this


       /* holder.itemView.besinIsmi.text = besinListesi.get(position).besinIsim
        holder.itemView.besinKalorisi.text = besinListesi.get(position).besinKalori
        //gorsel kısmı eklenecek
        holder.itemView.imageView.gorselIndir(besinListesi.get(position).besinGorsel,
            placeholderYapalim(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid) //ActionBesinListesiFragmentToBesinDetayiFragment(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiGuncelle (yeniBesinListesi: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()

    }

    override fun besinTiklandi(view: View) {
        val uuid= view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it) //ActionBesinListesiFragmentToBesinDetayiFragment(besinListesi[position].uuid)
            Navigation.findNavController(view).navigate(action)
        }

    }

}