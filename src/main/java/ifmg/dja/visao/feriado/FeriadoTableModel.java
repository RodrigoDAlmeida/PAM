package ifmg.dja.visao.feriado;



import ifmg.dja.modelo.Feriado;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class FeriadoTableModel extends AbstractTableModel{

    private List<Feriado> dados;
    private String[] colunas = {"Codigo","Descrição", "Data"};

    public FeriadoTableModel(List<Feriado> colaborador) {  
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
            case 1 : return dados.get(rowIndex).getDescricao();
            case 2 : return new SimpleDateFormat("dd/MM/yyyy").format(dados.get(rowIndex).getDataFeriado());
      }
        
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    
        if (value == null)
            return;
        
        switch (columnIndex){
            case 0 :  dados.get(rowIndex).setCodigo((Long)value);break;
            case 1 :  dados.get(rowIndex).setDescricao((String)value);break;
            case 2 :  dados.get(rowIndex).setDataFeriado((Date)value);break;
        }

        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public Feriado getValueAt(int rowIndex){
        return dados.get(rowIndex);
    }
    
    public void addRow(Feriado a){
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
