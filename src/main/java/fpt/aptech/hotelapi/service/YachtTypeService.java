/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.hotelapi.service;

import fpt.aptech.hotelapi.dto.YachtTypeDto;
import fpt.aptech.hotelapi.models.YachtType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fpt.aptech.hotelapi.repository.YachtTypeRepository;

/**
 *
 * @author TuanNguyen
 */
@Service
public class YachtTypeService {
    private YachtTypeRepository _yachtTypeRepo;

    @Autowired
    public YachtTypeService(YachtTypeRepository _yachtTypeRepo) {
        this._yachtTypeRepo = _yachtTypeRepo;
    }
    
    private YachtTypeDto mapToDto(YachtType yachtType) {
        YachtTypeDto yachtTypeDto = new YachtTypeDto();
        yachtTypeDto.setId(yachtType.getId());
        yachtTypeDto.setYacht_type_name(yachtType.getYacht_type_name());
        return yachtTypeDto;
    }
    
    public List<YachtTypeDto> allYachtType() {
        return _yachtTypeRepo.findAll()
                .stream()
                .map(mapper -> mapToDto(mapper))
                .toList();
    }
    
}
