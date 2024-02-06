package as.tienda_libros.servicio;

import as.tienda_libros.modelo.Libro;

import java.util.List;

public interface ILibroServicio {

    List<Libro> listarLibro();
    Libro encontrarLibroPorId(Integer id);
    void guardarLibro(Libro libro);
    void eliminarLibro(Libro libro);

    void guardarExcel();
}
