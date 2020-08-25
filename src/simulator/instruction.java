package simulator;

import Decoding.Decoding;
import Execute.Execute;
import Memory.Memory;
import modules.InstructionFetch;
import modules.WriteBack;
import units.InstructionMemory;
import units.RegisterFile;
import Decoding.ContSignals;

import java.io.PrintWriter;
import java.util.LinkedList;

public class instruction {
    public instruction(int PC) {
        stage = 1;
        curPc=PC;
    }
    int curPc;
    String[]stages=new String[] {"","Fetch","Decode","Execute","Memory","Write Back"};
    int instruction;
    int stage;
    Decoding DecodeOut;
    Execute execute;
    public int readMemory;

    void run() throws Exception {
    	if(stage!=1) {
    		pw.println("Instruction "+this+" in "+curStage()+" stage");
    		pw.println("Next PC : "+(curPc+1));
    	}
        switch (stage) {
            case 1:  //fetch
                fetch();
                break;
            case 2: //Decode
                decode();
                break;
            case 3: //Execute
                execute();
                break;

            case 4: //Memory
                memory();
                break;
            case 5: //WriteBack
                writeback();
                break;

        }
        pw.println();
        pw.println( "----------------------------------------------");
        pw.println();
        Stage_Inc();
    }

    public static int Mux(boolean sig, int val1, int val2) {
        return sig ? val1 : val2;
    }

    void fetch() throws Exception {
        InstructionFetch fetch = new InstructionFetch();
        instruction = fetch.InstFetch(curPc, instructionMemory);
        pw.println("Instruction "+this+" in "+curStage()+" stage");
    	pw.println("Next PC : "+(curPc+1));
    	
    	pw.println("Instruction: " + fetch.Print(instruction));
    }

    void decode() {
        DecodeOut = new Decoding();
        DecodeOut = DecodeOut.decode(instruction, registerFile);
        pw.println(DecodeOut);
    }

    void execute() {
        execute = new Execute();
        int dataIn2 = Mux(DecodeOut.signals.ALUSrc, DecodeOut.Immediate, DecodeOut.ReadData2);
        execute.execute(DecodeOut.signals.ALUOperation, DecodeOut.ReadData1, dataIn2);
        boolean equalFlag=execute.isZeroFlag()&&DecodeOut.signals.PCBranch;
        boolean lessThanFlag=execute.isZeroFlag()&&DecodeOut.signals.PCBranchLessThan;
        boolean jump=DecodeOut.signals.PCJump;
        int branchAddressResult=DecodeOut.Immediate+curPc;
        int pcjump=DecodeOut.jumpAdd;
        pw.println(" ALU OPCODE : "+DecodeOut.signals.ALUOperation);
        pw.println("First operand : "+DecodeOut.ReadData1);
        pw.println("Second operand : "+dataIn2);
        
        pw.println("zero flag : "+execute.isZeroFlag());
        pw.println("branch address : "+branchAddressResult);
        pw.println("ALU result/address : "+execute.getAluResult());
        pw.println("register value to write to memory: "+DecodeOut.ReadData2);
        pw.println("rt : "+DecodeOut.rt+" , rd  : "+DecodeOut.rd);
        pw.println(DecodeOut.signals);
        
        int prevPC=pc;
        pc=Mux(jump, pcjump, Mux(equalFlag||lessThanFlag,branchAddressResult,pc ));
        if(pc!=prevPC) {
        	jumpOcc=true;
        }
    }

    void memory() {
        readMemory = DataMemory.memAccess(execute.getAluResult(), DecodeOut.ReadData2,
                DecodeOut.signals.MemRead, DecodeOut.signals.MemWrite,pw);
    }

    void writeback() {
        WriteBack writeBack = new WriteBack();
        ContSignals sig = DecodeOut.signals;
        writeBack.writeBack(execute.getAluResult(), readMemory, sig.MemToReg,
                sig.RegDst, DecodeOut.rt, DecodeOut.rd, sig.RegWrite, registerFile,pw);
    }

    public void Stage_Inc() {
        stage++;
    }
    public String curStage() {
    	return stages[stage];
    }
    public String toString() {
    	String s = Integer.toBinaryString(this.instruction);
		while (s.length() < 32)
			s = "0" + s;
    	return s;
    }
    
    static InstructionMemory instructionMemory;
    static Memory DataMemory;
    static RegisterFile registerFile;
    static int pc;
    static boolean jumpOcc=false;
    static PrintWriter pw;
    public static void main(String[] args) throws Exception {
//    	pw=new PrintWriter("C:\\Users\\Omer\\Downloads\\c.txt");
    	pw=new PrintWriter(System.out);
        LinkedList<instruction> q = new LinkedList<instruction>();
        instructionMemory = new InstructionMemory(1024);
        
        DataMemory = new Memory();
        registerFile = new RegisterFile();
         pc = 0;
         instruction jumbInstruction=null;
         
        for (int clockCycle = 1; ; clockCycle++) {
            //fetch
            boolean fetch = pc < instructionMemory.getNumberOfInstructions();

            if (!fetch) {//no more instructions
                if (q.isEmpty())
                    break;

            } else {
                q.addLast(new instruction(pc));
            }

            int curInstructions = q.size();

            pw.println("After "+clockCycle+" Clock cycle"+(clockCycle>1?"s":"")+" :");
            pw.println();
            for (int i = 0; i < curInstructions; i++) {
                instruction cur = q.pollFirst();
                
               	cur.run();
               
                
                if(jumpOcc) {//current instruction made jump
                	jumbInstruction=cur;
                	jumpOcc=false;
                }
                
                if (cur.stage <= 5) {//instruction not yet finished
                    q.addLast(cur);
                }

            }
            pc++;
            if(jumbInstruction!=null) {
            	while(q.peekLast()!=jumbInstruction) {
            		instruction removed=q.pollLast();
            		pw.println("Instruction : "+removed+" removed from pipelining because instruction : "+jumbInstruction+" made a jumb to PC "+pc);
            		pw.println();
            	}
            	pw.println();
                pw.println( "----------------------------------------------");
                pw.println();
            	
            	jumbInstruction=null;
            }
            
        }
        pw.flush();
    }
}
