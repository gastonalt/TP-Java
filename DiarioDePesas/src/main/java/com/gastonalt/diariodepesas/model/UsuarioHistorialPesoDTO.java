package com.gastonalt.diariodepesas.model;

import java.util.List;

public class UsuarioHistorialPesoDTO {
    private Usuario usuario;
    private List<HistorialPeso> pesos;

    public UsuarioHistorialPesoDTO(Usuario usuario, List<HistorialPeso> pesos) {
        this.usuario = usuario;
        this.pesos = pesos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<HistorialPeso> getPesos() {
        return pesos;
    }

    public void setPesos(List<HistorialPeso> pesos) {
        this.pesos = pesos;
    }
}
