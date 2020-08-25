package modules;

import java.io.PrintWriter;

import units.RegisterFile;

public class WriteBack {

    int TargetRegister;
    int value;

    public WriteBack() {
    }
	public static int Mux(boolean sig, int val1, int val2) {
		return sig ? val1 : val2;
	}
    public WriteBack(int TargetRegister, int value) {
        this.TargetRegister = TargetRegister;
        this.value = value;
    }

   public void writeBack(int Aluresult, int ReadData, boolean MemToReg,
                        boolean RegDst, int RT, int RD, boolean regWrite,RegisterFile RF,PrintWriter pw) {

			int writeData=Mux(MemToReg, ReadData, Aluresult);
			int writeReg=Mux(RegDst, RD,RT);
			if(regWrite) {
                RF.writeRegister(writeReg, writeData);
                pw.printf(" Write in register %d Value %d\n",writeReg,writeData);
            }
//        if (MemToReg) {
//            if (!RegDst) {
//                RF.writeRegister(RT, ReadData);
//                return new WriteBack(RT, ReadData);
//
//            }
//
//        } else {
//            if (RegDst) {
//                RF.writeRegister(RD, ALUresult);
//                return new WriteBack(RD, ALUresult);
//
//            }
//
//        }
//        return null;

    }

}
