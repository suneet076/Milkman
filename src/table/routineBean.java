package table;

public class routineBean {
	String name;
	float cmv;
	float bmv;
	String dos;
public routineBean(){}
	
	public routineBean(String name, float cmv, float bmv, String dos) {
		super();
		this.name = name;
		this.cmv = cmv;
		this.bmv = bmv;
		this.dos = dos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCmv() {
		return cmv;
	}

	public void setCmv(float cmv) {
		this.cmv = cmv;
	}

	public float getBmv() {
		return bmv;
	}

	public void setBmv(float bmv) {
		this.bmv = bmv;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}
	
}
