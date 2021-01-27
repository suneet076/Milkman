package customerhistory;

public class customerBean {
	String name;
	float cmq;
	float bmq;
	String dos;
public customerBean(){}
	
	public customerBean(String name, float cmq, float bmq, String dos) {
		super();
		this.name = name;
		this.cmq = cmq;
		this.bmq = bmq;
		this.dos = dos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCmq() {
		return cmq;
	}

	public void setCmq(float cmq) {
		this.cmq = cmq;
	}

	public float getBmq() {
		return bmq;
	}

	public void setBmq(float bmq) {
		this.bmq = bmq;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}
	
}
