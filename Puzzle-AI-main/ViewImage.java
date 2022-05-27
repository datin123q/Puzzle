package puzzle;

import java.awt.*;

public class ViewImage extends Component{
    private Image bi;
    protected int w, h;
    
    public ViewImage(Image img)            
    {
        this.bi = img;
        int width = 165, height = 165;
        width = w = bi.getWidth(this);
        height = h = bi.getHeight(this);    
        if(w > h)
        {
            w = 165;
            h = w * height/width;
        }
        else if(w == h)
        {
            w = h = 165;
        }
        else
        {
            h = 165;
            w = h * width/height;
        }
        this.repaint();
    }
    
    public void paint(Graphics g)
    {
        g.drawImage(bi, 0, 0, w, h, null);        
    }
}
