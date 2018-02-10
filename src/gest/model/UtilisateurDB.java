package gest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * 
 * @author Marco Jakob
 */
@XmlRootElement(name = "users")
public class UtilisateurDB {

    private List<Parametres> users;

    @XmlElement(name = "user")
    public List<Parametres> getPersons() {
        return users;
    }

    public void setPersons(List<Parametres> users) {
        this.users = users;
    }

}
