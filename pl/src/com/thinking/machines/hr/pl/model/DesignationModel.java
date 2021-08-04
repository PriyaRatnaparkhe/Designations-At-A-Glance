package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.pl.exception.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.net.*;
import com.itextpdf.text.Font;
import java.util.Date.*;
import java.text.*;
import java.io.FileOutputStream.*;

public class DesignationModel extends AbstractTableModel
{
private Object data[];
private String title[];
private JTable table;
private DesignationManagerInterface dManager;
java.util.List<DesignationInterface> list;
private void populateDataStructures() throws ModelException
{
title=new String[2];
title[0]="S.No";
title[1]="Designations";
list=dManager.getDesignations(DesignationInterface.TITLE);
data=new Object[list.size()];
for(int i=0;i<list.size();i++)
{
data[i]=list.get(i).getTitle();
}
}
public DesignationModel() throws ModelException
{
try
{
dManager=DesignationManager.getInstance();
}
catch(BLException blException)
{
throw new ModelException(blException.getGenericException());
}
populateDataStructures();
table=new JTable(data.length,title.length);
}
public int getColumnCount()
{
return title.length;
}
public int getRowCount()
{
return data.length;
}
public boolean isCellEditable(int columnIndex)
{
return false;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
if(columnIndex==1) return data[rowIndex];
return rowIndex;
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0)
{
return Integer.class;
}
else
{
return String.class;
}
}
public DesignationInterface getDesignationAt(int rowIndex) throws ModelException
{
int a=list.size();
if(rowIndex<0 || rowIndex>a) throw new ModelException("Invalid index:");
return list.get(rowIndex);
}
public void add(DesignationInterface d) throws ModelException
{
try
{
dManager.add(d);
}
catch(BLException blException)
{
throw new ModelException(blException.getGenericException());
}
populateDataStructures();
fireTableDataChanged();
}
public void update(DesignationInterface d) throws ModelException
{
try
{
dManager.update(d);
}
catch(BLException blException)
{
throw new ModelException(blException.getGenericException());
}
populateDataStructures();
fireTableDataChanged();
}
public void delete(int code) throws ModelException
{
try
{
dManager.delete(code);
}
catch(BLException blException)
{
throw new ModelException(blException.getGenericException());
}
populateDataStructures();
fireTableDataChanged();
}
public int getIndex(DesignationInterface d) throws ModelException
{
int i=0;
try
{
String text=d.getTitle();
d=dManager.getByTitle(text);
i=list.indexOf(d);
}
catch(BLException blException)
{
throw new ModelException(blException.getGenericException());
}
return i;
}
public int getCode(DesignationInterface d) throws ModelException
{
int i=0;
try
{
String text=d.getTitle();
d=dManager.getByTitle(text);
i=d.getCode();
}
catch(BLException blException)
{
throw new ModelException(blException.getGenericException());
}
return i;
}

