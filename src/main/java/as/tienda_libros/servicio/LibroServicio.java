package as.tienda_libros.servicio;

import as.tienda_libros.modelo.Libro;
import as.tienda_libros.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que implementa la interfaz ILibroServicio
 * @Service indica que esta clase es un servicio
 * @Autowired inyecta la dependencia de la clase LibroRepositorio en esta clase
 * @Override indica que el método es una sobreescritura de la interfaz ILibroServicio
 */
@Service
public class LibroServicio implements ILibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Override
    public List<Libro> listarLibro() {
        return libroRepositorio.findAll();
    }

    @Override
    public Libro encontrarLibroPorId(Integer id) {
        return libroRepositorio.findById(id).orElse(null);
    }

    @Override
    public void guardarLibro(Libro libro) {
        libroRepositorio.save(libro);
    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroRepositorio.delete(libro);
    }
}
