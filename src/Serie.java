public class Serie extends Audiovisual {
    private int temporadas;

    public Serie(String titulo, String genero, int anio, String clasificacion, int temporadas) {
        super(titulo, genero, anio, clasificacion);
        this.temporadas = temporadas;
    }

    public int getTemporadas() { return temporadas; }
    public void setTemporadas(int temporadas) { this.temporadas = temporadas; }
}