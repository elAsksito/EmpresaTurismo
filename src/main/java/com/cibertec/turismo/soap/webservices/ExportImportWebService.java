package com.cibertec.turismo.soap.webservices;

import org.springframework.stereotype.Component;

import com.cibertec.turismo.exception.WebServiceException;
import com.cibertec.turismo.service.interfaces.IExportImportService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import lombok.RequiredArgsConstructor;

@WebService(serviceName = "ExportImportService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
@RequiredArgsConstructor
public class ExportImportWebService {
	
	private final IExportImportService exportImportService;
    private final String BASE_PATH = "C:/exports";

    @WebMethod
    public String exportJson(@WebParam(name = "entidad") String entidad) {
        try {
            exportImportService.exportJson(BASE_PATH, entidad);
            return "Exportación JSON de " + entidad + " completada.";
        } catch (RuntimeException e) {
            throw new WebServiceException("Error al exportar JSON: " + e.getMessage(), "500");
        }
    }

    @WebMethod
    public String importJson(@WebParam(name = "entidad") String entidad, @WebParam(name = "filePath") String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new WebServiceException("Error: Debes proporcionar la ruta del archivo.", "400");
        }
        try {
            exportImportService.importJson(filePath, entidad);
            return "Importación JSON de " + entidad + " desde " + filePath + " completada.";
        } catch (RuntimeException e) {
            throw new WebServiceException("Error al importar JSON: " + e.getMessage(), "500");
        }
    }

    @WebMethod
    public String exportXml(@WebParam(name = "entidad") String entidad) {
        try {
            exportImportService.exportXML(BASE_PATH, entidad);
            return "Exportación XML de " + entidad + " completada.";
        } catch (RuntimeException e) {
            throw new WebServiceException("Error al exportar XML: " + e.getMessage(), "500");
        }
    }

    @WebMethod
    public String importXml(@WebParam(name = "entidad") String entidad, @WebParam(name = "filePath") String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new WebServiceException("Error: Debes proporcionar la ruta del archivo.", "400");
        }
        try {
            exportImportService.importXML(filePath, entidad);
            return "Importación XML de " + entidad + " desde " + filePath + " completada.";
        } catch (RuntimeException e) {
            throw new WebServiceException("Error al importar XML: " + e.getMessage(), "500");
        }
    }
}