package br.com.gague.imagepoc.controllers;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import br.com.gague.imagepoc.service.impl.FileConverter;
 
@ManagedBean
public class FileUploadController {
     
    private UploadedFile file;
    private FileConverter fileConverter;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            fileConverter = new FileConverter();
            fileConverter.changeType(file, false);
        }
    }  
}