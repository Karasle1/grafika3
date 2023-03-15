package p01simple;

import lwjglutils.OGLBuffers;

public class GridFactory {

    static OGLBuffers generateGrid(int m, int n){

        float[] vb = new float [m*n*2];
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                vb[index++] = j / (float) (m - 1);
                vb[index++] = i / (float) (n - 1);
             //  vb[index++] = 1;

            }
        }
        int[] ib = new int[2 * 3 * (m-1) * (n -1)];
        int index2 = 0;
        for (int i = 0; i < n -1; i++ ) {
            int rowOfset = i * m;
            for (int j=0 ;j < m-1;j++ ){
                ib[index2++] = j + rowOfset;
                ib[index2++] = j + m + rowOfset;
                ib[index2++] = j + 1 + rowOfset;

                ib[index2++] = j + 1 + rowOfset;
                ib[index2++] = j + m + rowOfset;
                ib[index2++] = j + m + 1 + rowOfset;

            }

        }


        OGLBuffers.Attrib[] attributes = {
                new OGLBuffers.Attrib("inPosition",2)




        };
        return new OGLBuffers(vb,attributes,ib);



    }


}
