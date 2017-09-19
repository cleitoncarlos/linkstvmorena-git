package br.com.linkstvmorena.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cleiton.Consulta;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.linkstvmorena.model.Categoria;
import br.com.linkstvmorena.model.Contato;
import br.com.linkstvmorena.model.Foto;
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.model.Ponto;
import br.com.linkstvmorena.model.Status;
import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.Servico;

@Controller
@SessionScoped
public class Controlador {

	@Autowired
	private Servico servico;

	private Local local;
	private Local selectedLocal;
	private Ponto ponto;
	private Categoria categoria;
	private Contato contato;
	private StatusLocal statuslocal;
	private StatusPonto statusPonto;
	private List<Local> listadelocais;
	private List<Status> listadestatus;
	private List<Ponto> listaponto;
	private List<Categoria> listCategorias;
	private Set<Contato> listadecontato;
	private List<StatusLocal> liststatuslocal;
	private List<StatusPonto> liststatusponto;
	private Set<Ponto> listagemdeponto;
	private DualListModel<Categoria> listcategorias;
	private List<Local> listalocal;
	private List<Categoria> fonte;
	private List<Categoria> alvo;
	private String busca;
	private boolean painel;
	private List<Local> listSearch;

	private Foto foto;
	private List<Foto> fotos;

