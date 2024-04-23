/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.hotelapi.repository;

import fpt.aptech.hotelapi.models.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;


public interface YachtRepository extends JpaRepository<Yacht, Integer> {
    
}
