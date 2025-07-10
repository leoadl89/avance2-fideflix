import javax.swing.*;
import java.io.*;
import java.net.*;

public class VentanaCrearUsuario {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Crear Usuario");
        JTextField campoUsuario = new JTextField(15);
        JPasswordField campoContrasena = new JPasswordField(15);
        JButton btnCrear = new JButton("Crear Usuario");

        btnCrear.addActionListener(e -> {
            String usuario = campoUsuario.getText().trim();
            String contrasena = new String(campoContrasena.getPassword()).trim();
            try (Socket socket = new Socket("localhost", 12345);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                writer.println("crearUsuario");
                writer.println(usuario);
                writer.println(contrasena);

                String respuesta = reader.readLine();
                JOptionPane.showMessageDialog(frame, respuesta);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "No se pudo conectar al servidor:\n" + ex.getMessage(),
                                              "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Usuario:"));
        panel.add(campoUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(campoContrasena);
        panel.add(btnCrear);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}