import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListItems {
    private JButton btnExit;
    private JPanel panel;
    private JTable tableItems;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSave;
    private JButton btnLoad;
    private JLabel lblStatus;

    private MainWindow mainWindow;

    public ListItems(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        refresh();

        btnExit.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    panel,
                    "Вы действительно хотите выйти?",
                    "Подтверждение выхода",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        btnAdd.addActionListener(e -> {
            AddAndEditItems dialog = new AddAndEditItems(null);
            dialog.setVisible(true);
            refresh();
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = tableItems.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = ContexData.products.get(selectedRow);
                AddAndEditItems dialog = new AddAndEditItems(selectedProduct);
                dialog.setVisible(true);
                refresh();
            } else {
                JOptionPane.showMessageDialog(panel,
                        "Выберите строку для редактирования",
                        "Предупреждение",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = tableItems.getSelectedRow();
            if (selectedRow >= 0) {
                int result = JOptionPane.showConfirmDialog(panel,
                        "Удалить выбранную строку?",
                        "Подтверждение удаления",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    ContexData.products.remove(selectedRow);
                    refresh();
                }
            } else {
                JOptionPane.showMessageDialog(panel,
                        "Выберите строку для удаления",
                        "Предупреждение",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnSave.addActionListener(e -> {
            if (FileHandler.saveToFile()) {
                lblStatus.setText("Сохранено успешно");
            } else {
                lblStatus.setText("Ошибка сохранения");
            }
        });

        btnLoad.addActionListener(e -> {
            if (FileHandler.loadFromFile()) {
                refresh();
                lblStatus.setText("Загружено успешно");
            } else {
                lblStatus.setText("Ошибка загрузки");
            }
        });
    }

    public JPanel getJPanel() {
        return panel;
    }

    public void refresh() {
        String[] columnNames = {"Число N", "Время (сек)", "Получившееся число"};
        Object[][] data = new Object[ContexData.products.size()][3];

        for (int i = 0; i < ContexData.products.size(); i++) {
            Product p = ContexData.products.get(i);
            data[i] = new Object[]{
                    p.getInum(),
                    String.format("%.5f", p.getTime()),
                    String.format("%.5f", p.getJnum())
            };
        }
        tableItems.setModel(new DefaultTableModel(data, columnNames));
    }

}
