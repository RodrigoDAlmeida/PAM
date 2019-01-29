package ifmg.dja.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 */
@Entity
public class Projeto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String edital;
    private String termoCompromisso;
    private String titulo;
    private String coordenador;
    private String financiador;
    private Byte tipoFinanciador;
    private Integer horasSemanais;
    
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    private List<Colaborador_Projeto> colaboradores = new ArrayList<>();
    
      
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    private List<Lancamento> lancamentos = new ArrayList<>();

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public List<Colaborador_Projeto> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador_Projeto> colaboradores) {
        this.colaboradores = colaboradores;
    }
    
    public Projeto() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getEdital() {
        return edital;
    }

    public void setEdital(String edital) {
        this.edital = edital;
    }

    public String getTermoCompromisso() {
        return termoCompromisso;
    }

    public void setTermoCompromisso(String termoCompromisso) {
        this.termoCompromisso = termoCompromisso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCoordenador() {
        return coordenador;
    }
    

    public void setCoordenador(String coordenador) {
        this.coordenador = coordenador;
    }

    public String getFinanciador() {
        return financiador;
    }

    public void setFinanciador(String financiador) {
        this.financiador = financiador;
    }

    public Byte getTipoFinanciador() {
        return tipoFinanciador;
    }

    public void setTipoFinanciador(Byte tipoFinanciador) {
        this.tipoFinanciador = tipoFinanciador;
    }

    public Integer getHorasSemanais() {
        return horasSemanais;
    }

    public void setHorasSemanais(Integer horasSemanais) {
        this.horasSemanais = horasSemanais;
    }


    @Override
    public String toString() {
        return titulo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Projeto other = (Projeto) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }      

    public Projeto(String edital, String termoCompromisso, String titulo, String coordenador, String financiador, Byte tipoFinanciador, Integer horasSemanais) {
        this.edital = edital;
        this.termoCompromisso = termoCompromisso;
        this.titulo = titulo;
        this.coordenador = coordenador;
        this.financiador = financiador;
        this.tipoFinanciador = tipoFinanciador;
        this.horasSemanais = horasSemanais;
    }
    
    public void removeColaborador(Colaborador colaborador){
        
        
        for (Colaborador_Projeto colaboProj : colaboradores) {
            if(colaboProj.getColaborador().getCodigo().equals(colaborador.getCodigo())){
                this.colaboradores.remove(colaboProj);
                return;
            }
            
        }
        
    }
    
    
    
}
