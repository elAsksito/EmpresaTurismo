package com.cibertec.turismo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "destinos_turisticos")
@XmlRootElement(name = "destinoTuristico")
@XmlAccessorType(XmlAccessType.FIELD)
public class DestinoTuristico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "El nombre del destino es obligatorio.")
	@Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
	private String nombre;

	@Size(max = 255, message = "La descripción no puede superar los 255 caracteres.")
	private String descripcion;

	@Column(nullable = false)
	@NotBlank(message = "La ubicación es obligatoria.")
	private String ubicacion;
}