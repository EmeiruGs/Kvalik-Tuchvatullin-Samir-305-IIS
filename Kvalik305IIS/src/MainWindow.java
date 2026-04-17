import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ListItems listItems;

    public MainWindow() {
        listItems = new ListItems(this);
        mainPanel.add(listItems.getJPanel(), "listItems");
        cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "listItems");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void showPage(String page) {
        cardLayout.show(mainPanel, page);
    }
}