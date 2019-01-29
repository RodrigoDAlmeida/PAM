package ifmg.dja.visao.projeto;



import ifmg.dja.modelo.Projeto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProjetoTableModelCompleta extends AbstractTableModel{

    private List<Projeto> dados;
    private String[] colunas = {"Codigo","Titulo", "Coordenador", "Financiador", "Edital","Termo de Compromisso"};

    public ProjetoTableModelCompleta(List<Projeto> projeto) {  
        dados = projeto;
    }            
    
    @Override
    public int getRowCount() {
       return dados.size();
    }

    @Override
    public int getColumnCount() {
       return colunas.length;        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        switch (columnIndex){
            case 0 : return dados.get(rowIndex).getCodigo();
            case 1 : return dados.get(rowIndex).getTitulo();
            case 2 : return dados.get(rowIndex).getCoordenador();
            case 3 : return dados.get(rowIndex).getFinanciador();
            case 4 : return dados.get(rowIndex).getEdital();
            case 5 : return dados.get(rowIndex).getTermoCompromisso();
         
        }
        
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    
        if (value == null)
            return;
        
        switch (columnIndex){
            case 0 :  dados.get(rowIndex).setCodigo((Long)value);break;
            case 1 :  dados.get(rowIndex).setTitulo((String)value);break;
            case 2 :  dados.get(rowIndex).setCoordenador((String)value);break;
            case 3 :  dados.get(rowIndex).setFinanciador((String)value);break;
            case 4 :  dados.get(rowIndex).setEdital((String)value);break;
            case 5 :  dados.get(rowIndex).setTermoCompromisso((String)value);break;
        }

        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public Projeto getValueAt(int rowIndex){
        return dados.get(rowIndex);
    }
    
    public void addRow(Projeto a){
        dados.add(a);
        this.fireTableDataChanged();
    }

    public void removeRow(int linha){
        dados.remove(linha);
        this.fireTableDataChanged();
    }
    
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }                
}
