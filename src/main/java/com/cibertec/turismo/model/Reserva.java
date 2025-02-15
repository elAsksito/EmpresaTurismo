package com.cibertec.turismo.model;

import java.time.LocalDate;

import com.cibertec.turismo.config.soap.LocalDateAdapter;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
    private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destino_id", nullable = false)
    private DestinoTuristico destino;

    @Column(nullable = false)
    @Future(message = "La fecha de reserva debe ser en el futuro")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaReserva;

    @Column(nullable = false)
    private String estado = "PENDIENTE";
}