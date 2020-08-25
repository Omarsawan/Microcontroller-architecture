package units;

public class InstructionMemory {

    int[] instructions; // assuming instruction is int
    int numberOfInstructions;
    int idx;
    public InstructionMemory(int size) {
        instructions = new int[size];
        
        loadInstruction("0010 00111 00111 000000000010100000");//Addi $7 , $7 , 160
        loadInstruction("0000 00001 00000 11000 00000 00000001");//Or $1,$0,$24
        loadInstruction("0000 00001 00001 10000 00000 00000010");//Add $1,$1,$16
        loadInstruction("0100 00111 00111 000000000000000000");//SW $7,0($7)
        loadInstruction("0011 00111 11111 000000000000000000");//LW $31 ,0($7)
        loadInstruction("0101 11000 00001 000000000000000001");//BEQ $24,$1,1
        
        loadInstruction("0000 00001 00000 11000 00000 00000001");//Or $1,$0,$24  // should be skipped due to branch
        
        loadInstruction("1000 0000000000000000000001000000");//J 64
        
        loadInstruction("0000 00001 00000 11000 00000 00000001");//Or $1,$0,$24  // should be skipped due to jump
        
        numberOfInstructions = idx;
    }

    public int getInstruction(int index) {
        return instructions[index];
    }

    public void setInstruction(int index, int value) {
        instructions[index] = value;
        numberOfInstructions++;
    }

    public int getNumberOfInstructions() {
        return numberOfInstructions;
    }
    public void loadInstruction(String s) {
    	int ans=0,pow=1;
		for(int i=s.length()-1;i>=0;i--) {
			if(s.charAt(i)==' ')continue;
			
			if(s.charAt(i)=='1') {
				ans|=pow;
			}
			pow<<=1;
		}
		instructions[idx++]=ans;
    }
    public void loadInstruction(int inst) {
		instructions[idx++]=inst;
    }
}
