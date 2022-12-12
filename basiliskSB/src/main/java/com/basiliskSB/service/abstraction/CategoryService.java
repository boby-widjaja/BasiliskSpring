package com.basiliskSB.service.abstraction;
import java.util.*;
import com.basiliskSB.dto.category.*;

public interface CategoryService {
	public Long dependentProducts(Long id);
	public Boolean checkExistingCategoryName(Long id, String name);
}
