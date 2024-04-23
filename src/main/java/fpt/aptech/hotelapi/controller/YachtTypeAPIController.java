/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.hotelapi.controller;

import fpt.aptech.hotelapi.dto.YachtTypeDto;
import fpt.aptech.hotelapi.service.YachtTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author TuanNguyen
 */
@RestController
@RequestMapping("/api/yachttypecontroller")
public class YachtTypeAPIController {
    private YachtTypeService _yachtTypeService;

    @Autowired
    public YachtTypeAPIController(YachtTypeService _yachtTypeService) {
        this._yachtTypeService = _yachtTypeService;
    }
    
    @GetMapping("/all")
    public List<YachtTypeDto> function_allYachtType() {
        return _yachtTypeService.allYachtType();
    }
}
