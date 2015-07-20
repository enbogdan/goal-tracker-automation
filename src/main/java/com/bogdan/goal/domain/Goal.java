package com.bogdan.goal.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Goal {
	private String id;
	private String name;
	private String dueDate;
	private String notes;
	private String weight;

	public Goal() {
	}

	public Goal(String name, String dueDate, String notes, String weight) {
		this.name = name;
		this.dueDate = dueDate;
		this.notes = notes;
		this.weight = weight;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	@JsonProperty
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goal other = (Goal) obj;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		
		/* weight defaults comparison */
		if (weight == null) {
			if (!other.weight.equals("0"))
				return false;
		} else if (weight.equals("0")) {
				if (!(other.weight == null || other.weight.equals("0")))
					return false;
		}else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	public String toString() {
		return (this!=null)?("Id: " + id + "\n" + "Name: " + name + "\n" + "Due date: "
				+ dueDate + "\n" + "Notes: " + notes + "\n" + "Weight: "
				+ weight):null;
	}
	
	public String toShortString() {
		return (this!=null)?(id + "," + name + "," + dueDate + "," + notes + "," + weight):null;
	}
}
