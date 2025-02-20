package com.cibertec.turismo.service.interfaces;

public interface IExportImportService {
	
	void exportJson(String filePath, String entidad);
	void importJson(String filePath, String entidad);
	void exportXML(String filePath, String entidad);
	void importXML(String filePath, String entidad);
}