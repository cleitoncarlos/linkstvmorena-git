package br.com.linkstvmorena.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.linkstvmorena.model.StatusLocal;

//@FacesConverter(forClass = StatusLocal.class)
@FacesConverter(value = "statusLocalConverter")
public class StausLocalConverter implements Converter{
		
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		if (arg2 != null && !arg2.isEmpty()) {
            return (StatusLocal) arg1.getAttributes().get(arg2);
        }
        return null;
    }

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		if(arg2 instanceof StatusLocal) {
			StatusLocal entity= (StatusLocal) arg2;
            if (entity != null && entity instanceof StatusLocal && entity.getId() != null) {
            	arg1.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }	
	}