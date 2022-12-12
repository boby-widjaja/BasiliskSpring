package com.basiliskSB.rest;
import com.basiliskSB.dto.product.*;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.ProductService;
import com.basiliskSB.utility.FileHelper;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
public class ProductRestController {
    @Qualifier("productMenu")
    @Autowired
    private CrudService service;

    @Autowired
    private ProductService productService;

    @GetMapping("/categoryDropdown")
    public ResponseEntity<Object> getCategoryDropdown(){
        try{
            var dropdown = productService.getCategoryDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/supplierDropdown")
    public ResponseEntity<Object> getSupplierDropdown(){
        try{
            var dropdown = productService.getSupplierDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name, @RequestParam(required = false) Long categoryId,
                                      @RequestParam(required = false) Long supplierId){
        try{
            var pageCollection = service.getGrid(page, new ProductFilterDTO(name, categoryId, supplierId));
            var grid = MapperHelper.getGridDTO(pageCollection, row -> new ProductGridDTO(row));
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
            var dto = new UpsertProductDTO(entity);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> post(@Valid @RequestPart UpsertProductDTO dto, @RequestPart MultipartFile image, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                try{
                    String imagePath = null;
                    if(!image.isEmpty()){
                        imagePath = FileHelper.uploadProductPhoto(null, image);
                    }
                    dto.setImagePath(imagePath);
                    var respond = service.save(dto);
                    return ResponseEntity.status(HttpStatus.CREATED).body(respond);
                } catch (Exception exception){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping(value="/alternate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> postAlternate(@Valid UpsertProductDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                try{
                    String imagePath = null;
                    if(dto.getImage() != null && !dto.getImage().isEmpty()){
                        imagePath = FileHelper.uploadProductPhoto(null, dto.getImage());
                    }
                    dto.setImagePath(imagePath);
                    var respond = service.save(dto);
                    return ResponseEntity.status(HttpStatus.CREATED).body(respond);
                } catch (Exception exception){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> put(@Valid @RequestPart UpsertProductDTO dto, @RequestPart MultipartFile image, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                try{
                    if(!image.isEmpty()){
                        FileHelper.uploadProductPhoto(dto.getImagePath(), image);
                    }
                    service.save(dto);
                    return ResponseEntity.status(HttpStatus.OK).body(dto);
                } catch (Exception exception){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
                }
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
            String imagePath = productService.getImagePath(id);
            FileHelper.deleteProductPhoto(imagePath);
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
