package modules;


import units.InstructionMemory;

public class InstructionFetch {

	int PC;

	public int InstFetch(int pc,InstructionMemory im) throws Exception {

		int instruction = im.getInstruction(pc);

		pc = pc + 1;

		//System.out.println("Next PC: " + Print(pc));

		return instruction;
	}

	public String Print(int x) {
		String s = Integer.toBinaryString(x);
		while (s.length() < 32)
			s = "0" + s;

		return s;
	}

}
