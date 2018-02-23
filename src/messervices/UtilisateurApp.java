
package messervices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for utilisateurApp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="utilisateurApp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listUtilisateur" type="{http://messervices/}utilisateurApp" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="motDePass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="peusdo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prenom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "utilisateurApp", propOrder = {
    "cni",
    "listUtilisateur",
    "motDePass",
    "nom",
    "peusdo",
    "prenom"
})
public class UtilisateurApp {

    protected String cni;
    @XmlElement(nillable = true)
    protected List<UtilisateurApp> listUtilisateur;
    protected String motDePass;
    protected String nom;
    protected String peusdo;
    protected String prenom;

    /**
     * Gets the value of the cni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCni() {
        return cni;
    }

    /**
     * Sets the value of the cni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCni(String value) {
        this.cni = value;
    }

    /**
     * Gets the value of the listUtilisateur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listUtilisateur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListUtilisateur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UtilisateurApp }
     * 
     * 
     */
    public List<UtilisateurApp> getListUtilisateur() {
        if (listUtilisateur == null) {
            listUtilisateur = new ArrayList<UtilisateurApp>();
        }
        return this.listUtilisateur;
    }

    /**
     * Gets the value of the motDePass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotDePass() {
        return motDePass;
    }

    /**
     * Sets the value of the motDePass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotDePass(String value) {
        this.motDePass = value;
    }

    /**
     * Gets the value of the nom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets the value of the nom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNom(String value) {
        this.nom = value;
    }

    /**
     * Gets the value of the peusdo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeusdo() {
        return peusdo;
    }

    /**
     * Sets the value of the peusdo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeusdo(String value) {
        this.peusdo = value;
    }

    /**
     * Gets the value of the prenom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Sets the value of the prenom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenom(String value) {
        this.prenom = value;
    }

}
