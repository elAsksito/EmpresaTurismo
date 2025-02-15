package com.cibertec.turismo.soap.models.list;

import java.util.List;

import com.cibertec.turismo.model.Reserva;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reservas")
public class ListaReservas {

    private List<Reserva> reservas;

    public ListaReservas() {}

    public ListaReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @XmlElement(name = "reserva")
    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}