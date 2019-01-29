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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 */
@Entity
public class Lancamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String mesReferencia;
    private Integer anoReferencia;
    private String observacao;


    @OneToMany(mappedBy = "lancamento", fetch = FetchType.EAGER)
    private List<Registro> regPesquisa = new ArrayList<>();

    @OneToMany(mappedBy = "lancamento", fetch = FetchType.EAGER)
    private List<Registro> regPolo = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "codProjeto")
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "codColaborador")
    private Colaborador colaborador;

    public Lancamento() {
        this.observacao = "";
    }

    public Lancamento(Long codigo, String mesReferencia, Integer anoReferencia, String observacao, Projeto projeto, Colaborador colaborador) {
        this.codigo = codigo;
        this.mesReferencia = mesReferencia;
        this.anoReferencia = anoReferencia;
        this.observacao = observacao;
        this.projeto = projeto;
        this.colaborador = colaborador;
    }
    
    

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public String getMesReferencia() {
        return mesReferencia;
    }
    
    public String getObservacao() {
     return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


    public void setMesReferencia(String mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public List<Registro> getRegPesquisa() {
        return regPesquisa;
    }

    public void setRegPesquisa(List<Registro> regPesquisa) {
        this.regPesquisa = regPesquisa;
    }

    public List<Registro> getRegPolo() {
        return regPolo;
    }

    public void setRegPolo(List<Registro> regPolo) {
        this.regPolo = regPolo;
    }

    @Override
    public String toString() {
        return "Lancamento{" + "codigo=" + codigo + ", projeto=" + projeto
                + ", mesReferencia=" + mesReferencia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codigo);
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
        final Lancamento other = (Lancamento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
    
   

    public Lancamento(String mesReferencia, Integer anoReferencia, Projeto projeto, Colaborador colaborador) {
        this.mesReferencia = mesReferencia;
        this.projeto = projeto;
        this.colaborador = colaborador;
        this.anoReferencia = anoReferencia;
    }

    public Double somaHorasPesquisa() {

        Double soma = 0.0;

        for (Registro r : regPesquisa) {
            soma += r.getHorasTotais();
        }
        return soma;
    }

    public Double somaHorasPolo() {

        Double soma = 0.0;

        for (Registro r : regPolo) {
            soma += r.getHorasTotais();
        }
        return soma;
    }

    public Double somaTotal() {
        return somaHorasPesquisa() + somaHorasPolo();
    }

}
