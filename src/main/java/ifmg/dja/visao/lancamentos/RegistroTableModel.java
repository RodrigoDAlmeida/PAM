package ifmg.dja.visao.lancamentos;




import ifmg.dja.modelo.Registro;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class RegistroTableModel extends AbstractTableModel{

    private List<Registro> dados;
    private String[] colunas = {"Código","Descrição", "Data", "Entrada", "Saída","Horas Totais"};

    public RegistroTableModel(List<Registro> registro) {  
        dados = registro;
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
            case 2 : return new SimpleDateFormat("dd/MM/yyyy").format(dados.get(rowIndex).getDataRegistro());
            case 3 : return new SimpleDateFormat("HH:mm:ss").format(dados.get(rowIndex).getHoraEntrada());
            case 4 : return new SimpleDateFormat("HH:mm:ss").format(dados.get(rowIndex).getHoraSaida());
            case 5 : return dados.get(rowIndex).getHorasTotais();
         
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
            case 2 :  dados.get(rowIndex).setDataRegistro((Date)value);break;
            case 3 :  dados.get(rowIndex).setHoraEntrada((Date)value);break;
            case 4 :  dados.get(rowIndex).setHoraSaida((Date)value);break;
            case 5 :  dados.get(rowIndex).setHorasTotais((Double)value);break;
        }

        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public Registro getValueAt(int rowIndex){
        return dados.get(rowIndex);
    }
    
    public void addRow(Registro a){
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
