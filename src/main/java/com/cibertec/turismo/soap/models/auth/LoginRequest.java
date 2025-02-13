package com.cibertec.turismo.soap.models.auth;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "LoginRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LoginRequest {

	@XmlElement(name = "email", required = true)
    private String email;

    @XmlElement(name = "password", required = true)
    private String password;

}