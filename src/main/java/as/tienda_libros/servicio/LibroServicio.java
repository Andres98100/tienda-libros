package as.tienda_libros.servicio;

import as.tienda_libros.modelo.Libro;
import as.tienda_libros.repositorio.LibroRepositorio;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
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

    @Override
    public void guardarExcel() {
        List<Libro> libros = listarLibro();

        // código para guardar los libros en un archivo Excel
        Workbook libroExcel = new XSSFWorkbook();
        Sheet hoja = libroExcel.createSheet("Libros");

        int numColumnas = 5;
        for (int i = 0; i < numColumnas; i++) {
            hoja.setColumnWidth(i, 5000);
        }

        hoja.setDefaultRowHeightInPoints(20);

        Row cabecera = hoja.createRow(0);
        cabecera.createCell(0).setCellValue("Id");
        cabecera.createCell(1).setCellValue("Libro");
        cabecera.createCell(2).setCellValue("Autor");
        cabecera.createCell(3).setCellValue("Precio");
        cabecera.createCell(4).setCellValue("Existencias");

        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            Row fila = hoja.createRow(i + 1);
            fila.createCell(0).setCellValue(libro.getIdLibro());
            fila.createCell(1).setCellValue(libro.getNombreLibro());
            fila.createCell(2).setCellValue(libro.getAutor());
            fila.createCell(3).setCellValue(libro.getPrecio());
            fila.createCell(4).setCellValue(libro.getExistencias());
        }

        try (FileOutputStream fileOut = new FileOutputStream("D:\\proyectos\\registros\\Libros.xlsx")) {
            libroExcel.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
