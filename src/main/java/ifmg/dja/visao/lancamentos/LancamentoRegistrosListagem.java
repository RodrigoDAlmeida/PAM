/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.visao.lancamentos;

import ifmg.dja.modelo.Colaborador;
import ifmg.dja.modelo.Lancamento;
import ifmg.dja.modelo.Projeto;
import ifmg.dja.modelo.Registro;
import ifmg.dja.modelo.TipoRegistroENUM;
import ifmg.dja.service.LancamentoRN;
import ifmg.dja.service.RegistroRN;
import ifmg.dja.util.Ferramentas;
import ifmg.dja.util.NegocioException;
import ifmg.dja.visao.renderizador.RenderizadorStripped;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Rodrigo
 */
public class LancamentoRegistrosListagem extends javax.swing.JInternalFrame {

    /**
     * Creates new form RegistroListagem
     */
    private final RegistroRN rRN = new RegistroRN();
    private final LancamentoRN lRN = new LancamentoRN();

    private List<Registro> registrosPesquisa;
    private List<Registro> registrosPolo;

    private Lancamento lancamentoAtual;
    private Projeto projeto;
    private Colaborador colaborador;

    private RegistroTableModel rtmPesquisa;
    private RegistroTableModel rtmPolo;

    private Boolean cumpriuHoras = false;

    public LancamentoRegistrosListagem(Projeto projeto, Colaborador colaborador) {
        initComponents();

        this.projeto = projeto;
        this.colaborador = colaborador;
        carregaLancamento();
        carregaTabelas();
        carregaLabels();

    }

