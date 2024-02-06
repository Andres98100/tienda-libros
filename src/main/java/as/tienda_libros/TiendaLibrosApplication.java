package as.tienda_libros;

import as.tienda_libros.vista.LibroForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;


@SpringBootApplication
public class TiendaLibrosApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				new SpringApplicationBuilder(TiendaLibrosApplication.class)
						.headless(false) // se indica que no es una aplicación sin interfaz de usuario
						.web(WebApplicationType.NONE) // se indica que no es una aplicación web
						.run(args);

		EventQueue.invokeLater(() -> {
			// Se crea un objeto de la clase LibroForm
			LibroForm libroForm = context.getBean(LibroForm.class);
			libroForm.setVisible(true);

		});
	}
}
