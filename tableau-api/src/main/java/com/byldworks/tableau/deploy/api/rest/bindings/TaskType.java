//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.04 at 03:55:43 PM BST 
//


package com.byldworks.tableau.deploy.api.rest.bindings;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for taskType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="taskType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="extractRefresh" type="{http://tableau.com/api}taskExtractRefreshType"/&gt;
 *         &lt;element name="flowRun" type="{http://tableau.com/api}taskRunFlowType"/&gt;
 *         &lt;element name="dataAcceleration" type="{http://tableau.com/api}taskDataAccelerationType"/&gt;
 *         &lt;element name="schedule" type="{http://tableau.com/api}scheduleType" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="runNow" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="state" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="consecutiveFailedCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taskType", propOrder = {
    "extractRefresh",
    "flowRun",
    "dataAcceleration",
    "schedule"
})
public class TaskType {

    protected TaskExtractRefreshType extractRefresh;
    protected TaskRunFlowType flowRun;
    protected TaskDataAccelerationType dataAcceleration;
    protected ScheduleType schedule;
    @XmlAttribute(name = "runNow")
    protected Boolean runNow;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "priority")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger priority;
    @XmlAttribute(name = "consecutiveFailedCount")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger consecutiveFailedCount;
    @XmlAttribute(name = "type")
    protected String type;

    /**
     * Gets the value of the extractRefresh property.
     * 
     * @return
     *     possible object is
     *     {@link TaskExtractRefreshType }
     *     
     */
    public TaskExtractRefreshType getExtractRefresh() {
        return extractRefresh;
    }

    /**
     * Sets the value of the extractRefresh property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskExtractRefreshType }
     *     
     */
    public void setExtractRefresh(TaskExtractRefreshType value) {
        this.extractRefresh = value;
    }

    /**
     * Gets the value of the flowRun property.
     * 
     * @return
     *     possible object is
     *     {@link TaskRunFlowType }
     *     
     */
    public TaskRunFlowType getFlowRun() {
        return flowRun;
    }

    /**
     * Sets the value of the flowRun property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskRunFlowType }
     *     
     */
    public void setFlowRun(TaskRunFlowType value) {
        this.flowRun = value;
    }

    /**
     * Gets the value of the dataAcceleration property.
     * 
     * @return
     *     possible object is
     *     {@link TaskDataAccelerationType }
     *     
     */
    public TaskDataAccelerationType getDataAcceleration() {
        return dataAcceleration;
    }

    /**
     * Sets the value of the dataAcceleration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskDataAccelerationType }
     *     
     */
    public void setDataAcceleration(TaskDataAccelerationType value) {
        this.dataAcceleration = value;
    }

    /**
     * Gets the value of the schedule property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleType }
     *     
     */
    public ScheduleType getSchedule() {
        return schedule;
    }

    /**
     * Sets the value of the schedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleType }
     *     
     */
    public void setSchedule(ScheduleType value) {
        this.schedule = value;
    }

    /**
     * Gets the value of the runNow property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRunNow() {
        return runNow;
    }

    /**
     * Sets the value of the runNow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRunNow(Boolean value) {
        this.runNow = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPriority(BigInteger value) {
        this.priority = value;
    }

    /**
     * Gets the value of the consecutiveFailedCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getConsecutiveFailedCount() {
        return consecutiveFailedCount;
    }

    /**
     * Sets the value of the consecutiveFailedCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setConsecutiveFailedCount(BigInteger value) {
        this.consecutiveFailedCount = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}