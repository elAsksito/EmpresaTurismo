package com.cibertec.turismo.config.soap;

import com.cibertec.turismo.service.JwtService;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SoapSecurityHandler implements SOAPHandler<SOAPMessageContext> {

    private final JwtService jwtService;

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!isOutbound) {
            try {
                SOAPMessage soapMessage = context.getMessage();
                SOAPHeader soapHeader = soapMessage.getSOAPHeader();

                if (soapHeader == null) {
                    throw new SOAPException("Falta el encabezado de autenticación.");
                }

                Node authNode = (Node) soapHeader.getElementsByTagNameNS("http://security.turismo.cibertec.com/", "token").item(0);

                if (authNode == null) {
                    throw new SOAPException("No se encontró el token en el encabezado.");
                }

                String token = authNode.getTextContent();
                if (token == null || !token.startsWith("Bearer ")) {
                    throw new SOAPException("Token no proporcionado o mal formado.");
                }

                token = token.substring(7);
                String username = jwtService.extractUsername(token);

                if (!jwtService.isTokenValid(token, username)) {
                    throw new SOAPException("Token JWT inválido o expirado.");
                }

                String role = jwtService.extractRole(token);

                UserDetails userDetails = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);

            } catch (SOAPException e) {
                throw new RuntimeException("Error en autenticación SOAP: " + e.getMessage(), e);
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }
}
