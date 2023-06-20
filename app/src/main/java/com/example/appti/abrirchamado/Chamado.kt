package com.example.appti.abrirchamado

import android.os.Parcel
import android.os.Parcelable

data class Chamado(
    val nome: String = "",
    val setor: String = "",
    val celular: String = "",
    val protocolo: String = "",
    val descricao: String = ""

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(setor)
        parcel.writeString(celular)
        parcel.writeString(protocolo)
        parcel.writeString(descricao)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chamado> {
        override fun createFromParcel(parcel: Parcel): Chamado {
            return Chamado(parcel)
        }

        override fun newArray(size: Int): Array<Chamado?> {
            return arrayOfNulls(size)
        }
    }
}
