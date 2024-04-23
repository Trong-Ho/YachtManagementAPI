/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class YachtDto {
    private Integer id;
    private String yacht_no;
    private Double yacht_price;
    private String yacht_image;
    private Integer yacht_capacity;
    private String yacht_description;
    private Boolean is_active;
    
    private int booking_status_id;
    private BookingStatusDto booking_status_info;
    
    private int yacht_type_id;
    private YachtTypeDto yacht_type_info;
}
