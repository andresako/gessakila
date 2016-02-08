package Modelo;


/**
 * @author Andres
 */
public class LenguajeCombo {

    private byte id;
    private String nombre;
    private Language lng;

    public LenguajeCombo() {
    }

    public LenguajeCombo(byte id){
        this.id = id;
    }
    public LenguajeCombo(Language lng, String nombre, byte id){
        this.lng = lng;
        this.nombre = nombre;
        this.id = id;
    }
    public LenguajeCombo(byte id, String nombre) {
        this.id = id;
        this.nombre = nombre;;
    }

    public int getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Language getLanguage(){
        return this.lng;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return this.nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LenguajeCombo other = (LenguajeCombo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
    
    
}
