/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelapi.models;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_yacht")
public class Yacht {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    private String yacht_no;
    private Double yacht_price;
    
    private String yacht_image;
    private Integer yacht_capacity;
    private String yacht_description;
    private Boolean is_active;
   
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = YachtType.class)
    @JoinColumn(name = "yacht_type_id", referencedColumnName = "id")
    private YachtType yacht_type_id;
    
    @ManyToOne(fetch = FetchType.LAZY , targetEntity = BookingStatus.class)
    @JoinColumn(name = "booking_status_id", referencedColumnName = "id")
    private BookingStatus booking_status_id;
    
    @OneToMany(mappedBy = "yacht_id" , cascade = CascadeType.ALL)
    private Collection<Booking> bookingCollection;
    
    
}
