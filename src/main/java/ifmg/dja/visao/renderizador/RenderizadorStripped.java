/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.visao.renderizador;



import java.awt.Color;
import java.awt.Component;
import static java.awt.Label.CENTER;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


public class RenderizadorStripped implements TableCellRenderer {

        // toda célula vai ser renderizada por um JLabel
        // poderia ser qualquer outro componente
        // poderia ter mais de um componente, para renderizar celulas diferentes
        private final JLabel componenteRenderizador;

        public RenderizadorStripped() {
            componenteRenderizador = new JLabel();
            componenteRenderizador.setOpaque(true);
        }    
    
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            
            // atualizar componente renderizador
            componenteRenderizador.setText(String.valueOf(value));
            //componenteRenderizador.setHorizontalAlignment(getAlinhamento(column));
            componenteRenderizador.setBackground(getCor(row, isSelected));
            //componenteRenderizador.setHorizontalAlignment(1);
            return componenteRenderizador;        
    }
    
     // escolhe a cor a partir da linha
        private Color getCor(int linha, boolean selecionada) {

            // linhas selecionadas são azuis
            if (selecionada) {
                return new Color(135,206,235);
            }

            // linhas pares são amarelas e impares são verdes
            // isso vai criar um efeito zebrado
            if (linha % 2 == 0) {
                return new Color(255,248,220);
            }
            return new Color(220, 230, 220);   
        }

        // escolhe o alinhamento a partir da coluna
        private int getAlinhamento(int coluna) {
            switch (coluna) {
                case 0:
                    return SwingConstants.LEFT;
                case 1:
                    return SwingConstants.CENTER;
                case 2:
                default:
                    return SwingConstants.RIGHT;
            }
        }        
}
