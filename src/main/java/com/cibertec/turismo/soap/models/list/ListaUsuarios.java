package com.cibertec.turismo.soap.models.list;

import java.util.List;

import com.cibertec.turismo.model.Usuario;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuarios")
public class ListaUsuarios {

    private List<Usuario> usuarios;

    public ListaUsuarios() {}

    public ListaUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @XmlElement(name = "usuario")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
