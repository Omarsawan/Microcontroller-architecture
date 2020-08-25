package Memory;

import java.io.PrintWriter;

public class Memory {

    private CacheElement[] cache;
    private int[] dataMemory;


    public Memory() {
        dataMemory = new int[1024];
        cache = new CacheElement[32];
        for (int i = 0; i < cache.length; i++)
            cache[i] = new CacheElement();
    }

    public int memAccess(int aluResult, int readData2, boolean memRead, boolean memWrite,PrintWriter pw) {
        int readData = 0;
        pw.println("ALU result: " + aluResult); // address

        // get the index of the address to know which cache block
        int index = aluResult % 32;
        pw.println("Index: " + decToBin(index).substring(27, 32));

        // get the tag of the address to know if the data corresponds to the requested data
        //String tag = decToBin(aluResult).substring(0, 27); 	//tag = 32(address) - 5(index)= 27 bits
        int tag = aluResult >> 5;
        pw.println("Tag: " + completeTag(Integer.toBinaryString(tag)));
        if (memRead) {
            if (cache[index].isValid() && cache[index].getTag() == (tag)) {
                pw.println("Hit!");
//                validData = true;
                readData = cache[index].getData();
            } else {
                pw.println("Miss!");
//                validData = true;
                readData = dataMemory[aluResult];
                cache[index].setData(readData);
                cache[index].setTag(tag);
            }
            pw.println("memory word read: " + readData);
        } else if (memWrite) {
            pw.println("memory word read: don't care");
//            validData = false;
            dataMemory[aluResult] = readData2;

            // Check if we should update the cache
            cache[index].setData(readData2);
            cache[index].setTag(tag);
        } else {
            pw.println("memory word read: don't care");
//            validData = false;
            pw.println();
        }
        pw.println();
        return readData;
    }

    public static String completeTag(String s) {
        while (s.length() < 27)
            s = "0" + s;
        return s;
    }

    public static String decToBin(int n) {
        String bin = "";
        while (n > 0) {
            if (n % 2 == 0)
                bin = "0" + bin;
            else
                bin = "1" + bin;
            n /= 2;
        }
        for (int i = bin.length(); i < 32; i++)
            bin = "0" + bin;
        return bin;
    }


//    public int getReadData() {
//        return readData;
//    }

    public static void main(String[] args) {
        Memory m = new Memory();
    }

    static class CacheElement {

        private int tag;
        private int data;
        private boolean valid = false;

        public CacheElement() {
            this.valid = false;
        }

        public void setData(int data) {
            this.data = data;
            this.valid = true;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getTag() {
            return tag;
        }

        public int getData() {
            return data;
        }

        public boolean isValid() {
            return valid;
        }
    }
}
