package com.indigital.model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Table;
/**
* @author
*
*/

@Entity
@Table(name = "SIMPLE_COMMENT", schema = "KunderaExamples@cassandra_pu")
 @XmlRootElement(name = "SimpleComment_KunderaExamples")
public class SimpleComment_KunderaExamples implements Serializable {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "SIMPLE_COMMENT_ID")
private int id;

@Column(name = "COMMENT_TEXT")
private String commentText;

@Column(name = "USER_NAME")
private String userName;

@Column(name = "Position")
private String position;

public SimpleComment_KunderaExamples() {
}

public SimpleComment_KunderaExamples(int id , String commentText, String userName) {

this.id = id;
this.commentText = commentText;
this.userName = userName;

}


public SimpleComment_KunderaExamples(int id , String position) {

this.id = id;
this.position = position;

}


/**
* @return the id
*/
 public int getId() { 
 return this.id;
 }


/**
* @param id the id to set 
*/ 
 public void setId(int id){
 this.id = id;
}

/**
* @return the commentTextid
*/
 public String getCommentText() { 
 return this.commentText;
 }


/**
* @param commentText the commentText to set 
*/ 
 public void setCommentText(String commentText){
 this.commentText = commentText;
}

/**
* @return the userNameid
*/
 public String getUserName() { 
 return this.userName;
 }


/**
* @param userName the userName to set 
*/ 
 public void setUserName(String userName){
 this.userName = userName;
}

/**
* @return the positionid
*/
 public String getPosition() { 
 return this.position;
 }


/**
* @param position the position to set 
*/ 
 public void setPosition(String position){
 this.position = position;
}

}