	@PostConstruct
	public void init() {

		try {
			ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();

			 fotos = servico.buscaFotos();

			File folder = new File(sContext.getRealPath("/temp"));
			if (!folder.exists())
				folder.mkdirs();

			for (Foto f : fotos) {
				String nomeArquivo = f.getId() + ".jpg";
				String arquivo = sContext.getRealPath("/temp") + File.separator + nomeArquivo;

				criaArquivo(f.getFoto(), arquivo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		painel = false;
		listSearch = new ArrayList<Local>();
		local = new Local();
		contato = new Contato();
		ponto = new Ponto();
		categoria = new Categoria();
		statuslocal = new StatusLocal();
		statusPonto = new StatusPonto();
		listadelocais = new ArrayList<Local>();
		listadecontato = new HashSet<Contato>();
		liststatusponto = new ArrayList<StatusPonto>();
		listaponto = new ArrayList<Ponto>();
		listagemdeponto = new HashSet<>();
		liststatuslocal = new ArrayList<>();
		listCategorias = new ArrayList<>();
		listalocal = new ArrayList<Local>();

		listadestatus = new ArrayList<Status>();
		for (Status lc : Status.values()) {
			listadestatus.add(lc);
		}

		try {
			listadelocais = servico.buscarLocal(Status.ATIVO);
			liststatuslocal = servico.buscarStatusLocal();
			liststatusponto = servico.buscarStatusPonto();
			listCategorias = servico.buscarCategorias();
			fonte = new ArrayList<Categoria>();
			fonte = servico.buscarCategorias();
			alvo = new ArrayList<Categoria>();
			listcategorias = new DualListModel<Categoria>(fonte, alvo);

		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}
	public List<Foto> listaImagens() {
		fotos = new ArrayList<>();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

		fotos = servico.buscaFotos();

		if (!fotos.isEmpty()) {
			for (Foto f : fotos) {

				String arquivo = scontext.getRealPath("/img/" + f.getDescricao());
				byte[] conteudo = f.getFoto();
				criaArquivo(conteudo, arquivo);
			}
		}
		return fotos;
	}
	/*public List<String> getImages()  {
        
		System.out.println("Mostrar Fotos!");
		
		 fotos = servico.buscaFotos();
        List<String> images = new ArrayList<String>();
        
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/temp");
        
        for (Foto foto : fotos) {
        	try{
              FileOutputStream fos = new FileOutputStream(path + "/" + foto.getDescricao() + ".jpg");
              fos.write(foto.getFoto());
              fos.flush();
              fos.close();
              images.add(foto.getDescricao() + ".jpg");
        	}catch (Exception e) {
        		MenssagemUtil.mensagemErro("Errro ao abrir Foto!");
			}
        }
        
        return images;
   }*/
	
	private void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;

		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);

			fos.flush();
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	
	
	public void handleFileUpload(FileUploadEvent event) {

		foto = new Foto();

		foto.setDescricao(event.getFile().getFileName());
		foto.setFoto(event.getFile().getContents());

		servico.salvarFoto(foto);

		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " Salvo com Sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void selecaoLocal(Local l) {
		selectedLocal = new Local();
		selectedLocal = l;
	}

	public List<Local> search() {
		listSearch = new ArrayList<Local>();

		try {
			if (busca != "") {
				painel = true;
				listSearch = servico.buscaLocalTela(busca);
				if (listSearch.isEmpty()) {
					MenssagemUtil.mensagemInfo("Local nāo encontrado!!");
				}
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping("/home") // Define a url que quando for requisitada chamara
								// o metodo
	public String inicio() {
		// Retorna a view que deve ser chamada, no caso home (home.jsp) aqui o
		// .jsp é omitido
		return "home";
	}

	public String home() {
		return "home";
	}

	public String salvar() {
		try {
			local.setDataCadastro(Calendar.getInstance());
			servico.salvar(local);
			init();
			System.out.println("Ok!");
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
			return "localteste";
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
		return "";
	}

	public Local editarLocal(Local l) {

		// init();

		System.out.println("Entrou Editar Local!");

		List<Local> buscarPorId = servico.buscarPorId(l.getId());

		if (!buscarPorId.isEmpty()) {

			System.out.println("Editar Local!");

			for (Local ls : buscarPorId) {
				this.local = ls;
				this.listagemdeponto = ls.getPonto();
				this.listadecontato = ls.getContato();
				this.statuslocal = ls.getStatuslocal();
				Iterator<Categoria> it = ls.getCategoria().iterator();
				while (it.hasNext()) {
					Categoria posicao = it.next();
					this.fonte.remove(posicao);
					this.alvo.add(posicao);
				}

			}
			return this.local;
		} else
			return this.local;
	}

	public void remover(Local l) {
		try {
			l.setStatus(Status.INATIVO);
			servico.salvar(l);
			// servico.remover(l);
			init();
			MenssagemUtil.mensagemInfo("Excluido com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	public Ponto editarPonto(Ponto p) {
		this.listagemdeponto.remove(p);
		this.ponto = p;
		this.statusPonto = p.getStatusponto();
		return this.ponto;
	}

	public void buscarCep() {
		Consulta consulta = new Consulta();
		EnderecoERP buscaCep = consulta.buscaCep(this.local.getCep());

		this.local.setBairro(buscaCep.getBairro());
		this.local.setLogradouro(buscaCep.getEnd());
		this.local.setComplemento(buscaCep.getComplemento());

		System.out.println("Endereço: " + buscaCep.getEnd() + " Bairro: " + buscaCep.getBairro() + "Complemento: "
				+ buscaCep.getComplemento());
	}

	public Ponto excluirPonto(Ponto c) {
		this.listagemdeponto.remove(c);
		return this.ponto;
	}

	public Contato editarContato(Contato c) {
		this.contato = c;
		return this.contato;
	}

	public Contato excluirContato(Contato c) {
		this.listadecontato.remove(c);
		return this.contato;
	}

	public void adicionaCategoria() {
		this.local.adicionaCategorias(listcategorias.getTarget());
	}

	public void adicionaContato() {
		listadecontato.add(contato);
		local.adicionaContatos(listadecontato);
		contato = new Contato();
	}

	public void salvarCategoria() {
		try {
			servico.salvarCategoria(categoria);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	public void carregaCategoria() {
		System.out.println("Entrou Carrega Categoria!!");
		List<Categoria> fonte = servico.buscarCategorias();
		List<Categoria> alvo = new ArrayList<Categoria>();
		this.listcategorias = new DualListModel<Categoria>(fonte, alvo);
		System.out.println("Lista de categorias: " + listcategorias);
	}

	public void adicionaPonto() {
		listagemdeponto.add(ponto);
		local.adicionaPonto(listagemdeponto);
		/*
		 * this.local.setPonto(listagemdeponto); this.ponto.setLocal(local);
		 */
		this.ponto = new Ponto();
		this.statusPonto = new StatusPonto();
	}

	public void adicionaStatusPonto() {
		this.ponto.setStatusponto(statusPonto);
	}

	public void adicionaStatusLocal() {
		this.local.setStatuslocal(this.statuslocal);
	}

	public String onFlowProcess(FlowEvent event) {

		/*
		 * if(this.statuslocal.getNome().equals("Não Fecha Link")){ skip =
		 * false; // reset in case user goes back init(); return "confirm"; }
		 * else { return event.getNewStep(); }
		 */

		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public List<Local> getListSearch() {
		return listSearch;
	}

	public void setListSearch(List<Local> listSearch) {
		this.listSearch = listSearch;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<Local> getListadelocais() {
		return listadelocais;
	}

	public void setListadelocais(List<Local> listadelocais) {
		this.listadelocais = listadelocais;
	}

	public Set<Contato> getListadecontato() {
		return listadecontato;
	}

	public void setListadecontato(Set<Contato> listadecontato) {
		this.listadecontato = listadecontato;
	}

	public List<Status> getListadestatus() {
		return listadestatus;
	}

	public void setListadestatus(List<Status> listadestatus) {
		this.listadestatus = listadestatus;
	}

	public Set<Ponto> getListagemdeponto() {
		return listagemdeponto;
	}

	public void setListagemdeponto(Set<Ponto> listagemdeponto) {
		this.listagemdeponto = listagemdeponto;
	}

	public StatusPonto getStatusPonto() {
		return statusPonto;
	}

	public void setStatusPonto(StatusPonto statusPonto) {
		this.statusPonto = statusPonto;
	}

	public List<StatusPonto> getListstatusponto() {
		return liststatusponto;
	}

	public void setListstatusponto(List<StatusPonto> liststatusponto) {
		this.liststatusponto = liststatusponto;
	}

	public List<StatusLocal> getListstatuslocal() {
		return liststatuslocal;
	}

	public void setListstatuslocal(List<StatusLocal> liststatuslocal) {
		this.liststatuslocal = liststatuslocal;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public StatusLocal getStatuslocal() {
		return statuslocal;
	}

	public void setStatuslocal(StatusLocal statuslocal) {
		this.statuslocal = statuslocal;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public DualListModel<Categoria> getListcategorias() {
		return listcategorias;
	}

	public void setListcategorias(DualListModel<Categoria> listcategorias) {
		this.listcategorias = listcategorias;
	}

	public List<Ponto> getListaponto() {
		return listaponto;
	}

	public void setListaponto(List<Ponto> listaponto) {
		this.listaponto = listaponto;
	}

	public Local getSelectedLocal() {
		return selectedLocal;
	}

	public void setSelectedLocal(Local selectedLocal) {
		this.selectedLocal = selectedLocal;
	}

	public List<Local> getListalocal() {
		return listalocal;
	}

	public void setListalocal(List<Local> listalocal) {
		this.listalocal = listalocal;
	}

	public void onTransfer(TransferEvent event) {
		/*
		 * StringBuilder builder = new StringBuilder(); for (Object item :
		 * event.getItems()) { builder.append(((Categoria)
		 * item).getNome()).append("<br />"); }
		 */
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		// msg.setSummary("Items Transferred");
		msg.setDetail(event.getItems().toString());
		if (event.isAdd()) {
			msg.setSummary("Items Transferred");
		} else {
			msg.setSummary("Removidos");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	}

	public boolean isPainel() {
		return painel;
	}

	public void setPainel(boolean painel) {
		this.painel = painel;
	}

	private boolean skip;
	private int step;

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	
	

}