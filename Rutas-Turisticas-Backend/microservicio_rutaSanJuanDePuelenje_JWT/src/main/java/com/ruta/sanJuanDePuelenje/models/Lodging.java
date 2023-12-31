package com.ruta.sanJuanDePuelenje.models;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "lodging")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lodging {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lodging_id")
	private Integer id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 200)
	private String description;
	
	@Column(name = "quantity_available" ,nullable = false)
	private Integer quantityAvailable;
	
	@Column(name = "max_amount_person",nullable = true)
	private Integer maxAmountPerson;
	
	@Column(name = "unit_price" ,nullable = false)
	private Double unitPrice;
	
	@Column(nullable = true)
	private Boolean state;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "finca_id")
	@NotNull
	private Finca finca;
	
	@OneToMany(mappedBy = "lodging" , cascade = CascadeType.MERGE)
    private List<ReserveLodging> reserveLodging;
}
