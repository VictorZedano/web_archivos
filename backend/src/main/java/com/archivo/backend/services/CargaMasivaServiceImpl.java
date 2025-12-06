package com.archivo.backend.services;

import com.archivo.backend.dtos.RespuestaCargaDto;
import com.archivo.backend.entities.RegistroArchivoDigital;
import com.archivo.backend.repositories.RegistroArchivoDigitalRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CargaMasivaServiceImpl implements CargaMasivaService {

    private final RegistroArchivoDigitalRepository registroArchivoDigitalRepository;

    // Define tus constantes de columna (AJUSTAR ESTOS ÍNDICES A TU EXCEL REAL)
    private static final int COL_NUMERO_HISTORIA = 0;
    private static final int COL_NOMBRE_PACIENTE = 1;
    private static final int COL_SEDE_RAW = 2;
    private static final int COL_CAJA_RAW = 3; // <-- COLUMNA NO OBLIGATORIA
    private static final int COL_AREA_RAW = 4;
    private static final int COL_SERIE_DOC_RAW = 5;
    private static final int COL_NOMBRE_DOC_RAW = 6;
    private static final int COL_TIPO_ARCHIVO_RAW = 7;
    private static final int COL_FORMATO = 8;
    private static final int COL_DIGITALIZADOR_RAW = 9; 

    public CargaMasivaServiceImpl(RegistroArchivoDigitalRepository registroArchivoDigitalRepository) {
        this.registroArchivoDigitalRepository = registroArchivoDigitalRepository;
    }

    @Override
    @Transactional
    public RespuestaCargaDto cargarRegistrosDesdeExcel(MultipartFile file) throws IOException {

        RespuestaCargaDto respuesta = new RespuestaCargaDto();
        List<RegistroArchivoDigital> registrosValidos = new ArrayList<>();
        
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Saltar el encabezado (asume que está en la fila 0)
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                List<String> erroresFila = new ArrayList<>();
                boolean filaValida = true;

                // --- 2. Lectura y Validación de Obligatorios ---
                
                String numeroHistoriaRaw = getStringValue(row.getCell(COL_NUMERO_HISTORIA));
                String nombrePaciente = getStringValue(row.getCell(COL_NOMBRE_PACIENTE));
                String sedeRaw = getStringValue(row.getCell(COL_SEDE_RAW));
                String areaRaw = getStringValue(row.getCell(COL_AREA_RAW));
                String serieDocRaw = getStringValue(row.getCell(COL_SERIE_DOC_RAW));
                String nombreDocRaw = getStringValue(row.getCell(COL_NOMBRE_DOC_RAW));
                String tipoArchivoRaw = getStringValue(row.getCell(COL_TIPO_ARCHIVO_RAW));
                String formato = getStringValue(row.getCell(COL_FORMATO));
                String digitalizadorRaw = getStringValue(row.getCell(COL_DIGITALIZADOR_RAW));
                
                // Campo NO Obligatorio
                String cajaRaw = getStringValue(row.getCell(COL_CAJA_RAW)); 

                // Aplicar validación de obligatoriedad (TODOS DEBEN ESTAR, EXCEPTO CAJA)
                if (numeroHistoriaRaw == null) erroresFila.add("Número de Historia es obligatorio.");
                if (nombrePaciente == null) erroresFila.add("Nombre de Paciente es obligatorio.");
                if (sedeRaw == null) erroresFila.add("Sede es obligatoria.");
                if (areaRaw == null) erroresFila.add("Área es obligatoria.");
                if (serieDocRaw == null) erroresFila.add("Serie Documental es obligatoria.");
                if (nombreDocRaw == null) erroresFila.add("Nombre de Documento es obligatorio.");
                if (tipoArchivoRaw == null) erroresFila.add("Tipo de Archivo es obligatorio.");
                if (formato == null) erroresFila.add("Formato es obligatorio.");
                if (digitalizadorRaw == null) erroresFila.add("Digitalizador es obligatorio.");
                
                
                if (!erroresFila.isEmpty()) {
                    filaValida = false;
                }
                
                // --- 3. Mapeo y Almacenamiento ---

                if (filaValida) {
                    RegistroArchivoDigital registro = new RegistroArchivoDigital();
                    
                    // 🔑 CORRECCIÓN TIPO: setNumeroHistoria espera String
                    registro.setNumeroHistoria(numeroHistoriaRaw); 
                    registro.setNombrePaciente(nombrePaciente);
                    
                    // 🔑 CORRECCIÓN TIPO: setCajaRaw espera String (y ya existe en la entidad)
                    registro.setCajaRaw(cajaRaw); // Se acepta null si estaba vacío en Excel
                    
                    registro.setSedeRaw(sedeRaw);
                    registro.setAreaRaw(areaRaw);
                    registro.setSerieDocRaw(serieDocRaw);
                    registro.setNombreDocumentoRaw(nombreDocRaw);
                    registro.setTipoArchivoRaw(tipoArchivoRaw);
                    registro.setFormato(formato);
                    registro.setDigitalizadorRaw(digitalizadorRaw);

                    // 🔑 CORRECCIÓN TIPO: setStatus espera String
                    registro.setStatus("CARGADO"); // Asignar el valor String inicial del registro

                    // Asignación de fecha crítica
                    registro.setFechaCarga(LocalDateTime.now()); 

                    registro.setRevisado(false); 
                    registro.setCorregido(false);
                    // Los campos que son Integer (totalProduccion, muestra3Porciento) 
                    // deben ser mapeados usando getIntegerValue si son String en el Excel
                    
                    registrosValidos.add(registro);
                    
                    respuesta.setRegistrosNormalizadosConExito(respuesta.getRegistrosNormalizadosConExito() + 1);

                } else {
                    // Reporte de errores
                    String erroresConcatenados = String.join(" | ", erroresFila);
                    respuesta.getErroresDeNormalizacion().add("Fila " + (row.getRowNum() + 1) + ": " + erroresConcatenados);
                    respuesta.setRegistrosConErrores(respuesta.getRegistrosConErrores() + 1);
                }
            }

            // 4. Guardar todos los registros válidos
            registroArchivoDigitalRepository.saveAll(registrosValidos);
            
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo Excel: " + e.getMessage());
        }

        if (respuesta.getRegistrosConErrores() > 0) {
            respuesta.setMensaje("Carga finalizada. Se encontraron " + respuesta.getRegistrosConErrores() + " registros con errores.");
        }
        
        return respuesta;
    }

    // --- Funciones de Ayuda ---

    private String getStringValue(Cell cell) {
        if (cell == null) { return null; }
        DataFormatter formatter = new DataFormatter();
        String value = formatter.formatCellValue(cell).trim();
        return value.isEmpty() ? null : value;
    }
    
    private Integer getIntegerValue(String value) {
        if (value == null) { return null; }
        try {
            return Integer.parseInt(value.trim()); 
        } catch (NumberFormatException e) {
            return null; 
        }
    }
}