    private void carregaLancamento() {

        lancamentoAtual = lRN.buscarLancamentoMes(projeto, colaborador, Ferramentas.intParaMes(jmcMesReferencia.getMonth()), jycAnoReferencia.getYear());

        if (lancamentoAtual == null) { //Se não houver lançamentos, é criado um novo
            lancamentoAtual = new Lancamento((Ferramentas.intParaMes(jmcMesReferencia.getMonth())), jycAnoReferencia.getYear(), projeto, colaborador);
            lancamentoAtual.setAnoReferencia(jycAnoReferencia.getYear());
            lancamentoAtual.setObservacao(" ");
            try {
                lRN.salvar(lancamentoAtual);
            } catch (NegocioException ex) {
                Logger.getLogger(LancamentoRegistrosListagem.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void carregaTabelas() {

        this.lancamentoAtual = lRN.buscarPorCodigo(lancamentoAtual.getCodigo()); //Atualiza o Objeto
        registrosPesquisa = rRN.buscaRegistroPesquisa(lancamentoAtual);
        registrosPolo = rRN.buscaRegistroPolo(lancamentoAtual);

        rtmPesquisa = new RegistroTableModel(registrosPesquisa);
        jTableProjetoPesquisa.setModel(rtmPesquisa);
        // jTableProjetoPesquisa.setDefaultRenderer(Object.class, new RenderizadorStripped());

        rtmPolo = new RegistroTableModel(registrosPolo);
        jTablePoloInovacao.setModel(rtmPolo);
        //  jTablePoloInovacao.setDefaultRenderer(Object.class, new RenderizadorStripped());

        jTableProjetoPesquisa.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTableProjetoPesquisa.getColumnModel().getColumn(1).setPreferredWidth(517);
        jTableProjetoPesquisa.getColumnModel().getColumn(2).setPreferredWidth(70);
        jTableProjetoPesquisa.getColumnModel().getColumn(3).setPreferredWidth(63);
        jTableProjetoPesquisa.getColumnModel().getColumn(4).setPreferredWidth(63);
        jTableProjetoPesquisa.getColumnModel().getColumn(5).setPreferredWidth(75);

        jTablePoloInovacao.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTablePoloInovacao.getColumnModel().getColumn(1).setPreferredWidth(517);
        jTablePoloInovacao.getColumnModel().getColumn(2).setPreferredWidth(70);
        jTablePoloInovacao.getColumnModel().getColumn(3).setPreferredWidth(63);
        jTablePoloInovacao.getColumnModel().getColumn(4).setPreferredWidth(63);
        jTablePoloInovacao.getColumnModel().getColumn(5).setPreferredWidth(75);

    }

    private void carregaLabels() {

        Double horasTotaisPesquisa = Ferramentas.arredondar(rRN.somaTotalPesquisa(lancamentoAtual), 2);
        Double horasTotaisPolo = Ferramentas.arredondar(rRN.somaTotalPolo(lancamentoAtual), 2);

        jlbProjetoName.setText(projeto.getTitulo());
        jlbHorasMinimasProjeto.setText(projeto.getHorasSemanais().toString());
        jlbHorasPesquisa.setText(horasTotaisPesquisa.toString());
        jlbHorasPolo.setText(horasTotaisPolo.toString());
        jlbHorasTotais.setText(String.valueOf(Ferramentas.arredondar(horasTotaisPesquisa + horasTotaisPolo, 2)));

        try {
            jtxAObservacoes.setText(lancamentoAtual.getObservacao());
        } catch (NullPointerException e) {
            
            lancamentoAtual = new Lancamento((Ferramentas.intParaMes(jmcMesReferencia.getMonth())), jycAnoReferencia.getYear(), projeto, colaborador);
            lancamentoAtual.setAnoReferencia(jycAnoReferencia.getYear());
            lancamentoAtual.setObservacao(" ");
            jtxAObservacoes.setText(lancamentoAtual.getObservacao());
           
            
        }
        if ((horasTotaisPesquisa + horasTotaisPolo) < projeto.getHorasSemanais()) {

            jlbHorasTotais.setForeground(new java.awt.Color(255, 51, 51));
            cumpriuHoras = false;

        } else {
            jlbHorasTotais.setForeground(new java.awt.Color(51, 102, 0));
            cumpriuHoras = true;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlbSPROJETO = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jbtIncluir1 = new javax.swing.JButton();
        jbtEditar1 = new javax.swing.JButton();
        jbtExcluir1 = new javax.swing.JButton();
        jbtAtualizar1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableProjetoPesquisa = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jbtIncluir2 = new javax.swing.JButton();
        jbtEditar2 = new javax.swing.JButton();
        jbtExcluir2 = new javax.swing.JButton();
        jbtAtualizar2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePoloInovacao = new javax.swing.JTable();
        jlbProjetoName = new javax.swing.JLabel();
        jlbSHORASPROJ = new javax.swing.JLabel();
        jlbSTOTALMES = new javax.swing.JLabel();
        jlbSHORASPOLO = new javax.swing.JLabel();
        jlbHorasPolo = new javax.swing.JLabel();
        jlbHorasPesquisa = new javax.swing.JLabel();
        jlbHorasTotais = new javax.swing.JLabel();
        jlbSBARRA = new javax.swing.JLabel();
        jlbHorasMinimasProjeto = new javax.swing.JLabel();
        jbtGerarRelatorio = new javax.swing.JButton();
        jmcMesReferencia = new com.toedter.calendar.JMonthChooser();
        jycAnoReferencia = new com.toedter.calendar.JYearChooser();
        jlbSDATA = new javax.swing.JLabel();
        jbtBuscaLancamento = new javax.swing.JButton();
        jlbSObservacao = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtxAObservacoes = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setResizable(true);
        setOpaque(true);
        setPreferredSize(new java.awt.Dimension(900, 560));
        setVerifyInputWhenFocusTarget(false);

        jlbSPROJETO.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbSPROJETO.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbSPROJETO.setText("Projeto: ");

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jbtIncluir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sign-add-icon.png"))); // NOI18N
        jbtIncluir1.setText("Incluir");
        jbtIncluir1.setFocusable(false);
        jbtIncluir1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtIncluir1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtIncluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtIncluir1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtIncluir1);

        jbtEditar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/notepad-icon.png"))); // NOI18N
        jbtEditar1.setText("Editar");
        jbtEditar1.setFocusable(false);
        jbtEditar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtEditar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtEditar1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtEditar1);

        jbtExcluir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sign-error-icon.png"))); // NOI18N
        jbtExcluir1.setText("Exluir");
        jbtExcluir1.setFocusable(false);
        jbtExcluir1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtExcluir1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtExcluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtExcluir1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtExcluir1);

        jbtAtualizar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sign-sync-icon.png"))); // NOI18N
        jbtAtualizar1.setText("Atualizar");
        jbtAtualizar1.setFocusable(false);
        jbtAtualizar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtAtualizar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtAtualizar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAtualizar1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtAtualizar1);

        jTableProjetoPesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTableProjetoPesquisa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Registros Projeto de Pesquisa", jPanel2);

        jToolBar2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jbtIncluir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sign-add-icon.png"))); // NOI18N
        jbtIncluir2.setText("Incluir");
        jbtIncluir2.setFocusable(false);
        jbtIncluir2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtIncluir2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtIncluir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtIncluir2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jbtIncluir2);

        jbtEditar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/notepad-icon.png"))); // NOI18N
        jbtEditar2.setText("Editar");
        jbtEditar2.setFocusable(false);
        jbtEditar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtEditar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtEditar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtEditar2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jbtEditar2);

