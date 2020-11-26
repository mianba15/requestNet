package com.requestnet.jsf.clases;

import com.requestnet.entidades.IntegranteColaborador;
import com.requestnet.jsf.clases.util.JsfUtil;
import com.requestnet.jsf.clases.util.JsfUtil.PersistAction;
import com.requestnet.beans.sessions.IntegranteColaboradorFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("integranteColaboradorController")
@SessionScoped
public class IntegranteColaboradorController implements Serializable {

    @EJB
    private com.requestnet.beans.sessions.IntegranteColaboradorFacade ejbFacade;
    private List<IntegranteColaborador> items = null;
    private IntegranteColaborador selected;

    public IntegranteColaboradorController() {
    }

    public IntegranteColaborador getSelected() {
        return selected;
    }

    public void setSelected(IntegranteColaborador selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private IntegranteColaboradorFacade getFacade() {
        return ejbFacade;
    }

    public IntegranteColaborador prepareCreate() {
        selected = new IntegranteColaborador();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("IntegranteColaboradorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("IntegranteColaboradorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("IntegranteColaboradorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<IntegranteColaborador> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public IntegranteColaborador getIntegranteColaborador(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<IntegranteColaborador> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<IntegranteColaborador> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = IntegranteColaborador.class)
    public static class IntegranteColaboradorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IntegranteColaboradorController controller = (IntegranteColaboradorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "integranteColaboradorController");
            return controller.getIntegranteColaborador(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof IntegranteColaborador) {
                IntegranteColaborador o = (IntegranteColaborador) object;
                return getStringKey(o.getIdCliente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IntegranteColaborador.class.getName()});
                return null;
            }
        }

    }

}
