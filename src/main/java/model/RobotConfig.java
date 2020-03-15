package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DataTransferObject for Lejos Mindstorms -robot configuration.
 */
@Entity
@Table(name = "Robotconfig")
public class RobotConfig {

    @Id
    @Column(name = "name", columnDefinition="VARCHAR(32)")
    private String name;

    @Column(name = "diameter")
    private double diameter;
    
    @Column(name = "offset")
    private double offset;

    /**
     * Default constructor.
     */
    public RobotConfig() {}

    /**
     * 
     * @param name
     * @param diameter diameter of Lejos Mindstorms -robot's wheels.
     * @param offset Lejos Mindstorms -robot's track width.
     */
    public RobotConfig(String name, double diameter, double offset) {
	this.name = name;
	this.diameter = diameter;
	this.offset = offset;
    }

    /**
     * @return
     */
    public String getName() {
	return this.name;
    }

    /**
     * @return
     */
    public double getDiameter() {
	return this.diameter;
    }
    public double getOffset() {
	return this.offset;
    }

    /**
     * @param name
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @param diameter
     */
    public void setDiameter(double diameter) {
	this.diameter = diameter;
    }
    /**
     * @param offset
     */
    public void setOffset(double offset) {
	this.offset = offset;
    }

    @Override
    public String toString() {
	return name+" "+diameter+" "+offset;
    }
}
