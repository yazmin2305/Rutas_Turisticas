package com.ruta.sanJuanDePuelenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruta.sanJuanDePuelenje.models.Workshop;

public interface IWorkshopRepository extends JpaRepository<Workshop, Integer>{

}