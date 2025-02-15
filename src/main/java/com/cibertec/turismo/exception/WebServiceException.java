package com.cibertec.turismo.exception;

import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;

public class WebServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WebServiceException(String mensaje, String faultCode) {
        super(mensaje);
        throw crearSoapFaultException(mensaje, faultCode);
    }

    private SOAPFaultException crearSoapFaultException(String mensaje, String faultCode) {
        try {
            SOAPFault fault = SOAPFactory.newInstance().createFault();
            fault.setFaultString(mensaje);
            fault.setFaultCode(new QName("http://schemas.xmlsoap.org/soap/envelope/", faultCode));
            return new SOAPFaultException(fault);
        } catch (SOAPException e) {
            throw new RuntimeException("Error al crear SOAPFaultException", e);
        }
    }
}