package com.wfj.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for fujiSync complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="fujiSync">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queueName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queueData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fujiSync", propOrder = { "queueName", "queueData" })
public class FujiSync {

	protected String queueName;
	protected String queueData;

	/**
	 * Gets the value of the queueName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * Sets the value of the queueName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQueueName(String value) {
		this.queueName = value;
	}

	/**
	 * Gets the value of the queueData property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQueueData() {
		return queueData;
	}

	/**
	 * Sets the value of the queueData property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQueueData(String value) {
		this.queueData = value;
	}

}
