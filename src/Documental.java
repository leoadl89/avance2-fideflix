public class Documental extends Audiovisual {
    private String tema;

    public Documental(String titulo, String genero, int anio, String clasificacion, String tema) {
        super(titulo, genero, anio, clasificacion);
        this.tema = tema;
    }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
}