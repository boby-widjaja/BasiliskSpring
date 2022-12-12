package com.basiliskSB.rest;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    @Qualifier("orderMenu")
    @Autowired
    private CrudService service;

    @Autowired
    private OrderService orderService;

    @GetMapping("customerDropdown")
    public ResponseEntity<Object> getCustomerDropdown(){
        try{
            var dropdown = orderService.getCustomerDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("salesmanDropdown")
    public ResponseEntity<Object> getSalesmanDropdown(){
        try{
            var dropdown = orderService.getSalesmanDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("deliveryDropdown")
    public ResponseEntity<Object> getDeliveryDropdown(){
        try{
            var dropdown = orderService.getDeliveryDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String invoiceNumber, @RequestParam(required=false) Long customerId,
        @RequestParam(required=false) String employeeNumber, @RequestParam(required=false) Long deliveryId, @RequestParam(required=false) String orderDate){
        LocalDate formattedDate = null;
        if(orderDate != null && orderDate != "") {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDate = LocalDate.parse(orderDate, formatter);
        }
        try{
            var pageCollection = service.getGrid(page, new OrderFilterDTO(invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate));
            var grid = MapperHelper.getGridDTO(pageCollection, row -> new OrderGridDTO(row));
            var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
            return ResponseEntity.status(HttpStatus.OK).body(gridPage);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) String invoiceNumber){
        try{
            var entity = service.getUpdate(invoiceNumber);
            var dto = new UpdateOrderDTO(entity);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertOrderDTO dto, BindingResult bindingResult){
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateOrderDTO dto, BindingResult bindingResult){
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

    @DeleteMapping("/{invoiceNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String invoiceNumber){
        try{
            service.delete(invoiceNumber);
            return ResponseEntity.status(HttpStatus.OK).body(invoiceNumber);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
