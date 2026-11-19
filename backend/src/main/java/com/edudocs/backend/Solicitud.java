package com.edudocs.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_doc")
    private String tipoDoc; // Guarda "T.I." o "C.C."

    private String doc;
    private String nombre;
    private String correo;
    private String curso;
    private String nivel;
    private String tramite;
    private String estado;

    public Solicitud() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getDoc() { return doc; }
    public void setDoc(String doc) { this.doc = doc; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getTramite() { return tramite; }
    public void setTramite(String tramite) { this.tramite = tramite; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

}