package br.com.linkstvmorena.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.linkstvmorena.model.StatusPonto;

//@FacesConverter(forClass = StatusLocal.class)
@FacesConverter(value = "statusPontoConverter")
public class StausPontoConverter implements Converter{
		
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		if (arg2 != null && !arg2.isEmpty()) {
            return (StatusPonto) arg1.getAttributes().get(arg2);
        }
        return null;
    }

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		if(arg2 instanceof StatusPonto) {
			StatusPonto entity= (StatusPonto) arg2;
            if (entity != null && entity instanceof StatusPonto && entity.getId() != null) {
            	arg1.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }	
	}