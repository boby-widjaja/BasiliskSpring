package com.basiliskSB.service.implementation;
import java.time.LocalDate;
import java.util.*;
import com.basiliskSB.entity.Order;
import com.basiliskSB.entity.OrderDetail;
import com.basiliskSB.service.abstraction.CrudDetailService;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.*;

@Scope("singleton")
@Service("orderMenu")
public class OrderServiceImplementation implements CrudService, CrudDetailService, OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("invoiceNumber"));
		var invoiceNumber = MapperHelper.getStringField(filter, "invoiceNumber");
		var customerId = MapperHelper.getLongField(filter, "customerId");
		var employeeNumber = MapperHelper.getStringField(filter, "employeeNumber");
		var deliveryId = MapperHelper.getLongField(filter, "deliveryId");
		var orderDate = MapperHelper.getLocalDateField(filter, "orderDate");
		var grid = orderRepository.findAll(invoiceNumber, customerId, employeeNumber, deliveryId, orderDate, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = orderRepository.findById(id.toString());
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		var deliveryId = MapperHelper.getLongField(dto, "deliveryId");
		var delivery = deliveryRepository.findById(deliveryId).get();
		Double deliveryCost = delivery.getCost();
		var entity = new Order(
				MapperHelper.getStringField(dto, "invoiceNumber"),
				MapperHelper.getLongField(dto, "customerId"),
				MapperHelper.getStringField(dto, "salesEmployeeNumber"),
				MapperHelper.getLocalDateField(dto, "orderDate"),
				MapperHelper.getLocalDateField(dto, "shippedDate"),
				MapperHelper.getLocalDateField(dto, "dueDate"),
				MapperHelper.getLongField(dto, "deliveryId"),
				deliveryCost,
				MapperHelper.getStringField(dto, "destinationAddress"),
				MapperHelper.getStringField(dto, "destinationCity"),
				MapperHelper.getStringField(dto, "destinationPostalCode")
			);
		var respond = orderRepository.save(entity);
		return respond;
	}

	@Override
	public Boolean delete(Object id) {
		try{
			orderRepository.deleteById(id.toString());
			return true;
		} catch (Exception exception){
			return false;
		}
	}

	@Override
	public <T> Page<Object> getGridDetail(Object headerId, Integer page, T filter) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("invoiceNumber"));
		var grid = orderDetailRepository.findAll(headerId.toString(), pagination);
		return grid;
	}


	@Override
	public Object getUpdateDetail(Object id) {
		var entity = orderDetailRepository.findById((Long)id);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public <T> Object saveDetail(Object dto) {
		var productId = MapperHelper.getLongField(dto, "productId");
		var productEntity = productRepository.findById(productId).get();
		Double unitPrice = productEntity.getPrice();
		var orderDetailEntity = new OrderDetail(
			MapperHelper.getLongField(dto, "id"),
			MapperHelper.getStringField(dto, "invoiceNumber"),
			MapperHelper.getLongField(dto, "productId"),
			unitPrice,
			MapperHelper.getIntegerField(dto, "quantity"),
			MapperHelper.getDoubleField(dto, "discount")
		);
		var respond = orderDetailRepository.save(orderDetailEntity);
		return  respond;
	}

	@Override
	public Boolean deleteDetail(Object id) {
		try{
			orderDetailRepository.deleteById((Long)id);
			return true;
		}catch (Exception exception){
			return false;
		}
	}

	@Override
	public List<Object> getCustomerDropdown() {
		var dropdowns = customerRepository.findAllOrderByCompanyName();
		return dropdowns;
	}

	@Override
	public List<Object> getSalesmanDropdown() {
		var dropdowns = salesmanRepository.findAllOrderByFirstName();
		return dropdowns;
	}

	@Override
	public List<Object> getDeliveryDropdown() {
		var dropdowns = deliveryRepository.findAllOrderByCompanyName();
		return dropdowns;
		
	}

	@Override
	public List<Object> getProductDropdown() {
		var dropdowns = productRepository.findAllOrderByName();
		return dropdowns;
	}

	@Override
	public Object getHeader(Object headerId) {
		var order = orderRepository.findById(headerId.toString()).get();
		var customerId = order.getCustomerId();
		var customer = customerRepository.findById(customerId).get();
		var salesmanEmployeeNumber = order.getSalesEmployeeNumber();
		var salesman = salesmanRepository.findById(salesmanEmployeeNumber).get();

		@AllArgsConstructor @Getter @Setter
		class Header{
			private String invoiceNumber;
			private String customer;
			private String salesman;
			private LocalDate orderDate;
		};

		var header = new Header(
			order.getInvoiceNumber(),
			customer.getCompanyName(),
			String.format("%s %s", salesman.getFirstName(), salesman.getLastName()),
			order.getOrderDate()
		);
		return header;
	}

	@Override
	public Boolean checkExistingOrder(String invoiceNumber) {
		Long totalExistingOrder = orderRepository.countByInvoiceNumber(invoiceNumber);
		return (totalExistingOrder > 0) ? true : false;
	}
}
