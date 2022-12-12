package com.basiliskSB.rest;
import com.basiliskSB.dto.salesman.AssignRegionDTO;
import com.basiliskSB.dto.salesman.SalesmanDetailGridDTO;
import com.basiliskSB.dto.utility.DetailGridPageDTO;
import com.basiliskSB.service.abstraction.SalesmanService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/salesmanDetail")
public class SalesmanDetailRestController {
    @Autowired
    private SalesmanService service;

    @GetMapping("regionDropdown")
    public ResponseEntity<Object> getRegionDropdown(){
        try{
            var dropdown = service.getRegionDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    private String getSalesmanFullName(Object header){
        String firstName = MapperHelper.getStringField(header, "firstName");
        String lastName = MapperHelper.getStringField(header, "lastName");
        String fullName = firstName + ((lastName != null) ? String.format(" %s", lastName) : "");
        return fullName;
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = true) String employeeNumber, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String city){
        try{
            var salesman = service.getSalesmanHeader(employeeNumber);
            class Header{
                private String employeeNumber;
                private String fullName;
                public String getEmployeeNumber() {
                    return employeeNumber;
                }
                public String getFullName() {
                    return fullName;
                }
                public Header(String employeeNumber, String fullName) {
                    this.employeeNumber = employeeNumber;
                    this.fullName = fullName;
                }
            }
            String fullName = getSalesmanFullName(salesman);
            var header = new Header(employeeNumber, fullName);
            var pageCollection = service.getRegionGridBySalesman(employeeNumber, page, city);
            var grid = MapperHelper.getGridDTO(pageCollection, row -> new SalesmanDetailGridDTO(row));
            var gridPage = new DetailGridPageDTO(grid, page, pageCollection.getTotalPages(), header);
            return ResponseEntity.status(HttpStatus.OK).body(gridPage);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody AssignRegionDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.assignRegion(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("regionId={regionId}/employeeNumber={employeeNumber}")
    public ResponseEntity<Object> deleteDetail(@PathVariable(required = true) Long regionId, @PathVariable(required = true) String employeeNumber){
        try{
            service.detachRegionSalesman(regionId, employeeNumber);
            return ResponseEntity.status(HttpStatus.OK).body("remove assignment success.");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
