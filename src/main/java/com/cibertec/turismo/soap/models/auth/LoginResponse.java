package com.cibertec.turismo.soap.models.auth;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "LoginResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LoginResponse {

    @XmlElement(name = "token")
    private String token;
}