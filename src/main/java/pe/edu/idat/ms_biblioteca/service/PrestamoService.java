package pe.edu.idat.ms_biblioteca.service;

import org.springframework.stereotype.Service;
import pe.edu.idat.ms_biblioteca.dto.PrestamoDTO;
import pe.edu.idat.ms_biblioteca.entity.Libro;
import pe.edu.idat.ms_biblioteca.entity.Prestamo;
import pe.edu.idat.ms_biblioteca.entity.Usuario;
import pe.edu.idat.ms_biblioteca.repository.*;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository pr, UsuarioRepository ur, LibroRepository lr) {
        this.prestamoRepository = pr;
        this.usuarioRepository = ur;
        this.libroRepository = lr;
    }

    public Prestamo crearPrestamo(PrestamoDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(dto.idLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (libro.getStockDisponible() <= 0) {
            throw new RuntimeException("No hay stock disponible");
        }

        libro.setStockDisponible(libro.getStockDisponible() - 1);
        libroRepository.save(libro);

        Prestamo p = new Prestamo();
        p.setUsuario(usuario);
        p.setLibro(libro);
        p.setFechaPrestamo(dto.fechaPrestamo());
        p.setFechaDevolucion(dto.fechaDevolucion());
        p.setDevuelto(false);

        return prestamoRepository.save(p);
    }

    public java.util.List<Prestamo> listarPorUsuario(Long idUsuario) {
        return prestamoRepository.findByUsuarioId(idUsuario);
    }
}
