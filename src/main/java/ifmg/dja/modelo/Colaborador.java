package ifmg.dja.modelo;

import ifmg.dja.service.ProjetoRN;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 */
@Entity
public class Colaborador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String nome;
    private String login;
    private String senha;

    @JoinColumn()
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colaborador", fetch = FetchType.EAGER)
    private List<Colaborador_Projeto> projetos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colaborador", fetch = FetchType.EAGER)
    private List<Lancamento> lancamentos = new ArrayList<>();

    @Override
    public String toString() {
        return "Colaborador{" + "codigo=" + codigo + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", projetos=" + projetos + '}';
    }

    public List<Colaborador_Projeto> getProjetos() {
        return projetos;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public void setProjetos(List<Colaborador_Projeto> projetos) {
        this.projetos = projetos;
    }

    public Colaborador() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.codigo);
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
        final Colaborador other = (Colaborador) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    public Colaborador(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public void addProjeto(Projeto p, TipoColaboradorENUM tipo) {

        this.projetos.add(new Colaborador_Projeto(p, this, tipo));
    }

    public Boolean saiProjeto(Projeto p) {

        for (Colaborador_Projeto Cproj : projetos) {
            if (Cproj.getProjeto().getCodigo().equals(p.getCodigo())) {
                this.projetos.remove(Cproj);
                return true;

            }

        }

        return false;
    }

    public List<Projeto> getListaProjetosParticipante() {

        List<Projeto> listaProjetos = new ArrayList<>();

        for (int i = 0; i < this.projetos.size(); i++) {

            listaProjetos.add(this.projetos.get(i).getProjeto());
        }
        return listaProjetos;
    }

    public List<Projeto> getListaProjetosNaoParticipante() {

        ProjetoRN pRN = new ProjetoRN();

        List<Projeto> listaProjetos = pRN.buscarTodos();

        for (int i = 0; i < projetos.size(); i++) {

            listaProjetos.remove(projetos.get(i).getProjeto());
        }

        return listaProjetos;
    }

}
