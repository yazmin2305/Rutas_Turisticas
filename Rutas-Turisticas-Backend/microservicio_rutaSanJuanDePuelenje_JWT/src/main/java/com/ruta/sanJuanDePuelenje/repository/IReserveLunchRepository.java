package com.ruta.sanJuanDePuelenje.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ruta.sanJuanDePuelenje.models.Reserve;
import com.ruta.sanJuanDePuelenje.models.ReserveLunch;

public interface IReserveLunchRepository extends JpaRepository<ReserveLunch, Integer>{

	void deleteByReserve(Reserve reserve);

}
