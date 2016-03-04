/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabPaneHandler;

import chatterclient.MainPage;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author a-sobhy
 */
public class CustomTabePane {
    JTabbedPane pane;
    public int index;
    JPanel panel;
    JList<String> list;
    
    public CustomTabePane(JTabbedPane pane,JList<String> list,int index,JPanel panel){
       this.pane=pane;
       this.list=list;
       this.index=index;
       this.panel=panel;
    }
    
    
    public void addTab(){
         pane.addTab(list.getSelectedValue(), panel);
        
        int indexTab=pane.indexOfTab(list.getSelectedValue());
        JPanel pnlTab = new JPanel(new GridBagLayout());
        pnlTab.setOpaque(false);
        JLabel lblTitle=new JLabel(list.getSelectedValue());
        JButton XBtn = new JButton("X");
        
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.weightx = 1;
        
        index = list.getSelectedIndex();
        System.out.println(index);
        pnlTab.add(lblTitle,grid);
        
        grid.gridx++;
        
        grid.weightx = 0;
        pnlTab.add(XBtn,grid);
        
        pane.setTabComponentAt(indexTab, pnlTab);
        
        XBtn.addActionListener(new MyCloseActionHandler(list.getSelectedValue()));
                
    }
    
     public class MyCloseActionHandler implements ActionListener {
    private String tabName;
    
    public MyCloseActionHandler(String tabName) {
        this.tabName = tabName;
    }

    public String getTabName() {
        return tabName;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        int index = pane.indexOfTab(getTabName());
        if (index >= 0) {

            pane.removeTabAt(index);
        }
    }
}   
}
