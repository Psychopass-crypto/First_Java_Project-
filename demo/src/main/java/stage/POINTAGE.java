/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package stage;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Salah
 */
public class POINTAGE extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel(new String[] {"Référence lecteur","Id point de Controle","Date de Pointage","Heure de pointage"},0);
    File f;
    int h1,m1;
    int h2,m2;

    public POINTAGE() {
        initComponents();
        jTable1.setModel(table);
        this.spinner(jSpinner1);
        this.spinner(jSpinner2);
        this.setTitle("Système du contrôle de rondes");
        Image icon =new  ImageIcon(this.getClass().getResource("/temps-de-travail.png")).getImage();
        this.setIconImage(icon);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        JSpinner.DateEditor editor = new JSpinner.DateEditor(jSpinner3, format.toPattern());
        jSpinner3.setEditor(editor);

    }

    public String affiche_date(JSpinner j) {
        Date valeur = (Date) j.getValue();
        if (valeur != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(valeur);

        }
        return null;
    }
    public void alerte() {
        ArrayList<String> list = new ArrayList<String>();
        String ol = "";
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            Object value = table.getValueAt(i, 3);
            if (value == null) {
                list.add(Objects.toString(table.getValueAt(i, 1), ""));
            }
        }
        if (!list.isEmpty()) {
            for (String o : list) {
                if (ol.equals(""))
                    ol=o;
                ol = ol + " et " + o;
            }

            JOptionPane.showMessageDialog(null, "Les points de Contrôles " + ol + " n'ont pas été pointés", "Id point de Contrôle non pointé", JOptionPane.WARNING_MESSAGE);
        }
    }


    public void recherche_date() {
        String dateRecherchee = this.affiche_date(jSpinner3);
        if (dateRecherchee == null) {
            return;
        }
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            String dateTableau = String.valueOf(table.getValueAt(i, 2));
            if (!dateRecherchee.equals(dateTableau)) {
                table.removeRow(i);
            }
        }
    }



    private void spinner(JSpinner j){
        JSpinner.DateEditor editor = new JSpinner.DateEditor(j, "HH:mm");
        j.setEditor(editor);
    }
    public String affiche_heure(JSpinner j) {
        Date valeur = (Date) j.getValue();
        if (valeur != null) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(valeur);
        }
        return null;
    }
    public void recherche_heure() {
        LocalTime mini = null;
        LocalTime maxi = null;
        try {
            String heure1 = this.affiche_heure(jSpinner1);
            String heure2 = this.affiche_heure(jSpinner2);
            if (heure1 != null && heure2 != null) {
                mini = LocalTime.parse(heure1);
                maxi = LocalTime.parse(heure2);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            String temp = String.valueOf(table.getValueAt(i, 3));
            if (temp != null) {
                temp = temp.replace('h', ':');
                LocalTime actu = null;
                try {
                    actu = LocalTime.parse(temp);
                } catch (DateTimeParseException e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
                if (actu != null && !(actu.isAfter(mini) && actu.isBefore(maxi) || (actu.equals(mini) || actu.equals(maxi)))) {
                    table.removeRow(i);
                }
            }
        }
    }
    private void initialisation(File f){
        int i=0;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while ((line = br.readLine()) != null) {
                if(i!=0){
                    String[] colonnes = line.split(";");
                    table.addRow(colonnes);
                }
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("CallToPrintStackTrace")
    private void initialisation(){
        String defautcheminactuel="C:\\Users\\Salah\\Downloads";
        JFileChooser excel = new JFileChooser(defautcheminactuel);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv", "csv");
        excel.setFileSelectionMode(JFileChooser.FILES_ONLY);
        excel.setDialogTitle("Entrer le fichier excel concerné :");
        excel.setFileFilter(filter);
        int reponse=excel.showOpenDialog(this);
        if (reponse==JFileChooser.APPROVE_OPTION){
            File excel_choisie=excel.getSelectedFile();
            f=excel_choisie;
            int i=0;
            try (BufferedReader br = new BufferedReader(new FileReader(excel_choisie))) {
                String line;

                while ((line = br.readLine()) != null) {
                    if(i!=0){
                        String[] colonnes = line.split(";");
                        table.addRow(colonnes);
                    }
                    i++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }
    private void recherche_zone() {
        String zo = jComboBox1.getSelectedItem().toString();
        int imax = table.getRowCount();

        for (int i = imax - 1; i >= 0; i--) {
            if (!table.getValueAt(i, 0).equals(zo)) {
                table.removeRow(i);
            }
        }
    }
    @SuppressWarnings("/*unchecked*/")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jBt1 = new javax.swing.JButton();
        jSpinner3 = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel2.setText("Zone :");

        jLabel3.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel3.setText("Date :");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel4.setText("Entre heure :");

        jSpinner1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1690758000000L), null, null, java.util.Calendar.MINUTE));

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 14)); // NOI18N
        jLabel5.setText("ET");

        jSpinner2.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1690844340000L), null, null, java.util.Calendar.MINUTE));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel1.setText("   Critère : ");
        jLabel1.setToolTipText("");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "all", "1", "2", "3" }));
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Importer Excel");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jBt1.setBackground(new java.awt.Color(102, 255, 102));
        jBt1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBt1.setText("Rechercher");
        jBt1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt1ActionPerformed(evt);
            }
        });

        jSpinner3.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1627174260000L), null, null, java.util.Calendar.DAY_OF_MONTH));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(54, 54, 54))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSpinner1))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(41, 41, 41)
                                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28)
                                                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jBt1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(7, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(jButton1)
                                        .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jBt1))
                                .addContainerGap(8, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));

        jTable1.setBackground(new java.awt.Color(204, 255, 255));
        jTable1.setBorder(new javax.swing.border.MatteBorder(null));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setGridColor(new java.awt.Color(204, 255, 255));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(72, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        table.setRowCount(0);
        this.initialisation();
        this.alerte();

    }

    private void jBt1ActionPerformed(java.awt.event.ActionEvent evt) {
        if(Objects.isNull(f)){
            JOptionPane.showMessageDialog(this,"Veuiller importer un fichier excel d'abords","Pas de Données",JOptionPane.ERROR_MESSAGE);
        }
        table.setRowCount(0);
        this.initialisation(f);
        if (!(jComboBox1.getSelectedItem().toString()).equals("all"))
            this.recherche_zone();
        this.recherche_heure();
        this.recherche_date();
//this.alerte();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(POINTAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POINTAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POINTAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POINTAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new POINTAGE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jBt1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}