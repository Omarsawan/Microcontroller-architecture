package Decoding;

import units.RegisterFile;

public class Decoding {
	public int op,rs,rt,rd,shamt,funct,Immediate,jumpAdd,ReadData1,ReadData2;
	public ContSignals signals;
	

	public Decoding() {
		// TODO Auto-generated constructor stub
	}

	
	public Decoding(int op, int rs, int rt, int rd, int shamt, int funct, int immediate, int jumpAdd,int readData1,int readData2,ContSignals signals) {
		super();
		this.op = op;
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
		this.shamt = shamt;
		this.funct = funct;
		Immediate = immediate;
		this.jumpAdd = jumpAdd;
		this.signals = signals;
		this.ReadData1=readData1;
		this.ReadData2=readData2;

	}
	public static int signExtend(int n){
		String s=Integer.toBinaryString(n);
		char x='0';
		//if(s.length()==18)x='1';
		if(n>>17==1)x='1';
		while(s.length()<32)
			s=x+s;
		
		int ans=0,pow=1;
		for(int i=31;i>=0;i--) {
			if(s.charAt(i)=='1') {
				ans|=pow;
			}
			pow<<=1;
		}
		
		return ans;
	}
	static int lastiBits(int value,int i) {
		int iOnes=(1<<i)-1;
		int ans = value&iOnes;
		return ans;
	}
	
	public  Decoding decode(int inst, RegisterFile rf){
		int op = lastiBits((inst>>28), 4);
		int rs = lastiBits((inst>>23), 5);
		int rt = lastiBits((inst>>18), 5);
		int rd = lastiBits((inst>>13), 5);
		int shamt = lastiBits((inst>>8), 5);
		int funct = lastiBits((inst), 8);
		int immediate = signExtend(lastiBits((inst>>0), 18));
		int jumpAdd = lastiBits((inst>>0), 28);
		int readData1=rf.readRegister(rs);
		int readData2=rf.readRegister(rt);
		Decoding d = new Decoding(op, rs, rt, rd, shamt, funct, immediate, jumpAdd,readData1,readData2, new ContSignals(op, funct));
		return d;
	}

	@Override
	public String toString() {
		return "Decoding{" +
				"op=" + op +
				", rs=" + rs +
				", rt=" + rt +
				", rd=" + rd +
				", shamt=" + shamt +
				", funct=" + funct +
				", Immediate=" + Immediate +
				", jumpAdd=" + jumpAdd +
				", ReadData1=" + ReadData1 +
				", ReadData2=" + ReadData2 + '\n'+
				", signals=" + signals +
				'}';
	}
}
