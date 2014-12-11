package de.htw.fb4.imi.jumpup.user.entities;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;
import de.htw.fb4.imi.jumpup.util.Gender;
import de.htw.fb4.imi.jumpup.util.Languages;

@Entity
@Table(name = "userdetails")
public class UserDetails extends AbstractEntity
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -4235428005961187774L;

    @OneToOne
    protected User user;

    @Column(name = "dateOfBirth", nullable = false, updatable = true)
    protected Date dateOfBirth;

    @Column(name = "placeOfBirth", nullable = false, updatable = true)
    protected String placeOfBirth;

    // @XmlAttribute(name = "avatarIdentity")
    // @XmlJavaTypeAdapter(EntityIdentityAdapter.class)
    // @ManyToOne
    // @JoinColumn(name = "avatarIdentity", nullable = true, updatable = true)
    // protected volatile Document avatar;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    protected Byte[] avatar;

    @Column(name = "languages", nullable = false, updatable = true)
    protected Languages languages;

    @Column(name = "gender", nullable = false, updatable = true)
    protected Gender gender;

    @Column(name = "mobilenumber", nullable = false, updatable = true)
    protected String mobileNumber;

    @Column(name = "skype", nullable = false, updatable = true)
    protected String skype;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth()
    {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth)
    {
        this.placeOfBirth = placeOfBirth;
    }

    public Byte[] getAvatar()
    {
        return avatar;
    }

    public void setAvatar(Byte[] avatar)
    {
        this.avatar = avatar;
    }

    public Languages getLanguages()
    {
        return languages;
    }

    public void setLanguages(Languages languages)
    {
        this.languages = languages;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getSkype()
    {
        return skype;
    }

    public void setSkype(String skype)
    {
        this.skype = skype;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(avatar);
        result = prime * result
                + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result
                + ((languages == null) ? 0 : languages.hashCode());
        result = prime * result
                + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
        result = prime * result
                + ((placeOfBirth == null) ? 0 : placeOfBirth.hashCode());
        result = prime * result + ((skype == null) ? 0 : skype.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDetails other = (UserDetails) obj;
        if (!Arrays.equals(avatar, other.avatar))
            return false;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        if (gender != other.gender)
            return false;
        if (languages != other.languages)
            return false;
        if (mobileNumber == null) {
            if (other.mobileNumber != null)
                return false;
        } else if (!mobileNumber.equals(other.mobileNumber))
            return false;
        if (placeOfBirth == null) {
            if (other.placeOfBirth != null)
                return false;
        } else if (!placeOfBirth.equals(other.placeOfBirth))
            return false;
        if (skype == null) {
            if (other.skype != null)
                return false;
        } else if (!skype.equals(other.skype))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
