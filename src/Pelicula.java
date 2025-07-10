public class Pelicula extends Audiovisual {
    private String director;

    public Pelicula(String titulo, String genero, int anio, String clasificacion, String director) {
        super(titulo, genero, anio, clasificacion);
        this.director = director;
    }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
}