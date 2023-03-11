package com.maytemur.besinlerkitabiroom.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.maytemur.besinlerkitabiroom.R

//herhangi bir dosya açmamız yeterli- her yerden çağrılacak kullanacak ama mvvm
//den hiç bir klasmana uymayan dosyalara util diyoruz utility gibi
//basit bir fonksiyon yaratmamız yeterli

/*
fun String.benimEklentim (parametre: String){//bu şekilde String sınıfına
    println(parametre)  //benimEklentim diyerek extension eklemiş olduk
}                       //imageView'e de Glide'ı eklenti olarak ekleyip kullanıcaz

 */

fun ImageView.gorselIndir (url: String?,resimGoster: CircularProgressDrawable){
    val opsiyon = RequestOptions().placeholder(resimGoster).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(opsiyon).load(url).into(this)

}

fun placeholderYapalim (context: Context) :CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 7f
        centerRadius = 40f
        start()
    }
}
@BindingAdapter ("android:downloadImage")
fun downloadImage(view: ImageView,url: String?){
    view.gorselIndir(url, placeholderYapalim(view.context))
}


