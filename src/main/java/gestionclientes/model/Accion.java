package gestionclientes.model;

public class Accion {
    private final String descripcion;
    private final long timestampMillis;

    public Accion(String descripcion, long timestampMillis) {
        this.descripcion = descripcion;
        this.timestampMillis = timestampMillis;
    }

    public String getDescripcion() { return descripcion; }
    public long getTimestampMillis() { return timestampMillis; }

    @Override
    public String toString() {
        return "[" + timestampMillis + "] " + descripcion;
    }
}



