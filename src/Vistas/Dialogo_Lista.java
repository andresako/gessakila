package Vistas;

import Controlador.PeliTools;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 * @author Andres
 */
public class Dialogo_Lista extends javax.swing.JDialog {

    private Frame Padre;
    private PeliTools PT;
    private Short peliId;
    private ArrayList<Short> listaNuevos;
    private ArrayList<String> listaViejos;

    public Dialogo_Lista(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.Padre = parent;
        initComponents();
        listaNuevos = new ArrayList<>();
        listaViejos = new ArrayList<>();
        PT = new PeliTools();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listUnica = new javax.swing.JList();
        btnOk = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        listUnica.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listUnica);

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnAdd.setText("Añadir");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDel.setText("Borrar");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnAdd)
                    .addComponent(btnDel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (listaNuevos.size() > 0) {
            for (Short listaNuevo : listaNuevos) {
                PT.addActor(peliId, listaNuevo);
            }
        }
        if (listaViejos.size() > 0) {
            for (String listaViejo : listaViejos) {
                PT.delActor(peliId, listaViejo);
            }
        }
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Dialogo_NuevoActor newActor = new Dialogo_NuevoActor(Padre, true);
        newActor.SetUi(listUnica, this);
        newActor.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        if (listUnica.getSelectedIndex() != -1) {
            String[] buttons = {"Si", "No"};
            int rc = JOptionPane.showOptionDialog(null, "Quitar del reparto al Actor = [" + listUnica.getSelectedValue() + "]?",
                    "Confirmation", JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
            if (rc == 0) {
                DefaultListModel mdl = (DefaultListModel) listUnica.getModel();
                listaViejos.add(listUnica.getSelectedValue().toString());
                mdl.remove(listUnica.getSelectedIndex());
            }
        }

    }//GEN-LAST:event_btnDelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnOk;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listUnica;
    // End of variables declaration//GEN-END:variables

    public void RellenarTabla(DefaultListModel dlm) {
        listUnica.setModel(dlm);
    }

    public void botonesActivados(boolean b) {
        btnAdd.setVisible(b);
        btnDel.setVisible(b);
    }

    public void SetPeli(String peliId) {
        this.peliId = Short.valueOf(peliId);
    }

    public void addActor(Short newActor) {
        listaNuevos.add(newActor);
    }
}
