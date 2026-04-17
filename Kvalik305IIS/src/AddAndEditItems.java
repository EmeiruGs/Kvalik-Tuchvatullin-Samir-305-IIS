import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddAndEditItems extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfInum;
    private JTextField tfTime;
    private JTextField tfJnum;
    private JLabel lblInum;

    private Product currentProduct;

    public AddAndEditItems(Product product) {
        contentPane = new JPanel(new GridLayout(4, 2, 5, 5));
        lblInum = new JLabel("Изначальное число:");
        tfInum = new JTextField();
        JLabel lblTime = new JLabel("Время (сек):");
        tfTime = new JTextField();
        JLabel lblJnum = new JLabel("Получившееся число:");
        tfJnum = new JTextField();
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Отмена");

        contentPane.add(lblInum);
        contentPane.add(tfInum);
        contentPane.add(lblTime);
        contentPane.add(tfTime);
        contentPane.add(lblJnum);
        contentPane.add(tfJnum);
        contentPane.add(buttonOK);
        contentPane.add(buttonCancel);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400, 300);
        setLocationRelativeTo(null);

        if (product != null) {
            currentProduct = product;
            tfInum.setText(String.valueOf(product.getInum()));
            tfTime.setText(String.valueOf(product.getTime()));
            tfJnum.setText(String.valueOf(product.getJnum()));
            lblInum.setText("Число N: " + product.getInum());
        } else {
            lblInum.setText("Число N:");
        }

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String inumText = tfInum.getText().trim();
        String timeText = tfTime.getText().trim();
        String jnumText = tfJnum.getText().trim();

        int inum;
        try {
            inum = Integer.parseInt(inumText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Число N должно быть целым числом",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double time;
        try {
            time = Double.parseDouble(timeText);
            if (time <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Время должно быть положительным числом",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double jnum;
        try {
            jnum = Double.parseDouble(jnumText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Получившееся число должно быть числом",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentProduct != null) {
            int index = ContexData.products.indexOf(currentProduct);
            currentProduct.setInum(inum);
            currentProduct.setTime(time);
            currentProduct.setJnum(jnum);
            ContexData.products.set(index, currentProduct);
        } else {
            Product product = new Product(inum, time, jnum);
            ContexData.products.add(product);
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
