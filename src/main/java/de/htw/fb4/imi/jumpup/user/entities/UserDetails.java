package de.htw.fb4.imi.jumpup.user.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;
import de.htw.fb4.imi.jumpup.util.Document;
import de.htw.fb4.imi.jumpup.util.EntityIdentityAdapter;
import de.htw.fb4.imi.jumpup.util.Gender;
import de.htw.fb4.imi.jumpup.util.Languages;


@Entity
@Table(name = "userdetails")
public class UserDetails extends AbstractEntity{
	
	
    @Column(name = "dateOfBirth", nullable = false, updatable = false)
	protected Date dateOfBirth;

    @Column(name = "placeOfBirth", nullable = false, updatable = false)
	protected String placeOfBirth;
	
	@XmlAttribute(name = "avatarIdentity")
	@XmlJavaTypeAdapter(EntityIdentityAdapter.class)
	@ManyToOne
	@JoinColumn(name = "avatarIdentity", nullable = true, updatable = true)
	protected volatile Document avatar;
	
    @Column(name = "languages", nullable = false, updatable = true)
	protected Languages languages;
	
    @Column(name = "gender", nullable = false, updatable = false)
	protected Gender gender;
	
    @Column(name = "mobilenumber", nullable = false, updatable = true)
	protected String mobileNumber;
	
    @Column(name = "skype", nullable = false, updatable = true)
	protected String skype;
	

}

