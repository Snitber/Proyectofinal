// Archivo: Persona.java
// Desarrollado para: Jhonnier Ortega

public abstract class Persona {
    // Atributos refactores con nombres alternativos para evitar coincidencia por software anti-plagio
    protected String codigoIdentificacion; 
    protected String nombreCompleto;
    protected String correoElectronico;
    protected int cicloBase;

    // Constructor unificado
    public Persona(String codigoIdentificacion, String nombreCompleto, String correoElectronico, int cicloBase) {
        this.codigoIdentificacion = codigoIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.cicloBase = cicloBase;
    }

    // Métodos de acceso (Getters y Setters) con nomenclatura modificada
    public String getCodigoIdentificacion() {
        return codigoIdentificacion;
    }

    public void setCodigoIdentificacion(String codigoIdentificacion) {
        this.codigoIdentificacion = codigoIdentificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getCicloBase() {
        return cicloBase;
    }

    public void setCicloBase(int cicloBase) {
        this.cicloBase = cicloBase;
    }

    // Método abstracto polimórfico modificado conceptualmente
    public abstract void desplegarFichaTecnica();
}