package com.basiliskSB.rest;
import com.basiliskSB.dto.supplier.*;
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
@RequestMapping("/api/supplier")
public class SupplierRestController {

    @Qualifier("supplierMenu")
    @Autowired
    private CrudService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue="") String company,
                                      @RequestParam(defaultValue="") String contact,
                                      @RequestParam(defaultValue="") String jobTitle){
        try{
            var pageCollection = service.getGrid(page, new SupplierFilterDTO(company, contact, jobTitle));
            var grid = MapperHelper.getGridDTO(pageCollection, row -> new SupplierGridDTO(row));
            var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
            return ResponseEntity.status(HttpStatus.OK).body(gridPage);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        try{
            var entity = service.getUpdate(id);
            var dto = new UpsertSupplierDTO(entity);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                var respond = service.save(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(respond);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                var respond = service.save(dto);
                return ResponseEntity.status(HttpStatus.OK).body(respond);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try{
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
