package as.tienda_libros.vista;

import as.tienda_libros.modelo.Libro;
import as.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que hereda de JFrame y que implementa la interfaz ILibroForm
 * @Component indica que esta clase es un componente
 * @Autowired inyecta la dependencia de la clase LibroServicio en esta clase
 * @Override indica que el método es una sobreescritura de la interfaz ILibroForm
 * @createUIComponents indica que se crean componentes de la interfaz de usuario
 */
@Component
public class LibroForm extends JFrame {

    LibroServicio libroServicio;
    private JPanel panel;
    private JLabel label1;
    private JTable tablaLibros;
    private JTextField idTexto;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
        iniciarForma();
        agregarButton.addActionListener(e -> agregarLibro());
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });
        modificarButton.addActionListener(e -> modificarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());
    }

    // método para inicializar la forma donde se inicializan los componentes de la interfaz de usuario
    private void iniciarForma() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900, 700);
        Font font = new Font("PT", Font.BOLD, 28);
        label1.setFont(font);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int x = (dimension.width - getWidth() / 2);
        int y = (dimension.height - getHeight() / 2);
        setLocation(x, y);
    }

    // método para agregar un libro a la base de datos
    private void agregarLibro() {
        JTextField[] campos = {libroTexto, autorTexto, precioTexto, existenciasTexto};
        for (JTextField campo : campos) {
            if (campo.getText().isEmpty()) {
                mostrarMensaje("Todos los campos deben estar llenos");
                campo.requestFocusInWindow();
                return;
            }
        }

        var nombreLibro = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());

        // verificar si el libro ya existe
        Libro libroExistente = null;
        if (!idTexto.getText().isEmpty()) {
            libroExistente = libroServicio.encontrarLibroPorId(Integer.parseInt(idTexto.getText()));
        }
        if (libroExistente != null) {
            mostrarMensaje("El libro ya existe");
            return;
        }

        var libro = new Libro(null, nombreLibro, autor, precio, existencias);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se agrego el libro correctamente");
        limpiarCampos();
        listarLibros();
    }

    // método para modificar un libro en la base de datos
    private void modificarLibro () {
        // verifico que los campos no estén vacíos
        if (idTexto.getText().isEmpty() || libroTexto.getText().isEmpty() || autorTexto.getText().isEmpty()
                || precioTexto.getText().isEmpty() || existenciasTexto.getText().isEmpty()){
            mostrarMensaje("Falta información del libro");
            return;
        }
        // obtener los valores de los campos de texto
        var nombreLibro = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());

        // modificar el libro
        var libro = new Libro(null, nombreLibro, autor, precio, existencias);
        libro.setIdLibro(Integer.parseInt(idTexto.getText()));
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se modifico el libro correctamente");
        limpiarCampos();
        listarLibros();
    }

    // método para eliminar un libro de la base de datos
    public void eliminarLibro() {
        // verificar que se haya seleccionado un libro
        if (idTexto.getText().isEmpty()) {
            mostrarMensaje("Seleccione un libro");
            return;
        }
        var libro = libroServicio.encontrarLibroPorId(Integer.parseInt(idTexto.getText()));
        if (libro != null) {
            libroServicio.eliminarLibro(libro);
            mostrarMensaje("Se elimino el libro correctamente");
            limpiarCampos();
            listarLibros();
        } else {
            mostrarMensaje("El libro no existe");
        }
    }

    // método para mostrar un mensaje
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // método para cargar el libro seleccionado en los campos de texto
    private void cargarLibroSeleccionado() {
        // obtener el índice de la fila seleccionada
        var renglon = tablaLibros.getSelectedRow();
        if (renglon != -1) {
            JTextField[] campos = {idTexto, libroTexto, autorTexto, precioTexto, existenciasTexto};
            for (int i = 0; i < campos.length; i++) {
                String valor = tablaModeloLibros.getValueAt(renglon, i).toString();
                campos[i].setText(valor);
            }
        }
    }

    // método para limpiar los campos de texto seteando el texto vacío
    private void limpiarCampos() {
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // Creamos idTexto oculto
        idTexto = new JTextField();
        idTexto.setVisible(false);
        this.tablaModeloLibros = new DefaultTableModel(0, 5){
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        String [] cabeceros = {"ID", "Título", "Autor", "Precio", "Existencias"};
        tablaModeloLibros.setColumnIdentifiers(cabeceros);
        // inicializar el objeto JTable
        this.tablaLibros = new JTable(tablaModeloLibros);
        // evitar seleccionar vasrios renglones
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarLibros();
    }

    // método para listar los libros en la tabla
    private void listarLibros() {
        // limpiar la tabla
        tablaModeloLibros.setRowCount(0);
        // obtener libros
        var libros = libroServicio.listarLibro();
        libros.forEach((libro) -> {
            Object[] renglonLibro = {
                    libro.getIdLibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            this.tablaModeloLibros.addRow(renglonLibro);
        });
    }
}
