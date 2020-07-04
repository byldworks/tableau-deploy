//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.04 at 03:55:43 PM BST 
//


package com.byldworks.tableau.deploy.api.rest.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


/**
 * <p>Java class for intervalType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="intervalType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="minutes"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"&gt;
 *             &lt;enumeration value="15"/&gt;
 *             &lt;enumeration value="30"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="hours"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"&gt;
 *             &lt;enumeration value="1"/&gt;
 *             &lt;enumeration value="2"/&gt;
 *             &lt;enumeration value="4"/&gt;
 *             &lt;enumeration value="6"/&gt;
 *             &lt;enumeration value="8"/&gt;
 *             &lt;enumeration value="12"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="weekDay"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="Monday"/&gt;
 *             &lt;enumeration value="Tuesday"/&gt;
 *             &lt;enumeration value="Wednesday"/&gt;
 *             &lt;enumeration value="Thursday"/&gt;
 *             &lt;enumeration value="Friday"/&gt;
 *             &lt;enumeration value="Saturday"/&gt;
 *             &lt;enumeration value="Sunday"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="monthDay"&gt;
 *         &lt;simpleType&gt;
 *           &lt;union&gt;
 *             &lt;simpleType&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                 &lt;minInclusive value="1"/&gt;
 *                 &lt;maxInclusive value="31"/&gt;
 *               &lt;/restriction&gt;
 *             &lt;/simpleType&gt;
 *             &lt;simpleType&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                 &lt;enumeration value="LastDay"/&gt;
 *               &lt;/restriction&gt;
 *             &lt;/simpleType&gt;
 *           &lt;/union&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "intervalType")
public class IntervalType {

    @XmlAttribute(name = "minutes")
    protected BigInteger minutes;
    @XmlAttribute(name = "hours")
    protected BigInteger hours;
    @XmlAttribute(name = "weekDay")
    protected String weekDay;
    @XmlAttribute(name = "monthDay")
    protected String monthDay;

    /**
     * Gets the value of the minutes property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinutes() {
        return minutes;
    }

    /**
     * Sets the value of the minutes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinutes(BigInteger value) {
        this.minutes = value;
    }

    /**
     * Gets the value of the hours property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHours() {
        return hours;
    }

    /**
     * Sets the value of the hours property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHours(BigInteger value) {
        this.hours = value;
    }

    /**
     * Gets the value of the weekDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekDay() {
        return weekDay;
    }

    /**
     * Sets the value of the weekDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekDay(String value) {
        this.weekDay = value;
    }

    /**
     * Gets the value of the monthDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthDay() {
        return monthDay;
    }

    /**
     * Sets the value of the monthDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthDay(String value) {
        this.monthDay = value;
    }

}
