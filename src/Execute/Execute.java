package Execute;

public class Execute {

    private int aluResult;
    private boolean zeroFlag;
    private boolean lessThanFlag;

    public void execute(int aluOp, int x, int y) {
        

        switch (aluOp) {
            case 0:
                aluResult = x & y;
                break;
            case 1:
                aluResult = x | y;
                break;
            case 2:
                aluResult = x + y;
                break;
            case 3:
                aluResult = x - y;
                break;
            case 4:
                aluResult = x * y;
                break;
            case 5:
                aluResult = x << y;    //x * Math.pow(2,y)
                break;
            case 6:
                aluResult = x >> y;        //x / Math.pow(2,y)
                break;
            case 7:
                aluResult = (x < y ? 1 : 0);
                break;
        }
        zeroFlag = aluResult == 0;
        lessThanFlag = aluResult < 0;

    }

    public int getAluResult() {
        return aluResult;
    }

    public boolean isZeroFlag() {
        return zeroFlag;
    }
	public boolean isLessThanFlag() {
		return lessThanFlag;
	}

    public static void main(String[] args) {
        Execute ex = new Execute();
        ex.execute(6, 4, 1);
        System.out.println(ex.getAluResult());
        System.out.println(ex.isZeroFlag());
    }

    @Override
    public String toString() {
        return "Execute{" +
                "aluResult=" + aluResult +
                ", zeroFlag=" + zeroFlag +
                ", lessThanFlag=" + lessThanFlag +
                '}';
    }
}
