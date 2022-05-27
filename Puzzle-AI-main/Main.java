package puzzle;

public class Main {

    static MainForm window = new MainForm();

    public static void main(String[] args) {
        window.setDefaultCloseOperation(MainForm.EXIT_ON_CLOSE);
        window.setLocation(250, 30);
        window.setResizable(false);
        window.setVisible(true);
        window.start();
    }

}