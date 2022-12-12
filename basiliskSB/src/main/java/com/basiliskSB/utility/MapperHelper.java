package com.basiliskSB.utility;

import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.dto.utility.ErrorDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapperHelper {

    private static Object getFieldValue(Object object, Integer index){
        return ((Object[])object)[index];
    }

    private static <T> Object getFieldValue(Object object, String fieldName){
        try{
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            var value = field.get(object);
            return value;
        }catch (Exception exception){
        }
        return null;
    }

    public static Boolean getBooleanField(Object object, Integer index){
        try{
            return (Boolean) ((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Boolean getBooleanField(T object, String fieldName){
        try{
            return (Boolean) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static Integer getIntegerField(Object object, Integer index){
        try{
            return (Integer)((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Integer getIntegerField(T object, String fieldName){
        try{
            return (Integer) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static Long getLongField(Object object, Integer index){
        try{
            return (Long)((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Long getLongField(T object, String fieldName){
        try{
            return (Long) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static Double getDoubleField(Object object, Integer index){
        try{
            return (Double)((Object[])object)[index];
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> Double getDoubleField(T object, String fieldName){
        try{
            return (Double) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> String getStringField(T object, String fieldName){
        try{
            return getFieldValue(object, fieldName).toString();
        }catch (Exception exception){
            return null;
        }
    }

    public static String getStringField(Object object, Integer index){
        try{
            return ((Object[])object)[index].toString();
        }catch (Exception exception){
            return null;
        }
    }

    public static <T> LocalDate getLocalDateField(T object, String fieldName){
        try{
            var stringValue = getStringField(object, fieldName);
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var date = LocalDate.parse(stringValue, formatter);
            return date;
        }catch (Exception exception){
            return null;
        }
    }

    public static LocalDate getLocalDateField(Object object, Integer index){
        try{
            var stringValue = getStringField(object, index);
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var date = LocalDate.parse(stringValue, formatter);
            return date;
        }catch (Exception exception){
            return null;
        }
    }

    public static void setGridViewModel(List<Object> grid, Integer page, Integer totalPages, String breadCrumbs, Model model){
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("breadCrumbs", breadCrumbs);
    }

    public static void setDetailGridViewModel(Object header, List<Object> grid, Integer page, Integer totalPages, String breadCrumbs, Model model){
        setGridViewModel(grid, page, totalPages, breadCrumbs, model);
        model.addAttribute("header", header);
    }

    public static void setInsertViewModel(Object dto, String menu, Model model){
        model.addAttribute("dto", dto);
        model.addAttribute("type", "insert");
        model.addAttribute("breadCrumbs", String.format("%s Index / Insert %s", menu, menu));
    }

    public static void setInsertViewModel(Object dto, String menu, String breadCrumbs, Model model){
        model.addAttribute("dto", dto);
        model.addAttribute("type", "insert");
        model.addAttribute("breadCrumbs", String.format("%s Index / %s", menu, breadCrumbs));
    }

    public static void setUpdateViewModel(Object dto, String menu, Model model){
        model.addAttribute("dto", dto);
        model.addAttribute("type", "update");
        model.addAttribute("breadCrumbs", String.format("%s Index / Update %s", menu, menu));
    }

    public static void setUpdateViewModel(Object dto, String menu, String breadCrumbs, Model model){
        model.addAttribute("dto", dto);
        model.addAttribute("type", "update");
        model.addAttribute("breadCrumbs", String.format("%s Index / %s", menu, breadCrumbs));
    }

    public static List<Object> getGridDTO(Page<Object> pageCollection, RowHandler handler){
        Stream<Object> gridStream = pageCollection.toList().stream();
        return gridStream.map(row -> {
            return handler.getRow(row);
        }).collect(Collectors.toList());
    }

    private static List<DropdownDTO> getDropdownDTO(List<Object> dropdownData){
        Stream<Object> dropdownStream = dropdownData.stream();
        var dropdown = dropdownStream.map(row -> {
           return new DropdownDTO(
               getFieldValue(row, 0),
               getStringField(row, 1)
           );
        }).collect(Collectors.toList());
        return dropdown;
    }

    public static void setDropdownViewModel(List<Object> dropdownData, String attributeName, Model model){
        var dropdown = getDropdownDTO(dropdownData);
        model.addAttribute(attributeName, dropdown);
    }

    public static List<ErrorDTO> getErrors(List<ObjectError> errors){
        var dto = new ArrayList<ErrorDTO>();
        for(var error : errors){
            var fieldName = getStringField(error.getArguments()[0], "defaultMessage");
            fieldName = (fieldName.equals("")) ? "object" : fieldName;
            dto.add(new ErrorDTO(fieldName, error.getDefaultMessage()));
        }
        return dto;
    }
}
