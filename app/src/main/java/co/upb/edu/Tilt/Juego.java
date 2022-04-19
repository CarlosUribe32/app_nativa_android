package co.upb.edu.Tilt;

public class Juego {
    //Atributos
    private int id;
    private String name;
    private String description;
    private String released;
    private String background_image;

    public int getId() { return id;}
    public void setId(int id){ this.id = id;}

    public String getName(){
        return name;
    }
    public void setName(String nombre){
        this.name = nombre;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String descripcion){
        this.description = descripcion;
    }

    public String getReleased() {
        return released;
    }
    public void setReleased(String lanzamiento){
        this.released = lanzamiento;
    }

    public String getBackground_image() {
        return background_image;
    }
    public void setBackground_image(String imagen){
        this.background_image = imagen;
    }
}
