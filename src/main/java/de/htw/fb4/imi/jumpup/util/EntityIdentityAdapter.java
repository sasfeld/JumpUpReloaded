package de.htw.fb4.imi.jumpup.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import de.htw.fb4.imi.jumpup.entity.AbstractEntity;



/**
 * Adapter class for the XML marshaling of entities to their identities. Note that the marshaling
 * is one-way, i.e. the resulting values cannot be mapped back into an entity as we lack access to
 * an active entity manager!
 */

public class EntityIdentityAdapter extends XmlAdapter<Long,AbstractEntity> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long marshal (final AbstractEntity entity) {
		return entity == null ? null : entity.getIdentity();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractEntity unmarshal (final Long identity) {
		return null;
	}
}