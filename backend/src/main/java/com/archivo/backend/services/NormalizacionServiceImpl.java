package com.archivo.backend.services; // Asegúrate de usar el paquete correcto

import com.archivo.backend.entities.*;
import com.archivo.backend.repositories.*;
import com.archivo.backend.services.NormalizacionService; // Asegúrate de tener la interfaz
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class NormalizacionServiceImpl implements NormalizacionService {

    @Autowired
    private RegistroArchivoDigitalRepository registroRepository;

    // Repositorios de Catálogo inyectados para buscar IDs
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private AreaInternaRepository areaRepository;
    @Autowired
    private SerieDocumentalRepository serieRepository;
    @Autowired
    private TipoArchivoRepository tipoArchivoRepository;
    @Autowired
    private NombreDocumentoCatalogoRepository nombreDocumentoRepository;

    @Override
    @Transactional
    // Ejecutar cada 2 minutos (120,000 ms). Usamos fixedDelay para que inicie
    // 2 minutos DESPUÉS de que la ejecución anterior terminó, evitando
    // superposiciones.
    @Scheduled(fixedDelay = 120000)
    public int normalizarRegistrosPendientes() {

        System.out.println("--- Ejecutando Normalización: " + LocalDateTime.now() + " ---");

        // 1. BUSCAR registros cuyo status es 'PENDIENTE'
        List<RegistroArchivoDigital> registros = registroRepository.findByStatus("PENDIENTE");

        System.out.println("Registros PENDIENTES encontrados para procesar: " + registros.size());

        int normalizadosConExito = 0;

        for (RegistroArchivoDigital registro : registros) {
            boolean exitoNormalizacion = true;
            StringBuilder erroresDetectados = new StringBuilder();

            // --- 1. NORMALIZAR SEDE ---
            // Protección: Solo busca si el campo RAW no es nulo ni está vacío
            if (registro.getSedeRaw() != null && !registro.getSedeRaw().trim().isEmpty()) {
                Optional<Sede> sede = sedeRepository.findByNombre(registro.getSedeRaw().trim());
                if (sede.isPresent()) {
                    registro.setSedeId(sede.get().getId());
                } else {
                    exitoNormalizacion = false;
                    erroresDetectados.append("Sede [").append(registro.getSedeRaw()).append("] no existe. | ");
                }
            } else {
                // Si el campo RAW es NULL/Vacío, marcamos error si es un campo NOT NULL en la
                // BD
                // Aunque tu BD no tiene sede_raw como NOT NULL, es buena práctica registrarlo.
                // Si permites nulos en el RAW, simplemente no hagas nada (no marca error).
            }

            // --- 2. NORMALIZAR ÁREA INTERNA ---
            if (registro.getAreaRaw() != null && !registro.getAreaRaw().trim().isEmpty()) {
                Optional<AreaInterna> area = areaRepository.findByNombre(registro.getAreaRaw().trim());
                if (area.isPresent()) {
                    registro.setAreaInternaId(area.get().getId());
                } else {
                    exitoNormalizacion = false;
                    erroresDetectados.append("Área [").append(registro.getAreaRaw()).append("] no existe. | ");
                }
            }

            // --- 3. NORMALIZAR SERIE DOCUMENTAL ---
            if (registro.getSerieDocRaw() != null && !registro.getSerieDocRaw().trim().isEmpty()) {
                Optional<SerieDocumental> serie = serieRepository.findByNombre(registro.getSerieDocRaw().trim());
                if (serie.isPresent()) {
                    registro.setSerieDocumentalId(serie.get().getId());
                } else {
                    exitoNormalizacion = false;
                    erroresDetectados.append("Serie Documental [").append(registro.getSerieDocRaw())
                            .append("] no existe. | ");
                }
            }

            // --- 4. NORMALIZAR TIPO DE ARCHIVO ---
            if (registro.getTipoArchivoRaw() != null && !registro.getTipoArchivoRaw().trim().isEmpty()) {
                Optional<TipoArchivo> tipo = tipoArchivoRepository.findByNombre(registro.getTipoArchivoRaw().trim());
                if (tipo.isPresent()) {
                    registro.setTipoArchivoId(tipo.get().getId());
                } else {
                    exitoNormalizacion = false;
                    erroresDetectados.append("Tipo Archivo [").append(registro.getTipoArchivoRaw())
                            .append("] no existe. | ");
                }
            }

            // --- 5. NORMALIZAR NOMBRE DOCUMENTO ---
            if (registro.getNombreDocumentoRaw() != null && !registro.getNombreDocumentoRaw().trim().isEmpty()) {
                Optional<NombreDocumentoCatalogo> nombreDoc = nombreDocumentoRepository
                        .findByNombre(registro.getNombreDocumentoRaw().trim());

                if (nombreDoc.isPresent()) {
                    registro.setNombreDocumentoId(nombreDoc.get().getId());
                } else {
                    exitoNormalizacion = false;
                    erroresDetectados.append("Nombre Documento [").append(registro.getNombreDocumentoRaw())
                            .append("] no existe. | ");
                }
            }

            // --- FINALIZACIÓN DEL REGISTRO ---
            if (exitoNormalizacion) {
                registro.setStatus("NORMALIZADO");
                normalizadosConExito++;
            } else {
                // Si hubo algún error en la normalización:
                registro.setStatus("ERROR_NORMALIZACION");
                // Guardamos el detalle de los errores encontrados.
                registro.setTipoError(erroresDetectados.toString().trim());
            }
        }

        // Guardamos todos los registros modificados (éxito o error)
        registroRepository.saveAll(registros);

        System.out.println("Normalización finalizada. Registros normalizados con éxito: " + normalizadosConExito);
        return normalizadosConExito;
    }
}