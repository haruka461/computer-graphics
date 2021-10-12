/**
 * 1円玉を定義するクラス
 */

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import static java.lang.Math.*;


public class MyBike {

	// 自転車の色
	float color1[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	float color2[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	float silver[] = { 0.5f, 0.5f, 0.5f, 1.0f };

        static double pi = 3.1415;
    
        // 自転車の厚さと半径
        static double w = 0.03; 
        static double h = 0.37 + 1.3 * sin(60.0 * pi / 180.0); 

	// 自転車の番号
        int index = 0;

	// 自転車の位置
	double x = 0.0;
        double y = 0.0;
        double z = 0.0;

        // 自転車の角度
        double r = 0.0;

        // 次の自転車の角度
        double nextR = 0.0;

        // 最初の自転車の角度
        static double firstR = 0.0;

        boolean flag = false;
        boolean collision = false;
        boolean last = false;

        // 次の自転車の座標
        double[] next = new double[3];
    
		
	/**
	 * 自転車の色を設定する
	 */
	public void setColor(double r, double g, double b) {
		color1[0] = (float)r;
		color1[1] = (float)g;
		color1[2] = (float)b;
		color1[3] = 1.0f;
	}
	
	/**
	 * 自転車の初期化
	 */
    public void init(int i, double info[][], boolean l) {
	        index = i;
	        x = info[i][0];
	        y = info[i][1];
		z = info[i][2];
		last = l;
		if(last == false){
		    next[0] = info[i + 1][0];
		    next[1] = info[i + 1][1];
		    next[2] = info[i + 1][2];
		}
	}

        /**
         * 自転車の回転角を計算する
         */
         public void calculateMovement(){
	     
	     double pi = 3.1415;
	     double g = 9.8;
	     if(flag == true && collision == false){
		 
		 if(r <= 1.0 && index == 0){
		     r += 1.0;
		     firstR += 1.0;
		     
		 }else{
		     r += 5.0 * g * sin(firstR * 0.1 * pi / 180.0);
		     
		     if(index == 0){
			 firstR += 5.0 * g * sin(firstR * 0.1 * pi / 180.0);
		     }
		 }
	     }else if(flag == true && collision == true){
		 
		 // 次の自転車にめりこまないようにする		 
		 if(nextR <= r){
		     double rad1 = nextR * 0.1 * pi / 180.0;
		     double tangent1 = tan(rad1);
		     double bunbo1 = h * sqrt(1 + tangent1 * tangent1);
		     double gousei1 = asin((0.03 / cos(rad1) - abs((next[0] - x))) / bunbo1);
		     double alpha1 = asin(h * tangent1 / bunbo1);
		     
		     r = toDegrees(abs(gousei1 - alpha1)) * 10;

		 }

	     }
	     if(r >= 900.0){
		 r = 900.0;
	     }
	     MyScene.calculateMovement(index, r);
	 }

         public void setFlag(){
	     flag = true;
	 }
	
	/**
	 * 自転車の傾きをリセットする
	 */
	public void resetMovement() {
	    r = 0.0;
	    flag = false;
	    collision = false;
	    firstR = 0.0;
	}

        static public void resetFirstR(){
	    firstR = 0.0;
	}

    public void drawCylinder(GLAutoDrawable drawable, double r, double height){
	       GL2 gl = drawable.getGL().getGL2();
	       int sides = 30;
	       int i;
	       double pi = 3.1415; 
	       double step = pi*2 / (double)sides;

	        //上面
		gl.glNormal3d(0.0, 0.0, 1.0);
		gl.glBegin(GL2.GL_POLYGON);
		for(i = 0; i < sides; i++){
		    double t = step * (double)i;
		    gl.glVertex3d(r * sin(t), height, r * sin(t));
		}
		gl.glEnd();

		//下面
		gl.glNormal3d(0.0, 0.0, -1.0);
		gl.glBegin(GL2.GL_POLYGON);
		for(i = 0; i < sides; i++){
		    double t = step * (double)i;
		    gl.glVertex3d(r * sin(t), height, r * cos(t));
		}
		gl.glEnd();
		
		//側面
		gl.glBegin(GL2.GL_QUAD_STRIP);
		for(i = 0; i <= sides; i++){
		    double t = step * (double)i;
		    double x = sin(t);
		    double z = cos(t);

		    gl.glNormal3d(x, 0.0, z);
		    gl.glVertex3d(r * x, height, r * z);
		    gl.glVertex3d(r * x, 0.0, r * z);
		}
		gl.glEnd();
	
	}

    // タイヤを描画する
    public void drawTire(GLAutoDrawable drawable, double ir, double or){
	       GL2 gl = drawable.getGL().getGL2();
	       GLUT glut = new GLUT();

	       double length = or * 2;

	       gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color2, 0);
	       gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

	       glut.glutSolidTorus(ir, or, 30, 40);

	       gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, silver, 0);
	       gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

	       gl.glRotated(10, 0.0, 0.0, 1.0);

	       gl.glTranslated(0.0, -or, 0.0);
	       drawCylinder(drawable, 0.005, length);
	       gl.glTranslated(0.0, or, 0.0);


	       gl.glRotated(45, 0.0, 0.0, 1.0);
	       gl.glTranslated(0.0, -or, 0.0);
	       drawCylinder(drawable, 0.005, length);
	       gl.glTranslated(0.0, or, 0.0);

	       gl.glRotated(45, 0.0, 0.0, 1.0);
	       gl.glTranslated(0.0, -or, 0.0);
	       drawCylinder(drawable, 0.005, length);
	       gl.glTranslated(0.0, or, 0.0);

	       gl.glRotated(45, 0.0, 0.0, 1.0);
	       gl.glTranslated(0.0, -or, 0.0);
	       drawCylinder(drawable, 0.005, length);
	       gl.glTranslated(0.0, or, 0.0);
	       gl.glRotated(-145, 0.0, 0.0, 1.0);

	}

	
	/**
	 * 自転車を描画する
	 */
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		double r1 = 0.37;
		double r2 = 0.03;
		double r3 = 0.5;
		double height1 = 0.8;
		double height2 = 1.3;
		double ir = 0.025;
		double or = 0.37;
		

