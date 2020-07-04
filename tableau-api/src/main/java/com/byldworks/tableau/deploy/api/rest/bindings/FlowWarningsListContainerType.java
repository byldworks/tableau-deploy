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


/**
 * <p>Java class for flowWarningsListContainerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flowWarningsListContainerType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="connectionWarnings" type="{http://tableau.com/api}warningListType" minOccurs="0"/&gt;
 *         &lt;element name="nodeWarnings" type="{http://tableau.com/api}warningListType" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flowWarningsListContainerType", propOrder = {
    "connectionWarnings",
    "nodeWarnings"
})
public class FlowWarningsListContainerType {

    protected WarningListType connectionWarnings;
    protected WarningListType nodeWarnings;

    /**
     * Gets the value of the connectionWarnings property.
     * 
     * @return
     *     possible object is
     *     {@link WarningListType }
     *     
     */
    public WarningListType getConnectionWarnings() {
        return connectionWarnings;
    }

    /**
     * Sets the value of the connectionWarnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarningListType }
     *     
     */
    public void setConnectionWarnings(WarningListType value) {
        this.connectionWarnings = value;
    }

    /**
     * Gets the value of the nodeWarnings property.
     * 
     * @return
     *     possible object is
     *     {@link WarningListType }
     *     
     */
    public WarningListType getNodeWarnings() {
        return nodeWarnings;
    }

    /**
     * Sets the value of the nodeWarnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarningListType }
     *     
     */
    public void setNodeWarnings(WarningListType value) {
        this.nodeWarnings = value;
    }

}