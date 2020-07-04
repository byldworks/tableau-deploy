//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.04 at 03:55:43 PM BST 
//


package com.byldworks.tableau.deploy.api.rest.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serverInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serverInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="productVersion" type="{http://tableau.com/api}productVersion"/&gt;
 *         &lt;element name="prepConductorVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="restApiVersion" type="{http://tableau.com/api}restApiVersion"/&gt;
 *         &lt;element name="platform" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="serverSettings" type="{http://tableau.com/api}serverSettings"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serverInfo", propOrder = {
    "productVersion",
    "prepConductorVersion",
    "restApiVersion",
    "platform",
    "serverSettings"
})
public class ServerInfo {

    @XmlElement(required = true)
    protected ProductVersion productVersion;
    @XmlElement(required = true)
    protected String prepConductorVersion;
    @XmlElement(required = true)
    protected String restApiVersion;
    @XmlElement(required = true)
    protected String platform;
    @XmlElement(required = true)
    protected ServerSettings serverSettings;

    /**
     * Gets the value of the productVersion property.
     * 
     * @return
     *     possible object is
     *     {@link ProductVersion }
     *     
     */
    public ProductVersion getProductVersion() {
        return productVersion;
    }

    /**
     * Sets the value of the productVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductVersion }
     *     
     */
    public void setProductVersion(ProductVersion value) {
        this.productVersion = value;
    }

    /**
     * Gets the value of the prepConductorVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepConductorVersion() {
        return prepConductorVersion;
    }

    /**
     * Sets the value of the prepConductorVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepConductorVersion(String value) {
        this.prepConductorVersion = value;
    }

    /**
     * Gets the value of the restApiVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestApiVersion() {
        return restApiVersion;
    }

    /**
     * Sets the value of the restApiVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestApiVersion(String value) {
        this.restApiVersion = value;
    }

    /**
     * Gets the value of the platform property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the value of the platform property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatform(String value) {
        this.platform = value;
    }

    /**
     * Gets the value of the serverSettings property.
     * 
     * @return
     *     possible object is
     *     {@link ServerSettings }
     *     
     */
    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    /**
     * Sets the value of the serverSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerSettings }
     *     
     */
    public void setServerSettings(ServerSettings value) {
        this.serverSettings = value;
    }

}