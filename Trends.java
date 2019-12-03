/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author HIGG003
 */
public class Trends extends javax.swing.JFrame {

    /**
     * Creates new form Tends
     */
    public Trends() {
        initComponents();
        init();
    }
DefaultTableModel dtm;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "School", "Record", "My mL record", "Record vSpread", "Spread Percentage", "My Spread Record", "My Spread Percentage Won", "O/U Record (Overs)", "O/U Percentage %", "Point Diff", "My O/U Record", "My O/U %"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void init(){
        dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(354);
        // alg that grabs school stuff with ATS
        class Node {
            String name, mlRecord, RecordSpread,recP,o3,o4,o5,o6,o7;
            public Node(String n,String q, String w, String h, String k, String Ov, String ovPer, String pDiff, String ml){
                name = n;
                RecordSpread=q;
                recP=w;
                o3=h; //doesnt matter
                o4=k;//doesnt matter
                o5=Ov;
                o6=ovPer;
                o7=pDiff;
                mlRecord=ml;
            }
        }
        ArrayList<Node> lines = null;
        try {
            String blogUrl = "https://www.teamrankings.com/ncb/trends/ats_trends/";
            Document doc = Jsoup.connect(blogUrl).get();

            Element links = doc.select("body").get(0); // somehow grabs table
            Elements f = links.select("td");
            List<String> clist = f.eachText();
            lines = new ArrayList<>();
            for (int i = 0; i < clist.size(); i = i+5) {
                //gets school, ats record, cover%, mov, ATS+/-  all randomly
               //System.out.println(clist.get(i) + " " + clist.get(i+1) + " " + clist.get(i+2)+ " " + clist.get(i+3)+ " " + clist.get(i+4));
                Node n = new Node(clist.get(i),clist.get(i+1) , clist.get(i+2), clist.get(i+3),clist.get(i+4),null,null,null,null);
                
                lines.add(n);
            }
            
          
        } catch (IOException e) {
             System.out.println(e.toString());

        }
        //https://www.teamrankings.com/ncb/trends/win_trends/
        try {
            String blogUrl = "https://www.teamrankings.com/ncb/trends/win_trends/";
            Document doc = Jsoup.connect(blogUrl).get();

            Element links = doc.select("body").get(0); // somehow grabs table
            Elements f = links.select("td");
            List<String> clist = f.eachText();
           
            for (int i = 0; i < clist.size(); i = i+5) {
                
               
               for(Node e: lines){
                   if (e.name.equalsIgnoreCase(clist.get(i))){
                       e.mlRecord = clist.get(i+1);
                   }
               }
            }
        }catch(IOException ex){
            
        }
        
        
        
        //https://www.teamrankings.com/ncb/trends/ou_trends/
        try {
            String blogUrl = "https://www.teamrankings.com/ncb/trends/ou_trends/";
            Document doc = Jsoup.connect(blogUrl).get();

            Element links = doc.select("body").get(0); // somehow grabs table
            Elements f = links.select("td");
            List<String> clist = f.eachText();
           
            for (int i = 0; i < clist.size(); i = i+5) {
                
                
               //System.out.println(clist.get(i) + " " + clist.get(i+1) + " " + clist.get(i+2)+ " " + clist.get(i+3)+ " " + clist.get(i+4));
               for(Node e: lines){
                   if (e.name.equalsIgnoreCase(clist.get(i))){
                       e.o5 = clist.get(i+1);
                       e.o6 = clist.get(i+2);
                       e.o7 = clist.get(i+4);
                   }
               }
            }
            
            

            
            for (int k = 0; k < lines.size(); k++) {
                jTable1.setValueAt(lines.get(k).name, k, 0);
                jTable1.setValueAt(lines.get(k).mlRecord, k, 1);
                jTable1.setValueAt(lines.get(k).RecordSpread, k, 3);
                jTable1.setValueAt(lines.get(k).recP, k, 4);
                jTable1.setValueAt(lines.get(k).o5, k, 7);
                jTable1.setValueAt(lines.get(k).o6, k, 8);
                jTable1.setValueAt(lines.get(k).o7, k, 9);
            } 
            
        } catch (IOException e) {
             System.out.println(e.toString());

        }
        
        
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
            java.util.logging.Logger.getLogger(Trends.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Trends.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Trends.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Trends.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Trends().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration                   
}
