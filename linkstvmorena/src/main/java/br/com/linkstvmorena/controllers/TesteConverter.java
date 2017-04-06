package br.com.linkstvmorena.controllers;

import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import br.com.linkstvmorena.dao.StatusLocalDAO;
import br.com.linkstvmorena.model.StatusLocal;

@FacesConverter(value="testeverter")
public class TesteConverter  implements Converter {
	@Autowired
	@Qualifier("statusLocalDAO")
	private static StatusLocalDAO statuslocalDAO;
	StatusLocal bucaPorId = new StatusLocal();
	
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
    	 if(value != null) {  
             	try {// System.out.println("Buscar ID1: "+ value);
            	 	 bucaPorId = statuslocalDAO.bucaPorId(Integer.parseInt(value));
            	 	 System.out.println("Buscar ID2: "+ bucaPorId);
            	 	 return bucaPorId;
             	} catch (Exception ex) {
                 return ex;
             	}
    	 }
		return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {

       /* if (value != null && !"".equals(value)) {
        	
        	System.out.println("getAsSring Value: "+ value);

        	StatusLocal entity = (StatusLocal) value;
        	System.out.println("getAsSring entity: "+ entity);

            this.addAttribute(component, entity);
            System.out.println("Componente: "+ component);
            Integer codigo = entity.getId();

            if (codigo != null) {
                return String.valueOf(codigo);
            }
        }*/

    	String str = "";
	    if (value instanceof StatusLocal) {
	        str = "" + ((StatusLocal) value).getId();
	    }
	    return str;
	}
    	
    	
        //return (String) value;
   // }

    /*protected void addAttribute(UIComponent component, Object entity) {
        String key =  ((SelectOneMenu) entity).getId().toString(); // codigo da empresa como chave neste caso
        this.getAttributesFrom(component).put(key, entity);
    }*/
    
    protected void addAttribute(UIComponent component, StatusLocal entity) {
    	 System.out.println("Componente: "+ component);
    	 System.out.println("\nStatusLocal: "+ entity);
        String key = ""+((StatusLocal) entity).getId();
        this.getAttributesFrom(component).put(key, entity);
    }
    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}