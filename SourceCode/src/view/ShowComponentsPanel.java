/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DataValidator;
import controller.ImageHelper;
import controller.LinhKienDao;
import controller.LoaiLinhKienDao;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import model.LinhKien;
import model.LoaiLinhKien;
import model.MessageDialogHelper;


/**
 *
 * @author ndnha
 */
public class ShowComponentsPanel extends javax.swing.JPanel {
    private MainForm parentForm;
    private DefaultTableModel tblModel;
    private byte[] personalImage;
    /**
     * Creates new form StudentManagementPanel
     */
    public ShowComponentsPanel() {
        initComponents();
        initTable();
        initComboBox();        
        loadDataToTable();
    }
    
    private void initComboBox () {
        try {
            LoaiLinhKienDao dao = new LoaiLinhKienDao();
            ArrayList<LoaiLinhKien> list = dao.findAll();
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (LoaiLinhKien it : list) {
                model.addElement(it.getTenLoai());
            }
            jComboBoxComponentsType.setModel(model);
            jComboBoxComponentsType.setSelectedIndex(0);
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }
    
    private void initTable () {
        tblModel = new DefaultTableModel(
            new Object[][] {
            },
            new String[]{"Mã số linh kiện", "Tên linh kiện", "Thông tin", "Thông số cao nhất", 
            "Giá tiền", "Hình ảnh", "Số lượng còn lại"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
            
            Class[] columnTypes = new Class[] {
                Object.class, Object.class, Object.class, Object.class,
                Object.class, ImageIcon.class, Object.class
            };
            public Class getColumnClass (int columnIndex) {
                return columnTypes[columnIndex];
            }
        };
        tblComponents.setModel(tblModel);
        tblComponents.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
        tblComponents.getColumnModel().getColumn(2).setCellRenderer(new WordWrapCellRenderer());
    }
    
    public class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), 110);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, 110);
            }
            if (isSelected)
                setBackground(tblComponents.getSelectionBackground());
            else
                setBackground( null );
            return this;
        }
    }
    
//    public static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
//        WordWrapCellRenderer() {
//            setLineWrap(true);
//            setWrapStyleWord(true);
//        }
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            setText(value.toString());
//            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
//            if (table.getRowHeight(row) != getPreferredSize().height) {
//                table.setRowHeight(row, getPreferredSize().height);
//            }
//            return this;
//        }
//    }
    
    private void loadDataToTable() {
        try {
            LinhKienDao dao = new LinhKienDao();
            LoaiLinhKienDao daollk = new LoaiLinhKienDao();
            LoaiLinhKien llk = daollk.findByTenLoai(String.valueOf(jComboBoxComponentsType.getSelectedItem()));
            ArrayList<LinhKien> list = dao.findByMaLoai(llk.getMaLoai());
            tblModel.setRowCount(0);
            for (LinhKien it : list) {
                DecimalFormat formatter = new DecimalFormat("###,###,###,###.##");
                ImageIcon image;
                if (it.getHinhAnh() != null) {
                        Image img = ImageHelper.createImageFromByteArray(it.getHinhAnh(), "jpg");
                        img = ImageHelper.resize(img, 100, 100);
                        image = new ImageIcon(img);
                } else {
                    image = new ImageIcon(getClass().getResource("/icons/failed128.png"));
                }
                tblModel.addRow(new Object[] {
                    it.getMaSoLinhKien(), it.getTenLinhKien(), it.getThongTin(), it.getThongSoCaoNhat(),
                    formatter.format(it.getGiaTien()) + " VND", image, it.getSoLuong()
                });
            }
            tblModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
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

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblComponents = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelHinhAnh = new javax.swing.JLabel();
        txtMSLK = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTenLK = new javax.swing.JTextArea();
        txtThongSo = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtTTin = new javax.swing.JTextArea();
        txtGiaTien = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jComboBoxComponentsType = new javax.swing.JComboBox<>();
        jButtonOK = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("DANH SÁCH LINH KIỆN");

        tblComponents.setModel(new javax.swing.table.DefaultTableModel(
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
        tblComponents.setRowHeight(110);
        tblComponents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComponentsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblComponents);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setText("Mã số linh kiện:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("Thông tin:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setText("Giá tiền:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 51));
        jLabel5.setText("Hình ảnh:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("Tên linh kiện:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("Thông số cao nhất:");

        jLabelHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/failed128.png"))); // NOI18N
        jLabelHinhAnh.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        txtMSLK.setEditable(false);

        txtTenLK.setEditable(false);
        txtTenLK.setBackground(new java.awt.Color(240, 240, 240));
        txtTenLK.setColumns(20);
        txtTenLK.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTenLK.setRows(5);
        jScrollPane1.setViewportView(txtTenLK);

        txtThongSo.setEditable(false);

        txtTTin.setEditable(false);
        txtTTin.setBackground(new java.awt.Color(240, 240, 240));
        txtTTin.setColumns(20);
        txtTTin.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTTin.setRows(5);
        jScrollPane3.setViewportView(txtTTin);

        txtGiaTien.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 51));
        jLabel11.setText("Số lượng:");

        txtSoLuong.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMSLK)
                    .addComponent(txtThongSo)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong)
                    .addComponent(txtGiaTien)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(txtMSLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtThongSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel4)
                                .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jButtonOK.setForeground(new java.awt.Color(0, 0, 255));
        jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok16px.png"))); // NOI18N
        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxComponentsType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonOK)
                        .addGap(0, 146, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxComponentsType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonOK)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 55, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblComponentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComponentsMouseClicked
        try {
            int row = tblComponents.getSelectedRow();
            
            if (row >= 0) {
                String maSo = (String) tblComponents.getValueAt(row, 0);
                LinhKienDao dao = new LinhKienDao();
                LoaiLinhKienDao daollk = new LoaiLinhKienDao();
                LoaiLinhKien llk = daollk.findByTenLoai(String.valueOf(jComboBoxComponentsType.getSelectedItem()));
                LinhKien lk = dao.findByMaSoLinhKien_MaLoai(maSo, llk.getMaLoai());
                
                if (lk!=null) {
                    txtTenLK.setText(lk.getTenLinhKien());
                    txtMSLK.setText(lk.getMaSoLinhKien());
                    txtTTin.setText(lk.getThongTin());
                    txtThongSo.setText(lk.getThongSoCaoNhat() + "");
                    DecimalFormat formatter = new DecimalFormat("###,###,###,###.##");
                    txtGiaTien.setText(formatter.format(lk.getGiaTien()) + " VND");
                    if (lk.getHinhAnh() != null) {
                        Image img = ImageHelper.createImageFromByteArray(lk.getHinhAnh(), "jpg");
                        jLabelHinhAnh.setIcon(new ImageIcon(img));
                        personalImage = lk.getHinhAnh();
                    } else {
                        personalImage = lk.getHinhAnh();
                        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/failed128.png"));
                        jLabelHinhAnh.setIcon(icon);
                    }
                    txtSoLuong.setText(lk.getSoLuong() + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_tblComponentsMouseClicked

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        // TODO add your handling code here:
        loadDataToTable();
    }//GEN-LAST:event_jButtonOKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonOK;
    private javax.swing.JComboBox<String> jComboBoxComponentsType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelHinhAnh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblComponents;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMSLK;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextArea txtTTin;
    private javax.swing.JTextArea txtTenLK;
    private javax.swing.JTextField txtThongSo;
    // End of variables declaration//GEN-END:variables
}
