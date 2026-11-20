package com.edudocs.backend;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudController {

    private final SolicitudRepository repository;

    public SolicitudController(SolicitudRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Solicitud> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Solicitud guardar(@RequestBody Solicitud solicitud) {
        if (solicitud.getTipoDoc() == null || solicitud.getTipoDoc().trim().isEmpty()) {
            solicitud.setTipoDoc("C.C.");
        }
        
        if (solicitud.getEstado() == null) {
            solicitud.setEstado("Pendiente");
        }
        
        return repository.save(solicitud);
    }

    @PutMapping("/{id}/aprobar")
    public Solicitud aprobar(@PathVariable Long id) {
        Solicitud s = repository.findById(id).orElseThrow();
        s.setEstado("Aprobado");
        return repository.save(s);
    }

    @GetMapping("/{id}/generar")
    public ResponseEntity<byte[]> generarDocumento(@PathVariable Long id) {
        Solicitud solicitud = repository.findById(id).orElseThrow();
        
        String tipoDocTexto = solicitud.getTipoDoc() != null ? solicitud.getTipoDoc() : "C.C.";

        String contenido = "========================================\n" +
                           "         CERTIFICADO DE ESTUDIO         \n" +
                           "========================================\n\n" +
                           "Tipo de Documento: " + tipoDocTexto + "\n" +
                           "Documento de Identidad: " + solicitud.getDoc() + "\n" +
                           "Nombre del Estudiante: " + solicitud.getNombre() + "\n" +
                           "Correo Electrónico: " + solicitud.getCorreo() + "\n" +
                           "Tipo de Trámite: " + solicitud.getTramite() + "\n" +
                           "Estado Actual: " + solicitud.getEstado() + "\n\n" +
                           "Documento generado automáticamente por Edudocs System.";
        
        byte[] archivoBytes = contenido.getBytes();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "Certificado_" + solicitud.getDoc() + ".txt");
        
        return new ResponseEntity<>(archivoBytes, headers, HttpStatus.OK);
    }

    @DeleteMapping
    public void eliminarTodas() {
        repository.deleteAll();
    }
}