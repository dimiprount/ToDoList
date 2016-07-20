package com.dimiprount.todolist;

// private variables
public class Notepad {
	int id;
	String note;

	// Empty constructor
	public Notepad() {

	}

	/*// constructor
	public Notepad(int keyId, String note) {
		this.id = keyId;
		this.note = note;
	}

	// constructor
	public Notepad(String note) {
		this.note = note;
	}*/

	// getting ID
	public int getID() {
		return this.id;
	}

	// setting id
	public void setID(int keyId) {
		this.id = keyId;
	}

	// getting name
	public String getNote() {
		return this.note;
	}

	// setting name
	public void setNote(String note) {
		this.note = note;
	}

}