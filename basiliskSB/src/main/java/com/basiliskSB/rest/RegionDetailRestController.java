package com.basiliskSB.rest;
import com.basiliskSB.dto.region.AssignSalesmanDTO;
import com.basiliskSB.dto.region.RegionDetailGridDTO;
import com.basiliskSB.dto.utility.DetailGridPageDTO;
import com.basiliskSB.service.abstraction.RegionService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/regionDetail")
public class RegionDetailRestController {
    @Autowired
    private RegionService service;

    @Autowired
    private RegionService regionService;

    @GetMapping("salesmanDropdown")
    public ResponseEntity<Object> getSalesmanDropdown(){
        try{
            var dropdown = regionService.getSalesmanDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> get(
        @RequestParam(required = true) Long id, @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue="") String employeeNumber, @RequestParam(defaultValue="") String name,
        @RequestParam(defaultValue="") String employeeLevel, @RequestParam(defaultValue="") String superiorName){
        try{
            var header = service.getRegionHeader(id);
            var pageCollection = service.getSalesmanGridByRegion(id, page, employeeNumber, name, employeeLevel, superiorName);
            var grid = MapperHelper.getGridDTO(pageCollection, row -> new RegionDetailGridDTO(row));
            var gridPage = new DetailGridPageDTO(grid, page, pageCollection.getTotalPages(), header);
            return ResponseEntity.status(HttpStatus.OK).body(gridPage);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody AssignSalesmanDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                var response = service.assignSalesman(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("regionId={regionId}/employeeNumber={employeeNumber}")
    public ResponseEntity<Object> delete(
        @PathVariable(required = true) Long regionId,
        @PathVariable(required = true) String employeeNumber){
        try{
            service.detachRegionSalesman(regionId, employeeNumber);
            return ResponseEntity.status(HttpStatus.OK).body("remove assignment success.");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}