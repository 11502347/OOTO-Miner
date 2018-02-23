package results;

public class TestOfIndependenceResults 
{

	private float z,z2,n1,n2,p1,p2,pPrime,SEp, freq_p1, freq_p2;


	public float getFreq_p1() {
		return freq_p1;
	}

	public float getFreq_p2() {
		return freq_p2;
	}

	private String pass95, pass99; 

	public TestOfIndependenceResults(float z, float z2, float n1, float n2, float p1, float p2, float pPrime,
			float sEp, String pass99, String pass95,float freq_p1, float freq_p2) {
		this.z = z;
		this.z2 = z2;
		this.n1 = n1;
		this.n2 = n2;
		this.p1 = p1;
		this.p2 = p2;
		this.pPrime = pPrime;
		SEp = sEp;
		this.pass95 = pass95;
		this.pass99 = pass99; 
		this.freq_p1 = freq_p1;
		this.freq_p2 = freq_p2;
	}
	
	public float getZ() {
		return z;
	}

	public float getZ2() {
		return z2;
	}

	public float getN1() {
		return n1;
	}

	public float getN2() {
		return n2;
	}

	public float getP1() {
		return p1;
	}

	public float getP2() {
		return p2;
	}

	public float getpPrime() {
		return pPrime;
	}

	public float getSEp() {
		return SEp;
	}

	public String getPass95() {
		return pass95;
	}

	public String getPass99() {
		return pass99;
	}

	
	

}
