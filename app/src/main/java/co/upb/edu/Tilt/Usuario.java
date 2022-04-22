package co.upb.edu.Tilt;

public class Usuario {

    //Atributos
    private String correo;
    private String nombre;
    private String pass;

    //Constructor
    public Usuario(String correo, String nombre, String pass) {
        this.correo = correo;
        this.nombre = nombre;
        this.pass = pass;
    }


    //Accesores
    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }


}
