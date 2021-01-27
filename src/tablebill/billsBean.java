package tablebill;

public class billsBean {
	String name;
	String dos;
	String doe;
	float tcmq;
	float tbmq;
	float amount;
	public billsBean(){}
	public billsBean(String name,String dos,String doe,	float tcmq,float tbmq,	float amount) 
	{
		super();
		this.name=name;
		this.dos=dos;
		this.doe=doe;
		this.tcmq=tcmq;
		this.tbmq=tbmq;
		this.amount=amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDos() {
		return dos;
	}
	public void setDos(String dos) {
		this.dos = dos;
	}
	public String getDoe() {
		return doe;
	}
	public void setDoe(String doe) {
		this.doe = doe;
	}
	public float getTcmq() {
		return tcmq;
	}
	public void setTcmq(float tcmq) {
		this.tcmq = tcmq;
	}
	public float getTbmq() {
		return tbmq;
	}
	public void setTbmq(float tbmq) {
		this.tbmq = tbmq;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
