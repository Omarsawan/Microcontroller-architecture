package Decoding;

public class ContSignals {
	public boolean RegDst,RegWrite,ALUSrc,PCBranch,PCJump,MemRead,MemWrite,MemToReg,PCBranchLessThan;
	public int ALUOperation;
	public ContSignals(int op,int funct) {
		if (op==0) {
			RegDst=true;
			RegWrite=true;
			ALUOperation= funct;
		}
		else if(op==1){
			ALUOperation=0;
			ALUSrc=true;
			RegWrite=true;
		}
		else if(op==2){
			ALUOperation=2;
			ALUSrc=true;
			RegWrite=true;
		}
		else if(op==3){
			ALUOperation=2;
			RegWrite=true;
			ALUSrc=true;
			MemRead=true;
			MemToReg=true;
		}
		else if(op==4){
			ALUOperation=2;
			ALUSrc=true;
			MemWrite=true;
		}
		else if(op==5){
			ALUOperation=3;
			PCBranch=true;
			
		}
		else if(op==6){
			ALUOperation=3;
			PCBranchLessThan=true;
		}
		else if(op==7){
			ALUOperation=7;
			RegWrite=true;
		}
		else if(op==8){
			PCJump=true;
		}
	}

	@Override
	public String toString() {
		return "ContSignals :" +'\n'+
				"RegDst=" + RegDst +
				", RegWrite=" + RegWrite +
				", ALUSrc=" + ALUSrc +
				", PCBranch=" + PCBranch +
				", PCJump=" + PCJump +
				", MemRead=" + MemRead +
				", MemWrite=" + MemWrite +
				", MemToReg=" + MemToReg +
				", PCBranchLessThan=" + PCBranchLessThan +
				", ALUOperation=" + ALUOperation ;
	}
}
