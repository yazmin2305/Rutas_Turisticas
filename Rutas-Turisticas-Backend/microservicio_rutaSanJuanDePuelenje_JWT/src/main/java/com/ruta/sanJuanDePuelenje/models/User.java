package com.ruta.sanJuanDePuelenje.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String identification;
	
	@Column(nullable = false, length = 45)
	private String name;
	
	@Column(name = "last_name" ,nullable = false, length = 45)
	private String lastName;
	
	@Column(nullable = false, unique = true, length = 20)
	private String phone;
	
	@Column(nullable = false, unique = true, length = 30)
	@Email
	private String email;
	
	@Column(nullable = true)
	private Boolean state;
	
	@Column(nullable = false, length = 60)
	private String password;
	
	/* Cuando se pide un cambio de contraseña se crea un nuevo token 
	 * y se guarda en este campo que luego volvera a estar nulo	
	*/
	@Column(nullable = true, length = 50)
	private String tokenPassword;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rol_id", nullable = true)
	private Role role;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ruta_id")
	private Ruta ruta;
}

