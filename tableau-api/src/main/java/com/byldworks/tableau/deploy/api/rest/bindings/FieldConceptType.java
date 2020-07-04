//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.04 at 03:55:43 PM BST 
//


package com.byldworks.tableau.deploy.api.rest.bindings;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for fieldConceptType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fieldConceptType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="objectConceptURI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="names" type="{http://tableau.com/api}nameType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="nameCharacteristics" type="{http://tableau.com/api}nameCharacteristicsType" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parentFieldConceptURI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataTypes" type="{http://tableau.com/api}dataTypeType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="fieldRoles" type="{http://tableau.com/api}fieldRoleType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="defaultFormats" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="valueCharacteristics" type="{http://tableau.com/api}valueCharacteristicsType" minOccurs="0"/&gt;
 *         &lt;element name="ownerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="createdAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="updatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="valueSource" type="{http://tableau.com/api}valueSourceType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fieldConceptType", propOrder = {
    "uri",
    "objectConceptURI",
    "names",
    "nameCharacteristics",
    "description",
    "parentFieldConceptURI",
    "dataTypes",
    "fieldRoles",
    "defaultFormats",
    "valueCharacteristics",
    "ownerID",
    "createdAt",
    "updatedAt",
    "valueSource"
})
public class FieldConceptType {

    @XmlElement(required = true)
    protected String uri;
    @XmlElement(required = true)
    protected String objectConceptURI;
    protected List<NameType> names;
    protected NameCharacteristicsType nameCharacteristics;
    protected String description;
    protected String parentFieldConceptURI;
    @XmlSchemaType(name = "string")
    protected List<DataTypeType> dataTypes;
    @XmlSchemaType(name = "string")
    protected List<FieldRoleType> fieldRoles;
    protected List<String> defaultFormats;
    protected ValueCharacteristicsType valueCharacteristics;
    protected String ownerID;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updatedAt;
    protected ValueSourceType valueSource;

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the objectConceptURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectConceptURI() {
        return objectConceptURI;
    }

    /**
     * Sets the value of the objectConceptURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectConceptURI(String value) {
        this.objectConceptURI = value;
    }

    /**
     * Gets the value of the names property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the names property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameType }
     * 
     * 
     */
    public List<NameType> getNames() {
        if (names == null) {
            names = new ArrayList<NameType>();
        }
        return this.names;
    }

    /**
     * Gets the value of the nameCharacteristics property.
     * 
     * @return
     *     possible object is
     *     {@link NameCharacteristicsType }
     *     
     */
    public NameCharacteristicsType getNameCharacteristics() {
        return nameCharacteristics;
    }

    /**
     * Sets the value of the nameCharacteristics property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameCharacteristicsType }
     *     
     */
    public void setNameCharacteristics(NameCharacteristicsType value) {
        this.nameCharacteristics = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the parentFieldConceptURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentFieldConceptURI() {
        return parentFieldConceptURI;
    }

    /**
     * Sets the value of the parentFieldConceptURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentFieldConceptURI(String value) {
        this.parentFieldConceptURI = value;
    }

    /**
     * Gets the value of the dataTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataTypeType }
     * 
     * 
     */
    public List<DataTypeType> getDataTypes() {
        if (dataTypes == null) {
            dataTypes = new ArrayList<DataTypeType>();
        }
        return this.dataTypes;
    }

    /**
     * Gets the value of the fieldRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldRoles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldRoleType }
     * 
     * 
     */
    public List<FieldRoleType> getFieldRoles() {
        if (fieldRoles == null) {
            fieldRoles = new ArrayList<FieldRoleType>();
        }
        return this.fieldRoles;
    }

    /**
     * Gets the value of the defaultFormats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the defaultFormats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDefaultFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDefaultFormats() {
        if (defaultFormats == null) {
            defaultFormats = new ArrayList<String>();
        }
        return this.defaultFormats;
    }

    /**
     * Gets the value of the valueCharacteristics property.
     * 
     * @return
     *     possible object is
     *     {@link ValueCharacteristicsType }
     *     
     */
    public ValueCharacteristicsType getValueCharacteristics() {
        return valueCharacteristics;
    }

    /**
     * Sets the value of the valueCharacteristics property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueCharacteristicsType }
     *     
     */
    public void setValueCharacteristics(ValueCharacteristicsType value) {
        this.valueCharacteristics = value;
    }

    /**
     * Gets the value of the ownerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Sets the value of the ownerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerID(String value) {
        this.ownerID = value;
    }

    /**
     * Gets the value of the createdAt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the value of the createdAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedAt(XMLGregorianCalendar value) {
        this.createdAt = value;
    }

    /**
     * Gets the value of the updatedAt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the value of the updatedAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdatedAt(XMLGregorianCalendar value) {
        this.updatedAt = value;
    }

    /**
     * Gets the value of the valueSource property.
     * 
     * @return
     *     possible object is
     *     {@link ValueSourceType }
     *     
     */
    public ValueSourceType getValueSource() {
        return valueSource;
    }

    /**
     * Sets the value of the valueSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueSourceType }
     *     
     */
    public void setValueSource(ValueSourceType value) {
        this.valueSource = value;
    }

}