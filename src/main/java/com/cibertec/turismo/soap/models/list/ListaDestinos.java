package com.cibertec.turismo.soap.models.list;

import java.util.List;

import com.cibertec.turismo.model.DestinoTuristico;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "destinosTuristicos")
public class ListaDestinos {

	private List<DestinoTuristico> destinos;

    public ListaDestinos() {}

    public ListaDestinos(List<DestinoTuristico> destinos) {
        this.destinos = destinos;
    }

    @XmlElement(name = "destinoTuristico")
    public List<DestinoTuristico> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<DestinoTuristico> destinos) {
        this.destinos = destinos;
    }
}