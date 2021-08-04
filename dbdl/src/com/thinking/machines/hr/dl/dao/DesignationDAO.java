package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.*; 
import com.thinking.machines.hr.dl.exceptions.*; 
import java.util.*; 
import java.sql.*;

public class DesignationDAO implements DesignationDAOInterface
{ 

public void add(DesignationDTOInterface designationDTO) throws DAOException
{
String title=designationDTO.getTitle();
if(title.trim().length()==0)
{
throw new DAOException("Length of title cannot be zero");
}
if(title.trim().length()>35)
{
throw new DAOException("Length of title cannot be greater than 35");
}
try
{
Connection connection=null;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement =connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Title exists:"+title);
}
preparedStatement=connection.prepareStatement("insert into designation(title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
designationDTO.setCode(code);
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

public void update(DesignationDTOInterface designationDTO) throws DAOException
{
String title=designationDTO.getTitle();
int code=designationDTO.getCode();
try
{
if(code==0)
{
throw new DAOException("code cannot be zero");
}
if(code<0)
{
throw new DAOException("Code cannot be a negative number");
}
if(title.trim().length()==0)
{
throw new DAOException("Length of title cannot be zero");
}
if(title.trim().length()>35)
{
throw new DAOException("Length of title cannot be greater than 35");
}
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : "+code);
}
preparedStatement=connection.prepareStatement("select * from designation where title= ? and code !=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
int vCode=resultSet.getInt("code");
String vTitle=resultSet.getString("title").trim();
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException(vTitle+" exists against code "+vCode);
}
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
DesignationDTOInterface desigDTO;
desigDTO=new DesignationDTO();
desigDTO.setTitle(title);
desigDTO.setCode(code);
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

public void delete(int code) throws DAOException
{
try
{
if(code==0)
{
throw new  DAOException("Designation code cannot be zero");
}
if(code<0)
{
throw new DAOException("Code cannot be a negative number");
}
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
DesignationDTOInterface designationDTO=new DesignationDTO();
if(resultSet.next()==true)
{
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}
else
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code:"+code);
}
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

public DesignationDTOInterface getByCode(int code) throws DAOException
{
try
{
if(code==0)
{
throw new DAOException("code cannot be zero");
}
if(code<0)
{
throw new DAOException("Code cannot be a negative number");
}
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
DesignationDTOInterface designationDTO=new DesignationDTO();
if(resultSet.next()==true)
{
int vCode=resultSet.getInt("code");
String vTitle=resultSet.getString("title").trim();
designationDTO.setCode(vCode);
designationDTO.setTitle(vTitle);
}
else
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code:"+code);
}
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title.trim().length()==0)
{
throw new DAOException("Length of title cannot be zero");
}
if(title.trim().length()>35)
{
throw new DAOException("Length of title cannot be greater than 35");
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
DesignationDTOInterface designationDTO=new DesignationDTO();
if(resultSet.next()==true)
{
int code=resultSet.getInt("code");
String vTitle=resultSet.getString("title").trim();
designationDTO.setCode(code);
designationDTO.setTitle(vTitle);
}
else
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid title:"+title);
}
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

public List<DesignationDTOInterface> getAll() throws DAOException
{
try
{
List<DesignationDTOInterface> designationList=new LinkedList<>();
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation");
int code=0;
String title=new String();
while(resultSet.next())
{
DesignationDTOInterface designationDTO=new DesignationDTO();
code=resultSet.getInt("code");
title=resultSet.getString("title").trim();
designationDTO.setCode(code);
designationDTO.setTitle(title);
designationList.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
return designationList;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

public int getCount() throws DAOException
{
try
{
int count=0;
Connection connection;
connection=DAOConnection.getConnection();
Statement statement ;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as designation_count from designation"); 
resultSet.next();
count=resultSet.getInt("designation_count");
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean exists(int code) throws DAOException
{
try
{
if(code==0)
{
throw new DAOException("Code cannot be zero");
}
if(code<0)
{
throw new DAOException("Code cannot be a negative number");
}
boolean answer=false;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
answer=true;
}
resultSet.close();
preparedStatement.close();
connection.close();
return answer;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

public boolean exists(String title) throws DAOException
{
if(title.trim().length()==0)
{
throw new DAOException("Length of title cannot be 0");
}
if(title.trim().length()>35)
{
throw new DAOException("Length of title cannot be greater than 35");
}
try
{
boolean answer=false;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
answer=true;
}
resultSet.close();
preparedStatement.close();
connection.close();
return answer;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}
} 