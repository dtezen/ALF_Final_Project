package codigo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class frm_principal extends javax.swing.JFrame {
    
    JFileChooser seleccionar = new JFileChooser();
    File archivo1;
    FileInputStream entrada;
    FileOutputStream salida;

    public frm_principal() {
        initComponents();
        txtresultado.setLineWrap(true);
    }
    
    public String frm_principal(File archivo1){
        String documento="";
        try {
            entrada = new FileInputStream(archivo1);
            int ascci;
            while((ascci=entrada.read())!=-1){
            char caracter=(char)ascci;
            documento+=caracter;
        }
        }
        catch (IOException e){
            
        }
        return documento;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        btnAnalizar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtresultado = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEntrada = new javax.swing.JTextArea();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAnalizar.setText("Compilar");
        btnAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarActionPerformed(evt);
            }
        });

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Abrir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtresultado.setColumns(20);
        txtresultado.setRows(5);
        jScrollPane1.setViewportView(txtresultado);

        txtEntrada.setColumns(20);
        txtEntrada.setRows(5);
        jScrollPane2.setViewportView(txtEntrada);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnalizar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 924, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 924, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 173, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnalizar)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarActionPerformed
        
        /*El analizador lee solamente archivos creados en la máquina, por lo que necesita que 
        el texto ingresado por el usuario sea plasmado en un documento de texto*/
        File archivo = new File("archivo.txt"); //Crea un TXT con el código que se le ha ingresado.
        PrintWriter escribir; //Permitirá escribir en el documento.
        try {
            escribir = new PrintWriter(archivo); //Envía el texto hacia el archivo.
            escribir.print(txtEntrada.getText()); //Obtiene el texto ingresado en la vista.
            escribir.close(); //Cierra el archivo de texto.
        } catch (FileNotFoundException ex) {
            Logger.getLogger(frm_principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Se procede a leer el archivo que se ha creado.
        //Se utiliza try-catch por si el archivo no existe.
        try {
            Reader lector = new BufferedReader(new FileReader("archivo.txt")); //Llama el archio que será leído.
            Lexer lexer = new Lexer(lector); //Se envía el archivo a ser analizado por el Lexer.

            String resultado = ""; //Inicializando el resultado de la cadena que se analizará.
            
            //while infinito hasta que se analice el último texto del archivo.
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) { //Cuando llega a su fin.
                    resultado += "-----------------------------BUILD SUCCESSFUL-----------------------------";
                    txtresultado.setText(resultado); //Enviando el resultado a la caja de texto.
                    return; //Saliendo del While.
                }
                
                //El switch permitirá cambiar entre los tokens que existen. Esto para mostrar resultados al usuario.
                switch (tokens) {
                    case ERROR:
                        resultado +=  "Símbolo no definido\n";
                        break;
                    case Identificador: case Numero: case Reservadas:
                        //El token es el tipo de símbolo (palabra reservada, número, etc.)
                        resultado +=  lexer.lexeme + ": Es un " + tokens +" linea: "+ "en proceso" + "\n";
                        break;
                    default:
                        resultado += "Token: " + tokens + "\n";
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(frm_principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(frm_principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        /*El analizador lee solamente archivos creados en la máquina, por lo que necesita que 
        el texto ingresado por el usuario sea plasmado en un documento de texto*/
        File result = new File("result.txt"); //Crea un TXT con el código que se le ha ingresado.
        PrintWriter escribir; //Permitirá escribir en el documento.
        try {
            escribir = new PrintWriter(result); //Envía el texto hacia el archivo.
            escribir.print(txtresultado.getText()); //Obtiene el texto ingresado en la vista.
            escribir.close(); //Cierra el archivo de texto.
        } catch (FileNotFoundException ex) {
            Logger.getLogger(frm_principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(seleccionar.showDialog(null, "Abrrir")==JFileChooser.APPROVE_OPTION){
            archivo1=seleccionar.getSelectedFile();
            if(archivo1.canRead()){
                if(archivo1.getName().endsWith("txt"));
                    String documento=frm_principal(archivo1);
                    txtEntrada.setText(documento);
            }else{
                JOptionPane.showMessageDialog(null, "Archivo no Compatible");
            }
        }
        
        
    }//GEN-LAST:event_jButton3ActionPerformed


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
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextArea txtEntrada;
    private javax.swing.JTextArea txtresultado;
    // End of variables declaration//GEN-END:variables
}
