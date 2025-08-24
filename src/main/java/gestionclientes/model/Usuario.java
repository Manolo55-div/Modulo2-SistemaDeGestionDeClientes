package gestionclientes.model;

public class Usuario {
    private final int id;
    private String nombreCompleto;
    private final String username;
    private String password;
    private final Rol rol;

    private final Accion[] historial;
    private int contadorAcciones;

    public Usuario(int id, String nombreCompleto, String username, String password, Rol rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.historial = new Accion[100]; // máx 100
        this.contadorAcciones = 0;
        registrarAccion("Usuario creado");
    }

    public int getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getUsername() { return username; }
    public Rol getRol() { return rol; }

    public boolean validarPassword(String pass) {
        return this.password.equals(pass);
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        registrarAccion("Actualizó su nombre completo");
    }

    public boolean cambiarPassword(String actual, String nueva) {
        if (this.password.equals(actual)) {
            this.password = nueva;
            registrarAccion("Cambió su contraseña");
            return true;
        }
        return false;
    }

    public void forzarPassword(String nueva) {
        this.password = nueva;
        registrarAccion("Administrador forzó cambio de contraseña");
    }

    public void registrarAccion(String descripcion) {
        if (contadorAcciones < historial.length) {
            historial[contadorAcciones++] = new Accion(descripcion, System.currentTimeMillis());
        }
    }

    public void mostrarHistorial() {
        System.out.println("Historial de " + username + ":");
        for (int i = 0; i < contadorAcciones; i++) {
            System.out.println(historial[i]);
        }
    }
}

