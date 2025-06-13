package br.com.exemplo.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultComboBoxModel;
import java.util.List;
import java.util.ArrayList;
import br.edu.unicid.model.Leitor;
import br.edu.unicid.dao.LeitorDAO;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtCodLeitor;
    private JTextField txtNomeLeitor;
    private JTextArea txtListar; // Declaração como atributo
    private JComboBox<String> cmbTipoLeitor; // Declaração como atributo
    private Leitor leitor; // Declaração como atributo
    private LeitorDAO dao; // Declaração como atributo
    private JLabel lblMensagem; // Declaração como atributo

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaPrincipal frame = new TelaPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaPrincipal() {
        setTitle("Manutenção Leitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 762, 533);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Código do Leitor");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel.setBounds(29, 34, 105, 33);
        contentPane.add(lblNewLabel);

        JLabel lblNomeDoLeitor = new JLabel("Nome do Leitor");
        lblNomeDoLeitor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNomeDoLeitor.setBounds(29, 78, 105, 33);
        contentPane.add(lblNomeDoLeitor);

        JLabel lblTipoDoLeitor = new JLabel("Tipo do Leitor");
        lblTipoDoLeitor.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTipoDoLeitor.setBounds(29, 122, 105, 33);
        contentPane.add(lblTipoDoLeitor);

        lblMensagem = new JLabel("");
        lblMensagem.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        lblMensagem.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMensagem.setBounds(29, 370, 681, 52);
        contentPane.add(lblMensagem);

        txtCodLeitor = new JTextField();
        txtCodLeitor.setBounds(174, 34, 481, 33);
        contentPane.add(txtCodLeitor);
        txtCodLeitor.setColumns(10);

        txtNomeLeitor = new JTextField();
        txtNomeLeitor.setColumns(10);
        txtNomeLeitor.setBounds(174, 78, 481, 33);
        contentPane.add(txtNomeLeitor);

        cmbTipoLeitor = new JComboBox<>();
        cmbTipoLeitor.setModel(new DefaultComboBoxModel<>(
                new String[]{"Selecione uma opção", "Aluno", "Professor", "Administrativo"}));
        cmbTipoLeitor.setBounds(174, 128, 481, 22);
        contentPane.add(cmbTipoLeitor);

        txtListar = new JTextArea();
        txtListar.setBounds(42, 215, 645, 144);
        contentPane.add(txtListar);

        JButton btnNewButton = new JButton("Novo");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtCodLeitor.setText("");
                txtNomeLeitor.setText("");
                txtListar.setText("");
                cmbTipoLeitor.setSelectedIndex(0);
                lblMensagem.setText("");
            }
        });
        btnNewButton.setBounds(72, 181, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int codLeitor = Integer.parseInt(txtCodLeitor.getText().trim());
                    String nomeLeitor = txtNomeLeitor.getText().trim();
                    String tipoLeitor = (String) cmbTipoLeitor.getSelectedItem();
                    if (codLeitor <= 0 || nomeLeitor.isEmpty() || tipoLeitor.equals("Selecione uma opção")) {
                        lblMensagem.setText("Preencha todos os campos corretamente!");
                        return;
                    }
                    leitor = new Leitor(codLeitor, nomeLeitor, tipoLeitor);
                    dao = new LeitorDAO();
                    dao.salvar(leitor);
                    lblMensagem.setText("Salvo com Sucesso!");
                } catch (NumberFormatException ex) {
                    lblMensagem.setText("Código deve ser um número válido!");
                } catch (Exception ex) {
                    lblMensagem.setText("Erro ao Salvar: " + ex.getMessage());
                }
            }
        });
        btnSalvar.setBounds(171, 181, 89, 23);
        contentPane.add(btnSalvar);

        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(txtCodLeitor.getText().trim());
                    if (codigo <= 0) {
                        lblMensagem.setText("Código inválido!");
                        return;
                    }
                    dao = new LeitorDAO();
                    leitor = dao.consultar(codigo);
                    if (leitor != null) {
                        txtNomeLeitor.setText(leitor.getNomeLeitor());
                        String tipo = leitor.getTipoLeitor();
                        if (tipo.equals("Aluno")) {
                            cmbTipoLeitor.setSelectedIndex(1);
                        } else if (tipo.equals("Professor")) {
                            cmbTipoLeitor.setSelectedIndex(2);
                        } else if (tipo.equals("Administrativo")) {
                            cmbTipoLeitor.setSelectedIndex(3);
                        }
                        lblMensagem.setText("Consulta realizada com sucesso!");
                    } else {
                        lblMensagem.setText("Leitor não encontrado!");
                        txtNomeLeitor.setText("");
                        cmbTipoLeitor.setSelectedIndex(0);
                    }
                } catch (NumberFormatException ex) {
                    lblMensagem.setText("Código deve ser um número válido!");
                } catch (Exception ex) {
                    lblMensagem.setText("Erro ao Consultar: " + ex.getMessage());
                }
            }
        });
        btnConsultar.setBounds(269, 181, 89, 23);
        contentPane.add(btnConsultar);

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int codLeitor = Integer.parseInt(txtCodLeitor.getText().trim());
                    String nomeLeitor = txtNomeLeitor.getText().trim();
                    String tipoLeitor = (String) cmbTipoLeitor.getSelectedItem();
                    if (codLeitor <= 0 || nomeLeitor.isEmpty() || tipoLeitor.equals("Selecione uma opção")) {
                        lblMensagem.setText("Preencha todos os campos corretamente!");
                        return;
                    }
                    leitor = new Leitor(codLeitor, nomeLeitor, tipoLeitor);
                    dao = new LeitorDAO();
                    dao.alterar(leitor);
                    lblMensagem.setText("Alterado com Sucesso!");
                } catch (NumberFormatException ex) {
                    lblMensagem.setText("Código deve ser um número válido!");
                } catch (Exception ex) {
                    lblMensagem.setText("Erro ao Alterar: " + ex.getMessage());
                }
            }
        });
        btnAlterar.setBounds(368, 181, 89, 23);
        contentPane.add(btnAlterar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(txtCodLeitor.getText().trim());
                    if (codigo <= 0) {
                        lblMensagem.setText("Código inválido!");
                        return;
                    }
                    dao = new LeitorDAO();
                    dao.excluir(codigo);
                    lblMensagem.setText("Excluído com Sucesso!");
                    txtCodLeitor.setText("");
                    txtNomeLeitor.setText("");
                    cmbTipoLeitor.setSelectedIndex(0);
                } catch (NumberFormatException ex) {
                    lblMensagem.setText("Código deve ser um número válido!");
                } catch (Exception ex) {
                    lblMensagem.setText("Erro ao Excluir: " + ex.getMessage());
                }
            }
        });
        btnExcluir.setBounds(467, 181, 89, 23);
        contentPane.add(btnExcluir);

        JButton btnListar = new JButton("Listar");
        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Leitor> lista = new ArrayList<>();
                    dao = new LeitorDAO();
                    lista = dao.listarTodos();
                    txtListar.setText("");
                    if (lista.isEmpty()) {
                        lblMensagem.setText("Nenhum leitor encontrado!");
                    } else {
                        for (Leitor leitor : lista) {
                            txtListar.append("Código do Leitor.....: " + leitor.getCodLeitor() + "\n");
                            txtListar.append("Nome do Leitor.......: " + leitor.getNomeLeitor() + "\n");
                            txtListar.append("Tipo do Leitor.......: " + leitor.getTipoLeitor() + "\n\n");
                        }
                        lblMensagem.setText("Lista carregada com sucesso!");
                    }
                } catch (Exception ex) {
                    lblMensagem.setText("Erro ao Listar: " + ex.getMessage());
                }
            }
        });
        btnListar.setBounds(566, 181, 89, 23);
        contentPane.add(btnListar);
    }
}