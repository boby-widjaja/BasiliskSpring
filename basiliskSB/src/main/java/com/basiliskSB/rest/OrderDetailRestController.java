package com.basiliskSB.rest;
import com.basiliskSB.dto.order.OrderDetailGridDTO;
import com.basiliskSB.dto.order.UpsertOrderDetailDTO;
import com.basiliskSB.dto.utility.DetailGridPageDTO;
import com.basiliskSB.service.abstraction.CrudDetailService;
import com.basiliskSB.service.abstraction.OrderService;
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
@RequestMapping("/api/orderDetail")
public class OrderDetailRestController {
    @Qualifier("orderMenu")
    @Autowired
    private CrudDetailService service;

    @Autowired
    private OrderService orderService;

    @GetMapping("productDropdown")
    public ResponseEntity<Object> getProductDropdown(){
        try{
            var dropdown = orderService.getProductDropdown();
            return ResponseEntity.status(HttpStatus.OK).body(dropdown);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getDetail(@RequestParam(required = true) String invoiceNumber, @RequestParam(defaultValue = "1") Integer page){
        try{
            var header = service.getHeader(invoiceNumber);
            var pageCollection = service.getGridDetail(invoiceNumber, page, null);
            var grid = MapperHelper.getGridDTO(pageCollection, (row) -> {
                Double unitPrice = MapperHelper.getDoubleField(row, 2);
                Integer quantity = MapperHelper.getIntegerField(row, 3);
                Double discount = MapperHelper.getDoubleField(row, 4);
                Double totalPrice = OrderService.calculateTotalPrice(unitPrice, quantity, discount);
                return new OrderDetailGridDTO(row, totalPrice);
            });
            var gridPage = new DetailGridPageDTO(grid, 1, 1, header);
            return ResponseEntity.status(HttpStatus.OK).body(gridPage);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUpdateDetail(@PathVariable(required = true) Long id){
        try{
            var entity = service.getUpdateDetail(id);
            var dto = new UpsertOrderDetailDTO(entity);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> postDetail(@Valid @RequestBody UpsertOrderDetailDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                var response = service.saveDetail(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> putDetail(@Valid @RequestBody UpsertOrderDetailDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                var response = service.saveDetail(dto);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(MapperHelper.getErrors(bindingResult.getAllErrors()));
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteDetail(@PathVariable(required = true) Long id){
        try{
            service.deleteDetail(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

}
