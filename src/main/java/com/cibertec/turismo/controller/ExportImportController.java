package com.cibertec.turismo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.turismo.service.interfaces.IExportImportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExportImportController {
	
	private final IExportImportService exportImportService;
	private final String BASE_PATH = "/app/exports";
    
    @GetMapping("/export/json/{entidad}")
    public ResponseEntity<String> exportJson(@PathVariable String entidad) {
        exportImportService.exportJson(BASE_PATH, entidad);
        return ResponseEntity.ok("Exportación JSON de " + entidad + " completada.");
    }
    
    @PostMapping("/import/json/{entidad}")
    public ResponseEntity<String> importJson(@PathVariable String entidad, @RequestBody Map<String, String> request) {
        String filePath = request.get("filePath");
        if (filePath == null || filePath.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Debes proporcionar la ruta del archivo.");
        }
        exportImportService.importJson(filePath, entidad);
        return ResponseEntity.ok("Importación JSON de " + entidad + " desde " + filePath + " completada.");
    }

    @GetMapping("/export/xml/{entidad}")
    public ResponseEntity<String> exportXml(@PathVariable String entidad) {
        exportImportService.exportXML(BASE_PATH, entidad);
        return ResponseEntity.ok("Exportación XML de " + entidad + " completada.");
    }

    @PostMapping("/import/xml/{entidad}")
    public ResponseEntity<String> importXml(@PathVariable String entidad, @RequestBody Map<String, String> request) {
        String filePath = request.get("filePath");
        if (filePath == null || filePath.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Debes proporcionar la ruta del archivo.");
        }
        exportImportService.importXML(filePath, entidad);
        return ResponseEntity.ok("Importación XML de " + entidad + " desde " + filePath + " completada.");
    }

}
