import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class VentanaMenuPrincipal extends JFrame {
    private JList<Audiovisual> listaItems;
    private DefaultListModel<Audiovisual> modeloLista;
    private JTextArea detalleArea;

    public VentanaMenuPrincipal(String usuario) {
        super("Fideflix - Bienvenido " + usuario);
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JLabel lblBienvenida = new JLabel("¡Hola, disfruta de Fideflix!", SwingConstants.CENTER);
        lblBienvenida.setFont(lblBienvenida.getFont().deriveFont(18f));
        add(lblBienvenida, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        cargarEjemplos(modeloLista);
        listaItems = new JList<>(modeloLista);
        listaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaItems.setCellRenderer(new ListCellRenderer<>() {
            private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends Audiovisual> list,
                    Audiovisual value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(
                        list, value.getTitulo(), index, isSelected, cellHasFocus);
                return renderer;
            }
        });

        listaItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !listaItems.isSelectionEmpty()) {
                    mostrarDetalles(listaItems.getSelectedValue());
                }
            }
        });

        // Panel central: lista y área de detalle
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(listaItems),
                detallePanel()
        );
        split.setDividerLocation(200);
        add(split, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel detallePanel() {
        detalleArea = new JTextArea();
        detalleArea.setEditable(false);
        detalleArea.setLineWrap(true);
        detalleArea.setWrapStyleWord(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Detalles"));
        panel.add(new JScrollPane(detalleArea), BorderLayout.CENTER);
        return panel;
    }

    private void cargarEjemplos(DefaultListModel<Audiovisual> model) {
        model.addElement(new Pelicula("Inception", "Ciencia Ficción", 2010, "PG-13", "Christopher Nolan"));
        model.addElement(new Pelicula("The Godfather", "Crimen", 1972, "R", "Francis Ford Coppola"));
        model.addElement(new Serie("Breaking Bad", "Drama", 2008, "TV-MA", 5));
        model.addElement(new Serie("Stranger Things", "Ciencia Ficción", 2016, "TV-14", 4));
        model.addElement(new Documental("Our Planet", "Naturaleza", 2019, "TV-PG", "Vida salvaje"));
        model.addElement(new Documental("Free Solo", "Deportes", 2018, "PG-13", "Escalada libre"));
    }

    private void mostrarDetalles(Audiovisual item) {
        StringBuilder sb = new StringBuilder();
        sb.append("Título: ").append(item.getTitulo()).append("\n");
        sb.append("Género: ").append(item.getGenero()).append("\n");
        sb.append("Año: ").append(item.getAnio()).append("\n");
        sb.append("Clasificación: ").append(item.getClasificacion()).append("\n");

        if (item instanceof Pelicula) {
            sb.append("Director: ").append(((Pelicula) item).getDirector()).append("\n");
        } else if (item instanceof Serie) {
            sb.append("Temporadas: ").append(((Serie) item).getTemporadas()).append("\n");
        } else if (item instanceof Documental) {
            sb.append("Tema: ").append(((Documental) item).getTema()).append("\n");
        }

        detalleArea.setText(sb.toString());
    }
}
