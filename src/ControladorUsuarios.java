import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarios {
    private static final String FILE_NAME = "usuarios.dat";

    public static synchronized boolean addUsuario(Usuario user) {
        List<Usuario> usuarios = readUsuarios();
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        usuarios.add(user);
        saveUsuarios(usuarios);
        return true;
    }

    public static synchronized boolean verificarUsuario(Usuario user) {
        List<Usuario> usuarios = readUsuarios();
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static List<Usuario> readUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Usuario>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static void saveUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}