		gl.glRotated(90, 0.0, 1.0, 0.0);

		// 自転車の変位を求める
		gl.glTranslated(x, y - or, z);
		gl.glRotated(r * 0.1, 0.0, 0.0, 1.0);
		gl.glTranslated(0.0, or, 0.0);
				
		gl.glRotated(90, 0.0, 1.0, 0.0);
		drawTire(drawable, ir, or);

		// 自転車の拡散反射係数・鏡面反射係数を設定する
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color1, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);
		gl.glRotated(-90, 0.0, 1.0, 0.0);
		gl.glRotated(90, 1.0, 0.0, 0.0);
		drawCylinder(drawable, r2, height1);

		gl.glTranslated(0.0, 0.39, -0.69);
		drawCylinder(drawable, r2, height1);

		gl.glTranslated(0.0, -0.39, 0.69);
		gl.glRotated(-60, 1.0, 0.0, 0.0);
		drawCylinder(drawable, r2, height1);

		gl.glTranslated(0.0, 0.39, 0.68);
		drawCylinder(drawable, r2, height1);

		gl.glRotated(-60, 1.0, 0.0, 0.0);
		drawCylinder(drawable, r2, height1);

		gl.glTranslated(0.0, -0.4, 0.73);
		drawCylinder(drawable, r2, height2);

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color2, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);

		gl.glRotated(90, 0.0, 1.0, 0.0);
		drawTire(drawable, ir, or);

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, color1, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, silver, 0);
		gl.glTranslated(0.0, 1.3, 0.2);
		gl.glRotated(-90, 1.0, 0.0, 0.0);
		drawCylinder(drawable, r2, 0.4);


		double theta3 = r * 0.1 * pi / 180.0;
		double sine = h * sin(theta3);
		double cosine = h * cos(theta3);
		
		double ver[] = { 0.0, cosine, sine};
	
		if(last == false){
		    collision = Collision.calcCol(ver, ver, x, next[0] + 0.03);
		    
		}

		if(collision == true && last == false){
		    nextR = MyScene.setFlag(index + 1); // 前の自転車が倒れる
		}

		calculateMovement();
	}
	
	

}
