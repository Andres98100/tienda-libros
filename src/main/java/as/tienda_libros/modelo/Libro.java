package as.tienda_libros.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase que representa un libro
 * @Entity indica que esta clase es una entidad de la base de datos
 * @Data es una anotación de Lombok que genera los métodos getter, setter, equals, hashcode y toString
 * @NoArgsConstructor es una anotación de Lombok que genera un constructor sin argumentos
 * @AllArgsConstructor es una anotación de Lombok que genera un constructor con todos los argumentos
 * @ToString es una anotación de Lombok que genera el método toString
 * @Id indica que el atributo es la clave primaria
 * @GeneratedValue indica que el valor del atributo se genera automáticamente
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idLibro;
    String nombreLibro;
    String autor;
    Double precio;
    Integer existencias;
}
