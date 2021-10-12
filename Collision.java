/**
 * 衝突判定をするクラス
 */

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import static java.lang.Math.*;


public class Collision {

    public static boolean calcCol(double[] ver1, double[] ver2, double z, double nextZ){

	double interval = abs(nextZ - z);

	if(interval <= ver1[2]){
	    return true;
	}else if(interval <= ver2[2]){
	    return true;
	}
	return false;
	
    }
}
