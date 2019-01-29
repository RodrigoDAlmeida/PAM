package ifmg.dja.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Registro implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @Temporal(TemporalType.DATE)
    private Date dataRegistro;
    
    @Temporal(TemporalType.TIME)
    private Date horaEntrada;
    
    @Temporal(TemporalType.TIME)
    private Date horaSaida;
    
    @Enumerated(EnumType.STRING)
    private TipoRegistroENUM tipo;
    
    private Double horasTotais;

    public TipoRegistroENUM getTipo() {
        return tipo;
    }

    public void setTipo(TipoRegistroENUM tipo) {
        this.tipo = tipo;
    }

    public Double getHorasTotais() {
        return horasTotais;
    }

    public void setHorasTotais(Double horasTotais) {
        this.horasTotais = horasTotais;
    }
    
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "codLancamento")
    private Lancamento lancamento;

    public Registro() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }    

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Date horaSaida) {
        this.horaSaida = horaSaida;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Registro other = (Registro) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    public Registro(Date dataRegistro, Date horaEntrada, Date horaSaida, String descricao, Lancamento lancamento) {
        this.dataRegistro = dataRegistro;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.descricao = descricao;
        this.lancamento = lancamento;
    }
    
    
    
}
