//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.04 at 03:55:43 PM BST 
//


package com.byldworks.tableau.deploy.api.rest.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for listValueConceptsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listValueConceptsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="valueConcepts" type="{http://tableau.com/api}valueConceptType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="nextPageToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listValueConceptsType", propOrder = {
    "valueConcepts",
    "nextPageToken"
})
public class ListValueConceptsType {

    protected List<ValueConceptType> valueConcepts;
    protected String nextPageToken;

    /**
     * Gets the value of the valueConcepts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueConcepts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueConcepts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueConceptType }
     * 
     * 
     */
    public List<ValueConceptType> getValueConcepts() {
        if (valueConcepts == null) {
            valueConcepts = new ArrayList<ValueConceptType>();
        }
        return this.valueConcepts;
    }

    /**
     * Gets the value of the nextPageToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextPageToken() {
        return nextPageToken;
    }

    /**
     * Sets the value of the nextPageToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextPageToken(String value) {
        this.nextPageToken = value;
    }

}
