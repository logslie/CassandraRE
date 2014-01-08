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
@Table(name = "steering", schema = "WirelineALISteering@cassandra_wireling")
 @XmlRootElement(name = "Steering_WirelineALISteering")
public class Steering_WirelineALISteering implements Serializable {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "steering_ID")
private int id;

@Column(name = "provider")
private String provider;

@Column(name = "provider_esn")
private String providerEsn;

@Column(name = "provider_esrn")
private String providerEsrn;

public Steering_WirelineALISteering() {
}

public Steering_WirelineALISteering(int id , String provider) {

this.id = id;
this.provider = provider;

}


public Steering_WirelineALISteering(int id , String providerEsn, String providerEsrn) {

this.id = id;
this.providerEsn = providerEsn;
this.providerEsrn = providerEsrn;

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
* @return the providerid
*/
 public String getProvider() { 
 return this.provider;
 }


/**
* @param provider the provider to set 
*/ 
 public void setProvider(String provider){
 this.provider = provider;
}

/**
* @return the providerEsnid
*/
 public String getProviderEsn() { 
 return this.providerEsn;
 }


/**
* @param providerEsn the providerEsn to set 
*/ 
 public void setProviderEsn(String providerEsn){
 this.providerEsn = providerEsn;
}

/**
* @return the providerEsrnid
*/
 public String getProviderEsrn() { 
 return this.providerEsrn;
 }


/**
* @param providerEsrn the providerEsrn to set 
*/ 
 public void setProviderEsrn(String providerEsrn){
 this.providerEsrn = providerEsrn;
}

}
