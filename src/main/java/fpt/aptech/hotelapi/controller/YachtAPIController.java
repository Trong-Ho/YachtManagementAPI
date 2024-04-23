/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.hotelapi.controller;

import fpt.aptech.hotelapi.dto.YachtDto;
import fpt.aptech.hotelapi.service.YachtService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/yachtcontroller")
public class YachtAPIController {
    private YachtService _yachtService;

    @Autowired
    public YachtAPIController(YachtService _yachtService) {
        this._yachtService = _yachtService;
    }
    
    @GetMapping("/all")
    public List<YachtDto> function_allYacht() {
        return _yachtService.allYacht();
    }
    
    @GetMapping("/allyachtactive")
    public List<YachtDto> function_allYachtActive() {
        return _yachtService.allYachtActive();
    }
    
    @GetMapping("/allyachtvacancy")
    public List<YachtDto> function_allYachtVacancy() {
        return _yachtService.allYachtVacancy();
    }
    
    @GetMapping("/allyachtsortedbyactive")
    public List<YachtDto> function_allYachtSortedByActive() {
        return _yachtService.allYachtSortedByActive();
    }
    
    @GetMapping("/allyachtactiveandvacancy")
    public List<YachtDto> function_allYachtActiveAndVacancy() {
        return _yachtService.allYachtActiveAndVancancy();
    }
    
    @GetMapping("/find/{yachtId}")
    public YachtDto function_findYacht(@PathVariable("yachtId") int yachtId) {
        return _yachtService.findYacht(yachtId);
    }
    
    @PostMapping("/create")
    public YachtDto function_createNewYacht(@RequestBody YachtDto newYachtDto) {
        return _yachtService.createNewYacht(newYachtDto);
    }
    
    @PutMapping("/update")
    public YachtDto function_updateYacht(@RequestBody YachtDto updateYachtDto) {
        return _yachtService.updateYacht(updateYachtDto);
    }
}
