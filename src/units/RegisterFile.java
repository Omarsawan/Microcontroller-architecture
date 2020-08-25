package units;

public class RegisterFile {
	int[] registers;

	public RegisterFile() {
		registers = new int[32];
		registers[0]=0;//read only
		registers[1]=1;//read only
	}

	public int readRegister(int index) {
		return registers[index];
	}

	public void writeRegister(int index, int value) {
		if(index==0 || index==1) {
			return;
		}
		registers[index]=(value);
	}

}
