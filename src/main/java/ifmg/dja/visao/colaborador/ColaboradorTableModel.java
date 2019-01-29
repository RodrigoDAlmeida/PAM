package ifmg.dja.visao.colaborador;



import ifmg.dja.modelo.Colaborador;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ColaboradorTableModel extends AbstractTableModel{

    private List<Colaborador> dados;
    private String[] colunas = {"Codigo","Nome", "Login"};

    public ColaboradorTableModel(List<Colaborador> colaborador) {  
        dados = colaborador;
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
            case 1 : return dados.get(rowIndex).getNome();
            case 2 : return dados.get(rowIndex).getLogin();
      }
        
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    
        if (value == null)
            return;
        
        switch (columnIndex){
            case 0 :  dados.get(rowIndex).setCodigo((Long)value);break;
            case 1 :  dados.get(rowIndex).setNome((String)value);break;
            case 2 :  dados.get(rowIndex).setLogin((String)value);break;
        }

        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public Colaborador getValueAt(int rowIndex){
        return dados.get(rowIndex);
    }
    
    public void addRow(Colaborador a){
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
