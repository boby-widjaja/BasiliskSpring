package com.basiliskSB.rest;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/salesman")
public class SalesmanRestController {
    @Qualifier("salesmanMenu")
    @Autowired
    private CrudService service;

    @GetMapping("employeeLevelDropdown")
    public ResponseEntity<Object> getEmployeeLevelDropdown(){
        try{
            var employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(employeeLevelDropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> get(
        @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String employeeNumber,
        @RequestParam(defaultValue="") String name, @RequestParam(defaultValue="") String employeeLevel,
        @RequestParam(defaultValue="") String superiorName){
        try{
            var pageCollection = service.getGrid(page, new SalesmanFilterDTO(employeeNumber, name, employeeLevel, superiorName));
            var grid = MapperHelper.getGridDTO(pageCollection, row -> new SalesmanGridDTO(row));
            var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
            return ResponseEntity.status(HttpStatus.OK).body(gridPage);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{employeeNumber}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) String employeeNumber){
        try{
            var entity = service.getUpdate(employeeNumber);
            var dto = new UpdateSalesmanDTO(entity);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertSalesmanDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                var response = service.save(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateSalesmanDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                var response = service.save(dto);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{employeeNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String employeeNumber){
        try{
            service.delete(employeeNumber);
            return ResponseEntity.status(HttpStatus.OK).body(employeeNumber);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
