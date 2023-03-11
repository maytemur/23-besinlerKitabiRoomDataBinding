package com.maytemur.besinlerkitabiroom.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maytemur.besinlerkitabiroom.R
import com.maytemur.besinlerkitabiroom.databinding.FragmentBesinDetayiBinding
import com.maytemur.besinlerkitabiroom.util.gorselIndir
import com.maytemur.besinlerkitabiroom.util.placeholderYapalim
import com.maytemur.besinlerkitabiroom.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*

class BesinDetayiFragment : Fragment() {
    private var besinId = 0
    private lateinit var viewModel : BesinDetayiViewModel
    private lateinit var dataBinding : FragmentBesinDetayiBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        //return inflater.inflate(R.layout.fragment_besin_detayi, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId= BesinDetayiFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)


        observeLiveData()
    }

    fun observeLiveData () {
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {
                dataBinding.secilenBesin = it

                /*besin_ismi.text = it.besinIsim
                besin_kalori.text = it.besinKalori
                besin_karbonhidrat.text= it.besinKarbonhidrat
                besin_protein.text= it.besinProtein
                besin_yag.text = it.besinYag
                context?.let {
                    besinImage.gorselIndir(besin.besinGorsel, placeholderYapalim(it))

                }*/

            }

        })
    }

}