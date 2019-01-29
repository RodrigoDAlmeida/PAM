/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Rodrigo
 */
@Entity
public class Colaborador_Projeto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    public Colaborador_Projeto() {
    }
    
    @ManyToOne
    @JoinColumn(name = "codProjeto")
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "codColaborador")
    private Colaborador colaborador;

    @Enumerated(EnumType.STRING)
    private TipoColaboradorENUM tipo;

    public Long getCodigo() {
        return codigo;
    }
    

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public TipoColaboradorENUM getTipoColaborador() {
        return tipo;
    }

    public void setTipoColaborador(TipoColaboradorENUM tipoColaborador) {
        this.tipo = tipoColaborador;
    }

    @Override
    public String toString() {
        return "tipoColaborador=" + tipo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Colaborador_Projeto other = (Colaborador_Projeto) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    public Colaborador_Projeto(Projeto projeto, Colaborador colaborador, TipoColaboradorENUM tipoColaborador) {
        this.projeto = projeto;
        this.colaborador = colaborador;
        this.tipo = tipoColaborador;
    }
    
    
    

}
