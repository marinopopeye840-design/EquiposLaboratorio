
package ni.edu.uam.equiposlaboratorio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_equipo", schema = "catalogos")
public class TipoEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Long idTipo;
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    public TipoEquipo() {
    }
    public TipoEquipo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public Long getIdTipo() {
        return idTipo;
    }
    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
