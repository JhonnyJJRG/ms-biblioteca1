package pe.edu.idat.ms_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pe.edu.idat.ms_biblioteca.dto.PrestamoDTO;
import pe.edu.idat.ms_biblioteca.entity.Prestamo;
import pe.edu.idat.ms_biblioteca.service.PrestamoService;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Prestamo crearPrestamo(@RequestBody PrestamoDTO dto) {
        return prestamoService.crearPrestamo(dto);
    }
}
