import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileHandler.loadFromFile();

            JFrame frame = new JFrame("Подобие Excel");
            MainWindow form = new MainWindow();
            frame.setContentPane(form.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    int result = JOptionPane.showConfirmDialog(frame,
                            "Сохранить изменения перед выходом?",
                            "Подтверждение", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        FileHandler.saveToFile();
                    }
                    if (result != JOptionPane.CANCEL_OPTION) {
                        frame.dispose();
                        System.exit(0);
                    }
                }
            });

            frame.setSize(700, 450);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}