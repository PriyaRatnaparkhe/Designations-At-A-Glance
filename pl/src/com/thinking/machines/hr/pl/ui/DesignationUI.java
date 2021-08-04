package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.exception.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.pl.model.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.*;
import java.awt.Color.*;
import javax.swing.border.*;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
public class DesignationUI extends JFrame implements DocumentListener
{
private int flag=0;
private DesignationModel designationModel;
private Container container;
private JLabel titleLabel;
private JLabel searchNotFoundLabel;
private JLabel searchLabel;
private JTextField searchTextEdit;
private JButton cancelButton;
private JTable employeeTable;
private JPanel showPanel;
private JLabel designationLabel;
private JLabel designationSearchLabel;
private JTextField designationSearchTextField;
private JPanel secondaryPanel;
private JButton aButton;
private JButton eButton;
private JButton cButton;
private JButton dButton;
private JButton pButton;
private JScrollPane jScrollPane;
public DesignationUI()
{
flag=0;
container=getContentPane();
titleLabel=new JLabel("DesignationMaster");
Font font=new Font("Times New Roman",Font.BOLD,24);
titleLabel.setFont(font);
searchNotFoundLabel=new JLabel("");
searchLabel=new JLabel("Search");
Font searchFont=new Font("Times New Roman",Font.BOLD,12);
searchLabel.setFont(searchFont);
searchTextEdit=new JTextField();
searchTextEdit.getDocument().addDocumentListener(this);
cancelButton=new JButton(new ImageIcon(getClass().getResource("/images/Button-Close-icon.png")));
try
{
designationModel=new DesignationModel();
employeeTable=new JTable(designationModel);
}catch(ModelException me)
{
JFrame f;
f=new JFrame();
JOptionPane.showMessageDialog(f,me.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
}
designationLabel=new JLabel("Designation");
designationSearchLabel=new JLabel("");
designationSearchTextField=new JTextField();
designationSearchTextField.setText("");
designationSearchTextField.setVisible(false);
secondaryPanel=new JPanel();
aButton=new JButton(new ImageIcon(getClass().getResource("/images/addUser.png")));
eButton=new JButton(new ImageIcon(getClass().getResource("/images/updateUser.png")));
cButton=new JButton(new ImageIcon(getClass().getResource("/images/cancelUser.png")));
dButton=new JButton(new ImageIcon(getClass().getResource("/images/deleteUser.png")));
pButton=new JButton(new ImageIcon(getClass().getResource("/images/pdfUser.png")));
employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
public void valueChanged(ListSelectionEvent e)
{
try
{
searchNotFoundLabel.setText("Found");
DesignationInterface d=new Designation();
int p;
p=employeeTable.getSelectedRow();
d=designationModel.getDesignationAt(p);
String title=d.getTitle();
designationSearchLabel.setEnabled(true);
designationSearchLabel.setText(title);

designationSearchLabel.setVisible(true);
}catch(ModelException me)
{
System.out.println(me.getMessage());
}
}
});
cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextEdit.setText("");
searchNotFoundLabel.setText("");
employeeTable.clearSelection();
}
});
showPanel=new JPanel();
employeeTable.getTableHeader().setFont(new Font("Times new Roman",Font.BOLD,13));
employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
DefaultTableCellRenderer center= new DefaultTableCellRenderer();
center.setHorizontalAlignment(JLabel.CENTER);
employeeTable.getColumnModel().getColumn(0).setCellRenderer(center);
employeeTable.getColumnModel().getColumn(1).setCellRenderer(center);
employeeTable.getTableHeader().setResizingAllowed(false);
setTitle("HR AUTOMATION SYSTEM");
container.setLayout(null);
showPanel.setLayout(null);
secondaryPanel.setLayout(null);
jScrollPane=new JScrollPane(employeeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
int height=500;
int width=600;
int lb=10;
int ub=0;
titleLabel.setBounds(lb+20,ub+20,200,30);
searchNotFoundLabel.setBounds(lb+280,ub+20+30+10+10+20,70,20);
searchLabel.setBounds(lb+20,ub+20+30+10+10+30,50,40);
searchTextEdit.setBounds(lb+10+20+20+50,ub+20+30+10+10+10+30,250,20);
cancelButton.setBounds(lb+370,ub+20+30+10+10+10+10+10+10,60,20);
jScrollPane.setBounds(lb+20+50+10+20,ub+20+30+10+10+10+10+20+10+40,300,120);
aButton.setBounds(lb+10,ub+35,50,30);
eButton.setBounds(lb+10+20+50,ub+35,50,30);
cButton.setBounds(lb+10+20+50+20+50,ub+35,50,30);
dButton.setBounds(lb+10+20+50+20+50+20+50,ub+35,50,30);
pButton.setBounds(lb+10+20+50+20+50+20+50+50+20,ub+35,50,30);
secondaryPanel.setBounds(lb+5,ub+20+50+60,370,100);
designationLabel.setBounds(lb+20,ub+20,80,20);
designationSearchLabel.setBounds(lb+20+50+50,ub+20,250,20);
designationSearchTextField.setBounds(lb+20+50+50,ub+20,250,20);
showPanel.setBounds(lb+20+30,ub+20+30+10+10+10+10+20+10+40+150+30+20,400,250);
if(employeeTable.getRowCount()==0)
{
aButton.setEnabled(true);
eButton.setEnabled(true);
cButton.setEnabled(true);
dButton.setEnabled(true);
pButton.setEnabled(true);
}
if(employeeTable.getRowCount()!=0)
{
aButton.setEnabled(true);
eButton.setEnabled(true);
cButton.setEnabled(false);
dButton.setEnabled(true);
pButton.setEnabled(true);
}
aButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(flag==2)
{
try
{
String text=designationSearchTextField.getText();
DesignationInterface d=new Designation();
d.setTitle(text);
designationModel.add(d);
int a=designationModel.getIndex(d);
employeeTable.setRowSelectionInterval(a,a);
int rowIndex=a;
int columnIndex=0;
boolean includeSpacing=true;
Rectangle cellRect=employeeTable.getCellRect(rowIndex,columnIndex,includeSpacing);
employeeTable.scrollRectToVisible(cellRect);
designationSearchTextField.setText("");
}catch(ModelException me)
{
JFrame f;
f=new JFrame();
JOptionPane.showMessageDialog(f,me.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);     
}
}
else
{
flag=2;
employeeTable.setEnabled(false);
jScrollPane.setEnabled(false);
searchNotFoundLabel.setEnabled(false);
searchLabel.setEnabled(false);
searchTextEdit.setEnabled(false);
cancelButton.setEnabled(false);
eButton.setEnabled(false);
dButton.setEnabled(false);
pButton.setEnabled(false);
cButton.setEnabled(true);
aButton.setIcon(new ImageIcon(getClass().getResource("/images/saveUser.png")));
aButton.setEnabled(true);
searchNotFoundLabel.setText("");
searchNotFoundLabel.setVisible(false);
searchTextEdit.setText("");
designationSearchLabel.setEnabled(false);
designationSearchLabel.setVisible(false);
designationSearchTextField.setVisible(true);
designationSearchTextField.setText("");
designationSearchTextField.setEnabled(true);
}
}
});
eButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(flag==4)
{
try
{
designationSearchLabel.setEnabled(false);
designationSearchLabel.setVisible(false);
designationSearchLabel.setText("");
int a=employeeTable.getSelectedRow();
DesignationInterface s=designationModel.getDesignationAt(a);
int c=s.getCode();
String newText=designationSearchTextField.getText();
if(newText.trim().length()==0)
{
JFrame frame=new JFrame();
JOptionPane.showMessageDialog(frame,"Designation field cannot be empty","Warning",JOptionPane.WARNING_MESSAGE);
return;
}
DesignationInterface desig=new Designation();
desig.setCode(c);
desig.setTitle(newText);
designationModel.update(desig);
int aa=designationModel.getIndex(desig);
employeeTable.setRowSelectionInterval(aa,aa);
int rowIndex=aa;
int columnIndex=0;
boolean includeSpacing=true;
Rectangle cellRect=employeeTable.getCellRect(rowIndex,columnIndex,includeSpacing);
employeeTable.scrollRectToVisible(cellRect);
designationSearchTextField.setText("");
}catch(ModelException me)
{
JFrame f=new JFrame();
JOptionPane.showMessageDialog(f,me.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
}
}
else
{
if(employeeTable.getSelectionModel().isSelectionEmpty())
{
JFrame f;
f=new JFrame();
JOptionPane.showMessageDialog(f,"Please select a record","Warning",JOptionPane.WARNING_MESSAGE);     
return;
}
try
{
designationSearchTextField.setText("");
employeeTable.setEnabled(false);
jScrollPane.setEnabled(false);
searchNotFoundLabel.setVisible(false);
searchNotFoundLabel.setText("");
searchNotFoundLabel.setEnabled(false);
searchLabel.setEnabled(false);
searchTextEdit.setEnabled(false);
cancelButton.setEnabled(false);
aButton.setEnabled(false);
eButton.setEnabled(true);
dButton.setEnabled(false);
pButton.setEnabled(false);
cButton.setEnabled(true);
int a=employeeTable.getSelectedRow();
DesignationInterface s=designationModel.getDesignationAt(a);
String title=s.getTitle();
designationSearchLabel.setEnabled(false);
designationSearchLabel.setVisible(false);
designationSearchTextField.setText(title);
designationSearchTextField.setVisible(true);
designationSearchTextField.setEnabled(true);
String text=designationSearchTextField.getText();
DesignationInterface d=new Designation();
d.setTitle(text);
int c=designationModel.getCode(d);
eButton.setIcon(new ImageIcon(getClass().getResource("/images/editUser.png")));
flag=4;
JFrame f=new JFrame();
JOptionPane.showMessageDialog(f,"Do you want to update the designation "+title+":"+c,"Warning",JOptionPane.WARNING_MESSAGE);
return;
}catch(ModelException me)
{
JFrame f=new JFrame();
JOptionPane.showMessageDialog(f,me.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
}
}
}
});
dButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(employeeTable.getSelectionModel().isSelectionEmpty())
{
JFrame f;
f=new JFrame();
JOptionPane.showMessageDialog(f,"Please select a record","Warning",JOptionPane.WARNING_MESSAGE);     
return;
}
try
{
employeeTable.setEnabled(false);
jScrollPane.setEnabled(false);
searchNotFoundLabel.setVisible(false);
searchNotFoundLabel.setText("");
searchNotFoundLabel.setEnabled(false);
searchLabel.setEnabled(false);
searchTextEdit.setEnabled(false);
cancelButton.setEnabled(false);
aButton.setEnabled(false);
eButton.setEnabled(false);
dButton.setEnabled(true);
pButton.setEnabled(false);
cButton.setEnabled(true);
int a=employeeTable.getSelectedRow();
DesignationInterface s=designationModel.getDesignationAt(a);
String title=s.getTitle();
designationSearchLabel.setEnabled(false);
designationSearchLabel.setVisible(false);
designationSearchTextField.setText(title);
designationSearchTextField.setVisible(true);
designationSearchTextField.setEnabled(true);
String text=designationSearchTextField.getText();
DesignationInterface d=new Designation();
d.setTitle(text);
int c=designationModel.getCode(d);
JFrame f=new JFrame();
int sel=JOptionPane.showConfirmDialog(f,"Are you sure you want to delete designation with code as:"+c);
if(sel==JOptionPane.YES_OPTION)
{
designationModel.delete(c);
}
if(sel==JOptionPane.NO_OPTION)
{
searchNotFoundLabel.setEnabled(true);
searchLabel.setEnabled(true);
searchTextEdit.setEnabled(true);
cancelButton.setEnabled(true);
jScrollPane.setEnabled(true);
designationLabel.setEnabled(true);
designationSearchLabel.setText("");
designationSearchLabel.setEnabled(true);
designationSearchLabel.setVisible(true);
designationSearchTextField.setEnabled(false);
designationSearchTextField.setVisible(false);
aButton.setEnabled(true);
eButton.setEnabled(true);
dButton.setEnabled(true);
pButton.setEnabled(true);
cButton.setEnabled(false);
}
if(sel==JOptionPane.CANCEL_OPTION)
{
searchNotFoundLabel.setEnabled(true);
searchLabel.setEnabled(true);
searchTextEdit.setEnabled(true);
cancelButton.setEnabled(true);
jScrollPane.setEnabled(true);
designationLabel.setEnabled(true);
designationSearchLabel.setText("");
designationSearchLabel.setEnabled(true);
designationSearchLabel.setVisible(true);
designationSearchTextField.setEnabled(false);
designationSearchTextField.setText("");
designationSearchTextField.setVisible(false);
aButton.setEnabled(true);
eButton.setEnabled(true);
dButton.setEnabled(true);
pButton.setEnabled(true);
cButton.setEnabled(false);
}
}catch(ModelException me)
{
System.out.println(me.getMessage());
}
}
});
pButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser fc=new JFileChooser();
int returnValue=fc.showSaveDialog(null);
if(returnValue==JFileChooser.APPROVE_OPTION)
{
File file=fc.getSelectedFile();
String fileName=file.getName();
String pathName=file.getAbsolutePath();
if(!(pathName.endsWith(".pdf")))
{
pathName=pathName+".pdf";
}
String path=pathName.substring(0,pathName.lastIndexOf("\\"));
File file1=new File(path);
if(file1.isDirectory()==false)
{
JOptionPane.showMessageDialog(null,"Invalid Path","Error",JOptionPane.ERROR_MESSAGE);
}
else
{
if(file.exists())
{
int yN=JOptionPane.showConfirmDialog(null,"The File Already Exists Do You Want to Override ?","Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
if(yN==JOptionPane.YES_OPTION)
{
if(fileName.endsWith(".pdf")==false)
{
fileName=fileName+".pdf";
}
designationModel.pdfMaker(fileName);
JOptionPane.showMessageDialog(null,"PDF created in"+path,"Information",JOptionPane.INFORMATION_MESSAGE);
}
}
else
{
designationModel.pdfMaker(fileName);
JOptionPane.showMessageDialog(null,"PDF created in"+path,"Information",JOptionPane.INFORMATION_MESSAGE);
}
}
}
}
});
cButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
flag=0;
searchTextEdit.setEnabled(true);
searchNotFoundLabel.setText("");
searchNotFoundLabel.setVisible(false);
searchLabel.setEnabled(true);
cancelButton.setEnabled(true);
jScrollPane.setEnabled(true);
designationLabel.setEnabled(true);
employeeTable.setEnabled(true);
designationSearchLabel.setText("");
designationSearchTextField.setVisible(false);
designationSearchLabel.setVisible(true);
aButton.setEnabled(true);
eButton.setEnabled(true);
dButton.setEnabled(true);
pButton.setEnabled(true);
cButton.setEnabled(false);
aButton.setIcon(new ImageIcon(getClass().getResource("/images/addUser.png")));
employeeTable.clearSelection();
}
});
secondaryPanel.add(aButton);
secondaryPanel.add(eButton);
secondaryPanel.add(cButton);
secondaryPanel.add(dButton);
secondaryPanel.add(pButton);
showPanel.add(designationLabel);
showPanel.add(designationSearchLabel);
showPanel.add(designationSearchTextField);
showPanel.add(secondaryPanel);
Border border = new LineBorder(Color.orange,2, true);
secondaryPanel.setBorder(border);
Border greyLine =new LineBorder(Color.red,3,true);
showPanel.setBorder(greyLine);
container.add(titleLabel);
container.add(searchNotFoundLabel);
container.add(searchLabel);
container.add(searchTextEdit);
container.add(cancelButton);
container.add(jScrollPane);
container.add(showPanel);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setSize(width,height);
Dimension dimmension=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((dimmension.width/2)-(width/2),(dimmension.height/2)-(height/2));
}
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void searchDesignation()
{
String text=searchTextEdit.getText();
DesignationInterface d=designationModel.search(text,true,false);
if(d!=null)
{
try
{
searchNotFoundLabel.setEnabled(true);
int a=designationModel.getIndex(d);
employeeTable.setRowSelectionInterval(a,a);
int row=0;
int column=0;
boolean includeSpace=true;
Rectangle cellRect=employeeTable.getCellRect(row,column,includeSpace);
employeeTable.scrollRectToVisible(cellRect);
searchNotFoundLabel.setText("Found");
Font foundFont=new Font("Times New Roman",Font.PLAIN,12);
searchNotFoundLabel.setFont(foundFont);
searchNotFoundLabel.setForeground(Color.BLACK);
searchNotFoundLabel.setVisible(true);
}catch(ModelException me)
{
JFrame f;
f=new JFrame();
JOptionPane.showMessageDialog(f,me.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
}
}
else
{
searchNotFoundLabel.setText("");
searchNotFoundLabel.setText("Not Found");
searchNotFoundLabel.setVisible(true);
Font cancelFont=new Font("Times new roman",Font.PLAIN,12);
searchNotFoundLabel.setFont(cancelFont);
searchNotFoundLabel.setForeground(Color.RED);
designationSearchLabel.setText("");
employeeTable.clearSelection();
}
}
}