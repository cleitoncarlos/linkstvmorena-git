package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DualListModel;
import org.springframework.stereotype.Controller;
@Controller
public class CategoriaLocalController {
	
	private DualListModel<String> cities;
	public CategoriaLocalController() {
	List<String> source = new ArrayList<String>();
	List<String> target = new ArrayList<String>();
	source.add("Rua");
	source.add("Praca");
	source.add("Avenida");
	source.add("UBS");
	source.add("UPA");
	source.add("Rua");
	source.add("Praca");
	source.add("Avenida");
	source.add("UBS");
	source.add("UPA");
	//more cities
	cities = new DualListModel<String>(source, target);
	}
	public DualListModel<String> getCities() {
	return cities;
	}
	public void setCities(DualListModel<String> cities) {
	this.cities = cities;
	}
}
