/**
 * マウス操作に応じた処理のためのクラス
 */

import java.awt.event.*;
import java.awt.Point;
import com.jogamp.opengl.util.Animator;

public class CgMouseListener implements MouseListener, MouseMotionListener {
	CgCanvas canvas;
	Animator animator;	
	
	public CgMouseListener(CgCanvas c, Animator a) {
		canvas = c;
		animator = a;
	}
    
    /**
     * カーソルの移動に対する処理のメソッド
     */
    public void mouseMoved(MouseEvent e) {
    	
    }
    
    /**
     * カーソルがウィンドウ内部に入ったときのメソッド
     */
    public void mouseEntered(MouseEvent e) {
    	
    }
    
    /**
     * カーソルがウィンドウ外部に出たときのメソッド
     */
    public void mouseExited(MouseEvent e) {
    	
    }

    /**
     * マウスのクリック操作のメソッド
     */
    public void mouseClicked(MouseEvent e) {
	Point p = e.getPoint();
	MyScene.clickPos(p.x, p.y);
	animator.start();
	//double r = MyScene.setFlag(0);
    }
    
    /**
     * マウスのドラッグ操作のメソッド
     */
    public void mouseDragged(MouseEvent e) {
    	
    }
    
    /**
     * マウスのボタンを押したときのメソッド
     */
    public void mousePressed(MouseEvent e) {
    	//animator.start();
    }
    
    /**
     * マウスのボタンを離したときのメソッド
     */
    public void mouseReleased(MouseEvent e) {
    	//animator.stop();
    }

    
}
