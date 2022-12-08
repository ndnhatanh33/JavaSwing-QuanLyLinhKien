/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.LinhKienDao;
import controller.DataValidator;
import controller.ImageHelper;
import controller.LoaiLinhKienDao;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import model.LinhKien;
import model.LoaiLinhKien;
import model.MessageDialogHelper;

/**
 *
 * @author ndnha
 */
public class ComponentsManagementPanel extends javax.swing.JPanel {

    private MainForm parentForm;
    private DefaultTableModel tblModel;
    private byte[] personalImage;

    /**
     * Creates new form GPAManagementPanel
     */
    public ComponentsManagementPanel() {
        initComponents();
        initTable();
        initComboBox();
        loadBangLinhKien();
    }

    private void initTable() {
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

    private void initComboBox() {
        try {
            LoaiLinhKienDao dao = new LoaiLinhKienDao();
            ArrayList<LoaiLinhKien> list = dao.findAll();
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (LoaiLinhKien it : list) {
                model.addElement(it.getMaLoai() + " - " + it.getTenLoai());
            }
            jComboBoxComponentsType.setModel(model);
            jComboBoxComponentsType.setSelectedIndex(0);
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }

    private void loadBangLinhKien() {
        try {
            LinhKienDao dao = new LinhKienDao();
            LoaiLinhKienDao daollk = new LoaiLinhKienDao();
            String temp = String.valueOf(jComboBoxComponentsType.getSelectedItem());
            temp = temp.substring(temp.lastIndexOf("-")+2);
            LoaiLinhKien llk = daollk.findByTenLoai(temp);
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

    private void loadBangLinhKien(String maSo) {
        try {
            LinhKienDao dao = new LinhKienDao();
            LoaiLinhKienDao daollk = new LoaiLinhKienDao();
            String temp = String.valueOf(jComboBoxComponentsType.getSelectedItem());
            temp = temp.substring(temp.lastIndexOf("-")+2);
            LoaiLinhKien llk = daollk.findByTenLoai(temp);
            LinhKien it = dao.findByMaSoLinhKien_MaLoai(maSo,llk.getMaLoai());
            tblModel.setRowCount(0);
            if (it != null) {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtTenLK = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtThongTin = new javax.swing.JTextArea();
        txtGiaTien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMSLK = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblComponents = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxComponentsType = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtMSLKTimKiem = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelHinhAnh = new javax.swing.JLabel();
        btnChonHinh = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtThongSo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();

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
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ LINH KIỆN");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setText("Tên linh kiện:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("Thông tin:");

        txtTenLK.setColumns(20);
        txtTenLK.setRows(5);
        jScrollPane3.setViewportView(txtTenLK);

        txtThongTin.setColumns(20);
        txtThongTin.setRows(5);
        jScrollPane4.setViewportView(txtThongTin);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("Giá tiền:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("Mã số linh kiện:");

        jLabel10.setText("VND");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtMSLK, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtMSLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10))
                .addGap(0, 0, 0))
        );

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
        tblComponents.setMinimumSize(new java.awt.Dimension(60, 1000));
        tblComponents.setRowHeight(110);
        tblComponents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComponentsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblComponents);

        btnSave.setForeground(new java.awt.Color(0, 0, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save16.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setForeground(new java.awt.Color(0, 0, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete16.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setForeground(new java.awt.Color(0, 0, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/update16.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnNew.setForeground(new java.awt.Color(0, 0, 255));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new16.png"))); // NOI18N
        btnNew.setText("Làm mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnSave)
                    .addComponent(btnDelete)
                    .addComponent(btnNew))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 204));
        jLabel5.setText("Loại linh kiện:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 204));
        jLabel3.setText("Mã số linh kiện:");

        btnSearch.setForeground(new java.awt.Color(0, 0, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search16.png"))); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxComponentsType, 0, 200, Short.MAX_VALUE)
                    .addComponent(txtMSLKTimKiem))
                .addGap(42, 42, 42)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxComponentsType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnSearch)
                    .addComponent(txtMSLKTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setText("Hình ảnh:");

        jLabelHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/failed128.png"))); // NOI18N
        jLabelHinhAnh.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnChonHinh.setForeground(new java.awt.Color(255, 0, 51));
        btnChonHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/brows16.png"))); // NOI18N
        btnChonHinh.setText("Chọn hình");
        btnChonHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonHinhActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 51));
        jLabel9.setText("Thông số cao nhất:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 51));
        jLabel11.setText("Số lượng:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(btnChonHinh)
                    .addComponent(jLabel2)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtThongSo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jLabelHinhAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(txtSoLuong))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtThongSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(71, 71, 71)
                        .addComponent(btnChonHinh)
                        .addGap(121, 121, 121))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator3)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(435, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(435, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator4)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 218, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtGiaTien.setText("");
        txtMSLK.setText("");
        txtMSLKTimKiem.setText("");
        txtTenLK.setText("");
        txtThongSo.setText("");
        txtThongTin.setText("");
        txtSoLuong.setText("");
        loadBangLinhKien();
        personalImage = null;
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/failed128.png"));
        jLabelHinhAnh.setIcon(icon);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtMSLK, sb, "Cần nhập Mã số linh kiện");
        DataValidator.validateEmpty(txtTenLK, sb, "Cần nhập Tên linh kiện");
        DataValidator.validateEmptyNumberic(txtGiaTien, sb, "Cần nhập Giá tiền (Dưới dạng số thực)");
        DataValidator.validateEmptyNumberic(txtThongSo, sb, "Cần nhập Thông số cao nhất (Dưới dạng số thực)");
        DataValidator.validateEmptyNumberic(txtSoLuong, sb, "Cần nhập Số lượng");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        try {
            LinhKien lk = new LinhKien();
            lk.setMaSoLinhKien(txtMSLK.getText());
            LoaiLinhKienDao daollk = new LoaiLinhKienDao();
            String temp = String.valueOf(jComboBoxComponentsType.getSelectedItem());
            temp = temp.substring(temp.lastIndexOf("-")+2);
            LoaiLinhKien llk = daollk.findByTenLoai(temp);
            lk.setMaLoai(llk.getMaLoai());
            lk.setTenLinhKien(txtTenLK.getText());
            lk.setThongTin(txtThongTin.getText());
            lk.setGiaTien(BigDecimal.valueOf(Double.valueOf(txtGiaTien.getText())));
            lk.setThongSoCaoNhat(Float.valueOf(txtThongSo.getText()));
            lk.setHinhAnh(personalImage);
            lk.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
            
            LinhKienDao dao = new LinhKienDao();
            if (dao.insert(lk)) {
                MessageDialogHelper.showMessageDialog(parentForm, "Linh kiện đã được lưu", "Thông báo");
                loadBangLinhKien();
            } else {
                MessageDialogHelper.showMessageDialog(parentForm, "Không thể lưu linh kiện", "Thông báo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       try {
            if (txtMSLKTimKiem.getText().length() != 0) {
                loadBangLinhKien(txtMSLKTimKiem.getText());
                if (tblModel.getRowCount() == 0) {
                    MessageDialogHelper.showMessageDialog(parentForm, "Không tìm thấy linh kiện có mã số theo yêu cầu!", "Thông báo");
                }
            } else {
                loadBangLinhKien();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtMSLK, sb, "Cần nhập Mã số linh kiện");
        DataValidator.validateEmpty(txtTenLK, sb, "Cần nhập Tên linh kiện");
        DataValidator.validateEmptyNumberic(txtGiaTien, sb, "Cần nhập Giá tiền (Dưới dạng số thực)");
        DataValidator.validateEmptyNumberic(txtThongSo, sb, "Cần nhập Thông số cao nhất (Dưới dạng số thực)");
        DataValidator.validateEmptyNumberic(txtSoLuong, sb, "Cần nhập Số lượng");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        if (MessageDialogHelper.showConfirmDialog(parentForm,
                "Bạn có muốn cập nhật linh kiện không?", "Hỏi") == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            LinhKien lk = new LinhKien();
            lk.setMaSoLinhKien(txtMSLK.getText());
            LoaiLinhKienDao daollk = new LoaiLinhKienDao();
            String temp = String.valueOf(jComboBoxComponentsType.getSelectedItem());
            temp = temp.substring(temp.lastIndexOf("-")+2);
            LoaiLinhKien llk = daollk.findByTenLoai(temp);
            lk.setMaLoai(llk.getMaLoai());
            lk.setTenLinhKien(txtTenLK.getText());
            lk.setThongTin(txtThongTin.getText());
            lk.setGiaTien(BigDecimal.valueOf(Double.valueOf(txtGiaTien.getText())));
            lk.setThongSoCaoNhat(Float.valueOf(txtThongSo.getText()));
            lk.setHinhAnh(personalImage);
            lk.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
            
            LinhKienDao dao = new LinhKienDao();
            if (dao.update(lk)) {
                MessageDialogHelper.showMessageDialog(parentForm, "Linh kiện đã được cập nhật", "Thông báo");
                loadBangLinhKien();
            } else {
                MessageDialogHelper.showMessageDialog(parentForm, "Không thể cập nhật linh kiện", "Thông báo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtMSLK, sb, "Cần nhập Mã số linh kiện");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        if (MessageDialogHelper.showConfirmDialog(parentForm,
                "Bạn có muốn xóa dữ liệu về linh kiện này không?", "Hỏi") == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            LinhKienDao dao = new LinhKienDao();
            LoaiLinhKienDao daollk = new LoaiLinhKienDao();
            String temp = String.valueOf(jComboBoxComponentsType.getSelectedItem());
            temp = temp.substring(temp.lastIndexOf("-")+2);
            LoaiLinhKien llk = daollk.findByTenLoai(temp);
            if (dao.delete(txtMSLK.getText(), llk.getMaLoai())) {
                MessageDialogHelper.showMessageDialog(parentForm, "Dữ liệu về linh kiện đã được xóa", "Thông báo");
                loadBangLinhKien();
                btnNewActionPerformed(evt);
            } else {
                MessageDialogHelper.showMessageDialog(parentForm, "Không thể xóa dữ liệu về linh kiện này", "Thông báo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblComponentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComponentsMouseClicked
        try {
            int row = tblComponents.getSelectedRow();
            
            if (row >= 0) {
                String maSo = (String) tblComponents.getValueAt(row, 0);
                LinhKienDao dao = new LinhKienDao();
                LoaiLinhKienDao daollk = new LoaiLinhKienDao();
                String temp = String.valueOf(jComboBoxComponentsType.getSelectedItem());
                temp = temp.substring(temp.lastIndexOf("-")+2);
                LoaiLinhKien llk = daollk.findByTenLoai(temp);
                LinhKien lk = dao.findByMaSoLinhKien_MaLoai(maSo, llk.getMaLoai());
                
                if (lk!=null) {
                    txtTenLK.setText(lk.getTenLinhKien());
                    txtMSLK.setText(lk.getMaSoLinhKien());
                    txtThongTin.setText(lk.getThongTin());
                    txtThongSo.setText(lk.getThongSoCaoNhat() + "");
                    DecimalFormat formatter = new DecimalFormat("###,###,###,###.##");
                    txtGiaTien.setText(formatter.format(lk.getGiaTien()) + "");
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

    private void btnChonHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonHinhActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter () {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".jpg");
                }
            }

            @Override
            public String getDescription() {
                return "Image File (*.jpg)";
            }
        
        });
        if (chooser.showOpenDialog(parentForm) == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        try {
            ImageIcon icon = new ImageIcon(file.getPath());
            Image img = ImageHelper.resize(icon.getImage(), 220, 220);
            ImageIcon resizedIcon = new ImageIcon(img);
            jLabelHinhAnh.setIcon(resizedIcon);
            personalImage = ImageHelper.toByteArray(img, "jpg");
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showMessageDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnChonHinhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonHinh;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> jComboBoxComponentsType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelHinhAnh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblComponents;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMSLK;
    private javax.swing.JTextField txtMSLKTimKiem;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextArea txtTenLK;
    private javax.swing.JTextField txtThongSo;
    private javax.swing.JTextArea txtThongTin;
    // End of variables declaration//GEN-END:variables
}