        jbtExcluir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sign-error-icon.png"))); // NOI18N
        jbtExcluir2.setText("Exluir");
        jbtExcluir2.setFocusable(false);
        jbtExcluir2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtExcluir2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtExcluir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtExcluir2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jbtExcluir2);

        jbtAtualizar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sign-sync-icon.png"))); // NOI18N
        jbtAtualizar2.setText("Atualizar");
        jbtAtualizar2.setFocusable(false);
        jbtAtualizar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtAtualizar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtAtualizar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAtualizar2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jbtAtualizar2);

        jTablePoloInovacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTablePoloInovacao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Registros Polo de Inovação", jPanel3);

        jlbProjetoName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlbProjetoName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbProjetoName.setText("<nomeProjeto>");

        jlbSHORASPROJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlbSHORASPROJ.setText("Horas Projeto de Pesquisa:");

        jlbSTOTALMES.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbSTOTALMES.setText("Total de Horas do Mês:");

        jlbSHORASPOLO.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlbSHORASPOLO.setText("Horas Polo de Inovação:");

        jlbHorasPolo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbHorasPolo.setText("<hPI>");

        jlbHorasPesquisa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbHorasPesquisa.setText("<hPP>");

        jlbHorasTotais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbHorasTotais.setForeground(new java.awt.Color(51, 102, 0));
        jlbHorasTotais.setText("<hT>");

        jlbSBARRA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbSBARRA.setText("/");

        jlbHorasMinimasProjeto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbHorasMinimasProjeto.setText("<hP>");

        jbtGerarRelatorio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtGerarRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/print-icon.png"))); // NOI18N
        jbtGerarRelatorio.setText("Gerar Relatório");
        jbtGerarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGerarRelatorioActionPerformed(evt);
            }
        });

        jlbSDATA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbSDATA.setText("Data:");

        jbtBuscaLancamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/search-icon2.png"))); // NOI18N
        jbtBuscaLancamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtBuscaLancamentoActionPerformed(evt);
            }
        });

        jlbSObservacao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbSObservacao.setText("Observações:");

        jtxAObservacoes.setColumns(20);
        jtxAObservacoes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxAObservacoes.setRows(5);
        jtxAObservacoes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxAObservacoesFocusLost(evt);
            }
        });
        jScrollPane3.setViewportView(jtxAObservacoes);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logo56.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlbSHORASPOLO)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jlbHorasPolo))
                                            .addComponent(jbtGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(jlbSHORASPROJ)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlbHorasPesquisa))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jlbSTOTALMES)
                                        .addGap(15, 15, 15)
                                        .addComponent(jlbHorasTotais)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlbSBARRA)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlbHorasMinimasProjeto))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jlbSObservacao)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlbSPROJETO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlbSDATA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jmcMesReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jycAnoReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtBuscaLancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(126, 126, 126))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jlbProjetoName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jlbSPROJETO)
                                .addComponent(jlbSDATA))
                            .addComponent(jmcMesReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jycAnoReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtBuscaLancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(jlbProjetoName))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jlbSObservacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbSHORASPROJ)
                            .addComponent(jlbHorasPesquisa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbSHORASPOLO)
                            .addComponent(jlbHorasPolo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbSTOTALMES)
                            .addComponent(jlbHorasTotais)
                            .addComponent(jlbSBARRA)
                            .addComponent(jlbHorasMinimasProjeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtIncluir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIncluir2ActionPerformed

        RegistroCadastro dialog = new RegistroCadastro(null, true, rRN, lancamentoAtual, TipoRegistroENUM.POLO);
        dialog.setVisible(true);
        if (dialog.confirmou) {
            carregaTabelas();
            carregaLabels();
        }

    }//GEN-LAST:event_jbtIncluir2ActionPerformed

    private void jbtEditar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtEditar2ActionPerformed

        if (jTablePoloInovacao.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Registro", "Edição de Registro", JOptionPane.ERROR_MESSAGE);
        } else {
            RegistroCadastro dialog = new RegistroCadastro(null, true, rRN, lancamentoAtual, rtmPolo.getValueAt(jTablePoloInovacao.getSelectedRow()));
            dialog.setVisible(true);
            if (dialog.confirmou) {
                carregaTabelas();
                carregaLabels();
            }

        }


    }//GEN-LAST:event_jbtEditar2ActionPerformed

    private void jbtExcluir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtExcluir2ActionPerformed
        if (jTablePoloInovacao.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Registro", "Exclusão de Registro", JOptionPane.ERROR_MESSAGE);
        } else {
            int resp = JOptionPane.showConfirmDialog(this, "Confirma a exclusão deste registro?", "Exclusão de Registro", JOptionPane.YES_NO_OPTION);

            if (resp != JOptionPane.YES_OPTION) {
                return;
            } else {

                try {
                    rRN.excluir(rtmPolo.getValueAt(jTablePoloInovacao.getSelectedRow()));
                    carregaTabelas();
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(RegistroTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                carregaTabelas();
                carregaLabels();

            }
        }
    }//GEN-LAST:event_jbtExcluir2ActionPerformed

    private void jbtAtualizar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAtualizar2ActionPerformed
        carregaLabels();
        carregaTabelas();

    }//GEN-LAST:event_jbtAtualizar2ActionPerformed

    private void jbtGerarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGerarRelatorioActionPerformed

        if (jtxAObservacoes.getText() != null) {
            this.lancamentoAtual.setObservacao(jtxAObservacoes.getText());
        }
        try {
            lRN.salvar(lancamentoAtual);
        } catch (NegocioException ex) {
            Logger.getLogger(LancamentoRegistrosListagem.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!cumpriuHoras) {
            int resp = JOptionPane.showConfirmDialog(this, "Não foram cumpridas as horas necessárias para o validamento do Relatório. Deseja continuar mesmo assim? ", "Atenção !", JOptionPane.YES_NO_OPTION);

            if (resp == JOptionPane.YES_OPTION) {

                chamaRelatorio();
            }
        } else {
            chamaRelatorio();
        }

    }//GEN-LAST:event_jbtGerarRelatorioActionPerformed

    public void chamaRelatorio() {
        try {

            Properties props = new Properties();
            FileInputStream file = new FileInputStream(
                    "src/main/java/properties/user.properties");
            props.load(file);

            Class.forName("com.mysql.jdbc.Driver");

            Connection mySQLConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/freqpolo", props.getProperty("prop.sql.login"), props.getProperty("prop.sql.password"));

            InputStream relatorio = LancamentoRegistrosListagem.class.getResourceAsStream("/Frequencia.jasper");
            Map<String, Object> parameters = new LinkedHashMap<>();

            parameters.put("codLancamento", lancamentoAtual.getCodigo());

            JasperPrint jp = JasperFillManager.fillReport("src/Frequencia.jasper", parameters, mySQLConnection);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.show();

            //JRExporter exporter = new JRPdfExporter();
            //    exporter.setParameter(JRExporterParameter.JASPER_PRINT,jp);        
            //    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "meuRelPessoas.pdf");
            //exporter.exportReport();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LancamentoRegistrosListagem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LancamentoRegistrosListagem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(LancamentoRegistrosListagem.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(LancamentoRegistrosListagem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void jbtBuscaLancamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtBuscaLancamentoActionPerformed

        this.lancamentoAtual = null;
        this.lancamentoAtual = lRN.buscarLancamentoMes(projeto, colaborador, Ferramentas.intParaMes(jmcMesReferencia.getMonth()), jycAnoReferencia.getYear());

        if (lancamentoAtual == null) {
            lancamentoAtual = new Lancamento();
            lancamentoAtual = new Lancamento(Ferramentas.intParaMes(jmcMesReferencia.getMonth()), jycAnoReferencia.getYear(), projeto, colaborador);
            lancamentoAtual.setAnoReferencia(jycAnoReferencia.getYear());
            lancamentoAtual.setObservacao("");

        }
        carregaLancamento();
        carregaTabelas();
        carregaLabels();


    }//GEN-LAST:event_jbtBuscaLancamentoActionPerformed

    private void jtxAObservacoesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxAObservacoesFocusLost
        lancamentoAtual.setObservacao(jtxAObservacoes.getText());
        try {
            lRN.salvar(lancamentoAtual);
        } catch (NegocioException ex) {
            Logger.getLogger(LancamentoRegistrosListagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtxAObservacoesFocusLost

    private void jbtAtualizar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAtualizar1ActionPerformed
        carregaTabelas();
        carregaLabels();
    }//GEN-LAST:event_jbtAtualizar1ActionPerformed

    private void jbtExcluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtExcluir1ActionPerformed

        if (jTableProjetoPesquisa.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Registro", "Exclusão de Registro", JOptionPane.ERROR_MESSAGE);
        } else {
            int resp = JOptionPane.showConfirmDialog(this, "Confirma a exclusão deste registro?", "Exclusão de Registro", JOptionPane.YES_NO_OPTION);

            if (resp != JOptionPane.YES_OPTION) {
                return;
            } else {

                try {
                    rRN.excluir(rtmPesquisa.getValueAt(jTableProjetoPesquisa.getSelectedRow()));
                    carregaTabelas();
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(RegistroTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            carregaTabelas();
            carregaLabels();
        }
    }//GEN-LAST:event_jbtExcluir1ActionPerformed

    private void jbtEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtEditar1ActionPerformed

        if (jTableProjetoPesquisa.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Registro", "Edição de Registro", JOptionPane.ERROR_MESSAGE);
        } else {
            RegistroCadastro dialog = new RegistroCadastro(null, true, rRN, lancamentoAtual, rtmPesquisa.getValueAt(jTableProjetoPesquisa.getSelectedRow()));
            dialog.setVisible(true);
            if (dialog.confirmou) {
                carregaTabelas();
                carregaLabels();
            }

        }
    }//GEN-LAST:event_jbtEditar1ActionPerformed

    private void jbtIncluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIncluir1ActionPerformed
        RegistroCadastro dialog = new RegistroCadastro(null, true, rRN, lancamentoAtual, TipoRegistroENUM.PESQUISA);
        dialog.setVisible(true);
        if (dialog.confirmou) {
            carregaTabelas();
            carregaLabels();
        }
    }//GEN-LAST:event_jbtIncluir1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTablePoloInovacao;
    private javax.swing.JTable jTableProjetoPesquisa;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton jbtAtualizar1;
    private javax.swing.JButton jbtAtualizar2;
    private javax.swing.JButton jbtBuscaLancamento;
    private javax.swing.JButton jbtEditar1;
    private javax.swing.JButton jbtEditar2;
    private javax.swing.JButton jbtExcluir1;
    private javax.swing.JButton jbtExcluir2;
    private javax.swing.JButton jbtGerarRelatorio;
    private javax.swing.JButton jbtIncluir1;
    private javax.swing.JButton jbtIncluir2;
    private javax.swing.JLabel jlbHorasMinimasProjeto;
    private javax.swing.JLabel jlbHorasPesquisa;
    private javax.swing.JLabel jlbHorasPolo;
    private javax.swing.JLabel jlbHorasTotais;
    private javax.swing.JLabel jlbProjetoName;
    private javax.swing.JLabel jlbSBARRA;
    private javax.swing.JLabel jlbSDATA;
    private javax.swing.JLabel jlbSHORASPOLO;
    private javax.swing.JLabel jlbSHORASPROJ;
    private javax.swing.JLabel jlbSObservacao;
    private javax.swing.JLabel jlbSPROJETO;
    private javax.swing.JLabel jlbSTOTALMES;
    private com.toedter.calendar.JMonthChooser jmcMesReferencia;
    private javax.swing.JTextArea jtxAObservacoes;
    private com.toedter.calendar.JYearChooser jycAnoReferencia;
    // End of variables declaration//GEN-END:variables
}
