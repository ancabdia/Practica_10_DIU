/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ulpgc.dis.practica10;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.SwingWorker;

/**
 *
 * @author Andres
 */
public class progress extends javax.swing.JDialog {
    
    
    private final ArrayList<String> files;
    private final String selectFile;
    private final Tarea progress;

    /**
     * Creates new form progress
     */
    public progress(java.awt.Frame parent, boolean modal, ArrayList<String> files, String selectFile) {
        this.files = files;
        this.selectFile=selectFile;
        super.setLocationRelativeTo(null);
        super.setTitle("Compress Task");
        initComponents();
        
        this.progress = new Tarea();
    }

    //<Clase de datos a devolver, entero para barra de progreso >
    private class Tarea extends SwingWorker<Void, Void> {

        ZipOutputStream out;
        private boolean flag = false;

        @Override
        protected Void doInBackground() throws Exception {

            try {
                int progressValue = 0;

// Objeto para referenciar a los archivos que queremos comprimir
                BufferedInputStream origin = null;
// Objeto para referenciar el archivo zip de salida                
                FileOutputStream dest = new FileOutputStream(selectFile);
                out = new ZipOutputStream(new BufferedOutputStream(dest));

// Buffer de transferencia para almacenar datos a comprimir
                byte[] data = new byte[1024];

                Iterator i = files.iterator();
                while (i.hasNext() && !flag) {
                    
                    //tarea.execute();
                    String filename = (String) i.next();
                    
                    FileInputStream fi = new FileInputStream(filename);
                    origin = new BufferedInputStream(fi, 1024);
                    ZipEntry entry = new ZipEntry(filename.substring(filename.lastIndexOf('/') + 1));

                    out.putNextEntry(entry);
// Leemos datos desde el archivo origen y se envían al archivo destino
                    int count;

                    while ((count = origin.read(data, 0, 1024)) != -1) {
                        out.write(data, 0, count);
                    }

// Cerramos el archivo origen, ya enviado a comprimir
                    origin.close();
                    progressValue++;
                    jProgressBar1.setValue((progressValue * 100) / files.size());
                    //Thread.sleep(100);

                }
// Cerramos el archivo zip
                
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void done() {
            aceptButton.setEnabled(true);
            aceptButton.setVisible(true);
            flag = true;
            information.setText("Compression finished.");
        }

    }
    
    public void start(){
        progress.execute();
        aceptButton.setVisible(false);
        aceptButton.setEnabled(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        aceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        information = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        aceptButton.setText("Accept");
        aceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        information.setText("Compressing files ...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(aceptButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                                .addComponent(cancelButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(information, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(information)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aceptButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_aceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        progress.cancel(true);
        information.setText("Compression canceled by user.");
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel information;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
