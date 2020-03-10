package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public RobotConfig() {}

    public RobotConfig(String name, double diameter, double offset) {
	this.name = name;
	this.diameter = diameter;
	this.offset = offset;
    }

    public String getName() {
	return this.name;
    }

    public double getDiameter() {
	return this.diameter;
    }
    public double getOffset() {
	return this.offset;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setDiameter(double diameter) {
	this.diameter = diameter;
    }
    public void setOffset(double offset) {
	this.offset = offset;
    }

    @Override
    public String toString() {
	return name+" "+diameter+" "+offset;
    }
}
