package com.basiliskSB.rest;
import com.basiliskSB.dto.category.CategoryFilterDTO;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.basiliskSB.dto.category.CategoryGridDTO;
import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

	@Qualifier("categoryMenu")
	@Autowired
	private CrudService service;

	@GetMapping
	public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name){
		try{
			var pageCollection = service.getGrid(page, new CategoryFilterDTO(name));
			var grid = MapperHelper.getGridDTO(pageCollection, row -> new CategoryGridDTO(row));
			var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
		try{
			var entity = service.getUpdate(id);
			var dto = new UpsertCategoryDTO(entity);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}
	}

	@PostMapping
	public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
		try{
			if(!bindingResult.hasErrors()){
				dto.setId(0l);
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
	public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
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
