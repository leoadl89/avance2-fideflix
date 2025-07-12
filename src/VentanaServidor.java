import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class VentanaServidor extends JFrame {
    private JTextArea textArea;

    public VentanaServidor() {
        super("Servidor Fideflix");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        setVisible(true);

        // Arranca el hilo que escucha conexiones
        new Thread(this::startServer).start();
    }

    private void log(String msg) {
        SwingUtilities.invokeLater(() -> textArea.append(msg + "\n"));
    }

    private void startServer() {
        try (ServerSocket server = new ServerSocket(12345)) {
            log("Servidor iniciado en el puerto 12345");
            while (true) {
                Socket client = server.accept();
                log("Cliente conectado: " + client.getInetAddress());
                new Thread(() -> handleClient(client)).start();
            }
        } catch (IOException e) {
            log("Error en servidor: " + e);
            e.printStackTrace();
        }
    }

    private void handleClient(Socket client) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter    writer = new PrintWriter(client.getOutputStream(), true)) {

            String command   = reader.readLine();
            String usuario   = reader.readLine();
            String contrasen = reader.readLine();

            if ("iniciarSesion".equalsIgnoreCase(command)) {
                boolean ok = ControladorUsuarios.verificarUsuario(new Usuario(usuario, contrasen));
                writer.println(ok ? "OK" : "Usuario o contraseña inválidos");
                log("Login " + usuario + " → " + (ok ? "OK" : "FAIL"));

            } else if ("crearUsuario".equalsIgnoreCase(command)) {
                boolean created = ControladorUsuarios.addUsuario(new Usuario(usuario, contrasen));
                writer.println(created ? "Usuario creado con éxito" : "El usuario ya existe");
                log("CrearUsuario " + usuario + " → " + (created ? "CREATED" : "EXISTS"));

            } else {
                writer.println("Comando desconocido");
                log("Comando desconocido: " + command);
            }
        } catch (IOException e) {
            log("Error al atender cliente: " + e);
            e.printStackTrace();
        } finally {
            try { client.close(); } catch (IOException ignored) {}
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            //
            new VentanaServidor();
            //
            new VentanaAcceso();
        });
    }
}
