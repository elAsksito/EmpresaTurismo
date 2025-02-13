package com.cibertec.turismo.config.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.soap.SOAPBinding;
import com.cibertec.turismo.soap.webservices.AuthWebService;
import com.cibertec.turismo.soap.webservices.DestinoTuristicoWebService;
import com.cibertec.turismo.soap.webservices.ReservaWebService;
import com.cibertec.turismo.soap.webservices.UsuarioWebService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SoapConfig {

    private final AuthWebService authWebService;
    private final UsuarioWebService usuarioWebService;
    private final DestinoTuristicoWebService destinoTuristicoWebService;
    private final ReservaWebService reservaWebService;


    @Bean
    Endpoint authEndpoint() {
        Endpoint endpoint = Endpoint.create(SOAPBinding.SOAP11HTTP_BINDING, authWebService);
        endpoint.publish("http://localhost:9001/ws/auth");
        return endpoint;
    }

    @Bean
    Endpoint usuarioEndpoint() {
        Endpoint endpoint = Endpoint.create(SOAPBinding.SOAP11HTTP_BINDING, usuarioWebService);
        endpoint.publish("http://localhost:9001/ws/usuario");
        return endpoint;
    }

    @Bean
    Endpoint destinoTuristicoEndpoint() {
        Endpoint endpoint = Endpoint.create(SOAPBinding.SOAP11HTTP_BINDING, destinoTuristicoWebService);
        endpoint.publish("http://localhost:9001/ws/destino");
        return endpoint;
    }

    @Bean
    Endpoint reservaEndpoint() {
        Endpoint endpoint = Endpoint.create(SOAPBinding.SOAP11HTTP_BINDING, reservaWebService);
        endpoint.publish("http://localhost:9001/ws/reserva");
        return endpoint;
    }
}
