package com.cibertec.turismo.soap.models.auth;

import com.cibertec.turismo.model.Usuario;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "RegistroResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RegistroResponse {
	@XmlElement(name = "usuario")
    private Usuario usuario;
}