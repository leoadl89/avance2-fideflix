public class Audiovisual {
    private String titulo;
    private String genero;
    private int anio;
    private String clasificacion;

    public Audiovisual(String titulo, String genero, int anio, String clasificacion) {
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.clasificacion = clasificacion;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
}