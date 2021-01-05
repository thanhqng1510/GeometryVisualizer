public class Application {
    private String email;
    public Application(String email1) {
        this.email = email1;
    }

    public void start() {
        MainWindow main_window = new MainWindow(this.email);
    }

    public void exit() {

    }

    public static void main(String[] args) {
        MainLogin ml = new MainLogin();
    }

}
