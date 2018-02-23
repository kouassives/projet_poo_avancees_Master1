
package messervices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MonService", targetNamespace = "http://messervices/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MonService {


    /**
     * 
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(name = "un-getTva", partName = "un-getTva")
    @Action(input = "http://messervices/MonService/getTvaRequest", output = "http://messervices/MonService/getTvaResponse")
    public double getTva();

    /**
     * 
     * @param nom
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "un-salut", partName = "un-salut")
    @Action(input = "http://messervices/MonService/salutRequest", output = "http://messervices/MonService/salutResponse")
    public String salut(
        @WebParam(name = "nom", partName = "nom")
        String nom);

    /**
     * 
     * @return
     *     returns messervices.ArrayList
     */
    @WebMethod
    @WebResult(name = "un-listUtilisateur", partName = "un-listUtilisateur")
    @Action(input = "http://messervices/MonService/listUtilisateurRequest", output = "http://messervices/MonService/listUtilisateurResponse")
    public ArrayList listUtilisateur();

}
