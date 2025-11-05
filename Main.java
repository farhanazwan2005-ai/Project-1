public class Main {
    public static void main(String[] args) {
        // Launch only ONE window — the entry point of your system
        new Login();

        // The rest will be opened by login logic automatically.
        // Example: inside Login.java → doLogin():
        // if (role.equals("admin")) new MenuAdmin(empName);
        // else new MenuUser(empName);
    }
}
