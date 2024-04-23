/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.hotelapi.service;

import fpt.aptech.hotelapi.dto.BookingStatusDto;
import fpt.aptech.hotelapi.dto.YachtDto;
import fpt.aptech.hotelapi.dto.YachtTypeDto;
import fpt.aptech.hotelapi.models.BookingStatus;
import fpt.aptech.hotelapi.models.Yacht;
import fpt.aptech.hotelapi.models.YachtType;
import fpt.aptech.hotelapi.repository.BookingStatusRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fpt.aptech.hotelapi.repository.YachtRepository;
import fpt.aptech.hotelapi.repository.YachtTypeRepository;


@Service
public class YachtService {
    private YachtTypeRepository _yachtTypeRepo;
    private BookingStatusRepository _bookingStatusRepo;
    private YachtRepository _yachtRepo;

    @Autowired
    public YachtService(YachtTypeRepository _yachtTypeRepo, BookingStatusRepository _bookingStatusRepo, YachtRepository _yachtRepo) {
        this._yachtTypeRepo = _yachtTypeRepo;
        this._bookingStatusRepo = _bookingStatusRepo;
        this._yachtRepo = _yachtRepo;
    }
    
    private Yacht mapToModel(YachtDto yachtDto) {
        Yacht yacht = new Yacht();
        yacht.setYacht_no(yachtDto.getYacht_no());
        yacht.setYacht_price(yachtDto.getYacht_price());
        yacht.setYacht_image(yachtDto.getYacht_image());
        yacht.setYacht_capacity(yachtDto.getYacht_capacity());
        yacht.setYacht_description(yachtDto.getYacht_description());
        yacht.setIs_active(yachtDto.getIs_active());
        
        BookingStatus bookingStatusInfo = _bookingStatusRepo.findById(yachtDto.getBooking_status_id()).orElse(null);
        YachtType yachtTypeInfo = _yachtTypeRepo.findById(yachtDto.getYacht_type_id()).orElse(null);
        
        yacht.setBooking_status_id(bookingStatusInfo);
        yacht.setYacht_type_id(yachtTypeInfo);
        
        return yacht;
    }
    
    private YachtDto mapToDto(Yacht yacht) {
        YachtDto yachtDto = new YachtDto();
        yachtDto.setId(yacht.getId());
        yachtDto.setYacht_no(yacht.getYacht_no());
        yachtDto.setYacht_price(yacht.getYacht_price());
        yachtDto.setYacht_image(yacht.getYacht_image());
        yachtDto.setYacht_capacity(yacht.getYacht_capacity());
        yachtDto.setYacht_description(yacht.getYacht_description());
        yachtDto.setIs_active(yacht.getIs_active());
        
        BookingStatusDto bookingStatusDto = new BookingStatusDto();
        bookingStatusDto.setId(yacht.getBooking_status_id().getId());
        bookingStatusDto.setBooking_status_name(yacht.getBooking_status_id().getBooking_status_name());
        
        yachtDto.setBooking_status_id(bookingStatusDto.getId());
        yachtDto.setBooking_status_info(bookingStatusDto);
        
        YachtTypeDto yachtTypeDto = new YachtTypeDto();
        yachtTypeDto.setId(yacht.getYacht_type_id().getId());
        yachtTypeDto.setYacht_type_name(yacht.getYacht_type_id().getYacht_type_name());
        
        yachtDto.setYacht_type_id(yachtTypeDto.getId());
        yachtDto.setYacht_type_info(yachtTypeDto);
        
        return yachtDto;
    }
    
    public List<YachtDto> allYacht() {
        return _yachtRepo.findAll().stream()
                .map(mapper -> mapToDto(mapper))
                .collect(Collectors.toList());
    }
    
    public List<YachtDto> allYachtActive() {
        return _yachtRepo.findAll().stream()
                .filter(r -> r.getIs_active() == true)
                .map(mapper -> mapToDto(mapper))
                .toList();
    }
    
    public List<YachtDto> allYachtVacancy() {
        return _yachtRepo.findAll().stream()
                .filter(r -> r.getBooking_status_id().getId() == 1)
                .map(mapper -> mapToDto(mapper))
                .collect(Collectors.toList());
    }
    
    public List<YachtDto> allYachtSortedByActive() {
        return _yachtRepo.findAll().stream()
                .sorted((p1 , p2) -> -p1.getIs_active().compareTo(p2.getIs_active()))
                .map(mapper -> mapToDto(mapper))
                .collect(Collectors.toList());
    }
    
    public List<YachtDto> allYachtActiveAndVancancy() {
        return _yachtRepo.findAll().stream()
                .filter(r -> r.getBooking_status_id().getId() == 1 && r.getIs_active() == true)
                .map(mapper -> mapToDto(mapper))
                .collect(Collectors.toList());
    }
    
    public YachtDto findYacht(int yachtId) {
        Yacht yachtInfo = _yachtRepo.findById(yachtId).orElse(null);
        return mapToDto(yachtInfo);
    }
    
    public YachtDto createNewYacht(YachtDto newYachtDto) {
        newYachtDto.setBooking_status_id(1);
        Yacht newYacht = mapToModel(newYachtDto);
        
        Yacht response = _yachtRepo.save(newYacht);
        
        return mapToDto(response);
    }
    
    public YachtDto updateYacht(YachtDto updateYachtDto) {
        Yacht updateYacht = mapToModel(updateYachtDto);
        updateYacht.setId(updateYachtDto.getId());
        
        Yacht response = _yachtRepo.save(updateYacht);
        
        return mapToDto(response);
    }
}