public DesignationInterface search(String title,boolean b,boolean c)
{
if(b==true)
{
// this is for case sensitive comparision
for(int i=0;i<list.size();i++)
{
DesignationInterface d=list.get(i);
String vTitle=d.getTitle();
if(vTitle.equals(title))
{
return d;
}
}
}
if(b==false)
{
// this is for incase sensitive comparision
for(int i=0;i<list.size();i++)
{
DesignationInterface d=list.get(i);
String rTitle=d.getTitle();
if(rTitle.equalsIgnoreCase(title))
{
return d;
}
}
}
if(c==true)
{
// this is for partial comparision
for(int i=0;i<list.size();i++)
{
DesignationInterface d=list.get(i);
String vTitle=d.getTitle();
if(vTitle.startsWith(title))
{
return d;
}
}
}
if(c==false)
{
// this is for complete comparision
for(int i=0;i<list.size();i++)
{
DesignationInterface d=list.get(i);
String vTitle=d.getTitle();
if(vTitle.equalsIgnoreCase(title))
{
return d;
}
}
}
return null;
}
public void pdfMaker(String fileName)
{
int pageSize=10;
int sNo=0;
boolean newPage=true;
Document document=new Document();
try
{
PdfWriter pdf=PdfWriter.getInstance(document,new FileOutputStream(new File(fileName)));
assign a=new assign();
pdf.setPageEvent(a);
document.setMargins(120,115,120,110);
document.setMarginMirroring(false);
document.setPageSize(PageSize.A4);
Font f=new Font(Font.FontFamily.HELVETICA,14.0f,Font.BOLD,BaseColor.BLACK);
Paragraph pol=new Paragraph("S.No");
pol.setFont(f);
pol.setAlignment(Element.ALIGN_CENTER);
Paragraph poll=new Paragraph("Designations");
poll.setFont(f);
poll.setAlignment(Element.ALIGN_CENTER);
float[] columnWidth=new float[2];
columnWidth[0]=2.0f;
columnWidth[1]=7.0f;
PdfPTable pTable=new PdfPTable(columnWidth);
pTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
pTable.getDefaultCell().setFixedHeight(20);
document.open();
pTable.addCell(pol);
pTable.addCell(poll);
for(int i=0;i<list.size();i++)
{
pTable.addCell(String.valueOf(i+1));
String title=list.get(i).getTitle();
pTable.addCell(title);
}
document.add(pTable);
document.close();
}catch(DocumentException de)
{
System.out.println(de);
}catch(IOException ioException)
{
System.out.println(ioException);
}
}
}
class assign extends PdfPageEventHelper
{
private PdfTemplate template;
private com.itextpdf.text.Image total;
public void onOpenDocument(PdfWriter writer,Document document)
{
try
{
template=writer.getDirectContent().createTemplate(50,30);
total=com.itextpdf.text.Image.getInstance(template);
total.setRole(PdfName.ARTIFACT);
}catch(BadElementException be)
{
System.out.println(be);
}
}
public void onStartPage(PdfWriter writer,Document document)
{
try
{
PdfPTable list=new PdfPTable(1);
list.setTotalWidth(526);
list.setLockedWidth(true);
list.getDefaultCell().setFixedHeight(20);
list.getDefaultCell().setBorder(com.itextpdf.text.Rectangle.BOTTOM);
list.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
list.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
list.addCell(new Paragraph("List of Designations"));
PdfPTable header=new PdfPTable(3);
header.setWidths(new int[]{10,24,12});
header.setLockedWidth(true);
header.setTotalWidth(526);
header.getDefaultCell().setFixedHeight(40);
header.getDefaultCell().setBorder(com.itextpdf.text.Rectangle.BOTTOM);
header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
com.itextpdf.text.Image image=com.itextpdf.text.Image.getInstance("c:\\javaeg\\hr\\pl\\classes\\images\\Java-icon.png");
header.addCell(image);
header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
PdfPCell text=new PdfPCell();
text.setPaddingBottom(20);
text.setPaddingLeft(20);
text.setBorder(com.itextpdf.text.Rectangle.BOTTOM);
text.setBorderColor(BaseColor.LIGHT_GRAY);
Paragraph p1=new Paragraph("Designation form");
p1.setAlignment(Element.ALIGN_CENTER);
text.addElement(p1);
header.addCell(text);
header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
header.addCell(new Phrase(String.format("Page %d of 1",writer.getPageNumber())));
header.writeSelectedRows(0,-1,34,803,writer.getDirectContent());
list.writeSelectedRows(0,-1,34,760,writer.getDirectContent());
}catch(MalformedURLException mue)
{
System.out.println(mue);
}catch(IOException io)
{
System.out.println(io);
}catch(DocumentException de)
{
System.out.println(de);
}
}
public void onEndPage(PdfWriter writer,Document document)
{
PdfPTable footer=new PdfPTable(2);
footer.setTotalWidth(527);
footer.getDefaultCell().setBorder(com.itextpdf.text.Rectangle.TOP);
footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
footer.addCell(new Phrase("Software By:Priya Ratnaparkhe"));
footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
footer.addCell(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
PdfContentByte canvas=writer.getDirectContent();
canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
footer.writeSelectedRows(0,-1,34,30,canvas);
canvas.endMarkedContentSequence();
}
}
