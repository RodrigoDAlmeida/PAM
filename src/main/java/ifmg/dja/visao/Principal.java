package ifmg.dja.visao;

import ifmg.dja.dao.ColaboradorDAO;
import ifmg.dja.dao.FeriadoDAO;
import ifmg.dja.dao.ProjetoDAO;
import ifmg.dja.modelo.Colaborador;
import ifmg.dja.modelo.Feriado;
import ifmg.dja.modelo.Projeto;
import ifmg.dja.modelo.TipoColaboradorENUM;
import java.io.IOException;
import java.sql.Date;

/**
 *
 */
public class Principal {
    
    public static void main(String[] args) throws IOException {
   
        ColaboradorDAO cDAO = new ColaboradorDAO();
        ProjetoDAO pDAO = new ProjetoDAO();
        FeriadoDAO fDAO = new FeriadoDAO();
        
        /* //ADICIONANDO COLABORADOR
        Colaborador c = new Colaborador();
        c.setLogin("bruno");
        c.setNome("Bruno Ferreira");
        c.setSenha("123897999");
        cDAO.salvar(c);
        */
        
        /* //ASSOCIA UM PROJETO A UM COLABORADOR
        Colaborador c = cDAO.bucasPeloCodigo(1L);
        Projeto p = pDAO.bucasPeloCodigo(1L);
        c.addProjeto(p, TipoColaboradorENUM.SERVIDOR);
        cDAO.salvar(c);
        */
        
        
       /* //ADICIONANDO UM NOVO FERIADO
        Feriado f1 = new Feriado();
        f1.setDescricao("Aniversaio de Formiga - MG");
        f1.setDataFeriado(Date.valueOf("2000-06-06"));
        fDAO.salvar(f1);
        */
       
         //ALTERANDO
        
        
        
        System.out.println(cDAO.bucasPeloCodigo(1L));
        System.out.println(pDAO.bucasPeloCodigo(1L));
        
        //System.out.println(pDAO.buscarTodos());

        
        
        System.out.println("Fim da Execução");
    }
    
}
