package com.cibertec.turismo.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservas")
@XmlRootElement(name = "reservas")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reserva {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
	@NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destino_id", nullable = false)
	@NotNull(message = "El destino turístico es obligatorio")
    private DestinoTuristico destino;

    @Column(nullable = false)
    @Future(message = "La fecha de reserva debe ser en el futuro")
    private LocalDate fechaReserva;

    @Column(nullable = false)
    private String estado = "PENDIENTE";
}