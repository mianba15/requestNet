package com.requestnet.jsf.clases;

import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.jsf.clases.util.JsfUtil;
import com.requestnet.jsf.clases.util.JsfUtil.PersistAction;
import com.requestnet.beans.sessions.JefeinfraestructuraFacade;

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

@Named("jefeinfraestructuraController")
@SessionScoped
public class JefeinfraestructuraController implements Serializable {

    @EJB
    private com.requestnet.beans.sessions.JefeinfraestructuraFacade ejbFacade;
    private List<Jefeinfraestructura> items = null;
    private Jefeinfraestructura selected;

    public JefeinfraestructuraController() {
    }

    public Jefeinfraestructura getSelected() {
        return selected;
    }

    public void setSelected(Jefeinfraestructura selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private JefeinfraestructuraFacade getFacade() {
        return ejbFacade;
    }

    public Jefeinfraestructura prepareCreate() {
        selected = new Jefeinfraestructura();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("JefeinfraestructuraCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("JefeinfraestructuraUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("JefeinfraestructuraDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Jefeinfraestructura> getItems() {
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

    public Jefeinfraestructura getJefeinfraestructura(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Jefeinfraestructura> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Jefeinfraestructura> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Jefeinfraestructura.class)
    public static class JefeinfraestructuraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            JefeinfraestructuraController controller = (JefeinfraestructuraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "jefeinfraestructuraController");
            return controller.getJefeinfraestructura(getKey(value));
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
            if (object instanceof Jefeinfraestructura) {
                Jefeinfraestructura o = (Jefeinfraestructura) object;
                return getStringKey(o.getIdJefe());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Jefeinfraestructura.class.getName()});
                return null;
            }
        }

    }

}
