package com.cibertec.turismo.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservas")
public class Reserva {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destino_id", nullable = false)
    private DestinoTuristico destino;

    @Column(nullable = false)
    private LocalDate fechaReserva;

    @Column(nullable = false)
    private String estado = "PENDIENTE";
}