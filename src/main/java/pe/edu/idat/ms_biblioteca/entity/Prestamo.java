package pe.edu.idat.ms_biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table( name = "prestamo")
@Data
@NoArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate fechaPrestamo;

    @NotNull
    private LocalDate fechaDevolucion;

    @NotNull
    private Boolean devuelto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;


}
