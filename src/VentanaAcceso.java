import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class VentanaAcceso extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;

    // Login components
    private JTextField loginUsuario;
    private JPasswordField loginPass;

    // Register components
    private JTextField regUsuario;
    private JPasswordField regPass;

    public VentanaAcceso() {
        super("Fideflix - Acceso");
        initComponents();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Login Panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        loginPanel.setBorder(BorderFactory.createTitledBorder("Iniciar Sesión"));
        loginUsuario = new JTextField();
        loginPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");
        JButton btnToRegister = new JButton("Registrar");

        loginPanel.add(new JLabel("Usuario:"));
        loginPanel.add(loginUsuario);
        loginPanel.add(new JLabel("Contraseña:"));
        loginPanel.add(loginPass);
        loginPanel.add(btnLogin);
        loginPanel.add(btnToRegister);

        // Register Panel
        JPanel regPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        regPanel.setBorder(BorderFactory.createTitledBorder("Registrar Usuario"));
        regUsuario = new JTextField();
        regPass = new JPasswordField();
        JButton btnRegister = new JButton("Crear");
        JButton btnToLogin = new JButton("Volver");

        regPanel.add(new JLabel("Usuario:"));
        regPanel.add(regUsuario);
        regPanel.add(new JLabel("Contraseña:"));
        regPanel.add(regPass);
        regPanel.add(btnRegister);
        regPanel.add(btnToLogin);

        cards.add(loginPanel, "login");
        cards.add(regPanel, "register");

        add(cards);
        cardLayout.show(cards, "login");

        btnLogin.addActionListener(e -> login());
        btnToRegister.addActionListener(e -> {
            clearRegister();
            cardLayout.show(cards, "register");
        });
        btnRegister.addActionListener(e -> register());
        btnToLogin.addActionListener(e -> {
            clearLogin();
            cardLayout.show(cards, "login");
        });

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void login() {
        String user = loginUsuario.getText().trim();
        String pass = new String(loginPass.getPassword()).trim();
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            writer.println("iniciarSesion");
            writer.println(user);
            writer.println(pass);

            String resp = reader.readLine();
            if ("OK".equalsIgnoreCase(resp)) {
                dispose();
                SwingUtilities.invokeLater(() -> new VentanaMenuPrincipal(user));
            } else {
                JOptionPane.showMessageDialog(this, resp, "Login Fallido", JOptionPane.WARNING_MESSAGE);
                clearLogin();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión:\n" + ex.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void register() {
        String user = regUsuario.getText().trim();
        String pass = new String(regPass.getPassword()).trim();
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            writer.println("crearUsuario");
            writer.println(user);
            writer.println(pass);

            String resp = reader.readLine();
            JOptionPane.showMessageDialog(this, resp, "Registro", JOptionPane.INFORMATION_MESSAGE);
            clearLogin();
            clearRegister();
            cardLayout.show(cards, "login");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión:\n" + ex.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearLogin() {
        loginUsuario.setText("");
        loginPass.setText("");
    }

    private void clearRegister() {
        regUsuario.setText("");
        regPass.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaAcceso::new);
    }
}