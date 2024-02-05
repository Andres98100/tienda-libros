package as.tienda_libros.repositorio;

import as.tienda_libros.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz que extiende de JpaRepository para realizar operaciones CRUD sobre la entidad Libro
 */
public interface LibroRepositorio extends JpaRepository<Libro, Integer>{

}
