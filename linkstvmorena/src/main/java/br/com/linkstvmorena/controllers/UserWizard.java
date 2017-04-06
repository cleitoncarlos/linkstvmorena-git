package br.com.linkstvmorena.controllers;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.event.FlowEvent;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.User;

public class UserWizard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StatusLocalController st;
    private User user;
    
    private boolean skip;
    private int step;
     
    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
     
    public void save() {        
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFirstname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
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

	public String onFlowProcess(FlowEvent event) {
		LocalController lc = new LocalController();
		
		System.out.println("Wizard: "+lc.getLocal());
		
		if(skip) {
        	
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else { 
        	
            return event.getNewStep();
        }
    }
//    public String handleFlow(FlowEvent event) {
//    	String currentStepId = event.getCurrentStep();
//    	String stepToGo = event.getNextStep();
//    	if(skip)
//    	return "confirm";
//    	else
//    	return event.getNextStep();
//    	}
}
