import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class LayoutPanel extends JPanel implements ActionListener {
	
	// after creating BarPanel, uncomment out the three lines
	
	private OptionsPanel optionP;
	private BarPanel barP;
	
	public LayoutPanel() {
		this.setLayout(new BorderLayout());
		
		optionP = new OptionsPanel(this);
		this.add(optionP, BorderLayout.NORTH);

		barP = new BarPanel(this);
		this.add(barP, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(optionP.isAutorunOn()){
			barP.stepForward();
		}
	}
	public void performOption(OptionEvent type) {
		if(type.equals(OptionEvent.ADD)){
			barP.addBar();
		}else if(type.equals(OptionEvent.RANDOMIZE)){
		    barP.randomize();
        }else if(type.equals(OptionEvent.SORT)){
			String choice = optionP.getSortChoice();
			if(choice.equals("Bubble sort")){
				barP.bubbleSort();
			}else if(choice.equals("Selection sort")){
				barP.selectionSort();
			}else if(choice.equals("Insertion sort")){
				barP.insertionSort();
			}else if(choice.equals("Merge sort")){
				barP.mergeSort();
			}
		}else if(type.equals(OptionEvent.STEP)){
			barP.stepForward();
		}
	}
}
