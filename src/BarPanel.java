import javax.swing.*;
import java.awt.*;
import java.util.*;
public class BarPanel extends JPanel{
    private ArrayList<Bar> Bars;
    private LayoutPanel layoutP;
    private ArrayList<Step> steps;
    private Bar[] barsBuffer;
    private static final int MAXBARS = 100;
    private int stepCounter;
    public BarPanel(LayoutPanel p){
        layoutP = p;
        Bars = new ArrayList<Bar>();
        steps = new ArrayList<Step>();
        barsBuffer = new Bar[MAXBARS];
        stepCounter = -1;
    }
    public int getBarWidth(){
        int barWidth = this.getWidth()/Bars.size();
        return barWidth;
    }
    public int getBarX(int index){
        return index*this.getBarWidth();
    }
    public void addBar(){
        int pHeight = this.getHeight();
        int height = (int)(Math.random()*(pHeight - 50) + 51);
        Bar nBar = new Bar(height);
        Bars.add(nBar);
        this.repaint();
    }
    private void swapBars(int index1, int index2){
        Bar bar1 = Bars.get(index1);
        Bar bar2 = Bars.get(index2);
        Bars.set(index1, bar2);
        Bars.set(index2, bar1);
    }
    private void storeBar(int fromBarIndex, int toBufferIndex){
        //puts bar in buffer
        Bar temp = Bars.get(fromBarIndex);
        barsBuffer[toBufferIndex] = temp;
    }
    private void saveBar(int fromBufferIndex, int toBarIndex){
        //puts bar back in bars from buffer
        Bar temp = barsBuffer[fromBufferIndex];
        Bars.set(toBarIndex, temp);
        barsBuffer[fromBufferIndex] = null;
    }
    private void recordStore(int fromBarIndex, int toBufferIndex){
        storeBar(fromBarIndex, toBufferIndex);
        Step s = new Step(fromBarIndex, toBufferIndex, Step.STORETOBUFFER);
        steps.add(s);
    }
    private void recordSave(int fromBufferIndex, int toBarIndex){
        saveBar(fromBufferIndex, toBarIndex);
        Step s = new Step(fromBufferIndex, toBarIndex, Step.SAVEFROMBUFFER);
        steps.add(s);
    }
    public void randomize(){
        for(int k = Bars.size()-1; k > 0; k--){
            int randInd = (int)(Math.random()*(k+1));
            swapBars( randInd, k);
        }
        repaint();
    }
    public void bubbleSort(){
        if(Bars.size() == 0){
            return;
        }else{
            clearSteps();
        }
        int index1;
        int index2;
        int endIndex;
        for(endIndex = Bars.size(); endIndex >=0; endIndex--){
            for(index1 = 0; index1 < endIndex-1; index1++){
                index2 = index1+1;
                Bar bar1 = Bars.get(index1);
                Bar bar2 = Bars.get(index2);
                Step compareStep = new Step(index1, index2, Step.COMPARE);
                steps.add(compareStep);
                if(bar1.getHeight() > bar2.getHeight()){
                    Step swapStep = new Step(index1, index2, Step.SWAP);
                    steps.add(swapStep);
                    swapBars(index1, index2);
                }
            }
        }
        stepCounter = steps.size();
        while(stepCounter != 0){
            stepBack();
        }
        repaint();
    }
    public void selectionSort(){
        clearSteps();
        if(Bars.size() == 0){
            return;
        }
        for(int i = 0; i < Bars.size(); i++){
            for(int j = i + 1; j < Bars.size(); j++){
                Step compareStep = new Step(i, j, Step.COMPARE);
                steps.add(compareStep);
                Bar b1 = Bars.get(i);
                Bar b2 = Bars.get(j);
                if(b1.getHeight() >= b2.getHeight()){
                    Step swapStep = new Step(i, j, Step.SWAP);
                    steps.add(swapStep);
                    swapBars(i,j);
                }
            }
        }
        stepCounter = steps.size();
        while(stepCounter != 0){
            stepBack();
        }
        repaint();
    }
    public void insertionSort(){
        clearSteps();
        if(Bars.size() == 0){
            return;
        }
        for(int startI = 1; startI < Bars.size(); startI++){
            boolean end = true;
            int n = startI;
            while(end == true){
                Step compareStep = new Step(n-1, n, Step.COMPARE);
                steps.add(compareStep);
                Bar cur = Bars.get(n);
                Bar n1 = Bars.get(n-1);
                if(n1.getHeight() > cur.getHeight()){
                    Step swapStep = new Step(n-1, n, Step.SWAP);
                    steps.add(swapStep);
                    swapBars(n-1, n);
                    n--;
                }else{
                    end = false;
                }
                if(n == 0){
                    end = false;
                }
            }
        }
        stepCounter = steps.size();
        while(stepCounter != 0){
            stepBack();
        }
        repaint();
    }
    public void mergeSort(int minIndex, int maxIndex){
        if(minIndex == maxIndex){
            return;
        }else if((maxIndex - minIndex) == 1){
            Step s1 = new Step(minIndex, maxIndex, Step.COMPARE);
            steps.add(s1);
            Bar min = Bars.get(minIndex);
            Bar max = Bars.get(maxIndex);
            if(min.getHeight() > max.getHeight()){
                Step s2 = new Step(minIndex, maxIndex, Step.SWAP);
                steps.add(s2);
                swapBars(minIndex, maxIndex);
            }
        }
        int midIndex = (minIndex + maxIndex)/2;
        mergeSort(minIndex, midIndex);
        mergeSort(midIndex+1, maxIndex);
        int storeIndex = minIndex;
        int bottomIndex = minIndex;
        int topIndex = midIndex+1;
        while(storeIndex <= maxIndex){
            if(bottomIndex == midIndex + 1){
                recordStore(topIndex, storeIndex);
                topIndex++;
                storeIndex++;
            }else if(topIndex == maxIndex + 1){
                recordStore(bottomIndex, storeIndex);
                bottomIndex++;
                storeIndex++;
            }else{
                Step s3 = new Step(topIndex, bottomIndex, Step.COMPARE);
                steps.add(s3);
                Bar top = Bars.get(topIndex);
                Bar bottom = Bars.get(bottomIndex);
                if(top.getHeight() > bottom.getHeight()){
                    recordStore(bottomIndex, storeIndex);
                    bottomIndex++;
                    storeIndex++;
                }else{
                    recordStore(topIndex,storeIndex);
                    topIndex++;
                    storeIndex++;
                }
            }
        }
        for(int i = minIndex; i <= maxIndex; i++){
            recordSave(i, i);
        }
    }
    public void mergeSort(){
        if(Bars.size() == 0){
            return;
        }
        clearSteps();
        mergeSort(0, Bars.size()-1);
        stepCounter = steps.size();
        while(stepCounter!=0){
            stepBack();
        }
        repaint();
    }
    public void drawStepMark(Graphics g){
      if(stepCounter == -1 || stepCounter == steps.size()){
          return;
      }
      Step s = steps.get(stepCounter);
      int x1 = getBarX(s.getIndex1());
      int x2 = getBarX(s.getIndex2());
      int halfBar = this.getBarWidth()/2;
      if(s.getType() == Step.STORETOBUFFER) {
        g.setColor(Color.CYAN);
        g.fillRect(x1+ halfBar, layoutP.getHeight()-60, halfBar, 20);
        g.setColor(Color.YELLOW);
          g.fillRect(x2, layoutP.getHeight()-60, halfBar, 20);
      }else if(s.getType() == Step.SAVEFROMBUFFER){
          g.setColor(Color.YELLOW);
          g.fillRect(x1, layoutP.getHeight()-60, halfBar, 20);
          g.setColor(Color.BLUE);
          g.fillRect(x2 + halfBar, layoutP.getHeight()-60, halfBar, 20);
      }else if(s.getType() == Step.COMPARE){
          g.setColor(new Color(255, 51, 0));
      }else if(s.getType() == Step.SWAP){
          g.setColor(new Color(255, 102, 0));
      }
      g.fillRect(x1, layoutP.getHeight()-60, this.getBarWidth(),20);
      g.fillRect(x2,layoutP.getHeight()-60, this.getBarWidth(),20);
    }
    public void stepBack(){
        if(stepCounter == 0){
            return;
        }
        stepCounter--;
        Step s = steps.get(stepCounter);
        if(s.getType() == Step.SWAP){
            swapBars(s.getIndex1(),s.getIndex2());
        }else if(s.getType() == Step.STORETOBUFFER){
            saveBar(s.getIndex2(), s.getIndex1());
        }else if(s.getType() == Step.SAVEFROMBUFFER){
            storeBar(s.getIndex2(), s.getIndex1());
        }
        repaint();
    }
    public void stepForward(){
        if(stepCounter == -1 || stepCounter == steps.size()){
            return;
        }
        Step s = steps.get(stepCounter);
        if(s.getType() == Step.SWAP){
            swapBars(s.getIndex1(),s.getIndex2());
        }else if(s.getType() == Step.STORETOBUFFER){
            storeBar(s.getIndex1(), s.getIndex2());
        }else if(s.getType() == Step.SAVEFROMBUFFER){
            saveBar(s.getIndex1(), s.getIndex2());
        }
        stepCounter++;
        repaint();
    }
    public void clearSteps(){
        steps.clear();
        stepCounter = -1;
    }
    public void drawBuffer(Graphics g){
        for(int i = 0; i < MAXBARS; i++){
            Bar cur = barsBuffer[i];
            if(cur != null){
                g.setColor(getTranslucentColor(cur.getColor()));
                g.fillRect(getBarX(i), this.getHeight() - cur.getHeight()-20, this.getBarWidth(), cur.getHeight());
                g.setColor(Color.BLACK);
                g.drawRect(getBarX(i), this.getHeight() - cur.getHeight()-20, this.getBarWidth(), cur.getHeight());
            }
        }
    }
    private Color getTranslucentColor(Color c){
        Color TransColor = new Color(c.getRed(), c.getBlue(), c.getGreen(), 50);
        return TransColor;
    }
    public void paintComponent(Graphics g) {
        g.clearRect(0,0, layoutP.getHeight(), layoutP.getWidth());
        super.paintComponents(g);
        if(Bars.size() == 0){
            return;
        }
        int barWidth = getBarWidth();
        //int totalBarWidth = this.getWidth();
        int startX = getBarX(0);
        for(int i = 0; i < Bars.size(); i++){
            Bar cur = Bars.get(i);
            g.setColor(cur.getColor());
            g.fillRect(startX, this.getHeight() - cur.getHeight()-20, barWidth, cur.getHeight());
            startX = getBarX(i+1);
        }
        drawStepMark(g);
        drawBuffer(g);
    }
}
