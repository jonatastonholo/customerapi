package br.com.customerapi.util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * @author Jônatas Ribeiro Tonholo
 */
public abstract class StringUtils {

    /**
     * Remove the mask of cpf. Example: <code>044.123.987-55</code> will return <code>04412398755</code>
     * @param cpf The cpf with mask
     * @return the cpf without mask
     */
    public static String removeCpfMask(String cpf) {
        return cpf.replaceAll("[.-]","");
    }

    /**
     * Apply a <code>###.###.###-##</code> mask to a cpf
     * @param cpf the cpf without mask
     * @return the cpf with mask
     * @throws ParseException
     */
    public static String applyCpfMask(String cpf) throws ParseException {
        return new MaskFormatter("###.###.###-##").valueToString(cpf);
    }

    /**
     * Remove the mask of cep. Example: <code>31320-505</code> will return <code>31320505</code>
     * @param cep The cep with mask
     * @return the cep without mask
     */
    public static String removeCepMask(String cep) {
        return cep.replaceAll("[.-]","");
    }

    /**
     * Apply a <code>#####-###</code> mask to a cpf
     * @param cep the cpf without mask
     * @return the cep with mask
     * @throws ParseException
     */
    public static String applyCepMask(String cep) throws ParseException {
        return new MaskFormatter("###.###.###-##").valueToString(cep);
    }


}
